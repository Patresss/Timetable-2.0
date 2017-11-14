package com.patres.timetable.web.rest

import com.patres.timetable.TimetableApp
import com.patres.timetable.domain.Subject
import com.patres.timetable.repository.DivisionRepository
import com.patres.timetable.repository.SubjectRepository
import com.patres.timetable.service.SubjectService
import com.patres.timetable.service.dto.SubjectDTO
import com.patres.timetable.service.mapper.SubjectMapper
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
 * Test class for the SubjectResource REST controller.
 *
 * @see SubjectResource
 */
@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(TimetableApp::class))
open class SubjectResourceIntTest {

    @Autowired
    lateinit private var subjectRepository: SubjectRepository

    @Autowired
    lateinit private var divisionRepository: DivisionRepository

    @Autowired
    lateinit private var subjectMapper: SubjectMapper

    @Autowired
    lateinit private var subjectService: SubjectService

    @Autowired
    lateinit private var jacksonMessageConverter: MappingJackson2HttpMessageConverter

    @Autowired
    lateinit private var pageableArgumentResolver: PageableHandlerMethodArgumentResolver

    @Autowired
    lateinit private var exceptionTranslator: ExceptionTranslator

    lateinit private var restSubjectMockMvc: MockMvc

    private var subject: Subject = createEntity()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        restSubjectMockMvc = initMockMvc(SubjectResource(subjectService))
    }

    private fun initMockMvc(subjectResource: SubjectResource): MockMvc {
        return MockMvcBuilders.standaloneSetup(subjectResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build()
    }

    companion object {

        private val DEFAULT_NAME = "AAAAAAAAAA"
        private val UPDATED_NAME = "BBBBBBBBBB"

        private val DEFAULT_SHORT_NAME = "AAAAAAAAAA"
        private val UPDATED_SHORT_NAME = "BBBBBBBBBB"

        private val DEFAULT_COLOR_BACKGROUND = "AAAAAAAAAA"
        private val UPDATED_COLOR_BACKGROUND = "BBBBBBBBBB"

        private val DEFAULT_COLOR_TEXT = "AAAAAAAAAA"
        private val UPDATED_COLOR_TEXT = "BBBBBBBBBB"


        fun createEntity(): Subject {
            return Subject(
                name = DEFAULT_NAME
            ).apply {
                shortName = DEFAULT_SHORT_NAME
                colorBackground = DEFAULT_COLOR_BACKGROUND
                colorText = DEFAULT_COLOR_TEXT
            }
        }

        fun createEntityDto(): SubjectDTO {
            return SubjectDTO(
                name = DEFAULT_NAME
            ).apply {
                shortName = DEFAULT_SHORT_NAME
                colorBackground = DEFAULT_COLOR_BACKGROUND
                colorText = DEFAULT_COLOR_TEXT
            }
        }
    }

    @Before
    fun initTest() {
        subject = createEntity()
        val divisionOwner = DivisionResourceIntTest.createEntity()
        divisionRepository.saveAndFlush(divisionOwner)
        subject.divisionOwner = divisionOwner
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun createSubject() {
        val databaseSizeBeforeCreate = subjectRepository.findAll().size

        // Create the Subject
        val subjectDTO = subjectMapper.toDto(subject)
        restSubjectMockMvc.perform(post("/api/subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subjectDTO)))
            .andExpect(status().isCreated)

        // Validate the Subject in the database
        val subjectList = subjectRepository.findAll()
        assertThat(subjectList).hasSize(databaseSizeBeforeCreate + 1)
        val testSubject = subjectList[subjectList.size - 1]
        assertThat(testSubject.name).isEqualTo(DEFAULT_NAME)
        assertThat(testSubject.shortName).isEqualTo(DEFAULT_SHORT_NAME)
        assertThat(testSubject.colorBackground).isEqualTo(DEFAULT_COLOR_BACKGROUND)
        assertThat(testSubject.colorText).isEqualTo(DEFAULT_COLOR_TEXT)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun createSubjectWithExistingId() {
        val databaseSizeBeforeCreate = subjectRepository.findAll().size

        // Create the Subject with an existing ID
        subject.id = 1L
        val subjectDTO = subjectMapper.toDto(subject)

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubjectMockMvc.perform(post("/api/subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subjectDTO)))
            .andExpect(status().isBadRequest)

        // Validate the Alice in the database
        val subjectList = subjectRepository.findAll()
        assertThat(subjectList).hasSize(databaseSizeBeforeCreate)
    }


    @Test
    @Transactional
    @Throws(Exception::class)
    open fun checkNameIsRequired() {
        val databaseSizeBeforeTest = subjectRepository.findAll().size
        // set the field null
        subject.name = null

        // Create the Subject, which fails.
        val subjectDTO = subjectMapper.toDto(subject)

        restSubjectMockMvc.perform(post("/api/subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subjectDTO)))
            .andExpect(status().isBadRequest)

        val subjectList = subjectRepository.findAll()
        assertThat(subjectList).hasSize(databaseSizeBeforeTest)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun getAllSubjects() {
        // Initialize the database
        subjectRepository.saveAndFlush(subject)

        // Get all the subjectList
        restSubjectMockMvc.perform(get("/api/subjects?sort=id,desc"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subject.id?.toInt())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].colorBackground").value(hasItem(DEFAULT_COLOR_BACKGROUND)))
            .andExpect(jsonPath("$.[*].colorText").value(hasItem(DEFAULT_COLOR_TEXT)))
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun getSubject() {
        // Initialize the database
        subjectRepository.saveAndFlush(subject)

        // Get the subject
        restSubjectMockMvc.perform(get("/api/subjects/{id}", subject.id))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(subject.id))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORT_NAME))
            .andExpect(jsonPath("$.colorBackground").value(DEFAULT_COLOR_BACKGROUND))
            .andExpect(jsonPath("$.colorText").value(DEFAULT_COLOR_TEXT))
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun getNonExistingSubject() {
        // Get the subject
        restSubjectMockMvc.perform(get("/api/subjects/{id}", java.lang.Long.MAX_VALUE))
            .andExpect(status().isNotFound)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun updateSubject() {
        // Initialize the database
        subjectRepository.saveAndFlush(subject)
        val databaseSizeBeforeUpdate = subjectRepository.findAll().size

        // Update the subject
        val updatedSubject = subjectRepository.findOne(subject.id)
        updatedSubject.name = UPDATED_NAME
        updatedSubject.shortName = UPDATED_SHORT_NAME
        updatedSubject.colorBackground = UPDATED_COLOR_BACKGROUND
        updatedSubject.colorText = UPDATED_COLOR_TEXT
        val subjectDTO = subjectMapper.toDto(updatedSubject)

        restSubjectMockMvc.perform(put("/api/subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subjectDTO)))
            .andExpect(status().isOk)

        // Validate the Subject in the database
        val subjectList = subjectRepository.findAll()
        assertThat(subjectList).hasSize(databaseSizeBeforeUpdate)
        val testSubject = subjectList[subjectList.size - 1]
        assertThat(testSubject.name).isEqualTo(UPDATED_NAME)
        assertThat(testSubject.shortName).isEqualTo(UPDATED_SHORT_NAME)
        assertThat(testSubject.colorBackground).isEqualTo(UPDATED_COLOR_BACKGROUND)
        assertThat(testSubject.colorText).isEqualTo(UPDATED_COLOR_TEXT)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun updateNonExistingSubject() {
        val databaseSizeBeforeUpdate = subjectRepository.findAll().size

        // Create the Subject
        val subjectDTO = subjectMapper.toDto(subject)

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSubjectMockMvc.perform(put("/api/subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subjectDTO)))
            .andExpect(status().isCreated)

        // Validate the Subject in the database
        val subjectList = subjectRepository.findAll()
        assertThat(subjectList).hasSize(databaseSizeBeforeUpdate + 1)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun deleteSubject() {
        // Initialize the database
        subjectRepository.saveAndFlush(subject)
        val databaseSizeBeforeDelete = subjectRepository.findAll().size

        // Get the subject
        restSubjectMockMvc.perform(delete("/api/subjects/{id}", subject.id)
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk)

        // Validate the database is empty
        val subjectList = subjectRepository.findAll()
        assertThat(subjectList).hasSize(databaseSizeBeforeDelete - 1)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun equalsVerifier() {
        val subject1 = createEntity()
        subject1.id = 1L
        val subject2 = createEntity()
        subject2.id = subject1.id
        assertThat(subject1).isEqualTo(subject2)
        subject2.id = 2L
        assertThat(subject1).isNotEqualTo(subject2)
        subject1.id = null
        assertThat(subject1).isNotEqualTo(subject2)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun dtoEqualsVerifier() {
        val subjectDTO1 = createEntityDto()
        subjectDTO1.id = 1L
        val subjectDTO2 = createEntityDto()
        assertThat(subjectDTO1).isNotEqualTo(subjectDTO2)
        subjectDTO2.id = subjectDTO1.id
        assertThat(subjectDTO1).isEqualTo(subjectDTO2)
        subjectDTO2.id = 2L
        assertThat(subjectDTO1).isNotEqualTo(subjectDTO2)
        subjectDTO1.id = null
        assertThat(subjectDTO1).isNotEqualTo(subjectDTO2)
    }



}
