package com.patres.timetable.web.rest

import com.patres.timetable.TimetableApp
import com.patres.timetable.domain.AbstractApplicationEntity
import com.patres.timetable.domain.Interval
import com.patres.timetable.domain.Period
import com.patres.timetable.domain.Timetable
import com.patres.timetable.domain.enumeration.DivisionType
import com.patres.timetable.domain.enumeration.EventType
import com.patres.timetable.repository.DivisionRepository
import com.patres.timetable.repository.IntervalRepository
import com.patres.timetable.repository.PeriodRepository
import com.patres.timetable.repository.TimetableRepository
import com.patres.timetable.service.TimetableService
import com.patres.timetable.service.dto.TimetableDTO
import com.patres.timetable.service.mapper.TimetableMapper
import com.patres.timetable.web.rest.errors.ExceptionTranslator
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.hasItem
import org.hamcrest.Matchers.not
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
@SpringBootTest(classes = [(TimetableApp::class)])
open class TimetableResourceIntTest {

    @Autowired
    private lateinit var timetableRepository: TimetableRepository

    @Autowired
    private lateinit var divisionRepository: DivisionRepository

    @Autowired
    private lateinit var periodRepository: PeriodRepository

    @Autowired
    private lateinit var timetableMapper: TimetableMapper

    @Autowired
    private lateinit var timetableService: TimetableService

    @Autowired
    private lateinit var intervalRepository: IntervalRepository

    @Autowired
    private lateinit var jacksonMessageConverter: MappingJackson2HttpMessageConverter

    @Autowired
    private lateinit var pageableArgumentResolver: PageableHandlerMethodArgumentResolver

    @Autowired
    private lateinit var exceptionTranslator: ExceptionTranslator

    private lateinit var restTimetableMockMvc: MockMvc

    private var timetable: Timetable = createEntity()

