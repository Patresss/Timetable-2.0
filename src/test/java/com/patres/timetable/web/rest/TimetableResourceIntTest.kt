package com.patres.timetable.web.rest

import com.patres.timetable.TimetableApp
import com.patres.timetable.domain.Timetable
import com.patres.timetable.domain.enumeration.EventType
import com.patres.timetable.repository.TimetableRepository
import com.patres.timetable.service.TimetableService
import com.patres.timetable.service.dto.TimetableDTO
import com.patres.timetable.service.mapper.TimetableMapper
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
import java.time.LocalDate
import java.time.ZoneId


/**
 * Test class for the TimetableResource REST controller.
 *
 * @see TimetableResource
 */
@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(TimetableApp::class))
open class TimetableResourceIntTest {

    @Autowired
    lateinit private var timetableRepository: TimetableRepository

    @Autowired
    lateinit private var timetableMapper: TimetableMapper

    @Autowired
    lateinit private var timetableService: TimetableService

    @Autowired
    lateinit private var jacksonMessageConverter: MappingJackson2HttpMessageConverter

    @Autowired
    lateinit private var pageableArgumentResolver: PageableHandlerMethodArgumentResolver

    @Autowired
    lateinit private var exceptionTranslator: ExceptionTranslator

    lateinit private var restTimetableMockMvc: MockMvc

    private var timetable: Timetable = createEntity()

    companion object {

        private val DEFAULT_TITLE = "AAAAAAAAAA"
        private val UPDATED_TITLE = "BBBBBBBBBB"

        private val DEFAULT_START_TIME = 1L
        private val UPDATED_START_TIME = 2L

        private val DEFAULT_END_TIME = 1L
        private val UPDATED_END_TIME = 2L

        private val DEFAULT_START_DATE = LocalDate.ofEpochDay(0L)
        private val UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault())

