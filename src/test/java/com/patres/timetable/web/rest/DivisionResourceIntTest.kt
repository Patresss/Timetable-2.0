package com.patres.timetable.web.rest

import com.patres.timetable.TimetableApp
import com.patres.timetable.domain.Division
import com.patres.timetable.domain.enumeration.DivisionType
import com.patres.timetable.repository.DivisionRepository
import com.patres.timetable.service.DivisionService
import com.patres.timetable.service.dto.DivisionDTO
import com.patres.timetable.service.mapper.DivisionMapper
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
 * Test class for the DivisionResource REST controller.
 *
 * @see DivisionResource
 */
@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(TimetableApp::class))
open class DivisionResourceIntTest {

    @Autowired private
    lateinit var divisionRepository: DivisionRepository

    @Autowired
    lateinit var divisionMapper: DivisionMapper

    @Autowired
    lateinit var divisionService: DivisionService

    @Autowired
    lateinit var jacksonMessageConverter: MappingJackson2HttpMessageConverter

    @Autowired
    lateinit var pageableArgumentResolver: PageableHandlerMethodArgumentResolver

    @Autowired
    lateinit var exceptionTranslator: ExceptionTranslator

    lateinit private var restDivisionMockMvc: MockMvc

    private var division: Division = createEntity()