    companion object {

        private const val DEFAULT_TITLE = "AAAAAAAAAA"
        private const val UPDATED_TITLE = "BBBBBBBBBB"

        private const val DEFAULT_START_TIME = "12:34"
        private const val UPDATED_START_TIME = "13:56"

        private const val DEFAULT_END_TIME = "14:34"
        private const val UPDATED_END_TIME = "15:56"

        private val DEFAULT_DATE = LocalDate.ofEpochDay(0L)
        private val UPDATED_DATE = LocalDate.now(ZoneId.systemDefault())

        private val DEFAULT_TYPE = EventType.LESSON
        private val UPDATED_TYPE = EventType.SUBSTITUTION

        private const val DEFAULT_EVERY_WEEK = 1L
        private const val UPDATED_EVERY_WEEK = 2L

        private const val DEFAULT_START_WITH_WEEK = 1L
        private const val UPDATED_START_WITH_WEEK = 2L

        private const val DEFAULT_DESCRIPTION = "AAAAAAAAAA"
        private const val UPDATED_DESCRIPTION = "BBBBBBBBBB"

        private const val DEFAULT_COLOR_BACKGROUND = "AAAAAAAAAA"
        private const val UPDATED_COLOR_BACKGROUND = "BBBBBBBBBB"

        private const val DEFAULT_COLOR_TEXT = "AAAAAAAAAA"
        private const val UPDATED_COLOR_TEXT = "BBBBBBBBBB"

        private const val DEFAULT_DAY_OF_WEEK = 1
        private const val UPDATED_DAY_OF_WEEK = 2

        fun createEntity(): Timetable {
            return Timetable(
                title = DEFAULT_TITLE,
                type = DEFAULT_TYPE,
                date = DEFAULT_DATE,
                everyWeek = DEFAULT_EVERY_WEEK,
                startWithWeek = DEFAULT_START_WITH_WEEK,
                description = DEFAULT_DESCRIPTION,
                colorBackground = DEFAULT_COLOR_BACKGROUND,
                colorText = DEFAULT_COLOR_TEXT,
                dayOfWeek = DEFAULT_DAY_OF_WEEK)
                .apply {
                    setStartTimeHHmmFormatted(DEFAULT_START_TIME)
                    setEndTimeHHmmFormatted(DEFAULT_END_TIME)
                }
        }

        fun createEntityDto(): TimetableDTO {
            return TimetableDTO(
                title = DEFAULT_TITLE,
                type = DEFAULT_TYPE,
                startTimeString = DEFAULT_START_TIME,
                endTimeString = DEFAULT_END_TIME,
                date = DEFAULT_DATE,
                everyWeek = DEFAULT_EVERY_WEEK,
                startWithWeek = DEFAULT_START_WITH_WEEK,
                description = DEFAULT_DESCRIPTION,
                colorBackground = DEFAULT_COLOR_BACKGROUND,
                colorText = DEFAULT_COLOR_TEXT,
                dayOfWeek = DEFAULT_DAY_OF_WEEK)
        }
    }

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        restTimetableMockMvc = initMockMvc(TimetableResource(timetableService, intervalRepository))
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
        assertThat(testTimetable.startTime).isEqualTo(AbstractApplicationEntity.getSecondsFromString(DEFAULT_START_TIME))
        assertThat(testTimetable.endTime).isEqualTo(AbstractApplicationEntity.getSecondsFromString(DEFAULT_END_TIME))
        assertThat(testTimetable.date).isEqualTo(DEFAULT_DATE)
        assertThat(testTimetable.type).isEqualTo(DEFAULT_TYPE)
        assertThat(testTimetable.everyWeek).isEqualTo(DEFAULT_EVERY_WEEK)
        assertThat(testTimetable.startWithWeek).isEqualTo(DEFAULT_START_WITH_WEEK)
        assertThat(testTimetable.description).isEqualTo(DEFAULT_DESCRIPTION)
        assertThat(testTimetable.colorText).isEqualTo(DEFAULT_COLOR_TEXT)
        assertThat(testTimetable.dayOfWeek).isEqualTo(DEFAULT_DAY_OF_WEEK)
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
            .andExpect(jsonPath("$.[*].startTimeString").value(hasItem(DEFAULT_START_TIME)))
            .andExpect(jsonPath("$.[*].endTimeString").value(hasItem(DEFAULT_END_TIME)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].everyWeek").value(hasItem(DEFAULT_EVERY_WEEK.toInt())))
            .andExpect(jsonPath("$.[*].startWithWeek").value(hasItem(DEFAULT_START_WITH_WEEK.toInt())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
//            .andExpect(jsonPath("$.[*].colorBackground").value(hasItem(DEFAULT_COLOR_BACKGROUND)))
            .andExpect(jsonPath("$.[*].colorText").value(hasItem(DEFAULT_COLOR_TEXT)))
            .andExpect(jsonPath("$.[*].dayOfWeek").value(hasItem<Int>(DEFAULT_DAY_OF_WEEK)))

    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun getTimetable() {
        // Initialize the database
        timetableRepository.saveAndFlush(timetable)

        // Get the timetables
        restTimetableMockMvc.perform(get("/api/timetables/{id}", timetable.id))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(timetable.id))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.startTimeString").value(DEFAULT_START_TIME))
            .andExpect(jsonPath("$.endTimeString").value(DEFAULT_END_TIME))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.everyWeek").value(DEFAULT_EVERY_WEEK.toInt()))
            .andExpect(jsonPath("$.startWithWeek").value(DEFAULT_START_WITH_WEEK.toInt()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            //.andExpect(jsonPath("$.colorBackground").value(DEFAULT_COLOR_BACKGROUND))
            .andExpect(jsonPath("$.colorText").value(DEFAULT_COLOR_TEXT))
            .andExpect(jsonPath("$.dayOfWeek").value(DEFAULT_DAY_OF_WEEK))
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun getTimetableByDateAndDivisionList() {
        val lo2 = DivisionResourceIntTest.createEntity().apply { divisionType = DivisionType.SCHOOL }
        divisionRepository.saveAndFlush(lo2)

        val class1 = DivisionResourceIntTest.createEntity().apply {
            divisionType = DivisionType.CLASS
            parents = setOf(lo2)
        }
        divisionRepository.saveAndFlush(class1)

        val period1 = Period(name = "Semestr zimowy 2016/2017", divisionOwner = lo2)
        periodRepository.saveAndFlush(period1)
        val interval = Interval(startDate = LocalDate.parse("2016-09-01"), endDate = LocalDate.parse("2017-02-01"), includedState = true, period = period1)
        intervalRepository.saveAndFlush(interval)


        val period2 = Period(name = "Semestr letni 2017", divisionOwner = lo2)
        periodRepository.saveAndFlush(period2)
        val interval2 = Interval(startDate = LocalDate.parse("2017-02-02"), endDate = LocalDate.parse("2017-06-30"), includedState = true, period = period2)
        intervalRepository.saveAndFlush(interval2)

        val timetableWithPeriod = createEntity().apply {
            date = null
            dayOfWeek = 3
            division = class1
            period = period1
        }
        timetableRepository.saveAndFlush(timetableWithPeriod)

        val timetableWithWrongPeriod = createEntity().apply {
            date = null
            dayOfWeek = 3
            division = class1
            period = period2
        }
        timetableRepository.saveAndFlush(timetableWithWrongPeriod)

        val timetableWithDate = createEntity().apply {
            date = LocalDate.parse("2016-09-07") //Wednesday
            division = class1
            period = null
        }
        timetableRepository.saveAndFlush(timetableWithDate)

        val timetableWithWrongDate = createEntity().apply {
            date = LocalDate.parse("2016-09-08")
            division = class1
            period = null
        }
        timetableRepository.saveAndFlush(timetableWithWrongDate)


        val date = "2016-09-07"
        val divisionIdList = class1.id

        restTimetableMockMvc.perform(get("/api/timetables/division-list?divisionIdList=$divisionIdList&date=$date"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timetableWithPeriod.id?.toInt())))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timetableWithDate.id?.toInt())))
            .andExpect(jsonPath("$.[*].id").value(not(hasItem(timetableWithWrongPeriod.id?.toInt()))))
            .andExpect(jsonPath("$.[*].id").value(not(hasItem(timetableWithWrongDate.id?.toInt()))))
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun getTimetableByDateAndDivisionListWithEveryWeek() {
        val lo2 = DivisionResourceIntTest.createEntity().apply { divisionType = DivisionType.SCHOOL }
        divisionRepository.saveAndFlush(lo2)

        val class1 = DivisionResourceIntTest.createEntity().apply {
            divisionType = DivisionType.CLASS
            parents = setOf(lo2)
        }
        divisionRepository.saveAndFlush(class1)

        val period1 = Period(name = "Semestr zimowy 2016/2017", divisionOwner = lo2)
        periodRepository.saveAndFlush(period1)
        val interval = Interval(startDate = LocalDate.parse("2016-09-01"), endDate = LocalDate.parse("2017-02-01"), includedState = true, period = period1)
        intervalRepository.saveAndFlush(interval)

        val timetableWithEvery2WeekStartWith1Week = createEntity().apply {
            date = null
            dayOfWeek = 5
            division = class1
            period = period1
            everyWeek = 2
            startWithWeek = 1
        }
        timetableRepository.saveAndFlush(timetableWithEvery2WeekStartWith1Week)

        val timetableWithEvery2WeekStartWith2Week = createEntity().apply {
            date = null
            dayOfWeek = 5
            division = class1
            period = period1
            everyWeek = 2
            startWithWeek = 2
        }
        timetableRepository.saveAndFlush(timetableWithEvery2WeekStartWith2Week)


        val date = "2016-09-02"
        val divisionIdList = class1.id

        restTimetableMockMvc.perform(get("/api/timetables/division-list?divisionIdList=$divisionIdList&date=$date"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timetableWithEvery2WeekStartWith1Week.id?.toInt())))
            .andExpect(jsonPath("$.[*].id").value(not(hasItem(timetableWithEvery2WeekStartWith2Week.id?.toInt()))))

        val dateWeekLater = "2016-09-09"

        restTimetableMockMvc.perform(get("/api/timetables/division-list?divisionIdList=$divisionIdList&date=$dateWeekLater"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timetableWithEvery2WeekStartWith2Week.id?.toInt())))
            .andExpect(jsonPath("$.[*].id").value(not(hasItem(timetableWithEvery2WeekStartWith1Week.id?.toInt()))))
    }


