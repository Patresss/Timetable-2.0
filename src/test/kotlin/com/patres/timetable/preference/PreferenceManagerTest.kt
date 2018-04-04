package com.patres.timetable.preference

import com.patres.timetable.TimetableApp
import com.patres.timetable.service.*
import com.patres.timetable.service.dto.PreferenceDependencyDTO
import com.patres.timetable.service.mapper.PreferenceDependencyMapper
import com.patres.timetable.web.rest.FillerResource
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager


@RunWith(SpringRunner::class)
@SpringBootTest(classes = [(TimetableApp::class)])
open class PreferenceManagerTest {

    @Autowired
    private lateinit var preferenceDependencyMapper: PreferenceDependencyMapper

    @Autowired
    private lateinit var entityManager: EntityManager

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

    private lateinit var fillerResource: FillerResource


    @Before
    fun setup() {
        fillerResource = FillerResource(lessonService, divisionService, subjectService, timetableService, periodService, teacherService, curriculumService, placeService)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun `Test preference by Division`() {
        // Initialize the database
        fillerResource.fill()
        entityManager.flush()
        entityManager.clear()

        val preferenceDependencyDTO = PreferenceDependencyDTO(divisionId = fillerResource.class1a.id)
        val preferenceDependency = preferenceDependencyMapper.toEntity(preferenceDependencyDTO)
        val preference = PreferenceManager(preferenceDependency).calculatePreference()

        assertEquals(1, preference.preferredTeacherMap.size)
        assertTrue(preference.preferredTeacherMap.containsKey(fillerResource.czuba.id))
        assertFalse(preference.preferredTeacherMap.containsKey(fillerResource.stasik.id))
        assertTrue(preference.preferredTeacherMap[fillerResource.czuba.id]?.preferredByDivision == 1)

        assertEquals(20, preference.preferredSubjectMap.size)
        assertTrue(preference.preferredSubjectMap.containsKey(fillerResource.matematyka.id))
        assertFalse(preference.preferredSubjectMap.containsKey(fillerResource.jLaciński.id))
        assertTrue(preference.preferredSubjectMap[fillerResource.matematyka.id]?.preferredByDivision == 1)

        assertEquals(1, preference.preferredPlaceMap.size)
        assertTrue(preference.preferredPlaceMap.containsKey(fillerResource.p22.id))
        assertFalse(preference.preferredPlaceMap.containsKey(fillerResource.p7g.id))
        assertTrue(preference.preferredPlaceMap[fillerResource.p22.id]?.preferredByDivision == 1)
    }


    @Test
    @Transactional
    @Throws(Exception::class)
    open fun `Test preference by Place`() {
        // Initialize the database
        fillerResource.fill()
        entityManager.flush()
        entityManager.clear()

        val preferenceDependencyDTO = PreferenceDependencyDTO(placeId = fillerResource.p22.id)
        val preferenceDependency = preferenceDependencyMapper.toEntity(preferenceDependencyDTO)
        val preference = PreferenceManager(preferenceDependency).calculatePreference()

        assertEquals(1, preference.preferredTeacherMap.size)
        assertTrue(preference.preferredTeacherMap.containsKey(fillerResource.czuba.id))
        assertFalse(preference.preferredTeacherMap.containsKey(fillerResource.stasik.id))
        assertTrue(preference.preferredTeacherMap[fillerResource.czuba.id]?.preferredByPlace == 1)

        assertEquals(1, preference.preferredSubjectMap.size)
        assertTrue(preference.preferredSubjectMap.containsKey(fillerResource.matematyka.id))
        assertFalse(preference.preferredSubjectMap.containsKey(fillerResource.jLaciński.id))
        assertTrue(preference.preferredSubjectMap[fillerResource.matematyka.id]?.preferredByPlace == 1)

        assertEquals(1, preference.preferredDivisionMap.size)
        assertTrue(preference.preferredDivisionMap.containsKey(fillerResource.class1a.id))
        assertFalse(preference.preferredDivisionMap.containsKey(fillerResource.class2a.id))
        assertTrue(preference.preferredDivisionMap[fillerResource.class1a.id]?.preferredByPlace == 1)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun `Test preference by Subject `() {
        // Initialize the database
        fillerResource.fill()
        entityManager.flush()
        entityManager.clear()

        val preferenceDependencyDTO = PreferenceDependencyDTO(subjectId = fillerResource.matematyka.id)
        val preferenceDependency = preferenceDependencyMapper.toEntity(preferenceDependencyDTO)
        val preference = PreferenceManager(preferenceDependency).calculatePreference()

        assertEquals(3, preference.preferredTeacherMap.size)
        assertTrue(preference.preferredTeacherMap.containsKey(fillerResource.czuba.id))
        assertFalse(preference.preferredTeacherMap.containsKey(fillerResource.stasik.id))
        assertTrue(preference.preferredTeacherMap[fillerResource.czuba.id]?.preferredBySubject == 1)

        assertEquals(1, preference.preferredPlaceMap.size)
        assertTrue(preference.preferredPlaceMap.containsKey(fillerResource.p22.id))
        assertFalse(preference.preferredPlaceMap.containsKey(fillerResource.p10.id))
        assertTrue(preference.preferredPlaceMap[fillerResource.p22.id]?.preferredBySubject == 1)

        assertEquals(1, preference.preferredDivisionMap.size)
        assertTrue(preference.preferredDivisionMap.containsKey(fillerResource.class1a.id))
        assertFalse(preference.preferredDivisionMap.containsKey(fillerResource.class2a.id))
        assertTrue(preference.preferredDivisionMap[fillerResource.class1a.id]?.preferredBySubject == 1)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun `Test preference by Teacher `() {
        // Initialize the database
        fillerResource.fill()
        entityManager.flush()
        entityManager.clear()

        val preferenceDependencyDTO = PreferenceDependencyDTO(teacherId = fillerResource.czuba.id)
        val preferenceDependency = preferenceDependencyMapper.toEntity(preferenceDependencyDTO)
        val preference = PreferenceManager(preferenceDependency).calculatePreference()

        assertEquals(1, preference.preferredSubjectMap.size)
        assertTrue(preference.preferredSubjectMap.containsKey(fillerResource.matematyka.id))
        assertFalse(preference.preferredSubjectMap.containsKey(fillerResource.biologia.id))
        assertTrue(preference.preferredSubjectMap[fillerResource.matematyka.id]?.preferredByTeacher == 1)

        assertEquals(1, preference.preferredPlaceMap.size)
        assertTrue(preference.preferredPlaceMap.containsKey(fillerResource.p22.id))
        assertFalse(preference.preferredPlaceMap.containsKey(fillerResource.p10.id))
        assertTrue(preference.preferredPlaceMap[fillerResource.p22.id]?.preferredByTeacher == 1)

        assertEquals(1, preference.preferredDivisionMap.size)
        assertTrue(preference.preferredDivisionMap.containsKey(fillerResource.class1a.id))
        assertFalse(preference.preferredDivisionMap.containsKey(fillerResource.class2a.id))
        assertTrue(preference.preferredDivisionMap[fillerResource.class1a.id]?.preferredByTeacher == 1)
    }

}
