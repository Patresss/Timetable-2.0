package com.patres.timetable.web.rest

import com.patres.timetable.TimetableApp
import com.patres.timetable.domain.Teacher
import com.patres.timetable.repository.DivisionRepository
import com.patres.timetable.repository.TeacherRepository
import com.patres.timetable.service.TeacherService
import com.patres.timetable.service.dto.TeacherDTO
import com.patres.timetable.service.mapper.TeacherMapper
import com.patres.timetable.web.rest.errors.ExceptionTranslator
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.hasItem
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.transaction.annotation.Transactional


/**
 * Test class for the TeacherResource REST controller.
 *
 * @see TeacherResource
 */
@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(TimetableApp::class))
open class TeacherResourceIntTest {

    @Autowired
    lateinit private var teacherRepository: TeacherRepository

    @Autowired
    lateinit private var divisionRepository: DivisionRepository

    @Autowired
    lateinit private var teacherMapper: TeacherMapper

    @Autowired
    lateinit private var teacherService: TeacherService

    @Autowired
    lateinit private var jacksonMessageConverter: MappingJackson2HttpMessageConverter

    @Autowired
    lateinit private var pageableArgumentResolver: PageableHandlerMethodArgumentResolver

    @Autowired
    lateinit private var exceptionTranslator: ExceptionTranslator

    lateinit private var restTeacherMockMvc: MockMvc

    private var teacher: Teacher = createEntity()