    @Test
    @Transactional
    @Throws(Exception::class)
    open fun getNonExistingTimetable() {
        // Get the timetables
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

        // Update the timetables
        val updatedTimetable = timetableRepository.findOne(timetable.id).apply {
            title = UPDATED_TITLE
            date = UPDATED_DATE
            type = UPDATED_TYPE
            everyWeek = UPDATED_EVERY_WEEK
            startWithWeek = UPDATED_START_WITH_WEEK
            description = UPDATED_DESCRIPTION
            colorBackground = UPDATED_COLOR_BACKGROUND
            colorText = UPDATED_COLOR_TEXT
            dayOfWeek = UPDATED_DAY_OF_WEEK
            setStartTimeHHmmFormatted(UPDATED_START_TIME)
            setEndTimeHHmmFormatted(UPDATED_END_TIME)
        }
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
        assertThat(testTimetable.startTime).isEqualTo(AbstractApplicationEntity.getSecondsFromString(UPDATED_START_TIME))
        assertThat(testTimetable.endTime).isEqualTo(AbstractApplicationEntity.getSecondsFromString(UPDATED_END_TIME))
        assertThat(testTimetable.date).isEqualTo(UPDATED_DATE)
        assertThat(testTimetable.type).isEqualTo(UPDATED_TYPE)
        assertThat(testTimetable.everyWeek).isEqualTo(UPDATED_EVERY_WEEK)
        assertThat(testTimetable.startWithWeek).isEqualTo(UPDATED_START_WITH_WEEK)
        assertThat(testTimetable.description).isEqualTo(UPDATED_DESCRIPTION)
        //assertThat(testTimetable.colorBackground).isEqualTo(UPDATED_COLOR_BACKGROUND)
        assertThat(testTimetable.colorText).isEqualTo(UPDATED_COLOR_TEXT)
        assertThat(testTimetable.dayOfWeek).isEqualTo(UPDATED_DAY_OF_WEEK)
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

        // Get the timetables
        restTimetableMockMvc.perform(delete("/api/timetables/{id}", timetable.id)
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk)

        // Validate the database is empty
        val timetableList = timetableRepository.findAll()
        assertThat(timetableList).hasSize(databaseSizeBeforeDelete - 1)
    }

}
