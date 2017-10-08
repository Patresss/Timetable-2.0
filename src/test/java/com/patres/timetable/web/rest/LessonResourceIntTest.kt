package com.patres.timetable.web.rest

import com.patres.timetable.TimetableApp
import com.patres.timetable.domain.Lesson
import com.patres.timetable.repository.DivisionRepository
import com.patres.timetable.repository.LessonRepository
import com.patres.timetable.service.LessonService
import com.patres.timetable.service.dto.LessonDTO
import com.patres.timetable.service.mapper.LessonMapper
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


@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(TimetableApp::class))
open class LessonResourceIntTest {

    @Autowired
    lateinit private var lessonRepository: LessonRepository

    @Autowired
    lateinit private var divisionRepository: DivisionRepository

    @Autowired
    lateinit private var lessonMapper: LessonMapper

    @Autowired
    lateinit private var lessonService: LessonService

    @Autowired
    lateinit private var jacksonMessageConverter: MappingJackson2HttpMessageConverter

    @Autowired
    lateinit private var pageableArgumentResolver: PageableHandlerMethodArgumentResolver

    @Autowired
    lateinit private var exceptionTranslator: ExceptionTranslator

    lateinit private var restLessonMockMvc: MockMvc

    private var lesson: Lesson = createEntity()

