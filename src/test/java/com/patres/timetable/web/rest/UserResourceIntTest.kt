package com.patres.timetable.web.rest

import com.patres.timetable.TimetableApp
import com.patres.timetable.domain.Authority
import com.patres.timetable.domain.User
import com.patres.timetable.repository.UserRepository
import com.patres.timetable.security.AuthoritiesConstants
import com.patres.timetable.service.MailService
import com.patres.timetable.service.UserService
import com.patres.timetable.service.dto.UserDTO
import com.patres.timetable.service.mapper.UserMapper
import com.patres.timetable.web.rest.errors.ExceptionTranslator
import com.patres.timetable.web.rest.vm.ManagedUserVM
import org.apache.commons.lang3.RandomStringUtils
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.web.PageableHandlerMethodArgumentResolver
import org.springframework.http.MediaType
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.transaction.annotation.Transactional

import javax.persistence.EntityManager
import java.time.Instant
import java.util.Arrays
import java.util.HashSet
import java.util.stream.Collectors
import java.util.stream.Stream

import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.hasItem
import org.hamcrest.Matchers.containsInAnyOrder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

/**
 * Test class for the UserResource REST controller.
 *
 * @see UserResource
 */
@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(TimetableApp::class))
open class UserResourceIntTest {

    @Autowired
    private val userRepository: UserRepository? = null

    @Autowired
    private val mailService: MailService? = null

    @Autowired
    private val userService: UserService? = null

    @Autowired
    private val userMapper: UserMapper? = null

    @Autowired
    private val jacksonMessageConverter: MappingJackson2HttpMessageConverter? = null

    @Autowired
    private val pageableArgumentResolver: PageableHandlerMethodArgumentResolver? = null

    @Autowired
    private val exceptionTranslator: ExceptionTranslator? = null

    @Autowired
    private val em: EntityManager? = null

    private var restUserMockMvc: MockMvc? = null

    private var user: User? = null

