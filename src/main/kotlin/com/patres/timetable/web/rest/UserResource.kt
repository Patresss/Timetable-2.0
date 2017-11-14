package com.patres.timetable.web.rest

import com.codahale.metrics.annotation.Timed
import com.patres.timetable.config.Constants
import com.patres.timetable.repository.UserRepository
import com.patres.timetable.security.AuthoritiesConstants
import com.patres.timetable.service.MailService
import com.patres.timetable.service.UserService
import com.patres.timetable.service.dto.UserDTO
import com.patres.timetable.web.rest.util.HeaderUtil
import com.patres.timetable.web.rest.util.PaginationUtil
import com.patres.timetable.web.rest.vm.ManagedUserVM
import io.github.jhipster.web.util.ResponseUtil
import io.swagger.annotations.ApiParam
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.net.URISyntaxException
import java.util.*
import javax.validation.Valid


@RestController
@RequestMapping("/api")
open class UserResource(private val userRepository: UserRepository, private val mailService: MailService, private val userService: UserService) {

    companion object {
        private val ENTITY_NAME = "userManagement"
        private val log = LoggerFactory.getLogger(UserResource::class.java)
    }


    /**
     * @return a string list of the all of the roles
     */
    @GetMapping("/users/authorities")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    open fun getAuthorities(): List<String> {
        return userService.getAuthorities()
    }

    /**
     * POST  /users  : Creates a new user.
     *
     *
     * Creates a new user if the login and email are not already used, and sends an
     * mail with an activation link.
     * The user needs to be activated on creation.
     *
     * @param managedUserVM the user to create
     * @return the ResponseEntity with status 201 (Created) and with body the new user, or with status 400 (Bad Request) if the login or email is already in use
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/users")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    @Throws(URISyntaxException::class)
    open fun createUser(@Valid @RequestBody managedUserVM: ManagedUserVM): ResponseEntity<*> {
        log.debug("REST request to save User : {}", managedUserVM)
        if (managedUserVM.id != null) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new user cannot already have an ID"))
                .body<Any>(null)
            // Lowercase the user login before comparing with database
        } else if (managedUserVM.login != null && userRepository.findOneByLogin(managedUserVM.login!!.toLowerCase()) != null) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "userexists", "Login already in use"))
                .body<Any>(null)
        } else if (managedUserVM.email != null && userRepository.findOneByEmail(managedUserVM.email!!) != null) { //TODO
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "emailexists", "Email already in use"))
                .body<Any>(null)
        } else {
            val newUser = userService.createUser(managedUserVM)
            mailService.sendCreationEmail(newUser)
            return ResponseEntity.created(URI("/api/users/" + newUser.login!!))
                .headers(HeaderUtil.createAlert("userManagement.created", newUser.login))
                .body(newUser)
        }
    }

    /**
     * PUT  /users : Updates an existing User.
     *
     * @param managedUserVM the user to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated user,
     * or with status 400 (Bad Request) if the login or email is already in use,
     * or with status 500 (Internal Server Error) if the user couldn't be updated
     */
    @PutMapping("/users")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    open fun updateUser(@Valid @RequestBody managedUserVM: ManagedUserVM): ResponseEntity<UserDTO> {
        log.debug("REST request to update User : {}", managedUserVM)
        var existingUser = userRepository.findOneByEmail(managedUserVM.email!!)
        if (existingUser != null && existingUser.id != managedUserVM.id) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "emailexists", "Email already in use")).body(null)
        }
        existingUser = userRepository.findOneByLogin(managedUserVM.login!!.toLowerCase())
        if (existingUser != null && existingUser.id != managedUserVM.id) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "userexists", "Login already in use")).body(null)
        }
        val updatedUser = userService.updateUser(managedUserVM)

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(updatedUser),
            HeaderUtil.createAlert("userManagement.updated", managedUserVM.login))
    }

    /**
     * GET  /users : get all users.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @GetMapping("/users")
    @Timed
    open fun getAllUsers(@ApiParam pageable: Pageable): ResponseEntity<List<UserDTO>> {
        val page = userService.getAllManagedUsers(pageable)
        val headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/users")
        return ResponseEntity(page.content, headers, HttpStatus.OK)
    }

    /**
     * GET  /users/:login : get the "login" user.
     *
     * @param login the login of the user to find
     * @return the ResponseEntity with status 200 (OK) and with body the "login" user, or with status 404 (Not Found)
     */
    @GetMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
    @Timed
    open fun getUser(@PathVariable login: String): ResponseEntity<UserDTO> {
        log.debug("REST request to get User : {}", login)
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(
            userService.getUserWithAuthoritiesByLogin(login)))
    }

    /**
     * DELETE /users/:login : delete the "login" User.
     *
     * @param login the login of the user to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    open fun deleteUser(@PathVariable login: String): ResponseEntity<Void> {
        log.debug("REST request to delete User: {}", login)
        userService.deleteUser(login)
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("userManagement.deleted", login)).build()
    }


}