    companion object {

        private val DEFAULT_NAME = "AAAAAAAAAA"
        private val UPDATED_NAME = "BBBBBBBBBB"

        private val DEFAULT_SHORT_NAME = "AAAAAAAAAA"
        private val UPDATED_SHORT_NAME = "BBBBBBBBBB"

        private val DEFAULT_NUMBER_OF_PEOPLE = 1L
        private val UPDATED_NUMBER_OF_PEOPLE = 2L

        private val DEFAULT_DIVISION_TYPE = DivisionType.SCHOOL
        private val UPDATED_DIVISION_TYPE = DivisionType.CLASS

        private val DEFAULT_COLOR_BACKGROUND = "AAAAAAAAAA"
        private val UPDATED_COLOR_BACKGROUND = "BBBBBBBBBB"

        private val DEFAULT_COLOR_TEXT = "AAAAAAAAAA"
        private val UPDATED_COLOR_TEXT = "BBBBBBBBBB"

        fun createEntity(): Division {
            return Division(
                name = DEFAULT_NAME,
                shortName = DEFAULT_SHORT_NAME,
                numberOfPeople = DEFAULT_NUMBER_OF_PEOPLE,
                divisionType = DEFAULT_DIVISION_TYPE,
                colorBackground = DEFAULT_COLOR_BACKGROUND,
                colorText = DEFAULT_COLOR_TEXT)
        }

        fun createEntityDto(): DivisionDTO {
            return DivisionDTO(
                name = DEFAULT_NAME,
                shortName = DEFAULT_SHORT_NAME,
                numberOfPeople = DEFAULT_NUMBER_OF_PEOPLE,
                divisionType = DEFAULT_DIVISION_TYPE,
                colorBackground = DEFAULT_COLOR_BACKGROUND,
                colorText = DEFAULT_COLOR_TEXT)
        }
    }

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        restDivisionMockMvc = initMockMvc(DivisionResource(divisionService))
    }

    private fun initMockMvc(divisionResource: DivisionResource): MockMvc {
        return MockMvcBuilders.standaloneSetup(divisionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build()
    }

    @Before
    fun initTest() {
        division = createEntity()
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun createDivision() {
        val databaseSizeBeforeCreate = divisionRepository.findAll().size

        // Create the Division
        val divisionDTO = divisionMapper.toDto(division)
        restDivisionMockMvc.perform(post("/api/divisions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(divisionDTO)))
            .andExpect(status().isCreated)

        // Validate the Division in the database
        val divisionList = divisionRepository.findAll()
        assertThat(divisionList).hasSize(databaseSizeBeforeCreate + 1)
        val testDivision = divisionList[divisionList.size - 1]
        assertThat(testDivision.name).isEqualTo(DEFAULT_NAME)
        assertThat(testDivision.shortName).isEqualTo(DEFAULT_SHORT_NAME)
        assertThat(testDivision.numberOfPeople).isEqualTo(DEFAULT_NUMBER_OF_PEOPLE)
        assertThat(testDivision.divisionType).isEqualTo(DEFAULT_DIVISION_TYPE)
        assertThat(testDivision.colorBackground).isEqualTo(DEFAULT_COLOR_BACKGROUND)
        assertThat(testDivision.colorText).isEqualTo(DEFAULT_COLOR_TEXT)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun createDivisionWithExistingId() {
        val databaseSizeBeforeCreate = divisionRepository.findAll().size

        // Create the Division with an existing ID
        division.id = 1L
        val divisionDTO = divisionMapper.toDto(division)

        // An entity with an existing ID cannot be created, so this API call must fail
        restDivisionMockMvc.perform(post("/api/divisions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(divisionDTO)))
            .andExpect(status().isBadRequest)

        // Validate the Division in the database
        val divisionList = divisionRepository.findAll()
        assertThat(divisionList).hasSize(databaseSizeBeforeCreate)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    fun checkNameIsRequired() {
        val databaseSizeBeforeTest = divisionRepository.findAll().size
        // set the field null
        division.name = null

        // Create the Division, which fails.
        val divisionDTO = divisionMapper.toDto(division)

        restDivisionMockMvc.perform(post("/api/divisions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(divisionDTO)))
            .andExpect(status().isBadRequest)

        val divisionList = divisionRepository.findAll()
        assertThat(divisionList).hasSize(databaseSizeBeforeTest)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    fun checkDivisionTypeIsRequired() {
        val databaseSizeBeforeTest = divisionRepository.findAll().size
        // set the field null
        division.divisionType = null

        // Create the Division, which fails.
        val divisionDTO = divisionMapper.toDto(division)

        restDivisionMockMvc.perform(post("/api/divisions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(divisionDTO)))
            .andExpect(status().isBadRequest)

        val divisionList = divisionRepository.findAll()
        assertThat(divisionList).hasSize(databaseSizeBeforeTest)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun getAllDivisions() {
        // Initialize the database
        divisionRepository.saveAndFlush<Division>(division)

        // Get all the divisionList
        restDivisionMockMvc.perform(get("/api/divisions?sort=id,desc"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(division.id?.toInt())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].numberOfPeople").value(hasItem(DEFAULT_NUMBER_OF_PEOPLE.toInt())))
            .andExpect(jsonPath("$.[*].divisionType").value(hasItem(DEFAULT_DIVISION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].colorBackground").value(hasItem(DEFAULT_COLOR_BACKGROUND)))
            .andExpect(jsonPath("$.[*].colorText").value(hasItem(DEFAULT_COLOR_TEXT)))
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun getDivision() {
        // Initialize the database
        divisionRepository.saveAndFlush<Division>(division)

        // Get the division
        restDivisionMockMvc.perform(get("/api/divisions/{id}", division.id))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(division.id))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORT_NAME))
            .andExpect(jsonPath("$.numberOfPeople").value(DEFAULT_NUMBER_OF_PEOPLE.toInt()))
            .andExpect(jsonPath("$.divisionType").value(DEFAULT_DIVISION_TYPE.toString()))
            .andExpect(jsonPath("$.colorBackground").value(DEFAULT_COLOR_BACKGROUND))
            .andExpect(jsonPath("$.colorText").value(DEFAULT_COLOR_TEXT))
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun getNonExistingDivision() {
        // Get the division
        restDivisionMockMvc.perform(get("/api/divisions/{id}", java.lang.Long.MAX_VALUE))
            .andExpect(status().isNotFound)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun updateDivision() {
        // Initialize the database
        divisionRepository.saveAndFlush<Division>(division)
        val databaseSizeBeforeUpdate = divisionRepository.findAll().size

        // Update the division
        val updatedDivision = divisionRepository.findOne(division.id)

        updatedDivision.name = UPDATED_NAME
        updatedDivision.shortName = UPDATED_SHORT_NAME
        updatedDivision.numberOfPeople = UPDATED_NUMBER_OF_PEOPLE
        updatedDivision.divisionType = UPDATED_DIVISION_TYPE
        updatedDivision.colorBackground = UPDATED_COLOR_BACKGROUND
        updatedDivision.colorText = UPDATED_COLOR_TEXT
        val divisionDTO = divisionMapper.toDto(updatedDivision)

        restDivisionMockMvc.perform(put("/api/divisions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(divisionDTO)))
            .andExpect(status().isOk)

        // Validate the Division in the database
        val divisionList = divisionRepository.findAll()
        assertThat(divisionList).hasSize(databaseSizeBeforeUpdate)
        val testDivision = divisionList[divisionList.size - 1]
        assertThat(testDivision.name).isEqualTo(UPDATED_NAME)
        assertThat(testDivision.shortName).isEqualTo(UPDATED_SHORT_NAME)
        assertThat(testDivision.numberOfPeople).isEqualTo(UPDATED_NUMBER_OF_PEOPLE)
        assertThat(testDivision.divisionType).isEqualTo(UPDATED_DIVISION_TYPE)
        assertThat(testDivision.colorBackground).isEqualTo(UPDATED_COLOR_BACKGROUND)
        assertThat(testDivision.colorText).isEqualTo(UPDATED_COLOR_TEXT)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun updateNonExistingDivision() {
        val databaseSizeBeforeUpdate = divisionRepository.findAll().size

        // Create the Division
        val divisionDTO = divisionMapper.toDto(division)

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDivisionMockMvc.perform(put("/api/divisions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(divisionDTO)))
            .andExpect(status().isCreated)

        // Validate the Division in the database
        val divisionList = divisionRepository.findAll()
        assertThat(divisionList).hasSize(databaseSizeBeforeUpdate + 1)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun deleteDivision() {
        // Initialize the database
        divisionRepository.saveAndFlush<Division>(division)
        val databaseSizeBeforeDelete = divisionRepository.findAll().size

        // Get the division
        restDivisionMockMvc.perform(delete("/api/divisions/{id}", division.id)
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk)

        // Validate the database is empty
        val divisionList = divisionRepository.findAll()
        assertThat(divisionList).hasSize(databaseSizeBeforeDelete - 1)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun equalsVerifier() {
        val division1 = createEntity()
        division1.id = 1L
        val division2 = createEntity()
        division2.id = division1.id
        assertThat(division1).isEqualTo(division2)
        division2.id = 2L
        assertThat(division1).isNotEqualTo(division2)
        division1.id = null
        assertThat(division1).isNotEqualTo(division2)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun dtoEqualsVerifier() {
        val divisionDTO1 = createEntityDto()
        divisionDTO1.id = 1L
        val divisionDTO2 = createEntityDto()
        assertThat(divisionDTO1).isNotEqualTo(divisionDTO2)
        divisionDTO2.id = divisionDTO1.id
        assertThat(divisionDTO1).isEqualTo(divisionDTO2)
        divisionDTO2.id = 2L
        assertThat(divisionDTO1).isNotEqualTo(divisionDTO2)
        divisionDTO1.id = null
        assertThat(divisionDTO1).isNotEqualTo(divisionDTO2)
    }




}
