package com.patres.timetable.web.rest

import com.patres.timetable.TimetableApp
import com.patres.timetable.domain.Authority
import com.patres.timetable.domain.User
import com.patres.timetable.repository.AuthorityRepository
import com.patres.timetable.repository.UserRepository
import com.patres.timetable.security.AuthoritiesConstants
import com.patres.timetable.service.MailService
import com.patres.timetable.service.UserService
import com.patres.timetable.service.dto.UserDTO
import com.patres.timetable.service.mapper.UserMapper
import com.patres.timetable.web.rest.vm.KeyAndPasswordVM
import com.patres.timetable.web.rest.vm.ManagedUserVM
import org.apache.commons.lang3.RandomStringUtils
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.transaction.annotation.Transactional

import java.time.Instant
import java.util.*

import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.hasItem
import org.mockito.Matchers.anyObject
import org.mockito.Mockito
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.`when`
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(TimetableApp::class))
open class AccountResourceIntTest {

    @Autowired
    lateinit private var userRepository: UserRepository

    @Autowired
    lateinit private var authorityRepository: AuthorityRepository

    @Autowired
    lateinit private var userService: UserService

    @Autowired
    lateinit private var userMapper: UserMapper

    @Autowired
    lateinit private var passwordEncoder: PasswordEncoder

    @Autowired
    lateinit private var httpMessageConverters: Array<HttpMessageConverter<*>>

    @Mock
    private val mockUserService: UserService? = null

    @Mock
    private val mockMailService: MailService? = null

    private var restUserMockMvc: MockMvc? = null

    private var restMvc: MockMvc? = null

    // workaround for anyObject() in Kotlin https://stackoverflow.com/questions/30305217/is-it-possible-to-use-mockito-in-kotlin
    private fun <T> anyObject(): T {
        return Mockito.anyObject<T>()
    }

    @Before
    open fun setup() {
        MockitoAnnotations.initMocks(this)
        doNothing().`when`<MailService>(mockMailService).sendActivationEmail(anyObject())

        val accountResource = AccountResource(userRepository, userService, userMapper, mockMailService)

        val accountUserMockResource = AccountResource(userRepository, mockUserService, userMapper, mockMailService)

        this.restMvc = MockMvcBuilders.standaloneSetup(accountResource)
                .setMessageConverters(*httpMessageConverters!!)
                .build()
        this.restUserMockMvc = MockMvcBuilders.standaloneSetup(accountUserMockResource).build()
    }

    @Test
    @Throws(Exception::class)
    open fun testNonAuthenticatedUser() {
        restUserMockMvc!!.perform(get("/api/authenticate")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().string(""))
    }

    @Test
    @Throws(Exception::class)
    open fun testAuthenticatedUser() {
        restUserMockMvc!!.perform(get("/api/authenticate")
                .with { request ->
                    request.remoteUser = "test"
                    request
                }
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().string("test"))
    }