    companion object {

        private val DEFAULT_NAME = "AAAAAAAAAA"
        private val UPDATED_NAME = "BBBBBBBBBB"

        private val DEFAULT_SURNAME = "AAAAAAAAAA"
        private val UPDATED_SURNAME = "BBBBBBBBBB"

        private val DEFAULT_DEGREE = "AAAAAAAAAA"
        private val UPDATED_DEGREE = "BBBBBBBBBB"

        private val DEFAULT_SHORT_NAME = "AAAAAAAAAA"
        private val UPDATED_SHORT_NAME = "BBBBBBBBBB"

        fun createEntity(): Teacher {
            return Teacher(
                name = DEFAULT_NAME,
                surname = DEFAULT_SURNAME
            ).apply {
                degree = DEFAULT_DEGREE
                shortName = DEFAULT_SHORT_NAME
            }
        }

        fun createEntityDto(): TeacherDTO {
            return TeacherDTO(
                name = DEFAULT_NAME,
                surname = DEFAULT_SURNAME
            ).apply {
                degree = DEFAULT_DEGREE
                shortName = DEFAULT_SHORT_NAME
            }
        }

    }

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        restTeacherMockMvc = initMockMvc(TeacherResource(teacherService))
    }

    private fun initMockMvc(teacherResource: TeacherResource): MockMvc {
        return MockMvcBuilders.standaloneSetup(teacherResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build()
    }


    @Before
    fun initTest() {
        teacher = createEntity()
        val divisionOwner = DivisionResourceIntTest.createEntity()
        divisionRepository.saveAndFlush(divisionOwner)
        teacher.divisionOwner = divisionOwner
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun createTeacher() {
        val databaseSizeBeforeCreate = teacherRepository.findAll().size

        // Create the Teacher
        val teacherDTO = teacherMapper.toDto(teacher)
        restTeacherMockMvc.perform(post("/api/teachers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teacherDTO)))
            .andExpect(status().isCreated)

        // Validate the Teacher in the database
        val teacherList = teacherRepository.findAll()
        assertThat(teacherList).hasSize(databaseSizeBeforeCreate + 1)
        val testTeacher = teacherList[teacherList.size - 1]
        assertThat(testTeacher.name).isEqualTo(DEFAULT_NAME)
        assertThat(testTeacher.surname).isEqualTo(DEFAULT_SURNAME)
        assertThat(testTeacher.degree).isEqualTo(DEFAULT_DEGREE)
        assertThat(testTeacher.shortName).isEqualTo(DEFAULT_SHORT_NAME)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun createTeacherWithExistingId() {
        val databaseSizeBeforeCreate = teacherRepository.findAll().size

        // Create the Teacher with an existing ID
        teacher.id = 1L
        val teacherDTO = teacherMapper.toDto(teacher)

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeacherMockMvc.perform(post("/api/teachers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teacherDTO)))
            .andExpect(status().isBadRequest)

        // Validate the Alice in the database
        val teacherList = teacherRepository.findAll()
        assertThat(teacherList).hasSize(databaseSizeBeforeCreate)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun checkNameIsRequired() {
        val databaseSizeBeforeTest = teacherRepository.findAll().size
        // set the field null
        teacher.name = null

        // Create the Teacher, which fails.
        val teacherDTO = teacherMapper.toDto(teacher)

        restTeacherMockMvc.perform(post("/api/teachers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teacherDTO)))
            .andExpect(status().isBadRequest)

        val teacherList = teacherRepository.findAll()
        assertThat(teacherList).hasSize(databaseSizeBeforeTest)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun checkSurnameIsRequired() {
        val databaseSizeBeforeTest = teacherRepository.findAll().size
        // set the field null
        teacher.surname = null

        // Create the Teacher, which fails.
        val teacherDTO = teacherMapper.toDto(teacher)

        restTeacherMockMvc.perform(post("/api/teachers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teacherDTO)))
            .andExpect(status().isBadRequest)

        val teacherList = teacherRepository.findAll()
        assertThat(teacherList).hasSize(databaseSizeBeforeTest)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun getAllTeachers() {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher)

        // Get all the teacherList
        restTeacherMockMvc.perform(get("/api/teachers?sort=id,desc"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teacher.id?.toInt())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].surname").value(hasItem(DEFAULT_SURNAME)))
            .andExpect(jsonPath("$.[*].degree").value(hasItem(DEFAULT_DEGREE)))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME)))
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun getTeacher() {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher)

        // Get the teacher
        restTeacherMockMvc.perform(get("/api/teachers/{id}", teacher.id))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(teacher.id))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.surname").value(DEFAULT_SURNAME))
            .andExpect(jsonPath("$.degree").value(DEFAULT_DEGREE))
            .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORT_NAME))
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun getNonExistingTeacher() {
        // Get the teacher
        restTeacherMockMvc.perform(get("/api/teachers/{id}", java.lang.Long.MAX_VALUE))
            .andExpect(status().isNotFound)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun updateTeacher() {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher)
        val databaseSizeBeforeUpdate = teacherRepository.findAll().size

        // Update the teacher
        val updatedTeacher = teacherRepository.findOne(teacher.id)

        updatedTeacher.name = UPDATED_NAME
        updatedTeacher.surname = UPDATED_SURNAME
        updatedTeacher.degree = UPDATED_DEGREE
        updatedTeacher.shortName = UPDATED_SHORT_NAME
        val teacherDTO = teacherMapper.toDto(updatedTeacher)

        restTeacherMockMvc.perform(put("/api/teachers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teacherDTO)))
            .andExpect(status().isOk)

        // Validate the Teacher in the database
        val teacherList = teacherRepository.findAll()
        assertThat(teacherList).hasSize(databaseSizeBeforeUpdate)
        val testTeacher = teacherList[teacherList.size - 1]
        assertThat(testTeacher.name).isEqualTo(UPDATED_NAME)
        assertThat(testTeacher.surname).isEqualTo(UPDATED_SURNAME)
        assertThat(testTeacher.degree).isEqualTo(UPDATED_DEGREE)
        assertThat(testTeacher.shortName).isEqualTo(UPDATED_SHORT_NAME)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun updateNonExistingTeacher() {
        val databaseSizeBeforeUpdate = teacherRepository.findAll().size

        // Create the Teacher
        val teacherDTO = teacherMapper.toDto(teacher)

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTeacherMockMvc.perform(put("/api/teachers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teacherDTO)))
            .andExpect(status().isCreated)

        // Validate the Teacher in the database
        val teacherList = teacherRepository.findAll()
        assertThat(teacherList).hasSize(databaseSizeBeforeUpdate + 1)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun deleteTeacher() {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher)
        val databaseSizeBeforeDelete = teacherRepository.findAll().size

        // Get the teacher
        restTeacherMockMvc.perform(delete("/api/teachers/{id}", teacher.id)
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk)

        // Validate the database is empty
        val teacherList = teacherRepository.findAll()
        assertThat(teacherList).hasSize(databaseSizeBeforeDelete - 1)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun equalsVerifier() {
        val teacher1 = createEntity()
        teacher1.id = 1L
        val teacher2 = createEntity()
        teacher2.id = teacher1.id
        assertThat(teacher1).isEqualTo(teacher2)
        teacher2.id = 2L
        assertThat(teacher1).isNotEqualTo(teacher2)
        teacher1.id = null
        assertThat(teacher1).isNotEqualTo(teacher2)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun dtoEqualsVerifier() {
        val teacherDTO1 = createEntityDto()
        teacherDTO1.id = 1L
        val teacherDTO2 = createEntityDto()
        assertThat(teacherDTO1).isNotEqualTo(teacherDTO2)
        teacherDTO2.id = teacherDTO1.id
        assertThat(teacherDTO1).isEqualTo(teacherDTO2)
        teacherDTO2.id = 2L
        assertThat(teacherDTO1).isNotEqualTo(teacherDTO2)
        teacherDTO1.id = null
        assertThat(teacherDTO1).isNotEqualTo(teacherDTO2)
    }


}