    companion object {

        private val DEFAULT_NAME = "AAAAAAAAAA"
        private val UPDATED_NAME = "BBBBBBBBBB"

        private val DEFAULT_START_TIME = 1L
        private val UPDATED_START_TIME = 2L

        private val DEFAULT_END_TIME = 1L
        private val UPDATED_END_TIME = 2L

        /**
         * Create an entity for this test.
         *
         * This is a static method, as tests for other entities might also need it,
         * if they test an entity which requires the current entity.
         */
        fun createEntity(): Lesson {
            return Lesson(DEFAULT_NAME, DEFAULT_START_TIME, DEFAULT_END_TIME)
        }

        fun createEntityDto(): LessonDTO {
            return LessonDTO(DEFAULT_NAME, DEFAULT_START_TIME, DEFAULT_END_TIME)
        }
    }

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        restLessonMockMvc = initMockMvc(LessonResource(lessonService))
    }

    private fun initMockMvc(lessonResource: LessonResource): MockMvc {
        return MockMvcBuilders.standaloneSetup(lessonResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build()
    }

    @Before
    fun initTest() {
        lesson = createEntity()
        val divisionOwner = DivisionResourceIntTest.createEntity()
        divisionRepository.saveAndFlush(divisionOwner)
        lesson.divisionOwner = divisionOwner
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun createLesson() {
        val databaseSizeBeforeCreate = lessonRepository.findAll().size

        // Create the Lesson
        val lessonDTO = lessonMapper.toDto(lesson)
        restLessonMockMvc.perform(post("/api/lessons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonDTO)))
            .andExpect(status().isCreated)

        // Validate the Lesson in the database
        val lessonList = lessonRepository.findAll()
        assertThat(lessonList).hasSize(databaseSizeBeforeCreate + 1)
        val testLesson = lessonList[lessonList.size - 1]
        assertThat(testLesson.name).isEqualTo(DEFAULT_NAME)
        assertThat(testLesson.startTime).isEqualTo(DEFAULT_START_TIME)
        assertThat(testLesson.endTime).isEqualTo(DEFAULT_END_TIME)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun createLessonWithExistingId() {
        val databaseSizeBeforeCreate = lessonRepository.findAll().size

        // Create the Lesson with an existing ID
        lesson.id = 1L
        val lessonDTO = lessonMapper.toDto(lesson)

        // An entity with an existing ID cannot be created, so this API call must fail
        restLessonMockMvc.perform(post("/api/lessons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonDTO)))
            .andExpect(status().isBadRequest)

        // Validate the Alice in the database
        val lessonList = lessonRepository.findAll()
        assertThat(lessonList).hasSize(databaseSizeBeforeCreate)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun `check name is required`() {
        val databaseSizeBeforeTest = lessonRepository.findAll().size
        // set the field null
        lesson.name = null

        // Create the Lesson, which fails.
        val lessonDTO = lessonMapper.toDto(lesson)

        restLessonMockMvc.perform(post("/api/lessons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonDTO)))
            .andExpect(status().isBadRequest)

        val lessonList = lessonRepository.findAll()
        assertThat(lessonList).hasSize(databaseSizeBeforeTest)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun checkStartTimeIsRequired() {
        val databaseSizeBeforeTest = lessonRepository.findAll().size
        // set the field null
        lesson.startTime = null

        // Create the Lesson, which fails.
        val lessonDTO = lessonMapper.toDto(lesson)

        restLessonMockMvc.perform(post("/api/lessons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonDTO)))
            .andExpect(status().isBadRequest)

        val lessonList = lessonRepository.findAll()
        assertThat(lessonList).hasSize(databaseSizeBeforeTest)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun checkEndTimeIsRequired() {
        val databaseSizeBeforeTest = lessonRepository.findAll().size
        // set the field null
        lesson.endTime = null

        // Create the Lesson, which fails.
        val lessonDTO = lessonMapper.toDto(lesson)

        restLessonMockMvc.perform(post("/api/lessons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonDTO)))
            .andExpect(status().isBadRequest)

        val lessonList = lessonRepository.findAll()
        assertThat(lessonList).hasSize(databaseSizeBeforeTest)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun getAllLessons() {
        // Initialize the database
        lessonRepository.saveAndFlush<Lesson>(lesson)

        // Get all the lessonList
        restLessonMockMvc.perform(get("/api/lessons?sort=id,desc"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lesson.id?.toInt())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toInt())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toInt())))
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun getLesson() {
        // Initialize the database
        lessonRepository.saveAndFlush<Lesson>(lesson)

        // Get the lesson
        restLessonMockMvc.perform(get("/api/lessons/{id}", lesson.id))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lesson.id?.toInt()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toInt()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toInt()))
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun getNonExistingLesson() {
        // Get the lesson
        restLessonMockMvc.perform(get("/api/lessons/{id}", java.lang.Long.MAX_VALUE))
            .andExpect(status().isNotFound)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun updateLesson() {
        // Initialize the database
        lessonRepository.saveAndFlush<Lesson>(lesson)
        val databaseSizeBeforeUpdate = lessonRepository.findAll().size

        // Update the lesson
        val updatedLesson = lessonRepository.findOne(lesson.id)
        updatedLesson.name = UPDATED_NAME
        updatedLesson.startTime = UPDATED_START_TIME
        updatedLesson.endTime = UPDATED_END_TIME
        val lessonDTO = lessonMapper.toDto(updatedLesson)

        restLessonMockMvc.perform(put("/api/lessons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonDTO)))
            .andExpect(status().isOk)

        // Validate the Lesson in the database
        val lessonList = lessonRepository.findAll()
        assertThat(lessonList).hasSize(databaseSizeBeforeUpdate)
        val testLesson = lessonList[lessonList.size - 1]
        assertThat(testLesson.name).isEqualTo(UPDATED_NAME)
        assertThat(testLesson.startTime).isEqualTo(UPDATED_START_TIME)
        assertThat(testLesson.endTime).isEqualTo(UPDATED_END_TIME)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun updateNonExistingLesson() {
        val databaseSizeBeforeUpdate = lessonRepository.findAll().size

        // Create the Lesson
        val lessonDTO = lessonMapper.toDto(lesson)

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLessonMockMvc.perform(put("/api/lessons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonDTO)))
            .andExpect(status().isCreated)

        // Validate the Lesson in the database
        val lessonList = lessonRepository.findAll()
        assertThat(lessonList).hasSize(databaseSizeBeforeUpdate + 1)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun deleteLesson() {
        // Initialize the database
        lessonRepository.saveAndFlush<Lesson>(lesson)
        val databaseSizeBeforeDelete = lessonRepository.findAll().size

        // Get the lesson
        restLessonMockMvc.perform(delete("/api/lessons/{id}", lesson.id)
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk)

        // Validate the database is empty
        val lessonList = lessonRepository.findAll()
        assertThat(lessonList).hasSize(databaseSizeBeforeDelete - 1)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun equalsVerifier() {
        val lesson1 = createEntity()
        lesson1.id = 1L
        val lesson2 = createEntity()
        lesson2.id = lesson1.id
        assertThat(lesson1).isEqualTo(lesson2)
        lesson2.id = 2L
        assertThat(lesson1).isNotEqualTo(lesson2)
        lesson1.id = null
        assertThat(lesson1).isNotEqualTo(lesson2)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun dtoEqualsVerifier() {
        val lessonDTO1 = createEntityDto()
        lessonDTO1.id = 1L
        val lessonDTO2 = createEntityDto()
        assertThat(lessonDTO1).isNotEqualTo(lessonDTO2)
        lessonDTO2.id = lessonDTO1.id
        assertThat(lessonDTO1).isEqualTo(lessonDTO2)
        lessonDTO2.id = 2L
        assertThat(lessonDTO1).isNotEqualTo(lessonDTO2)
        lessonDTO1.id = null
        assertThat(lessonDTO1).isNotEqualTo(lessonDTO2)
    }


}