    @Test
    @Throws(Exception::class)
    open fun testGetExistingAccount() {
        val authorities = HashSet<Authority>()
        val authority = Authority()
        authority.name = AuthoritiesConstants.ADMIN
        authorities.add(authority)

        val user = User()
        user.login = "test"
        user.firstName = "john"
        user.lastName = "doe"
        user.email = "john.doe@jhipster.com"
        user.imageUrl = "http://placehold.it/50x50"
        user.langKey = "en"
        user.authorities = authorities
        `when`<User>(mockUserService!!.getUserWithAuthorities()).thenReturn(user)

        restUserMockMvc!!.perform(get("/api/account")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.login").value("test"))
                .andExpect(jsonPath("$.firstName").value("john"))
                .andExpect(jsonPath("$.lastName").value("doe"))
                .andExpect(jsonPath("$.email").value("john.doe@jhipster.com"))
                .andExpect(jsonPath("$.imageUrl").value("http://placehold.it/50x50"))
                .andExpect(jsonPath("$.langKey").value("en"))
                .andExpect(jsonPath("$.authorities").value(AuthoritiesConstants.ADMIN))
    }

    @Test
    @Throws(Exception::class)
    open fun testGetUnknownAccount() {
        `when`<User>(mockUserService!!.getUserWithAuthorities()).thenReturn(null)

        restUserMockMvc!!.perform(get("/api/account")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun testRegisterValid() {
        val validUser = ManagedUserVM().apply {
            login = "joe"
            firstName = "Joe"
            lastName = "Shmoe"
            email = "joe@example.com"
            imageUrl = "http://placehold.it/50x50"
            isActivated = true
            langKey = "en"
            authorities = HashSet(listOf(AuthoritiesConstants.SCHOOL_ADMIN))
            password = "password"
        }


        restMvc!!.perform(
                post("/api/register")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(validUser)))
                .andExpect(status().isCreated)

        val user = userRepository!!.findOneByLogin("joe")
        assertThat(user.isPresent).isTrue()
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun testRegisterInvalidLogin() {
        val invalidUser = ManagedUserVM().apply {
            login = "open funky-log!n"
            firstName = "Joe"
            lastName = "Shmoe"
            email = "joe@example.com"
            imageUrl = "http://placehold.it/50x50"
            isActivated = true
            langKey = "en"
            authorities = HashSet(listOf(AuthoritiesConstants.SCHOOL_ADMIN))
            password = "password"
        }

        restUserMockMvc!!.perform(
                post("/api/register")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(invalidUser)))
                .andExpect(status().isBadRequest)

        val user = userRepository.findOneByEmail("open funky@example.com")
        assertThat(user.isPresent).isFalse()
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun testRegisterInvalidEmail() {
        val invalidUser = ManagedUserVM().apply {
            login = "bob"
            firstName = "Joe"
            lastName = "Shmoe"
            email = "invalid"
            imageUrl = "http://placehold.it/50x50"
            isActivated = true
            langKey = "en"
            authorities = HashSet(listOf(AuthoritiesConstants.SCHOOL_ADMIN))
            password = "password"
        }

        restUserMockMvc!!.perform(
                post("/api/register")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(invalidUser)))
                .andExpect(status().isBadRequest)

        val user = userRepository!!.findOneByLogin("bob")
        assertThat(user.isPresent).isFalse()
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun testRegisterInvalidPassword() {
        val invalidUser = ManagedUserVM().apply {
            login = "bob"
            firstName = "Joe"
            lastName = "Shmoe"
            email = "bob@example.com"
            imageUrl = "http://placehold.it/50x50"
            isActivated = true
            langKey = "en"
            authorities = HashSet(listOf(AuthoritiesConstants.SCHOOL_ADMIN))
            password = "123"
        }

        restUserMockMvc!!.perform(
                post("/api/register")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(invalidUser)))
                .andExpect(status().isBadRequest)

        val user = userRepository!!.findOneByLogin("bob")
        assertThat(user.isPresent).isFalse()
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open  fun testRegisterNullPassword() {
        val invalidUser = ManagedUserVM().apply {
            login = "bob"
            firstName = "Joe"
            lastName = "Shmoe"
            email = "bob@example.com"
            imageUrl = "http://placehold.it/50x50"
            isActivated = true
            langKey = "en"
            authorities = HashSet(listOf(AuthoritiesConstants.SCHOOL_ADMIN))
        }

        restUserMockMvc!!.perform(
                post("/api/register")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(invalidUser)))
                .andExpect(status().isBadRequest)

        val user = userRepository!!.findOneByLogin("bob")
        assertThat(user.isPresent).isFalse()
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun testRegisterDuplicateLogin() {
        // Good
        val validUser = ManagedUserVM().apply {
            login = "alice"
            firstName = "Joe"
            lastName = "Shmoe"
            email = "bob@example.com"
            imageUrl = "http://placehold.it/50x50"
            isActivated = true
            langKey = "en"
            authorities = HashSet(listOf(AuthoritiesConstants.SCHOOL_ADMIN))
            password = "password"
        }
        // Duplicate login, different email
        val duplicatedUser = ManagedUserVM().apply {
            id = validUser.id
            login = validUser.login
            firstName = validUser.firstName
            lastName = validUser.lastName
            email = "alicejr@example.com"
            imageUrl = validUser.imageUrl
            langKey = validUser.langKey
            createdBy = validUser.createdBy
            createdDate = validUser.createdDate
            lastModifiedBy = validUser.lastModifiedBy
            lastModifiedDate = validUser.lastModifiedDate
            authorities = validUser.authorities
            password = validUser.password
        }

        // Good user
        restMvc!!.perform(
                post("/api/register")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(validUser)))
                .andExpect(status().isCreated)

        // Duplicate login
        restMvc!!.perform(
                post("/api/register")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(duplicatedUser)))
                .andExpect(status().is4xxClientError)

        val userDup = userRepository!!.findOneByEmail("alicejr@example.com")
        assertThat(userDup.isPresent).isFalse()
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun testRegisterDuplicateEmail() {
        // Good
        val validUser = ManagedUserVM().apply {
            login = "bob"
            firstName = "Joe"
            lastName = "Shmoe"
            email = "bob@example.com"
            imageUrl = "http://placehold.it/50x50"
            isActivated = true
            langKey = "en"
            authorities = HashSet(listOf(AuthoritiesConstants.SCHOOL_ADMIN))
            password = "password"
        }
        // createdBy
        // createdDate
        // lastModifiedBy
        // password

        // Duplicate email, different login
        val duplicatedUser = ManagedUserVM().apply {
            id = validUser.id
            login = "johnjr"
            firstName = validUser.firstName
            lastName = validUser.lastName
            email = validUser.email
            imageUrl = validUser.imageUrl
            langKey = validUser.langKey
            createdBy = validUser.createdBy
            createdDate = validUser.createdDate
            lastModifiedBy = validUser.lastModifiedBy
            lastModifiedDate = validUser.lastModifiedDate
            authorities = validUser.authorities
            password = validUser.password
        }
        // Good user
        restMvc!!.perform(
                post("/api/register")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(validUser)))
                .andExpect(status().isCreated)

        // Duplicate email
        restMvc!!.perform(
                post("/api/register")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(duplicatedUser)))
                .andExpect(status().is4xxClientError)

        val userDup = userRepository!!.findOneByLogin("johnjr")
        assertThat(userDup.isPresent).isFalse()
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun testRegisterAdminIsIgnored() {
        val validUser = ManagedUserVM().apply {
            login = "badguy"
            firstName = "Joe"
            lastName = "Shmoe"
            email = "bob@example.com"
            imageUrl = "http://placehold.it/50x50"
            isActivated = true
            langKey = "en"
            authorities = HashSet(listOf(AuthoritiesConstants.ADMIN))
            password = "password"
        }

        restMvc!!.perform(
                post("/api/register")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(validUser)))
                .andExpect(status().isCreated)

        val userDup = userRepository!!.findOneByLogin("badguy")
        assertThat(userDup.isPresent).isTrue()
        assertThat(userDup.get().authorities).hasSize(1)
                .containsExactly(authorityRepository!!.findOne(AuthoritiesConstants.SCHOOL_ADMIN))
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun testActivateAccount() {
        val activationKey = "some activation key"
        var user = User()
        user.login = "activate-account"
        user.email = "activate-account@example.com"
        user.password = RandomStringUtils.random(60)
        user.activated = false
        user.activationKey = activationKey

        userRepository!!.saveAndFlush(user)

        restMvc!!.perform(get("/api/activate?key={activationKey}", activationKey))
                .andExpect(status().isOk)

        user = userRepository.findOneByLogin(user.login).orElse(null)
        assertThat(user.activated).isTrue()
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun testActivateAccountWithWrongKey() {
        restMvc!!.perform(get("/api/activate?key=wrongActivationKey"))
                .andExpect(status().isInternalServerError)
    }

    @Test
    @Transactional
    @WithMockUser("save-account")
    @Throws(Exception::class)
    open fun testSaveAccount() {
        val user = User()
        user.login = "save-account"
        user.email = "save-account@example.com"
        user.password = RandomStringUtils.random(60)
        user.activated = true

        userRepository!!.saveAndFlush(user)

        val userDTO = UserDTO(null, // id
                "not-used", // login
                "firstname", // firstName
                "lastname", // lastName
                "save-account@example.com", // email
                "http://placehold.it/50x50", //imageUrl
                false, // activated
                "en", null, null, null, null, // lastModifiedDate
                HashSet(listOf(AuthoritiesConstants.ADMIN))
        )// langKey
        // createdBy
        // createdDate
        // lastModifiedBy

        restMvc!!.perform(
                post("/api/account")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(userDTO)))
                .andExpect(status().isOk)

        val updatedUser = userRepository.findOneByLogin(user.login).orElse(null)
        assertThat(updatedUser.firstName).isEqualTo(userDTO.firstName)
        assertThat(updatedUser.lastName).isEqualTo(userDTO.lastName)
        assertThat(updatedUser.email).isEqualTo(userDTO.email)
        assertThat(updatedUser.langKey).isEqualTo(userDTO.langKey)
        assertThat(updatedUser.password).isEqualTo(user.password)
        assertThat(updatedUser.imageUrl).isEqualTo(userDTO.imageUrl)
        assertThat(updatedUser.activated).isEqualTo(true)
        assertThat(updatedUser.authorities).isEmpty()
    }

    @Test
    @Transactional
    @WithMockUser("save-invalid-email")
    @Throws(Exception::class)
    open fun testSaveInvalidEmail() {
        val user = User()
        user.login = "save-invalid-email"
        user.email = "save-invalid-email@example.com"
        user.password = RandomStringUtils.random(60)
        user.activated = true

        userRepository.saveAndFlush(user)

        val userDTO = UserDTO(null, // id
                "not-used", // login
                "firstname", // firstName
                "lastname", // lastName
                "invalid email", // email
                "http://placehold.it/50x50", //imageUrl
                false, // activated
                "en", null, null, null, null, // lastModifiedDate
                HashSet(listOf(AuthoritiesConstants.ADMIN))
        )
        restMvc!!.perform(
                post("/api/account")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(userDTO)))
                .andExpect(status().isBadRequest)

        assertThat(userRepository.findOneByEmail("invalid email")).isNotPresent
    }