        private val DEFAULT_END_DATE = LocalDate.ofEpochDay(0L)
        private val UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault())

        private val DEFAULT_DATE = LocalDate.ofEpochDay(0L)
        private val UPDATED_DATE = LocalDate.now(ZoneId.systemDefault())

        private val DEFAULT_TYPE = EventType.LESSON
        private val UPDATED_TYPE = EventType.SUBSTITUTION

        private val DEFAULT_EVERY_WEEK = 1L
        private val UPDATED_EVERY_WEEK = 2L

        private val DEFAULT_START_WITH_WEEK = 1L
        private val UPDATED_START_WITH_WEEK = 2L

        private val DEFAULT_DESCRIPTION = "AAAAAAAAAA"
        private val UPDATED_DESCRIPTION = "BBBBBBBBBB"

        private val DEFAULT_COLOR_BACKGROUND = "AAAAAAAAAA"
        private val UPDATED_COLOR_BACKGROUND = "BBBBBBBBBB"

        private val DEFAULT_COLOR_TEXT = "AAAAAAAAAA"
        private val UPDATED_COLOR_TEXT = "BBBBBBBBBB"

        private val DEFAULT_IN_MONDAY = false
        private val UPDATED_IN_MONDAY = true

        private val DEFAULT_IN_TUESDAY = false
        private val UPDATED_IN_TUESDAY = true

        private val DEFAULT_IN_WEDNESDAY = false
        private val UPDATED_IN_WEDNESDAY = true

        private val DEFAULT_IN_THURSDAY = false
        private val UPDATED_IN_THURSDAY = true

        private val DEFAULT_IN_FRIDAY = false
        private val UPDATED_IN_FRIDAY = true

        private val DEFAULT_IN_SATURDAY = false
        private val UPDATED_IN_SATURDAY = true

        private val DEFAULT_IN_SUNDAY = false
        private val UPDATED_IN_SUNDAY = true

        fun createEntity(): Timetable {
            return Timetable(
                title = DEFAULT_TITLE,
                type = DEFAULT_TYPE
            ).apply {
                startTime = DEFAULT_START_TIME
                endTime = DEFAULT_END_TIME
                startDate = DEFAULT_START_DATE
                endDate = DEFAULT_END_DATE
                date = DEFAULT_DATE
                type = DEFAULT_TYPE
                everyWeek = DEFAULT_EVERY_WEEK
                startWithWeek = DEFAULT_START_WITH_WEEK
                description = DEFAULT_DESCRIPTION
                colorBackground = DEFAULT_COLOR_BACKGROUND
                colorText = DEFAULT_COLOR_TEXT
                isInMonday = DEFAULT_IN_MONDAY
                isInTuesday = DEFAULT_IN_TUESDAY
                isInWednesday = DEFAULT_IN_WEDNESDAY
                isInThursday = DEFAULT_IN_THURSDAY
                isInFriday = DEFAULT_IN_FRIDAY
                isInSaturday = DEFAULT_IN_SATURDAY
                isInSunday = DEFAULT_IN_SUNDAY
            }
        }

        fun createEntityDto(): TimetableDTO {
            return TimetableDTO(
                title = DEFAULT_TITLE,
                type = DEFAULT_TYPE
            ).apply {
                startTime = DEFAULT_START_TIME
                endTime = DEFAULT_END_TIME
                startDate = DEFAULT_START_DATE
                endDate = DEFAULT_END_DATE
                date = DEFAULT_DATE
                type = DEFAULT_TYPE
                everyWeek = DEFAULT_EVERY_WEEK
                startWithWeek = DEFAULT_START_WITH_WEEK
                description = DEFAULT_DESCRIPTION
                colorBackground = DEFAULT_COLOR_BACKGROUND
                colorText = DEFAULT_COLOR_TEXT
                isInMonday = DEFAULT_IN_MONDAY
                isInTuesday = DEFAULT_IN_TUESDAY
                isInWednesday = DEFAULT_IN_WEDNESDAY
                isInThursday = DEFAULT_IN_THURSDAY
                isInFriday = DEFAULT_IN_FRIDAY
                isInSaturday = DEFAULT_IN_SATURDAY
                isInSunday = DEFAULT_IN_SUNDAY
            }
        }
    }

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        restTimetableMockMvc = initMockMvc(TimetableResource(timetableService))
    }

    private fun initMockMvc(timetableResource: TimetableResource): MockMvc {
        return MockMvcBuilders.standaloneSetup(timetableResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build()
    }

    @Before
    fun initTest() {
        timetable = createEntity()
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun createTimetable() {
        val databaseSizeBeforeCreate = timetableRepository.findAll().size

        // Create the Timetable
        val timetableDTO = timetableMapper.toDto(timetable)
        restTimetableMockMvc.perform(post("/api/timetables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timetableDTO)))
            .andExpect(status().isCreated)

        // Validate the Timetable in the database
        val timetableList = timetableRepository.findAll()
        assertThat(timetableList).hasSize(databaseSizeBeforeCreate + 1)
        val testTimetable = timetableList[timetableList.size - 1]
        assertThat(testTimetable.title).isEqualTo(DEFAULT_TITLE)
        assertThat(testTimetable.startTime).isEqualTo(DEFAULT_START_TIME)
        assertThat(testTimetable.endTime).isEqualTo(DEFAULT_END_TIME)
        assertThat(testTimetable.startDate).isEqualTo(DEFAULT_START_DATE)
        assertThat(testTimetable.endDate).isEqualTo(DEFAULT_END_DATE)
        assertThat(testTimetable.date).isEqualTo(DEFAULT_DATE)
        assertThat(testTimetable.type).isEqualTo(DEFAULT_TYPE)
        assertThat(testTimetable.everyWeek).isEqualTo(DEFAULT_EVERY_WEEK)
        assertThat(testTimetable.startWithWeek).isEqualTo(DEFAULT_START_WITH_WEEK)
        assertThat(testTimetable.description).isEqualTo(DEFAULT_DESCRIPTION)
        assertThat(testTimetable.colorBackground).isEqualTo(DEFAULT_COLOR_BACKGROUND)
        assertThat(testTimetable.colorText).isEqualTo(DEFAULT_COLOR_TEXT)
        assertThat(testTimetable.isInMonday).isEqualTo(DEFAULT_IN_MONDAY)
        assertThat(testTimetable.isInTuesday).isEqualTo(DEFAULT_IN_TUESDAY)
        assertThat(testTimetable.isInWednesday).isEqualTo(DEFAULT_IN_WEDNESDAY)
        assertThat(testTimetable.isInThursday).isEqualTo(DEFAULT_IN_THURSDAY)
        assertThat(testTimetable.isInFriday).isEqualTo(DEFAULT_IN_FRIDAY)
        assertThat(testTimetable.isInSaturday).isEqualTo(DEFAULT_IN_SATURDAY)
        assertThat(testTimetable.isInSunday).isEqualTo(DEFAULT_IN_SUNDAY)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun createTimetableWithExistingId() {
        val databaseSizeBeforeCreate = timetableRepository.findAll().size

        // Create the Timetable with an existing ID
        timetable.id = 1L
        val timetableDTO = timetableMapper.toDto(timetable)

        // An entity with an existing ID cannot be created, so this API call must fail
        restTimetableMockMvc.perform(post("/api/timetables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timetableDTO)))
            .andExpect(status().isBadRequest)

        // Validate the Alice in the database
        val timetableList = timetableRepository.findAll()
        assertThat(timetableList).hasSize(databaseSizeBeforeCreate)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun checkTitleIsRequired() {
        val databaseSizeBeforeTest = timetableRepository.findAll().size
        // set the field null
        timetable.title = null

        // Create the Timetable, which fails.
        val timetableDTO = timetableMapper.toDto(timetable)

        restTimetableMockMvc.perform(post("/api/timetables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timetableDTO)))
            .andExpect(status().isBadRequest)

        val timetableList = timetableRepository.findAll()
        assertThat(timetableList).hasSize(databaseSizeBeforeTest)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun checkTypeIsRequired() {
        val databaseSizeBeforeTest = timetableRepository.findAll().size
        // set the field null
        timetable.type = null

        // Create the Timetable, which fails.
        val timetableDTO = timetableMapper.toDto(timetable)

        restTimetableMockMvc.perform(post("/api/timetables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timetableDTO)))
            .andExpect(status().isBadRequest)

        val timetableList = timetableRepository.findAll()
        assertThat(timetableList).hasSize(databaseSizeBeforeTest)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun getAllTimetables() {
        // Initialize the database
        timetableRepository.saveAndFlush(timetable)

        // Get all the timetableList
        restTimetableMockMvc.perform(get("/api/timetables?sort=id,desc"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timetable.id?.toInt())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toInt())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toInt())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].everyWeek").value(hasItem(DEFAULT_EVERY_WEEK.toInt())))
            .andExpect(jsonPath("$.[*].startWithWeek").value(hasItem(DEFAULT_START_WITH_WEEK.toInt())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].colorBackground").value(hasItem(DEFAULT_COLOR_BACKGROUND)))
            .andExpect(jsonPath("$.[*].colorText").value(hasItem(DEFAULT_COLOR_TEXT)))
            .andExpect(jsonPath("$.[*].inMonday").value(hasItem<Boolean>(DEFAULT_IN_MONDAY)))
            .andExpect(jsonPath("$.[*].inTuesday").value(hasItem<Boolean>(DEFAULT_IN_TUESDAY)))
            .andExpect(jsonPath("$.[*].inWednesday").value(hasItem<Boolean>(DEFAULT_IN_WEDNESDAY)))
            .andExpect(jsonPath("$.[*].inThursday").value(hasItem<Boolean>(DEFAULT_IN_THURSDAY)))
            .andExpect(jsonPath("$.[*].inFriday").value(hasItem<Boolean>(DEFAULT_IN_FRIDAY)))
            .andExpect(jsonPath("$.[*].inSaturday").value(hasItem<Boolean>(DEFAULT_IN_SATURDAY)))
            .andExpect(jsonPath("$.[*].inSunday").value(hasItem<Boolean>(DEFAULT_IN_SUNDAY)))
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun getTimetable() {
        // Initialize the database
        timetableRepository.saveAndFlush(timetable)

        // Get the timetable
        restTimetableMockMvc.perform(get("/api/timetables/{id}", timetable.id))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(timetable.id))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toInt()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toInt()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.everyWeek").value(DEFAULT_EVERY_WEEK.toInt()))
            .andExpect(jsonPath("$.startWithWeek").value(DEFAULT_START_WITH_WEEK.toInt()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.colorBackground").value(DEFAULT_COLOR_BACKGROUND))
            .andExpect(jsonPath("$.colorText").value(DEFAULT_COLOR_TEXT))
            .andExpect(jsonPath("$.inMonday").value(DEFAULT_IN_MONDAY))
            .andExpect(jsonPath("$.inTuesday").value(DEFAULT_IN_TUESDAY))
            .andExpect(jsonPath("$.inWednesday").value(DEFAULT_IN_WEDNESDAY))
            .andExpect(jsonPath("$.inThursday").value(DEFAULT_IN_THURSDAY))
            .andExpect(jsonPath("$.inFriday").value(DEFAULT_IN_FRIDAY))
            .andExpect(jsonPath("$.inSaturday").value(DEFAULT_IN_SATURDAY))
            .andExpect(jsonPath("$.inSunday").value(DEFAULT_IN_SUNDAY))
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun getNonExistingTimetable() {
        // Get the timetable
        restTimetableMockMvc.perform(get("/api/timetables/{id}", java.lang.Long.MAX_VALUE))
            .andExpect(status().isNotFound)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun updateTimetable() {
        // Initialize the database
        timetableRepository.saveAndFlush(timetable)
        val databaseSizeBeforeUpdate = timetableRepository.findAll().size

        // Update the timetable
        val updatedTimetable = timetableRepository.findOne(timetable.id)

        updatedTimetable.title = UPDATED_TITLE
        updatedTimetable.startTime = UPDATED_START_TIME
        updatedTimetable.endTime = UPDATED_END_TIME
        updatedTimetable.startDate = UPDATED_START_DATE
        updatedTimetable.endDate = UPDATED_END_DATE
        updatedTimetable.date = UPDATED_DATE
        updatedTimetable.type = UPDATED_TYPE
        updatedTimetable.everyWeek = UPDATED_EVERY_WEEK
        updatedTimetable.startWithWeek = UPDATED_START_WITH_WEEK
        updatedTimetable.description = UPDATED_DESCRIPTION
        updatedTimetable.colorBackground = UPDATED_COLOR_BACKGROUND
        updatedTimetable.colorText = UPDATED_COLOR_TEXT
        updatedTimetable.isInMonday = UPDATED_IN_MONDAY
        updatedTimetable.isInTuesday = UPDATED_IN_TUESDAY
        updatedTimetable.isInWednesday = UPDATED_IN_WEDNESDAY
        updatedTimetable.isInThursday = UPDATED_IN_THURSDAY
        updatedTimetable.isInFriday = UPDATED_IN_FRIDAY
        updatedTimetable.isInSaturday = UPDATED_IN_SATURDAY
        updatedTimetable.isInSunday = UPDATED_IN_SUNDAY
        val timetableDTO = timetableMapper.toDto(updatedTimetable)

        restTimetableMockMvc.perform(put("/api/timetables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timetableDTO)))
            .andExpect(status().isOk)

        // Validate the Timetable in the database
        val timetableList = timetableRepository.findAll()
        assertThat(timetableList).hasSize(databaseSizeBeforeUpdate)
        val testTimetable = timetableList[timetableList.size - 1]
        assertThat(testTimetable.title).isEqualTo(UPDATED_TITLE)
        assertThat(testTimetable.startTime).isEqualTo(UPDATED_START_TIME)
        assertThat(testTimetable.endTime).isEqualTo(UPDATED_END_TIME)
        assertThat(testTimetable.startDate).isEqualTo(UPDATED_START_DATE)
        assertThat(testTimetable.endDate).isEqualTo(UPDATED_END_DATE)
        assertThat(testTimetable.date).isEqualTo(UPDATED_DATE)
        assertThat(testTimetable.type).isEqualTo(UPDATED_TYPE)
        assertThat(testTimetable.everyWeek).isEqualTo(UPDATED_EVERY_WEEK)
        assertThat(testTimetable.startWithWeek).isEqualTo(UPDATED_START_WITH_WEEK)
        assertThat(testTimetable.description).isEqualTo(UPDATED_DESCRIPTION)
        assertThat(testTimetable.colorBackground).isEqualTo(UPDATED_COLOR_BACKGROUND)
        assertThat(testTimetable.colorText).isEqualTo(UPDATED_COLOR_TEXT)
        assertThat(testTimetable.isInMonday).isEqualTo(UPDATED_IN_MONDAY)
        assertThat(testTimetable.isInTuesday).isEqualTo(UPDATED_IN_TUESDAY)
        assertThat(testTimetable.isInWednesday).isEqualTo(UPDATED_IN_WEDNESDAY)
        assertThat(testTimetable.isInThursday).isEqualTo(UPDATED_IN_THURSDAY)
        assertThat(testTimetable.isInFriday).isEqualTo(UPDATED_IN_FRIDAY)
        assertThat(testTimetable.isInSaturday).isEqualTo(UPDATED_IN_SATURDAY)
        assertThat(testTimetable.isInSunday).isEqualTo(UPDATED_IN_SUNDAY)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun updateNonExistingTimetable() {
        val databaseSizeBeforeUpdate = timetableRepository.findAll().size

        // Create the Timetable
        val timetableDTO = timetableMapper.toDto(timetable)

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTimetableMockMvc.perform(put("/api/timetables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timetableDTO)))
            .andExpect(status().isCreated)

        // Validate the Timetable in the database
        val timetableList = timetableRepository.findAll()
        assertThat(timetableList).hasSize(databaseSizeBeforeUpdate + 1)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun deleteTimetable() {
        // Initialize the database
        timetableRepository.saveAndFlush(timetable)
        val databaseSizeBeforeDelete = timetableRepository.findAll().size

        // Get the timetable
        restTimetableMockMvc.perform(delete("/api/timetables/{id}", timetable.id)
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk)

        // Validate the database is empty
        val timetableList = timetableRepository.findAll()
        assertThat(timetableList).hasSize(databaseSizeBeforeDelete - 1)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun equalsVerifier() {
        val timetable1 = createEntity()
        timetable1.id = 1L
        val timetable2 = createEntity()
        timetable2.id = timetable1.id
        assertThat(timetable1).isEqualTo(timetable2)
        timetable2.id = 2L
        assertThat(timetable1).isNotEqualTo(timetable2)
        timetable1.id = null
        assertThat(timetable1).isNotEqualTo(timetable2)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun dtoEqualsVerifier() {
        val timetableDTO1 = createEntityDto()
        timetableDTO1.id = 1L
        val timetableDTO2 = createEntityDto()
        assertThat(timetableDTO1).isNotEqualTo(timetableDTO2)
        timetableDTO2.id = timetableDTO1.id
        assertThat(timetableDTO1).isEqualTo(timetableDTO2)
        timetableDTO2.id = 2L
        assertThat(timetableDTO1).isNotEqualTo(timetableDTO2)
        timetableDTO1.id = null
        assertThat(timetableDTO1).isNotEqualTo(timetableDTO2)
    }


}
