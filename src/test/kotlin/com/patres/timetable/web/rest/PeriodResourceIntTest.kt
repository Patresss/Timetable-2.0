package com.patres.timetable.web.rest

import com.patres.timetable.TimetableApp
import com.patres.timetable.domain.Period
import com.patres.timetable.repository.DivisionRepository
import com.patres.timetable.repository.PeriodRepository
import com.patres.timetable.service.PeriodService
import com.patres.timetable.service.dto.PeriodDTO
import com.patres.timetable.service.mapper.PeriodMapper
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
 * Test class for the PeriodResource REST controller.
 *
 * @see PeriodResource
 */
@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(TimetableApp::class))
open class PeriodResourceIntTest {

    @Autowired
    lateinit private var periodRepository: PeriodRepository

    @Autowired
    lateinit private var divisionRepository: DivisionRepository

    @Autowired
    lateinit private var periodMapper: PeriodMapper

    @Autowired
    lateinit private var periodService: PeriodService

    @Autowired
    lateinit private var jacksonMessageConverter: MappingJackson2HttpMessageConverter

    @Autowired
    lateinit private var pageableArgumentResolver: PageableHandlerMethodArgumentResolver

    @Autowired
    lateinit private var exceptionTranslator: ExceptionTranslator

    lateinit private var restPeriodMockMvc: MockMvc

    private var period: Period = createEntity()

    companion object {
        private val DEFAULT_NAME = "AAAAAAAAAA"
        private val UPDATED_NAME = "BBBBBBBBBB"

        fun createEntity(): Period {
            return Period(DEFAULT_NAME)
        }

        fun createEntityDto(): PeriodDTO {
            return PeriodDTO(DEFAULT_NAME)
        }
    }

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        restPeriodMockMvc = initMockMvc(PeriodResource(periodService))
    }

    private fun initMockMvc(periodResource: PeriodResource): MockMvc {
        return MockMvcBuilders.standaloneSetup(periodResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build()
    }

    @Before
    fun initTest() {
        period = createEntity()
        val divisionOwner = DivisionResourceIntTest.createEntity()
        divisionRepository.saveAndFlush(divisionOwner)
        period.divisionOwner = divisionOwner
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun createPeriod() {
        val databaseSizeBeforeCreate = periodRepository.findAll().size

        // Create the Period
        val periodDTO = periodMapper.toDto(period)
        restPeriodMockMvc.perform(post("/api/periods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodDTO)))
            .andExpect(status().isCreated)

        // Validate the Period in the database
        val periodList = periodRepository.findAll()
        assertThat(periodList).hasSize(databaseSizeBeforeCreate + 1)
        val testPeriod = periodList[periodList.size - 1]
        assertThat(testPeriod.name).isEqualTo(DEFAULT_NAME)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun createPeriodWithExistingId() {
        val databaseSizeBeforeCreate = periodRepository.findAll().size

        // Create the Period with an existing ID
        period.id = 1L
        val periodDTO = periodMapper.toDto(period)

        // An entity with an existing ID cannot be created, so this API call must fail
        restPeriodMockMvc.perform(post("/api/periods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodDTO)))
            .andExpect(status().isBadRequest)

        // Validate the Alice in the database
        val periodList = periodRepository.findAll()
        assertThat(periodList).hasSize(databaseSizeBeforeCreate)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun getAllPeriods() {
        // Initialize the database
        periodRepository.saveAndFlush<Period>(period)

        // Get all the periodList
        restPeriodMockMvc.perform(get("/api/periods?sort=id,desc"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(period.id?.toInt())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun getPeriod() {
        // Initialize the database
        periodRepository.saveAndFlush<Period>(period)

        // Get the period
        restPeriodMockMvc.perform(get("/api/periods/{id}", period.id))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(period.id))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun getNonExistingPeriod() {
        // Get the period
        restPeriodMockMvc.perform(get("/api/periods/{id}", java.lang.Long.MAX_VALUE))
            .andExpect(status().isNotFound)
    }


    @Test
    @Transactional
    @Throws(Exception::class)
    fun checkNameIsRequired() {
        val databaseSizeBeforeTest = periodRepository.findAll().size
        // set the field null
        period.name = null

        // Create the Period, which fails.
        val periodDTO = periodMapper.toDto(period)

        restPeriodMockMvc.perform(post("/api/periods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodDTO)))
            .andExpect(status().isBadRequest)

        val periodList = periodRepository.findAll()
        assertThat(periodList).hasSize(databaseSizeBeforeTest)
    }


    @Test
    @Transactional
    @Throws(Exception::class)
    open fun updatePeriod() {
        // Initialize the database
        periodRepository.saveAndFlush<Period>(period)
        val databaseSizeBeforeUpdate = periodRepository.findAll().size

        // Update the period
        val updatedPeriod = periodRepository.findOne(period.id)
        updatedPeriod.name = UPDATED_NAME
        val periodDTO = periodMapper.toDto(updatedPeriod)

        restPeriodMockMvc.perform(put("/api/periods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodDTO)))
            .andExpect(status().isOk)

        // Validate the Period in the database
        val periodList = periodRepository.findAll()
        assertThat(periodList).hasSize(databaseSizeBeforeUpdate)
        val testPeriod = periodList[periodList.size - 1]
        assertThat(testPeriod.name).isEqualTo(UPDATED_NAME)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun updateNonExistingPeriod() {
        val databaseSizeBeforeUpdate = periodRepository.findAll().size

        // Create the Period
        val periodDTO = periodMapper.toDto(period)

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPeriodMockMvc.perform(put("/api/periods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodDTO)))
            .andExpect(status().isCreated)

        // Validate the Period in the database
        val periodList = periodRepository.findAll()
        assertThat(periodList).hasSize(databaseSizeBeforeUpdate + 1)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun deletePeriod() {
        // Initialize the database
        periodRepository.saveAndFlush<Period>(period)
        val databaseSizeBeforeDelete = periodRepository.findAll().size

        // Get the period
        restPeriodMockMvc.perform(delete("/api/periods/{id}", period.id)
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk)

        // Validate the database is empty
        val periodList = periodRepository.findAll()
        assertThat(periodList).hasSize(databaseSizeBeforeDelete - 1)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun equalsVerifier() {
        val period1 = createEntity()
        period1.id = 1L
        val period2 = createEntity()
        period2.id = period1.id
        assertThat(period1).isEqualTo(period2)
        period2.id = 2L
        assertThat(period1).isNotEqualTo(period2)
        period1.id = null
        assertThat(period1).isNotEqualTo(period2)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun dtoEqualsVerifier() {
        val periodDTO1 = createEntityDto()
        periodDTO1.id = 1L
        val periodDTO2 = createEntityDto()
        assertThat(periodDTO1).isNotEqualTo(periodDTO2)
        periodDTO2.id = periodDTO1.id
        assertThat(periodDTO1).isEqualTo(periodDTO2)
        periodDTO2.id = 2L
        assertThat(periodDTO1).isNotEqualTo(periodDTO2)
        periodDTO1.id = null
        assertThat(periodDTO1).isNotEqualTo(periodDTO2)
    }




}
