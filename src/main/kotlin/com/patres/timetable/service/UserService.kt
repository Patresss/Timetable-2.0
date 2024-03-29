package com.patres.timetable.service

import com.patres.timetable.config.Constants
import com.patres.timetable.domain.Authority
import com.patres.timetable.domain.User
import com.patres.timetable.repository.AuthorityRepository
import com.patres.timetable.repository.DivisionRepository
import com.patres.timetable.repository.TeacherRepository
import com.patres.timetable.repository.UserRepository
import com.patres.timetable.security.AuthoritiesConstants
import com.patres.timetable.security.SecurityUtils
import com.patres.timetable.service.dto.UserDTO
import com.patres.timetable.service.mapper.UserMapper
import com.patres.timetable.service.util.RandomUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cache.CacheManager
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.streams.toList

@Service
@Transactional
open class UserService(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
    private val passwordEncoder: PasswordEncoder,
    private val authorityRepository: AuthorityRepository,
    private val cacheManager: CacheManager,
    private val divisionRepository: DivisionRepository,
    private val teacherRepository: TeacherRepository,
    private val authenticationManager: AuthenticationManager
) {


    companion object {
        private val log: Logger = LoggerFactory.getLogger(UserService::class.java)
    }


    @Transactional(readOnly = true)
    open fun getUserWithAuthorities(): User? {
        val login = SecurityUtils.getCurrentUserLogin()
        return when {
            login != null -> userRepository.findOneWithAuthoritiesByLogin(login)
            else -> null
        }
    }

    open fun getAuthorities(): List<String> {
        return authorityRepository.findAll().stream().map { it.name }.toList()
    }

    open fun activateRegistration(key: String): User? {
        log.debug("Activating user for activation key {}", key)
        val user = userRepository.findOneByActivationKey(key)
        user?.apply {
            activated = true
            activationKey = null
            cacheManager.getCache(User::class.java.name).evict(user.login)
            log.debug("Activated user: {}", user)
        }
        return user
    }

    open fun completePasswordReset(newPassword: String, key: String): User? {
        log.debug("Reset user password for reset key {}", key)

        val user = userRepository.findOneByResetKey(key)
        user?.takeIf { user.resetDate?.isAfter(Instant.now().minusSeconds(86400)) == true }?.apply {
            password = passwordEncoder.encode(newPassword)
            resetKey = null
            resetDate = null
            cacheManager.getCache(User::class.java.name).evict(user.login)
        }
        return user
    }

    open fun requestPasswordReset(mail: String): User? {
        val user = userRepository.findOneByEmail(mail)
        user?.takeIf { user.activated }?.apply {
            resetKey = RandomUtil.generateResetKey()
            resetDate = Instant.now()
            cacheManager.getCache(User::class.java.name).evict(login)
        }
        return user
    }

    open fun createUser(login: String, password: String, firstName: String? = null, lastName: String? = null, email: String,
                        imageUrl: String? = null, langKey: String? = "en", schoolId: Long? = null, teacherId: Long? = null): User {

        val newUser = User()

        val school = schoolId?.let { divisionRepository.getOne(schoolId) }
        val teacher = teacherId?.let { teacherRepository.getOne(teacherId) }
        val authority: Authority = if (teacher != null) {
            authorityRepository.findOne(AuthoritiesConstants.TEACHER)
        } else {
            authorityRepository.findOne(AuthoritiesConstants.SCHOOL_ADMIN)
        }

        val authorities = HashSet<Authority>()
        val encryptedPassword = passwordEncoder.encode(password)
        newUser.login = login
        // new user gets initially a generated password
        newUser.password = encryptedPassword
        newUser.firstName = firstName
        newUser.lastName = lastName
        newUser.email = email
        newUser.imageUrl = imageUrl
        newUser.langKey = langKey
        // new user is not active
        newUser.activated = false
        // new user gets registration key
        newUser.activationKey = RandomUtil.generateActivationKey()
        authorities.add(authority)
        newUser.authorities = authorities
        newUser.school = school
        newUser.teacher = teacher

        userRepository.save(newUser)
        log.debug("Created Information for User: {}", newUser)
        return newUser
    }

    open fun createUser(userDTO: UserDTO): User {
        val user = User()
        val school = userDTO.schoolId?.let { divisionRepository.getOne(it) }
        val teacher = userDTO.schoolId?.let { teacherRepository.getOne(it) }

        user.school = school
        user.teacher = teacher

        user.login = userDTO.login
        user.firstName = userDTO.firstName
        user.lastName = userDTO.lastName
        user.email = userDTO.email
        user.imageUrl = userDTO.imageUrl
        if (userDTO.langKey == null) {
            user.langKey = "en" // default language
        } else {
            user.langKey = userDTO.langKey
        }
        val authorities = HashSet<Authority>()
        userDTO.authorities.forEach { authority -> authorities.add(authorityRepository.findOne(authority)) }
        user.authorities = authorities

        val encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword())
        user.password = encryptedPassword
        user.resetKey = RandomUtil.generateResetKey()
        user.resetDate = Instant.now()
        user.activated = true
        userRepository.save(user)
        log.debug("Created Information for User: {}", user)
        return user
    }

    /**
     * Update basic information (first name, last name, email, language) for the current user.
     *
     * @param firstName first name of user
     * @param lastName last name of user
     * @param email email id of user
     * @param langKey language key
     * @param imageUrl image URL of user
     */
    open fun updateUser(firstName: String?, lastName: String?, email: String?, langKey: String?, imageUrl: String?) {
        val login = SecurityUtils.getCurrentUserLogin()
        login?.let {
            val user = userRepository.findOneByLogin(login)
            user?.apply {
                user.firstName = firstName
                user.lastName = lastName
                user.email = email
                user.langKey = langKey
                user.imageUrl = imageUrl
                cacheManager.getCache(User::class.java.name).evict(user.login)
                log.debug("Changed Information for User: {}", user)
            }
        }
    }

    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param userDTO user to update
     * @return updated user
     */
    open fun updateUser(userDTO: UserDTO): UserDTO? {
        val user = userRepository.findOne(userDTO.id)
        user.login = userDTO.login
        user.firstName = userDTO.firstName
        user.lastName = userDTO.lastName
        user.email = userDTO.email
        user.imageUrl = userDTO.imageUrl
        user.activated = userDTO.activated
        user.langKey = userDTO.langKey
        user.authorities = userDTO.authorities.map { authority -> authorityRepository.findOne(authority) }.toSet()
        userDTO.authorities
            .map { authorityRepository.findOne(it) }
            .forEach { user.authorities.plus(it) }
        cacheManager.getCache(User::class.java.name).evict(user.login)
        log.debug("Changed Information for User: {}", user)
        return UserDTO()
    }

    open fun deleteUser(login: String) {
        val user = userRepository.findOneByLogin(login)
        user.let {
            userRepository.delete(user)
            cacheManager.getCache(User::class.java.name).evict(login)
            log.debug("Deleted User: {}", user)
        }
    }

    open fun changePassword(password: String) {
        val login = SecurityUtils.getCurrentUserLogin()
        login?.let {
            val userFromRepository = userRepository.findOneByLogin(login)
            userFromRepository?.let { user ->
                val encryptedPassword = passwordEncoder.encode(password)
                user.password = encryptedPassword
                cacheManager.getCache(User::class.java.name).evict(user.login)
                log.debug("Changed password for User: {}", user)
            }
        }
    }

    @Transactional(readOnly = true)
    open fun getAllManagedUsers(pageable: Pageable): Page<UserDTO> {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            return userRepository.findAllByLoginNot(pageable, Constants.ANONYMOUS_USER).map { userMapper.toDto(it) }
        }
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCHOOL_ADMIN)) {
            val login = SecurityUtils.getCurrentUserLogin()
            val userFromRepository = userRepository.findOneByLogin(login)
            val schoolId = userFromRepository?.school?.id?: -1
            return userRepository.findAllByLoginNotAndSchoolId(pageable, Constants.ANONYMOUS_USER, schoolId).map { userMapper.toDto(it) }
        }
        return PageImpl<UserDTO>(emptyList())
    }

    @Transactional(readOnly = true)
    open fun getUserWithAuthoritiesByLogin(login: String): UserDTO? {
        val entity = userRepository.findOneWithAuthoritiesByLogin(login)
        return if (entity != null) userMapper.toDto(entity) else null
    }

    @Transactional(readOnly = true)
    open fun getUserWithAuthorities(id: Long?): User? {
        return userRepository.findOneWithAuthoritiesById(id)
    }

    /**
     * Not activated users should be automatically deleted after 3 days.
     *
     *
     * This is scheduled to get fired everyday, at 01:00 (am).
     */
    @Scheduled(cron = "0 0 1 * * ?")
    open fun removeNotActivatedUsers() {
        val users = userRepository.findAllByActivatedIsFalseAndCreatedDateBefore(Instant.now().minus(3, ChronoUnit.DAYS))
        for (user in users) {
            log.debug("Deleting not activated user {}", user.login)
            userRepository.delete(user)
            cacheManager.getCache(User::class.java.name).evict(user.login)
        }
    }
}
