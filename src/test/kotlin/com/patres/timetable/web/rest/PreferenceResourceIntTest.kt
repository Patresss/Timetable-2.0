package com.patres.timetable.web.rest

import com.patres.timetable.TimetableApp
import com.patres.timetable.preference.PreferenceManager
import com.patres.timetable.service.*
import com.patres.timetable.service.mapper.preference.PreferenceDependencyMapper
import com.patres.timetable.web.rest.errors.ExceptionTranslator
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
    private lateinit var curriculumListService: CurriculumListService
    @Autowired
    private lateinit var placeService: PlaceService

    @Autowired
    private lateinit var preferenceManager: PreferenceManager

    private lateinit var restPreferenceMockMvc: MockMvc
    private lateinit var restFillerMockMvc: MockMvc
    private lateinit var fillerResource: FillerResource


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        restPreferenceMockMvc = initMockMvc(PreferenceResource(preferenceDependencyMapper, preferenceManager))
        fillerResource = FillerResource(lessonService, divisionService, subjectService, timetableService, periodService, teacherService, curriculumService, curriculumListService, placeService)
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
    open fun `Test preference`() {
        // Get the Preference
        restPreferenceMockMvc.perform(get("/api/preferences?divisionOwnerId=1"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
    }


}
