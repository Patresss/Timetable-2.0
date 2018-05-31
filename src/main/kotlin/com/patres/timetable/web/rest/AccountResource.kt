package com.patres.timetable.web.rest

import com.codahale.metrics.annotation.Timed
import com.patres.timetable.repository.UserRepository
import com.patres.timetable.security.SecurityUtils
import com.patres.timetable.service.MailService
import com.patres.timetable.service.UserService
import com.patres.timetable.service.dto.UserDTO
import com.patres.timetable.service.mapper.UserMapper
import com.patres.timetable.web.rest.util.HeaderUtil
import com.patres.timetable.web.rest.vm.KeyAndPasswordVM
import com.patres.timetable.web.rest.vm.ManagedUserVM
import org.apache.commons.lang3.StringUtils
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
open class AccountResource(private val userRepository: UserRepository, private val userService: UserService,
                      private val userMapper: UserMapper, private val mailService: MailService) {

    private val log = LoggerFactory.getLogger(AccountResource::class.java)

    /**
     * GET  /account : get the current user.
     *
     * @return the ResponseEntity with status 200 (OK) and the current user in body, or status 500 (Internal Server Error) if the user couldn't be returned
     */
    @GetMapping("/account")
    @Timed
    open fun getAccount(): ResponseEntity<UserDTO> {
        val userWithAuthorities = userService.getUserWithAuthorities()
        return if(userWithAuthorities != null) {
            ResponseEntity(userMapper.toDto(userWithAuthorities), HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    /**
     * POST  /register : register the user.
     *
     * @param managedUserVM the managed user View Model
     * @return the ResponseEntity with status 201 (Created) if the user is registered or 400 (Bad Request) if the login or email is already in use
     */
    @PostMapping(path = arrayOf("/register"), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE))
    @Timed
    open fun registerAccount(@Valid @RequestBody managedUserVM: ManagedUserVM): ResponseEntity<*> {
        val textPlainHeaders = HttpHeaders()
        textPlainHeaders.contentType = MediaType.TEXT_PLAIN
        return if (!checkPasswordLength(managedUserVM.password)) {
            ResponseEntity(CHECK_ERROR_MESSAGE, HttpStatus.BAD_REQUEST)
        } else {
            val userFromRepositoryByLogin = userRepository.findOneByLogin(managedUserVM.login!!.toLowerCase())
            if (userFromRepositoryByLogin != null) {
                return ResponseEntity("login already in use", textPlainHeaders, HttpStatus.BAD_REQUEST)
            } else {
                val userFromRepositoryByEmail = userRepository.findOneByEmail(managedUserVM.email!!)
                if (userFromRepositoryByEmail != null) {
                    return ResponseEntity("email address already in use", textPlainHeaders, HttpStatus.BAD_REQUEST);
                } else {

                    val user = userService
                        .createUser(managedUserVM.login!!, managedUserVM.password!!,
                            managedUserVM.firstName, managedUserVM.lastName,
                            managedUserVM.email!!.toLowerCase(), managedUserVM.imageUrl,
                            managedUserVM.langKey, managedUserVM.schoolId, managedUserVM.teacherId)
                    mailService.sendActivationEmail(user)
                    return ResponseEntity<ManagedUserVM>(HttpStatus.CREATED)
                }

            }

        }
    }

    /**
     * GET  /activate : activate the registered user.
     *
     * @param key the activation key
     * @return the ResponseEntity with status 200 (OK) and the activated user in body, or status 500 (Internal Server Error) if the user couldn't be activated
     */
    @GetMapping("/activate")
    @Timed
    open fun activateAccount(@RequestParam(value = "key") key: String): ResponseEntity<String> {
        val user = userService.activateRegistration(key)
        return if (user == null) ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR) else ResponseEntity(HttpStatus.OK)
    }

    /**
     * GET  /authenticate : check if the user is authenticated, and return its login.
     *
     * @param request the HTTP request
     * @return the login if the user is authenticated
     */
    @GetMapping("/authenticate")
    @Timed
    open fun isAuthenticated(request: HttpServletRequest): String? {
        log.debug("REST request to check if the current user is authenticated")
        return request.remoteUser
    }

    /**
     * POST  /account : update the current user information.
     *
     * @param userDTO the current user information
     * @return the ResponseEntity with status 200 (OK), or status 400 (Bad Request) or 500 (Internal Server Error) if the user couldn't be updated
     */
    @PostMapping("/account")
    @Timed
    open fun saveAccount(@Valid @RequestBody userDTO: UserDTO): ResponseEntity<*> {
        val userLogin = SecurityUtils.getCurrentUserLogin()
        if(userLogin != null) {
            val existingUser = userRepository.findOneByEmail(userDTO.email)
            return if (existingUser != null && !existingUser.login.equals(userLogin, ignoreCase = true)) {
                ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("user-management", "emailexists", "Email already in use")).body<Any>(null)
            } else {
                val userFromRepository = userRepository.findOneByLogin(userLogin)
                return if (userFromRepository != null) {
                    userService.updateUser(userDTO.firstName, userDTO.lastName, userDTO.email, userDTO.langKey, userDTO.imageUrl)
                    ResponseEntity<UserDTO>(HttpStatus.OK)
                } else {
                    ResponseEntity<UserDTO>(HttpStatus.INTERNAL_SERVER_ERROR)
                }
            }
        } else {
            return ResponseEntity<UserDTO>(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    /**
     * POST  /account/change-password : changes the current user's password
     *
     * @param password the new password
     * @return the ResponseEntity with status 200 (OK), or status 400 (Bad Request) if the new password is not strong enough
     */
    @PostMapping(path = arrayOf("/account/change-password"), produces = arrayOf(MediaType.TEXT_PLAIN_VALUE))
    @Timed
    open fun changePassword(@RequestBody password: String): ResponseEntity<*> {
        if (!checkPasswordLength(password)) {
            return ResponseEntity(CHECK_ERROR_MESSAGE, HttpStatus.BAD_REQUEST)
        }
        userService.changePassword(password)
        return ResponseEntity<Any>(HttpStatus.OK)
    }

    /**
     * POST   /account/reset-password/init : Send an email to reset the password of the user
     *
     * @param mail the mail of the user
     * @return the ResponseEntity with status 200 (OK) if the email was sent, or status 400 (Bad Request) if the email address is not registered
     */
    @PostMapping(path = arrayOf("/account/reset-password/init"), produces = arrayOf(MediaType.TEXT_PLAIN_VALUE))
    @Timed
    open fun requestPasswordReset(@RequestBody mail: String): ResponseEntity<*> {
        val userFromRepository = userService.requestPasswordReset(mail)
        if(userFromRepository != null) {
            mailService.sendPasswordResetMail(userFromRepository)
            return ResponseEntity("email was sent", HttpStatus.OK)
        } else {
            return ResponseEntity("email address not registered", HttpStatus.BAD_REQUEST)
        }
    }

    /**
     * POST   /account/reset-password/finish : Finish to reset the password of the user
     *
     * @param keyAndPassword the generated key and the new password
     * @return the ResponseEntity with status 200 (OK) if the password has been reset,
     * or status 400 (Bad Request) or 500 (Internal Server Error) if the password could not be reset
     */
    @PostMapping(path = arrayOf("/account/reset-password/finish"), produces = arrayOf(MediaType.TEXT_PLAIN_VALUE))
    @Timed
    open fun finishPasswordReset(@RequestBody keyAndPassword: KeyAndPasswordVM): ResponseEntity<String> {
        return if (!checkPasswordLength(keyAndPassword.newPassword)) {
            ResponseEntity(CHECK_ERROR_MESSAGE, HttpStatus.BAD_REQUEST)
        } else {
            val user = userService.completePasswordReset(keyAndPassword.newPassword, keyAndPassword.key)
            if(user != null ) {
                return ResponseEntity(HttpStatus.OK)
            } else {
                return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
            }
        }
    }

    private fun checkPasswordLength(password: String?): Boolean {
        return !StringUtils.isEmpty(password) &&
            password!!.length >= ManagedUserVM.PASSWORD_MIN_LENGTH &&
            password.length <= ManagedUserVM.PASSWORD_MAX_LENGTH
    }

    companion object {
        private val CHECK_ERROR_MESSAGE = "Incorrect password"
    }
}