    @Test
    @Transactional
    @WithMockUser("save-existing-email")
    @Throws(Exception::class)
    open fun testSaveExistingEmail() {
        val user = User()
        user.login = "save-existing-email"
        user.email = "save-existing-email@example.com"
        user.password = RandomStringUtils.random(60)
        user.activated = true

        userRepository!!.saveAndFlush(user)

        val anotherUser = User()
        anotherUser.login = "save-existing-email2"
        anotherUser.email = "save-existing-email2@example.com"
        anotherUser.password = RandomStringUtils.random(60)
        anotherUser.activated = true

        userRepository.saveAndFlush(anotherUser)

        val userDTO = UserDTO(null, // id
                "not-used", // login
                "firstname", // firstName
                "lastname", // lastName
                "save-existing-email2@example.com", // email
                "http://placehold.it/50x50", //imageUrl
                false, // activated
                "en", null, null, null, null, // lastModifiedDate
                HashSet(listOf(AuthoritiesConstants.ADMIN))
        )// langKey
        // createdBy
        // createdDate
        // lastModifiedBy

        restMvc!!.perform(
                post("/api/account")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(userDTO)))
                .andExpect(status().isBadRequest)

        val updatedUser = userRepository.findOneByLogin("save-existing-email").orElse(null)
        assertThat(updatedUser.email).isEqualTo("save-existing-email@example.com")
    }

    @Test
    @Transactional
    @WithMockUser("save-existing-email-and-login")
    @Throws(Exception::class)
    open fun testSaveExistingEmailAndLogin() {
        val user = User()
        user.login = "save-existing-email-and-login"
        user.email = "save-existing-email-and-login@example.com"
        user.password = RandomStringUtils.random(60)
        user.activated = true

        userRepository!!.saveAndFlush(user)

        val userDTO = UserDTO(null, // id
                "not-used", // login
                "firstname", // firstName
                "lastname", // lastName
                "save-existing-email-and-login@example.com", // email
                "http://placehold.it/50x50", //imageUrl
                false, // activated
                "en", null, null, null, null, // lastModifiedDate
                HashSet(listOf(AuthoritiesConstants.ADMIN))
        )// langKey
        // createdBy
        // createdDate
        // lastModifiedBy

        restMvc!!.perform(
                post("/api/account")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(userDTO)))
                .andExpect(status().isOk)

        val updatedUser = userRepository.findOneByLogin("save-existing-email-and-login").orElse(null)
        assertThat(updatedUser.email).isEqualTo("save-existing-email-and-login@example.com")
    }

    @Test
    @Transactional
    @WithMockUser("change-password")
    @Throws(Exception::class)
    open fun testChangePassword() {
        val user = User()
        user.password = RandomStringUtils.random(60)
        user.login = "change-password"
        user.email = "change-password@example.com"
        userRepository!!.saveAndFlush(user)

        restMvc!!.perform(post("/api/account/change-password").content("new password"))
                .andExpect(status().isOk)

        val updatedUser = userRepository.findOneByLogin("change-password").orElse(null)
        assertThat(passwordEncoder!!.matches("new password", updatedUser.password)).isTrue()
    }

    @Test
    @Transactional
    @WithMockUser("change-password-too-small")
    @Throws(Exception::class)
    open fun testChangePasswordTooSmall() {
        val user = User()
        user.password = RandomStringUtils.random(60)
        user.login = "change-password-too-small"
        user.email = "change-password-too-small@example.com"
        userRepository!!.saveAndFlush(user)

        restMvc!!.perform(post("/api/account/change-password").content("new"))
                .andExpect(status().isBadRequest)

        val updatedUser = userRepository.findOneByLogin("change-password-too-small").orElse(null)
        assertThat(updatedUser.password).isEqualTo(user.password)
    }

    @Test
    @Transactional
    @WithMockUser("change-password-too-long")
    @Throws(Exception::class)
    open fun testChangePasswordTooLong() {
        val user = User()
        user.password = RandomStringUtils.random(60)
        user.login = "change-password-too-long"
        user.email = "change-password-too-long@example.com"
        userRepository!!.saveAndFlush(user)

        restMvc!!.perform(post("/api/account/change-password").content(RandomStringUtils.random(101)))
                .andExpect(status().isBadRequest)

        val updatedUser = userRepository.findOneByLogin("change-password-too-long").orElse(null)
        assertThat(updatedUser.password).isEqualTo(user.password)
    }

    @Test
    @Transactional
    @WithMockUser("change-password-empty")
    @Throws(Exception::class)
    open fun testChangePasswordEmpty() {
        val user = User()
        user.password = RandomStringUtils.random(60)
        user.login = "change-password-empty"
        user.email = "change-password-empty@example.com"
        userRepository!!.saveAndFlush(user)

        restMvc!!.perform(post("/api/account/change-password").content(RandomStringUtils.random(0)))
                .andExpect(status().isBadRequest)

        val updatedUser = userRepository.findOneByLogin("change-password-empty").orElse(null)
        assertThat(updatedUser.password).isEqualTo(user.password)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun testRequestPasswordReset() {
        val user = User()
        user.password = RandomStringUtils.random(60)
        user.activated = true
        user.login = "password-reset"
        user.email = "password-reset@example.com"
        userRepository!!.saveAndFlush(user)

        restMvc!!.perform(post("/api/account/reset-password/init")
                .content("password-reset@example.com"))
                .andExpect(status().isOk)
    }

    @Test
    @Throws(Exception::class)
    open fun testRequestPasswordResetWrongEmail() {
        restMvc!!.perform(
                post("/api/account/reset-password/init")
                        .content("password-reset-wrong-email@example.com"))
                .andExpect(status().isBadRequest)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun testFinishPasswordReset() {
        val user = User()
        user.password = RandomStringUtils.random(60)
        user.login = "finish-password-reset"
        user.email = "finish-password-reset@example.com"
        user.resetDate = Instant.now().plusSeconds(60)
        user.resetKey = "reset key"
        userRepository!!.saveAndFlush(user)

        val keyAndPassword = KeyAndPasswordVM()
        keyAndPassword.key = user.resetKey
        keyAndPassword.newPassword = "new password"

        restMvc!!.perform(
                post("/api/account/reset-password/finish")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(keyAndPassword)))
                .andExpect(status().isOk)

        val updatedUser = userRepository.findOneByLogin(user.login).orElse(null)
        assertThat(passwordEncoder!!.matches(keyAndPassword.newPassword, updatedUser.password)).isTrue()
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun testFinishPasswordResetTooSmall() {
        val user = User()
        user.password = RandomStringUtils.random(60)
        user.login = "finish-password-reset-too-small"
        user.email = "finish-password-reset-too-small@example.com"
        user.resetDate = Instant.now().plusSeconds(60)
        user.resetKey = "reset key too small"
        userRepository!!.saveAndFlush(user)

        val keyAndPassword = KeyAndPasswordVM()
        keyAndPassword.key = user.resetKey
        keyAndPassword.newPassword = "foo"

        restMvc!!.perform(
                post("/api/account/reset-password/finish")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(keyAndPassword)))
                .andExpect(status().isBadRequest)

        val updatedUser = userRepository.findOneByLogin(user.login).orElse(null)
        assertThat(passwordEncoder!!.matches(keyAndPassword.newPassword, updatedUser.password)).isFalse()
    }


    @Test
    @Transactional
    @Throws(Exception::class)
    open fun testFinishPasswordResetWrongKey() {
        val keyAndPassword = KeyAndPasswordVM()
        keyAndPassword.key = "wrong reset key"
        keyAndPassword.newPassword = "new password"

        restMvc!!.perform(
                post("/api/account/reset-password/finish")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(keyAndPassword)))
                .andExpect(status().isInternalServerError)
    }
}
