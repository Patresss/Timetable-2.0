package com.patres.timetable.web.rest

import com.patres.timetable.TimetableApp
import com.patres.timetable.domain.Authority
import com.patres.timetable.domain.User
import com.patres.timetable.repository.AuthorityRepository
import com.patres.timetable.repository.UserRepository
import com.patres.timetable.security.AuthoritiesConstants
import com.patres.timetable.service.MailService
import com.patres.timetable.service.UserService
import com.patres.timetable.service.mapper.UserMapper
import com.patres.timetable.web.rest.vm.KeyAndPasswordVM
import com.patres.timetable.web.rest.vm.ManagedUserVM
import org.apache.commons.lang3.RandomStringUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.doNothing
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

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
    lateinit private var mockUserService: UserService

    @Mock
    lateinit private var mockMailService: MailService

    lateinit private var restUserMockMvc: MockMvc

    lateinit private var restMvc: MockMvc

    companion object {

        fun createValidAccount(): ManagedUserVM {
            val user = UserResourceIntTest.createEntity()
            return ManagedUserVM().apply {
                login = user.login
                firstName = user.firstName
                lastName = user.lastName
                email = user.email
                imageUrl = user.imageUrl
                activated = user.activated
                langKey = user.langKey
                authorities = user.authorities.map { it.name }.toSet()
                password = user.password
            }
        }
    }

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
        this.restMvc = MockMvcBuilders.standaloneSetup(accountResource).setMessageConverters(*httpMessageConverters).build()
        this.restUserMockMvc = MockMvcBuilders.standaloneSetup(accountUserMockResource).build()
    }

    @Test
    @Throws(Exception::class)
    open fun `test non authenticated user`() {
        restUserMockMvc.perform(get("/api/authenticate")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().string(""))
    }

    @Test
    @Throws(Exception::class)
    open fun `test authenticated user`() {
        restUserMockMvc.perform(get("/api/authenticate")
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
    open fun `test get existing account`() {
        val authorities = HashSet<Authority>()
        val authority = Authority()
        authority.name = AuthoritiesConstants.ADMIN
        authorities.add(authority)

        val user = User().apply {
            login = "test"
            firstName = "john"
            lastName = "doe"
            email = "john.doe@jhipster.com"
            imageUrl = "http://placehold.it/50x50"
            langKey = "en"
            this.authorities = authorities
        }

        `when`<User>(mockUserService.getUserWithAuthorities()).thenReturn(user)

        restUserMockMvc.perform(get("/api/account")
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
    open fun `test get unknown account`() {
        `when`<User>(mockUserService.getUserWithAuthorities()).thenReturn(null)

        restUserMockMvc.perform(get("/api/account")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isInternalServerError)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun `test register valid`() {
        val validUser = createValidAccount()

        restMvc.perform(
            post("/api/register")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(validUser)))
            .andExpect(status().isCreated)

        val user = userRepository.findOneByLogin(validUser.login!!)
        assertThat(user != null).isTrue()
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun `test register invalid login`() {
        val invalidUser = createValidAccount().apply {
            login = "open funky-log!n"
        }

        restUserMockMvc.perform(
            post("/api/register")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(invalidUser)))
            .andExpect(status().isBadRequest)

        val user = userRepository.findOneByEmail(invalidUser.email!!)
        assertThat(user != null).isFalse()
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun `test register invalid email`() {
        val invalidUser = createValidAccount().apply {
            email = "invalid"
        }

        restUserMockMvc.perform(
            post("/api/register")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(invalidUser)))
            .andExpect(status().isBadRequest)

        val user = userRepository.findOneByLogin(invalidUser.login!!)
        assertThat(user != null).isFalse()
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun `test register invalid password`() {
        val invalidUser = createValidAccount().apply {
            password = "123"
        }

        restUserMockMvc.perform(
            post("/api/register")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(invalidUser)))
            .andExpect(status().isBadRequest)

        val user = userRepository.findOneByLogin(invalidUser.login!!)
        assertThat(user != null).isFalse()
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun `test register null password`() {
        val invalidUser = createValidAccount().apply {
            password = null
        }

        restUserMockMvc.perform(
            post("/api/register")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(invalidUser)))
            .andExpect(status().isBadRequest)

        val user = userRepository.findOneByLogin(invalidUser.login!!)
        assertThat(user != null).isFalse()
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun `test register duplicate login`() {
        val validUser = createValidAccount()
        val duplicatedUser = createValidAccount().apply {
            login = validUser.login
            email = "alicejr@example.com"
        }

        restMvc.perform(
            post("/api/register")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(validUser)))
            .andExpect(status().isCreated)

        restMvc.perform(
            post("/api/register")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(duplicatedUser)))
            .andExpect(status().is4xxClientError)

        val userDup = userRepository.findOneByEmail(duplicatedUser.email!!)
        assertThat(userDup!= null).isFalse()
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun `test register duplicate email`() {
        val validUser = createValidAccount()

        val duplicatedUser = createValidAccount().apply {
            login = "johnjr"
            email = validUser.email
        }
        restMvc.perform(
            post("/api/register")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(validUser)))
            .andExpect(status().isCreated)

        restMvc.perform(
            post("/api/register")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(duplicatedUser)))
            .andExpect(status().is4xxClientError)

        val userDup = userRepository.findOneByLogin(duplicatedUser.login!!)
        assertThat(userDup!= null).isFalse()
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun `test register admin is ignored`() {
        val validUser = createValidAccount().apply {
            authorities = HashSet(listOf(AuthoritiesConstants.ADMIN))
        }

        restMvc.perform(
            post("/api/register")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(validUser)))
            .andExpect(status().isCreated)

        val userDup = userRepository.findOneByLogin(validUser.login!!)
        assertThat(userDup!= null).isTrue()
        assertThat(userDup?.authorities).hasSize(1)
            .containsExactly(authorityRepository.findOne(AuthoritiesConstants.SCHOOL_ADMIN))
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun `test activate account`() {
        val activationKey = "some activation key"
        var user = UserResourceIntTest.createEntity().apply {
            login = "activate-account"
            activated = false
            this.activationKey = activationKey

        }

        userRepository.saveAndFlush(user)

        restMvc.perform(get("/api/activate?key={activationKey}", activationKey))
            .andExpect(status().isOk)

        user = userRepository.findOneByLogin(user.login!!)!!
        assertThat(user.activated).isTrue()
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun `test activate account with wrong key`() {
        restMvc.perform(get("/api/activate?key=wrongActivationKey")).andExpect(status().isInternalServerError)
    }

    @Test
    @Transactional
    @WithMockUser("save-account")
    @Throws(Exception::class)
    open fun `test save account`() {
        val user = UserResourceIntTest.createEntity().apply {
            login = "save-account"
        }

        userRepository.saveAndFlush(user)

        val userDTO = UserResourceIntTest.createEntityDto()

        restMvc.perform(
            post("/api/account")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userDTO)))
            .andExpect(status().isOk)

        val updatedUser = userRepository.findOneByLogin(user.login!!)
        assertThat(updatedUser!!.firstName).isEqualTo(userDTO.firstName)
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
    open fun `test save invalid email`() {
        val user = UserResourceIntTest.createEntity().apply {
            login = "save-invalid-email"
            email = "save-invalid-email@example.com"
            activated = true
        }

        userRepository.saveAndFlush(user)

        val userDTO = UserResourceIntTest.createEntityDto().apply {
            login = "not-used"
            email = "invalid email"
            activated = false
        }

        restMvc.perform(
            post("/api/account")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userDTO)))
            .andExpect(status().isBadRequest)

        assertThat(userRepository.findOneByEmail("invalid email") != null)
    }

    @Test
    @Transactional
    @WithMockUser("save-existing-email")
    @Throws(Exception::class)
    open fun `test save existing email`() {
        val user = UserResourceIntTest.createEntity().apply {
            login = "save-existing-email"
            email = "save-existing-email@example.com"
        }
        userRepository.saveAndFlush(user)

        val anotherUser = UserResourceIntTest.createEntity().apply {
            login = "save-existing-email2"
            email = "save-existing-email2@example.com"
        }
        userRepository.saveAndFlush(anotherUser)

        val userDTO = UserResourceIntTest.createEntityDto().apply {
            email = "save-existing-email2@example.com"
            activated = false
            authorities = HashSet(listOf(AuthoritiesConstants.ADMIN))
        }
        restMvc.perform(
            post("/api/account")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userDTO)))
            .andExpect(status().isBadRequest)

        val updatedUser = userRepository.findOneByLogin(user.login!!)
        assertThat(updatedUser!!.email).isEqualTo("save-existing-email@example.com")
    }

    @Test
    @Transactional
    @WithMockUser("save-existing-email-and-login")
    @Throws(Exception::class)
    open fun `test save existing email and login`() {
        val user = UserResourceIntTest.createEntity().apply {
            login = "save-existing-email-and-login"
            email = "save-existing-email-and-login@example.com"
            activated = true
        }
        userRepository.saveAndFlush(user)

        val userDTO = UserResourceIntTest.createEntityDto().apply {
            login = "not-used"
            email = "save-existing-email-and-login@example.com"
            activated = false
        }

        restMvc.perform(
            post("/api/account")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userDTO)))
            .andExpect(status().isOk)
    }

    @Test
    @Transactional
    @WithMockUser("change-password")
    @Throws(Exception::class)
    open fun `test change password`() {
        val user = UserResourceIntTest.createEntity().apply {
            login = "change-password"
        }
        userRepository.saveAndFlush(user)

        restMvc.perform(post("/api/account/change-password").content("new password"))
            .andExpect(status().isOk)

        val updatedUser = userRepository.findOneByLogin("change-password")
        assertThat(passwordEncoder.matches("new password", updatedUser?.password)).isTrue()
    }

    @Test
    @Transactional
    @WithMockUser("change-password-too-small")
    @Throws(Exception::class)
    open fun `test change password too small`() {
        val user = UserResourceIntTest.createEntity().apply {
            login = "change-password-too-small"
        }
        userRepository.saveAndFlush(user)

        restMvc.perform(post("/api/account/change-password").content("new"))
            .andExpect(status().isBadRequest)

        val updatedUser = userRepository.findOneByLogin("change-password-too-small")
        assertThat(updatedUser?.password).isEqualTo(user.password)
    }

    @Test
    @Transactional
    @WithMockUser("change-password-too-long")
    @Throws(Exception::class)
    open fun `test change password too long`() {
        val user = UserResourceIntTest.createEntity().apply {
            login = "change-password-too-long"
        }
        userRepository.saveAndFlush(user)

        restMvc.perform(post("/api/account/change-password").content(RandomStringUtils.random(101)))
            .andExpect(status().isBadRequest)

        val updatedUser = userRepository.findOneByLogin("change-password-too-long")
        assertThat(updatedUser?.password).isEqualTo(user.password)
    }

    @Test
    @Transactional
    @WithMockUser("change-password-empty")
    @Throws(Exception::class)
    open fun `test change password empty`() {
        val user = UserResourceIntTest.createEntity().apply {
            login = "change-password-empty"
        }
        userRepository.saveAndFlush(user)

        restMvc.perform(post("/api/account/change-password").content(RandomStringUtils.random(0)))
            .andExpect(status().isBadRequest)

        val updatedUser = userRepository.findOneByLogin("change-password-empty")
        assertThat(updatedUser?.password).isEqualTo(user.password)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun `test request password reset`() {
        val user = UserResourceIntTest.createEntity().apply {
            email = "password-reset@example.com"
            activated = true
        }
        userRepository.saveAndFlush(user)

        restMvc.perform(post("/api/account/reset-password/init")
            .content("password-reset@example.com"))
            .andExpect(status().isOk)
    }

    @Test
    @Throws(Exception::class)
    open fun `test request password reset wrong email`() {
        restMvc.perform(
            post("/api/account/reset-password/init")
                .content("password-reset-wrong-email@example.com"))
            .andExpect(status().isBadRequest)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun `test finish password reset`() {
        val user = UserResourceIntTest.createEntity().apply {
            login = "finish-password-reset"
            email = "finish-password-reset@example.com"
            resetDate = Instant.now().plusSeconds(60)
            resetKey = "reset key"
        }

        userRepository.saveAndFlush(user)

        val keyAndPassword = KeyAndPasswordVM()
        keyAndPassword.key = user.resetKey
        keyAndPassword.newPassword = "new password"

        restMvc.perform(
            post("/api/account/reset-password/finish")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(keyAndPassword)))
            .andExpect(status().isOk)

        val updatedUser = userRepository.findOneByLogin(user.login!!)
        assertThat(passwordEncoder.matches(keyAndPassword.newPassword, updatedUser?.password)).isTrue()
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun `test finish password reset too small`() {
        val user = UserResourceIntTest.createEntity().apply {
            login = "finish-password-reset-too-small"
            email = "finish-password-reset-too-small@example.com"
            resetDate = Instant.now().plusSeconds(60)
            resetKey = "reset key too small"
        }
        userRepository.saveAndFlush(user)

        val keyAndPassword = KeyAndPasswordVM()
        keyAndPassword.key = user.resetKey
        keyAndPassword.newPassword = "foo"

        restMvc.perform(
            post("/api/account/reset-password/finish")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(keyAndPassword)))
            .andExpect(status().isBadRequest)

        val updatedUser = userRepository.findOneByLogin(user.login!!)
        assertThat(passwordEncoder.matches(keyAndPassword.newPassword, updatedUser?.password)).isFalse()
    }


    @Test
    @Transactional
    @Throws(Exception::class)
    open fun `test finish password reset wrong key`() {
        val keyAndPassword = KeyAndPasswordVM()
        keyAndPassword.key = "wrong reset key"
        keyAndPassword.newPassword = "new password"

        restMvc.perform(
            post("/api/account/reset-password/finish")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(keyAndPassword)))
            .andExpect(status().isInternalServerError)
    }
}
