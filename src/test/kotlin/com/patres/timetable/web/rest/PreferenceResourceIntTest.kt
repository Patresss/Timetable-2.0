package com.patres.timetable.web.rest

import com.patres.timetable.TimetableApp
import com.patres.timetable.service.*
import com.patres.timetable.service.dto.PreferenceDependencyDTO
import com.patres.timetable.service.mapper.PreferenceDependencyMapper
import com.patres.timetable.web.rest.errors.ExceptionTranslator
import org.hamcrest.Matchers
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.transaction.annotation.Transactional


@RunWith(SpringRunner::class)
@SpringBootTest(classes = [(TimetableApp::class)])
open class PreferenceResourceIntTest {

    @Autowired
    private lateinit var jacksonMessageConverter: MappingJackson2HttpMessageConverter

    @Autowired
    private lateinit var preferenceDependencyMapper: PreferenceDependencyMapper

    @Autowired
    private lateinit var exceptionTranslator: ExceptionTranslator

    @Autowired
    private lateinit var lessonService: LessonService
    @Autowired
    private lateinit var divisionService: DivisionService
    @Autowired
    private lateinit var subjectService: SubjectService
    @Autowired
    private lateinit var timetableService: TimetableService
    @Autowired
    private lateinit var periodService: PeriodService
    @Autowired
    private lateinit var teacherService: TeacherService
    @Autowired
    private lateinit var curriculumService: CurriculumService
    @Autowired
    private lateinit var placeService: PlaceService

    private lateinit var restPreferenceMockMvc: MockMvc
    private lateinit var restFillerMockMvc: MockMvc
    private lateinit var fillerResource: FillerResource


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        restPreferenceMockMvc = initMockMvc(PreferenceResource(preferenceDependencyMapper))
        fillerResource = FillerResource(lessonService, divisionService, subjectService, timetableService, periodService, teacherService, curriculumService, placeService)
        restFillerMockMvc = initMockMvc(fillerResource)
    }

    private fun initMockMvc(resource: Any): MockMvc {
        return MockMvcBuilders.standaloneSetup(resource)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build()
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun `Test preference by Division `() {
        // Initialize the database
        fillerResource.fill()

        val preferenceDependencyDTO = PreferenceDependencyDTO(divisionId = fillerResource.class1a.id)

        // Get the Preference
        restPreferenceMockMvc.perform(get("/api/preferences")
            .flashAttr("preferenceDependencyDTO", preferenceDependencyDTO))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.preferredTeachersId.*", Matchers.hasSize<Int>(1)))
            .andExpect(jsonPath("$.preferredTeachersId.*").value(Matchers.hasItem(fillerResource.czuba.id?.toInt())))
            .andExpect(jsonPath("$.preferredTeachersId.*").value(not (Matchers.hasItem(fillerResource.stasik.id?.toInt()))))
            .andExpect(jsonPath("$.preferredSubjectsMap.*", Matchers.hasSize<Int>(20)))
            .andExpect(jsonPath("$.preferredSubjectsMap.*").value(Matchers.hasItem(fillerResource.matematyka.id?.toInt())))
            .andExpect(jsonPath("$.preferredSubjectsMap.*").value(not (Matchers.hasItem(fillerResource.jLaciński.id?.toInt()))))
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun `Test preference by Place `() {
        // Initialize the database
        fillerResource.fill()

        val preferenceDependencyDTO = PreferenceDependencyDTO(divisionId = fillerResource.p22.id)

        // Get the Preference
        restPreferenceMockMvc.perform(get("/api/preferences")
            .flashAttr("preferenceDependencyDTO", preferenceDependencyDTO))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.preferredTeachersId.*", Matchers.hasSize<Int>(1)))
            .andExpect(jsonPath("$.preferredTeachersId.*").value(Matchers.hasItem(fillerResource.czuba.id?.toInt())))
            .andExpect(jsonPath("$.preferredTeachersId.*").value(not (Matchers.hasItem(fillerResource.stasik.id?.toInt()))))
            .andExpect(jsonPath("$.preferredSubjectsMap.*", Matchers.hasSize<Int>(1)))
            .andExpect(jsonPath("$.preferredSubjectsMap.*").value(Matchers.hasItem(fillerResource.matematyka.id?.toInt())))
            .andExpect(jsonPath("$.preferredSubjectsMap.*").value(not (Matchers.hasItem(fillerResource.jLaciński.id?.toInt()))))
            .andExpect(jsonPath("$.preferredDivisionMap.*", Matchers.hasSize<Int>(1)))
            .andExpect(jsonPath("$.preferredDivisionMap.*").value(Matchers.hasItem(fillerResource.class1a.id?.toInt())))
            .andExpect(jsonPath("$.preferredDivisionMap.*").value(not (Matchers.hasItem(fillerResource.class1b.id?.toInt()))))
    }


}
