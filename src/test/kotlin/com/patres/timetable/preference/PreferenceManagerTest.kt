package com.patres.timetable.preference

import com.patres.timetable.TimetableApp
import com.patres.timetable.repository.PlaceRepository
import com.patres.timetable.repository.TimetableRepository
import com.patres.timetable.service.*
import com.patres.timetable.service.dto.PreferenceDependencyDTO
import com.patres.timetable.service.mapper.preference.PreferenceDependencyMapper
import com.patres.timetable.web.rest.FillerResource
import junit.framework.Assert.*
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.Month
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
    private lateinit var curriculumListService: CurriculumListService


    @Autowired
    private lateinit var placeService: PlaceService

    @Autowired
    private lateinit var placeRepository: PlaceRepository

    @Autowired
    private lateinit var timetableRepository: TimetableRepository

    private lateinit var fillerResource: FillerResource

    @Autowired
    private lateinit var preferenceManager: PreferenceManager


    @Before
    fun setup() {
        fillerResource = FillerResource(lessonService, divisionService, subjectService, timetableService, periodService, teacherService, curriculumService, curriculumListService, placeService)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun `Test preference by Division`() {
        // Initialize the database
        fillerResource.fill()
        entityManager.flush()
        entityManager.clear()

        val preferenceDependencyDTO = PreferenceDependencyDTO(divisionId = fillerResource.class1a.id, divisionOwnerId = fillerResource.lo2.id)
        val preferenceDependency = preferenceDependencyMapper.toEntity(preferenceDependencyDTO)
        val preference = preferenceManager.createPreference(fillerResource.lo2.id!!)
        preferenceManager.calculateAll(preference, preferenceDependency)

        assertTrue(preference.preferredTeacherMap.containsKey(fillerResource.CzBe.id))
        assertTrue(preference.preferredTeacherMap.containsKey(fillerResource.StBo.id))
        assertNotEquals(0, preference.preferredTeacherMap[fillerResource.CzBe.id]?.preferredByDivision)
        assertEquals(0, preference.preferredTeacherMap[fillerResource.StBo.id]?.preferredByDivision)

        assertTrue(preference.preferredSubjectMap.containsKey(fillerResource.matematyka.id))
        assertTrue(preference.preferredSubjectMap.containsKey(fillerResource.jLacińskiPrawniczy.id))
        assertNotEquals(0, preference.preferredSubjectMap[fillerResource.matematyka.id]?.preferredByDivision)
        assertEquals(0, preference.preferredSubjectMap[fillerResource.jLacińskiPrawniczy.id]?.preferredByDivision)

        assertTrue(preference.preferredPlaceMap.containsKey(fillerResource.p22.id))
        assertTrue(preference.preferredPlaceMap.containsKey(fillerResource.p20.id))
        assertNotEquals(0, preference.preferredPlaceMap[fillerResource.p22.id]?.preferredByDivision)
        assertEquals(0, preference.preferredPlaceMap[fillerResource.p20.id]?.preferredByDivision)
    }


    @Test
    @Transactional
    @Throws(Exception::class)
    open fun `Test preference by Place`() {
        // Initialize the database
        fillerResource.fill()
        entityManager.flush()
        entityManager.clear()

        val preferenceDependencyDTO = PreferenceDependencyDTO(placeId = fillerResource.p22.id, divisionOwnerId = fillerResource.lo2.id)
        val preferenceDependency = preferenceDependencyMapper.toEntity(preferenceDependencyDTO)
        val preference = preferenceManager.createPreference(fillerResource.lo2.id!!)
        preferenceManager.calculateAll(preference, preferenceDependency)

        assertTrue(preference.preferredTeacherMap.containsKey(fillerResource.CzBe.id))
        assertTrue(preference.preferredTeacherMap.containsKey(fillerResource.StBo.id))
        assertNotEquals(0, preference.preferredTeacherMap[fillerResource.CzBe.id]?.preferredByPlace)
        assertEquals(0, preference.preferredTeacherMap[fillerResource.StBo.id]?.preferredByPlace)

        assertTrue(preference.preferredSubjectMap.containsKey(fillerResource.matematyka.id))
        assertTrue(preference.preferredSubjectMap.containsKey(fillerResource.jLacińskiPrawniczy.id))
        assertNotEquals(0, preference.preferredSubjectMap[fillerResource.matematyka.id]?.preferredByPlace)
        assertEquals(0, preference.preferredSubjectMap[fillerResource.jLacińskiPrawniczy.id]?.preferredByPlace)

        assertTrue(preference.preferredDivisionMap.containsKey(fillerResource.class1a.id))
        assertTrue(preference.preferredDivisionMap.containsKey(fillerResource.class2a.id))
        assertNotEquals(0, preference.preferredDivisionMap[fillerResource.class1a.id]?.preferredByPlace)
        assertEquals(0, preference.preferredDivisionMap[fillerResource.class2a.id]?.preferredByPlace)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun `Test preference by Subject `() {
        // Initialize the database
        fillerResource.fill()
        entityManager.flush()
        entityManager.clear()

        val preferenceDependencyDTO = PreferenceDependencyDTO(subjectId = fillerResource.matematyka.id, divisionOwnerId = fillerResource.lo2.id)
        val preferenceDependency = preferenceDependencyMapper.toEntity(preferenceDependencyDTO)
        val preference = preferenceManager.createPreference(fillerResource.lo2.id!!)
        preferenceManager.calculateAll(preference, preferenceDependency)

        assertTrue(preference.preferredTeacherMap.containsKey(fillerResource.CzBe.id))
        assertTrue(preference.preferredTeacherMap.containsKey(fillerResource.StBo.id))
        assertNotEquals(0, preference.preferredTeacherMap[fillerResource.CzBe.id]?.preferredBySubject)
        assertEquals(0, preference.preferredTeacherMap[fillerResource.StBo.id]?.preferredBySubject)

        assertTrue(preference.preferredPlaceMap.containsKey(fillerResource.p22.id))
        assertTrue(preference.preferredPlaceMap.containsKey(fillerResource.p10.id))
        assertNotEquals(0, preference.preferredPlaceMap[fillerResource.p22.id]?.preferredBySubject)
        assertEquals(0, preference.preferredPlaceMap[fillerResource.p10.id]?.preferredBySubject)

        assertTrue(preference.preferredDivisionMap.containsKey(fillerResource.class1a.id))
        assertTrue(preference.preferredDivisionMap.containsKey(fillerResource.class2a.id))
        assertNotEquals(0, preference.preferredDivisionMap[fillerResource.class1a.id]?.preferredBySubject)
        assertEquals(0, preference.preferredDivisionMap[fillerResource.class2a.id]?.preferredBySubject)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun `Test preference by Teacher `() {
        // Initialize the database
        fillerResource.fill()
        entityManager.flush()
        entityManager.clear()

        val preferenceDependencyDTO = PreferenceDependencyDTO(teacherId = fillerResource.CzBe.id, divisionOwnerId = fillerResource.lo2.id)
        val preferenceDependency = preferenceDependencyMapper.toEntity(preferenceDependencyDTO)
        val preference = preferenceManager.createPreference(fillerResource.lo2.id!!)
        preferenceManager.calculateAll(preference, preferenceDependency)

        assertTrue(preference.preferredSubjectMap.containsKey(fillerResource.matematyka.id))
        assertTrue(preference.preferredSubjectMap.containsKey(fillerResource.biologia.id))
        assertNotEquals(0, preference.preferredSubjectMap[fillerResource.matematyka.id]?.preferredByTeacher)
        assertEquals(0, preference.preferredSubjectMap[fillerResource.biologia.id]?.preferredByTeacher)

        assertTrue(preference.preferredPlaceMap.containsKey(fillerResource.p22.id))
        assertTrue(preference.preferredPlaceMap.containsKey(fillerResource.p10.id))
        assertNotEquals(0, preference.preferredPlaceMap[fillerResource.p22.id]?.preferredByTeacher)
        assertEquals(0, preference.preferredPlaceMap[fillerResource.p10.id]?.preferredByTeacher)

        assertTrue(preference.preferredDivisionMap.containsKey(fillerResource.class1a.id))
        assertTrue(preference.preferredDivisionMap.containsKey(fillerResource.class2a.id))
        assertNotEquals(0, preference.preferredDivisionMap[fillerResource.class1a.id]?.preferredByTeacher)
        assertEquals(0, preference.preferredDivisionMap[fillerResource.class2a.id]?.preferredByTeacher)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun `Test too small Place`() {
        // Initialize the database
        fillerResource.fill()
        entityManager.flush()
        entityManager.clear()

        val preferenceDependencyDTO = PreferenceDependencyDTO(divisionOwnerId = fillerResource.lo2.id, divisionId = fillerResource.class1a.id)
        val preferenceDependency = preferenceDependencyMapper.toEntity(preferenceDependencyDTO)
        val preference = preferenceManager.createPreference(fillerResource.lo2.id!!)
        preferenceManager.calculateAll(preference, preferenceDependency)

        assertTrue(preference.preferredPlaceMap.containsKey(fillerResource.p7g.id))
        assertNotEquals(0, preference.preferredPlaceMap[fillerResource.p7g.id]?.tooSmallPlace)
        assertTrue(preference.preferredPlaceMap.containsKey(fillerResource.p22.id))
        assertEquals(0, preference.preferredPlaceMap[fillerResource.p22.id]?.tooSmallPlace)
    }



    @Test
    @Transactional
    @Throws(Exception::class)
    open fun `Test taken by date and lesson`() {
        // Initialize the database
        fillerResource.fill()
        entityManager.flush()
        entityManager.clear()

        val preferenceDependencyDTO = PreferenceDependencyDTO(divisionOwnerId = fillerResource.lo2.id, date = LocalDate.of(2018, Month.APRIL, 3), lessonId = fillerResource.l4.id)
        val preferenceDependency = preferenceDependencyMapper.toEntity(preferenceDependencyDTO)
        val preference = preferenceManager.createPreference(fillerResource.lo2.id!!)
        preferenceManager.calculateAll(preference, preferenceDependency)

        assertTrue(preference.preferredDivisionMap.containsKey(fillerResource.class1b.id))
        assertTrue(preference.preferredDivisionMap.containsKey(fillerResource.class1a.id))
        assertNotEquals(0, preference.preferredDivisionMap[fillerResource.class1a.id]?.taken)
        assertEquals(0, preference.preferredDivisionMap[fillerResource.class1b.id]?.taken)

        assertTrue(preference.preferredTeacherMap.containsKey(fillerResource.StBo.id))
        assertTrue(preference.preferredTeacherMap.containsKey(fillerResource.CzBe.id))
        assertNotEquals(0, preference.preferredTeacherMap[fillerResource.CzBe.id]?.taken)
        assertEquals(0, preference.preferredTeacherMap[fillerResource.StBo.id]?.taken)

        assertTrue(preference.preferredPlaceMap.containsKey(fillerResource.p10.id))
        assertTrue(preference.preferredPlaceMap.containsKey(fillerResource.p22.id))
        assertNotEquals(0, preference.preferredPlaceMap[fillerResource.p22.id]?.taken)
        assertEquals(0, preference.preferredPlaceMap[fillerResource.p10.id]?.taken)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun `Test Preference Data Time For Teacher by teacher`() {
        // Initialize the database
        fillerResource.fill()
        entityManager.flush()
        entityManager.clear()

        val preferenceDependencyDTO = PreferenceDependencyDTO(divisionOwnerId = fillerResource.lo2.id, teacherId = fillerResource.CzBe.id)
        val preferenceDependency = preferenceDependencyMapper.toEntity(preferenceDependencyDTO)
        val preference = preferenceManager.createPreference(fillerResource.lo2.id!!)
        preferenceManager.calculateAll(preference, preferenceDependency)

        assertTrue(preference.preferredDivisionMap.containsKey(fillerResource.class1a.id))
        assertEquals(10, preference.getPreferenceByLessonAndDay(1, fillerResource.l1.id!!)?.preference?.preferredByTeacher)
        assertEquals( -10000, preference.getPreferenceByLessonAndDay(5, fillerResource.l1.id!!)?.preference?.preferredByTeacher)
        assertEquals(5, preference.getPreferenceByLessonAndDay(1, fillerResource.l2.id!!)?.preference?.preferredByTeacher)
    }


    @Test
    @Transactional
    @Throws(Exception::class)
    open fun `Test by date and lesson `() {
        // Initialize the database
        fillerResource.fill()
        entityManager.flush()
        entityManager.clear()

        val preferenceDependencyDTO = PreferenceDependencyDTO(dayOfWeek = 1, lessonId = fillerResource.l1.id, divisionOwnerId = fillerResource.lo2.id)
        val preferenceDependency = preferenceDependencyMapper.toEntity(preferenceDependencyDTO)
        val preference = preferenceManager.createPreference(fillerResource.lo2.id!!)
        preferenceManager.calculateAll(preference, preferenceDependency)

        assertEquals(10, preference.preferredTeacherMap[fillerResource.CzBe.id]?.preferredByDataTime)
    }



}