    companion object {

        private val DEFAULT_ID = 1L

        private val DEFAULT_LOGIN = "johndoe"
        private val UPDATED_LOGIN = "jhipster"

        private val DEFAULT_PASSWORD = "passjohndoe"
        private val UPDATED_PASSWORD = "passjhipster"

        private val DEFAULT_EMAIL = "johndoe@localhost"
        private val UPDATED_EMAIL = "jhipster@localhost"

        private val DEFAULT_FIRSTNAME = "john"
        private val UPDATED_FIRSTNAME = "jhipsterFirstName"

        private val DEFAULT_LASTNAME = "doe"
        private val UPDATED_LASTNAME = "jhipsterLastName"

        private val DEFAULT_IMAGEURL = "http://placehold.it/50x50"
        private val UPDATED_IMAGEURL = "http://placehold.it/40x40"

        private val DEFAULT_LANGKEY = "en"
        private val UPDATED_LANGKEY = "fr"

        /**
         * Create a User.
         *
         * This is a static method, as tests for other entities might also need it,
         * if they test an entity which has a required relationship to the User entity.
         */
        fun createEntity(em: EntityManager?): User {
            val user = User()
            user.login = DEFAULT_LOGIN
            user.password = RandomStringUtils.random(60)
            user.activated = true
            user.email = DEFAULT_EMAIL
            user.firstName = DEFAULT_FIRSTNAME
            user.lastName = DEFAULT_LASTNAME
            user.imageUrl = DEFAULT_IMAGEURL
            user.langKey = DEFAULT_LANGKEY
            return user
        }
    }

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        val userResource = UserResource(userRepository!!, mailService!!, userService!!)
        this.restUserMockMvc = MockMvcBuilders.standaloneSetup(userResource)
                .setCustomArgumentResolvers(pageableArgumentResolver!!)
                .setControllerAdvice(exceptionTranslator!!)
                .setMessageConverters(jacksonMessageConverter!!)
                .build()
    }

    @Before
    fun initTest() {
        user = createEntity(em)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun createUser() {
        val databaseSizeBeforeCreate = userRepository!!.findAll().size

        // Create the User
        val authorities = HashSet<String>()
        authorities.add("ROLE_SCHOOL_ADMIN")
        val managedUserVM = ManagedUserVM().apply {
            login = DEFAULT_LOGIN
            firstName = DEFAULT_FIRSTNAME
            lastName = DEFAULT_LASTNAME
            email = DEFAULT_EMAIL
            imageUrl = DEFAULT_IMAGEURL
            langKey = DEFAULT_LANGKEY
            password = DEFAULT_PASSWORD
            this.authorities = authorities
        }

        restUserMockMvc!!.perform(post("/api/users")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(managedUserVM)))
                .andExpect(status().isCreated)

        // Validate the User in the database
        val userList = userRepository.findAll()
        assertThat(userList).hasSize(databaseSizeBeforeCreate + 1)
        val testUser = userList[userList.size - 1]
        assertThat(testUser.login).isEqualTo(DEFAULT_LOGIN)
        assertThat(testUser.firstName).isEqualTo(DEFAULT_FIRSTNAME)
        assertThat(testUser.lastName).isEqualTo(DEFAULT_LASTNAME)
        assertThat(testUser.email).isEqualTo(DEFAULT_EMAIL)
        assertThat(testUser.imageUrl).isEqualTo(DEFAULT_IMAGEURL)
        assertThat(testUser.langKey).isEqualTo(DEFAULT_LANGKEY)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun createUserWithExistingId() {
        val databaseSizeBeforeCreate = userRepository!!.findAll().size

        val authorities = HashSet<String>()
        authorities.add("ROLE_SCHOOL_ADMIN")
        val managedUserVM = ManagedUserVM().apply {
            id = 1L
            login = DEFAULT_LOGIN
            firstName = DEFAULT_FIRSTNAME
            lastName = DEFAULT_LASTNAME
            email = DEFAULT_EMAIL
            imageUrl = DEFAULT_IMAGEURL
            langKey = DEFAULT_LANGKEY
            password = DEFAULT_PASSWORD
            this.authorities = authorities
        }


        // An entity with an existing ID cannot be created, so this API call must fail
        restUserMockMvc!!.perform(post("/api/users")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(managedUserVM)))
                .andExpect(status().isBadRequest)

        // Validate the User in the database
        val userList = userRepository.findAll()
        assertThat(userList).hasSize(databaseSizeBeforeCreate)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun createUserWithExistingLogin() {
        // Initialize the database
        userRepository!!.saveAndFlush<User>(user)
        val databaseSizeBeforeCreate = userRepository.findAll().size

        val authorities = HashSet<String>()
        authorities.add("ROLE_SCHOOL_ADMIN")
        val managedUserVM = ManagedUserVM().apply {
            login = DEFAULT_LOGIN
            firstName = DEFAULT_FIRSTNAME
            lastName = DEFAULT_LASTNAME
            email = "anothermail@localhost"
            imageUrl = DEFAULT_IMAGEURL
            langKey = DEFAULT_LANGKEY
            password = DEFAULT_PASSWORD
            this.authorities = authorities
        }

        // Create the User
        restUserMockMvc!!.perform(post("/api/users")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(managedUserVM)))
                .andExpect(status().isBadRequest)

        // Validate the User in the database
        val userList = userRepository.findAll()
        assertThat(userList).hasSize(databaseSizeBeforeCreate)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun createUserWithExistingEmail() {
        // Initialize the database
        userRepository!!.saveAndFlush<User>(user)
        val databaseSizeBeforeCreate = userRepository.findAll().size

        val authorities = HashSet<String>()
        authorities.add("ROLE_SCHOOL_ADMIN")
        val managedUserVM = ManagedUserVM().apply {
            login = "anotherlogin"
            firstName = DEFAULT_FIRSTNAME
            lastName = DEFAULT_LASTNAME
            email = DEFAULT_EMAIL
            imageUrl = DEFAULT_IMAGEURL
            langKey = DEFAULT_LANGKEY
            password = DEFAULT_PASSWORD
            this.authorities = authorities
        }


        // Create the User
        restUserMockMvc!!.perform(post("/api/users")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(managedUserVM)))
                .andExpect(status().isBadRequest)

        // Validate the User in the database
        val userList = userRepository.findAll()
        assertThat(userList).hasSize(databaseSizeBeforeCreate)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun getAllUsers() {
        // Initialize the database
        userRepository!!.saveAndFlush<User>(user)

        // Get all the users
        restUserMockMvc!!.perform(get("/api/users?sort=id,desc")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN)))
                .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRSTNAME)))
                .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LASTNAME)))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
                .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGEURL)))
                .andExpect(jsonPath("$.[*].langKey").value(hasItem(DEFAULT_LANGKEY)))
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun getUser() {
        // Initialize the database
        userRepository!!.saveAndFlush<User>(user)

        // Get the user
        restUserMockMvc!!.perform(get("/api/users/{login}", user!!.login))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.login").value(user!!.login))
                .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRSTNAME))
                .andExpect(jsonPath("$.lastName").value(DEFAULT_LASTNAME))
                .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
                .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGEURL))
                .andExpect(jsonPath("$.langKey").value(DEFAULT_LANGKEY))
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun getNonExistingUser() {
        restUserMockMvc!!.perform(get("/api/users/unknown"))
                .andExpect(status().isNotFound)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun updateUser() {
        // Initialize the database
        userRepository!!.saveAndFlush<User>(user)
        val databaseSizeBeforeUpdate = userRepository.findAll().size

        // Update the user
        val updatedUser = userRepository.findOne(user!!.id)

        val authorities = HashSet<String>()
        authorities.add("ROLE_SCHOOL_ADMIN")
        val managedUserVM = ManagedUserVM().apply {
            id = updatedUser.id
            login = updatedUser.login
            firstName = UPDATED_FIRSTNAME
            lastName = UPDATED_LASTNAME
            email = UPDATED_EMAIL
            imageUrl = UPDATED_IMAGEURL
            isActivated = updatedUser.activated
            langKey = UPDATED_LANGKEY
            createdBy = updatedUser.createdBy
            createdDate = updatedUser.createdDate
            lastModifiedBy = updatedUser.lastModifiedBy
            lastModifiedDate = updatedUser.lastModifiedDate
            this.authorities = authorities
            password = UPDATED_PASSWORD
        }

        restUserMockMvc!!.perform(put("/api/users")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(managedUserVM)))
                .andExpect(status().isOk)

        // Validate the User in the database
        val userList = userRepository.findAll()
        assertThat(userList).hasSize(databaseSizeBeforeUpdate)
        val testUser = userList[userList.size - 1]
        assertThat(testUser.firstName).isEqualTo(UPDATED_FIRSTNAME)
        assertThat(testUser.lastName).isEqualTo(UPDATED_LASTNAME)
        assertThat(testUser.email).isEqualTo(UPDATED_EMAIL)
        assertThat(testUser.imageUrl).isEqualTo(UPDATED_IMAGEURL)
        assertThat(testUser.langKey).isEqualTo(UPDATED_LANGKEY)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun updateUserLogin() {
        // Initialize the database
        userRepository!!.saveAndFlush<User>(user)
        val databaseSizeBeforeUpdate = userRepository.findAll().size

        // Update the user
        val updatedUser = userRepository.findOne(user!!.id)

        val authorities = HashSet<String>()
        authorities.add("ROLE_SCHOOL_ADMIN")
        val managedUserVM = ManagedUserVM().apply {
            id = updatedUser.id
            login = UPDATED_LOGIN
            firstName = UPDATED_FIRSTNAME
            lastName = UPDATED_LASTNAME
            email = UPDATED_EMAIL
            imageUrl = UPDATED_IMAGEURL
            isActivated = updatedUser.activated
            langKey = UPDATED_LANGKEY
            createdBy = updatedUser.createdBy
            createdDate = updatedUser.createdDate
            lastModifiedBy = updatedUser.lastModifiedBy
            lastModifiedDate = updatedUser.lastModifiedDate
            this.authorities = authorities
            password = UPDATED_PASSWORD
        }

        restUserMockMvc!!.perform(put("/api/users")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(managedUserVM)))
                .andExpect(status().isOk)

        // Validate the User in the database
        val userList = userRepository.findAll()
        assertThat(userList).hasSize(databaseSizeBeforeUpdate)
        val testUser = userList[userList.size - 1]
        assertThat(testUser.login).isEqualTo(UPDATED_LOGIN)
        assertThat(testUser.firstName).isEqualTo(UPDATED_FIRSTNAME)
        assertThat(testUser.lastName).isEqualTo(UPDATED_LASTNAME)
        assertThat(testUser.email).isEqualTo(UPDATED_EMAIL)
        assertThat(testUser.imageUrl).isEqualTo(UPDATED_IMAGEURL)
        assertThat(testUser.langKey).isEqualTo(UPDATED_LANGKEY)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun updateUserExistingEmail() {
        // Initialize the database with 2 users
        userRepository!!.saveAndFlush<User>(user)

        val anotherUser = User()
        anotherUser.login = "jhipster"
        anotherUser.password = RandomStringUtils.random(60)
        anotherUser.activated = true
        anotherUser.email = "jhipster@localhost"
        anotherUser.firstName = "java"
        anotherUser.lastName = "hipster"
        anotherUser.imageUrl = ""
        anotherUser.langKey = "en"
        userRepository.saveAndFlush(anotherUser)

        // Update the user
        val updatedUser = userRepository.findOne(user!!.id)

        val authorities = HashSet<String>()
        authorities.add("ROLE_SCHOOL_ADMIN")
        val managedUserVM = ManagedUserVM().apply {
            id = updatedUser.id
            login = updatedUser.login
            firstName = updatedUser.firstName
            lastName = updatedUser.lastName
            email = "jhipster@localhost"
            imageUrl = updatedUser.imageUrl
            isActivated = updatedUser.activated
            langKey = updatedUser.langKey
            createdBy = updatedUser.createdBy
            createdDate = updatedUser.createdDate
            lastModifiedBy = updatedUser.lastModifiedBy
            lastModifiedDate = updatedUser.lastModifiedDate
            this.authorities = authorities
            password = updatedUser.password
        }


        restUserMockMvc!!.perform(put("/api/users")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(managedUserVM)))
                .andExpect(status().isBadRequest)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun updateUserExistingLogin() {
        // Initialize the database
        userRepository!!.saveAndFlush<User>(user)

        val anotherUser = User()
        anotherUser.login = "jhipster"
        anotherUser.password = RandomStringUtils.random(60)
        anotherUser.activated = true
        anotherUser.email = "jhipster@localhost"
        anotherUser.firstName = "java"
        anotherUser.lastName = "hipster"
        anotherUser.imageUrl = ""
        anotherUser.langKey = "en"
        userRepository.saveAndFlush(anotherUser)

        // Update the user
        val updatedUser = userRepository.findOne(user!!.id)

        val authorities = HashSet<String>()
        authorities.add("ROLE_SCHOOL_ADMIN")
        val managedUserVM = ManagedUserVM().apply {
            id = updatedUser.id
            login = "jhipster"
            firstName = updatedUser.firstName
            lastName = updatedUser.lastName
            email = updatedUser.email
            imageUrl = updatedUser.imageUrl
            isActivated = updatedUser.activated
            langKey = updatedUser.langKey
            createdBy = updatedUser.createdBy
            createdDate = updatedUser.createdDate
            lastModifiedBy = updatedUser.lastModifiedBy
            lastModifiedDate = updatedUser.lastModifiedDate
            this.authorities = authorities
            password = updatedUser.password
        }


        restUserMockMvc!!.perform(put("/api/users")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(managedUserVM)))
                .andExpect(status().isBadRequest)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun deleteUser() {
        // Initialize the database
        userRepository!!.saveAndFlush<User>(user)
        val databaseSizeBeforeDelete = userRepository.findAll().size

        // Delete the user
        restUserMockMvc!!.perform(delete("/api/users/{login}", user!!.login)
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk)

        // Validate the database is empty
        val userList = userRepository.findAll()
        assertThat(userList).hasSize(databaseSizeBeforeDelete - 1)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun getAllAuthorities() {
        restUserMockMvc!!.perform(get("/api/users/authorities")
                .accept(TestUtil.APPLICATION_JSON_UTF8)
                .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$").isArray)
                .andExpect(jsonPath("$").value(containsInAnyOrder("ROLE_SCHOOL_ADMIN", "ROLE_ADMIN", "ROLE_TEACHER", "ROLE_TIMETABLE_WATCHER")))
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun testUserEquals() {
        val user1 = User()
        user1.id = 1L
        val user2 = User()
        user2.id = user1.id
        assertThat(user1).isEqualTo(user2)
        user2.id = 2L
        assertThat(user1).isNotEqualTo(user2)
        user1.id = null
        assertThat(user1).isNotEqualTo(user2)
    }


    @Test
    open fun testUserDTOtoUser() {
        val userDTO = UserDTO(
                DEFAULT_ID,
                DEFAULT_LOGIN,
                DEFAULT_FIRSTNAME,
                DEFAULT_LASTNAME,
                DEFAULT_EMAIL,
                DEFAULT_IMAGEURL,
                true,
                DEFAULT_LANGKEY,
                DEFAULT_LOGIN, null,
                DEFAULT_LOGIN, null,
            mutableSetOf(AuthoritiesConstants.SCHOOL_ADMIN)
        )
        val user = userMapper!!.toEntity(userDTO)
        assertThat(user.id).isEqualTo(DEFAULT_ID)
        assertThat(user.login).isEqualTo(DEFAULT_LOGIN)
        assertThat(user.firstName).isEqualTo(DEFAULT_FIRSTNAME)
        assertThat(user.lastName).isEqualTo(DEFAULT_LASTNAME)
        assertThat(user.email).isEqualTo(DEFAULT_EMAIL)
        assertThat(user.activated).isEqualTo(true)
        assertThat(user.imageUrl).isEqualTo(DEFAULT_IMAGEURL)
        assertThat(user.langKey).isEqualTo(DEFAULT_LANGKEY)
        assertThat(user.createdBy).isNull()
        assertThat(user.createdDate).isNotNull()
        assertThat(user.lastModifiedBy).isNull()
        assertThat(user.lastModifiedDate).isNotNull()
        assertThat(user.authorities).extracting("name").containsExactly(AuthoritiesConstants.SCHOOL_ADMIN)
    }

    @Test
    open fun testUserToUserDTO() {
        user!!.id = DEFAULT_ID
        user!!.createdBy = DEFAULT_LOGIN
        user!!.createdDate = Instant.now()
        user!!.lastModifiedBy = DEFAULT_LOGIN
        user!!.lastModifiedDate = Instant.now()

        val authorities = HashSet<Authority>()
        val authority = Authority()
        authority.name = AuthoritiesConstants.SCHOOL_ADMIN
        authorities.add(authority)
        user!!.authorities = authorities

        val userDTO = userMapper!!.toDto(user!!)

        assertThat(userDTO.id).isEqualTo(DEFAULT_ID)
        assertThat(userDTO.login).isEqualTo(DEFAULT_LOGIN)
        assertThat(userDTO.firstName).isEqualTo(DEFAULT_FIRSTNAME)
        assertThat(userDTO.lastName).isEqualTo(DEFAULT_LASTNAME)
        assertThat(userDTO.email).isEqualTo(DEFAULT_EMAIL)
        assertThat(userDTO.isActivated).isEqualTo(true)
        assertThat(userDTO.imageUrl).isEqualTo(DEFAULT_IMAGEURL)
        assertThat(userDTO.langKey).isEqualTo(DEFAULT_LANGKEY)
        assertThat(userDTO.createdBy).isEqualTo(DEFAULT_LOGIN)
        assertThat(userDTO.createdDate).isEqualTo(user!!.createdDate)
        assertThat(userDTO.lastModifiedBy).isEqualTo(DEFAULT_LOGIN)
        assertThat(userDTO.lastModifiedDate).isEqualTo(user!!.lastModifiedDate)
        assertThat(userDTO.authorities).containsExactly(AuthoritiesConstants.SCHOOL_ADMIN)
        assertThat(userDTO.toString()).isNotNull()
    }

    @Test
    @Throws(Exception::class)
    open fun testAuthorityEquals() {
        val authorityA = Authority()
        assertThat(authorityA).isEqualTo(authorityA)
        assertThat(authorityA).isNotEqualTo(null)
        assertThat(authorityA).isNotEqualTo(Any())
        assertThat(authorityA.hashCode()).isEqualTo(0)
        assertThat(authorityA.toString()).isNotNull()

        val authorityB = Authority()
        assertThat(authorityA).isEqualTo(authorityB)

        authorityB.name = AuthoritiesConstants.ADMIN
        assertThat(authorityA).isNotEqualTo(authorityB)

        authorityA.name = AuthoritiesConstants.SCHOOL_ADMIN
        assertThat(authorityA).isNotEqualTo(authorityB)

        authorityB.name = AuthoritiesConstants.SCHOOL_ADMIN
        assertThat(authorityA).isEqualTo(authorityB)
        assertThat(authorityA.hashCode()).isEqualTo(authorityB.hashCode())
    }



}
