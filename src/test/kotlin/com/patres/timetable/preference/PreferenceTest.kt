package com.patres.timetable.preference

import com.patres.timetable.domain.*
import com.patres.timetable.domain.preference.PreferenceDataTimeForTeacher
import com.patres.timetable.domain.preference.PreferenceSubjectByTeacher
import com.patres.timetable.domain.preference.PreferenceTeacherByDivision
import com.patres.timetable.domain.preference.PreferenceTeacherByPlace
import org.junit.Assert
import org.junit.jupiter.api.*
import java.time.DayOfWeek

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PreferenceTest {

    private val smithTeacher = Teacher().apply {
        id = 1001
        surname = "Smith"
    }


    private val maths = Subject().apply {
        id = 2001
        name = "Maths"
    }

    private val biology = Subject().apply {
        id = 2002
        name = "Biology"
    }


    private val place22 = Place().apply {
        id = 3001
        name = "22"
    }

    private val placeGym = Place().apply {
        id = 3002
        name = "Gym"
    }

    private val division1A = Division().apply {
        id = 4001
        name = "class 1 A"
    }

    private val division1B = Division().apply {
        id = 4002
        name = "class 1 B"
    }

    private val lesson1 = Lesson().apply {
        id = 5001
        name = "1"
    }

    private val lesson2 = Lesson().apply {
        id = 5002
        name = "2"
    }


    init {
        smithTeacher.apply {
            preferenceSubjectByTeacher = setOf(
                PreferenceSubjectByTeacher(subject = maths, teacher = this, points = 10),
                PreferenceSubjectByTeacher(subject = biology, teacher = this, points = 5)
            )
            preferenceTeacherByPlace = setOf(
                PreferenceTeacherByPlace(teacher = this, place = place22, points = 10),
                PreferenceTeacherByPlace(teacher = this, place = placeGym, points = -10_000)
            )
            preferencesTeacherByDivision = setOf(
                PreferenceTeacherByDivision(teacher = this, division = division1A, points = 10),
                PreferenceTeacherByDivision(teacher = this, division = division1B, points = 5)
            )

            preferenceDataTimeForTeachers = setOf(
                PreferenceDataTimeForTeacher(teacher = this, lesson = lesson1, dayOfWeek = DayOfWeek.MONDAY.value, points = 10),
                PreferenceDataTimeForTeacher(teacher = this, lesson = lesson2, dayOfWeek = DayOfWeek.MONDAY.value, points = 5)
            )
        }
    }

    private lateinit var preference: Preference


    @BeforeEach
    internal fun setUp() {
        preference = Preference(
            teachersId = setOf(smithTeacher.id!!),
            subjectsId = setOf(maths.id!!, biology.id!!),
            placesId = setOf(place22.id!!, placeGym.id!!),
            divisionsId = setOf(division1A.id!!, division1B.id!!),
            lessonsId = setOf(lesson1.id!!, lesson2.id!!)
        )
    }


    @Nested
    inner class CalculateByTeacherCase {

        @Test
        fun `test calculate subject preferences by teacher`() {
            preference.calculateByTeacher(smithTeacher)

            val allPointsOfPreferenceMath = preference.preferredSubjectMap[maths.id]?.points
            val teacherPointsOfPreferenceMath = preference.preferredSubjectMap[maths.id]?.preferredByTeacher
            val placePointsOfPreferenceMath = preference.preferredSubjectMap[maths.id]?.preferredByPlace
            val divisionPointsOfPreferenceMath = preference.preferredSubjectMap[maths.id]?.preferredByDivision
            val dateTimePointsOfPreferenceMath = preference.preferredSubjectMap[maths.id]?.preferredByDateTime

            Assert.assertEquals(10, allPointsOfPreferenceMath)
            Assert.assertEquals(10, teacherPointsOfPreferenceMath)
            Assert.assertEquals(0, placePointsOfPreferenceMath)
            Assert.assertEquals(0, divisionPointsOfPreferenceMath)
            Assert.assertEquals(0, dateTimePointsOfPreferenceMath)

            val allPointsOfPreferenceBiology = preference.preferredSubjectMap[biology.id]?.points
            val teacherPointsOfPreferenceBiology = preference.preferredSubjectMap[biology.id]?.preferredByTeacher
            val placePointsOfPreferenceBiology = preference.preferredSubjectMap[biology.id]?.preferredByPlace
            val divisionPointsOfPreferenceBiology = preference.preferredSubjectMap[biology.id]?.preferredByDivision
            val dateTimePointsOfPreferenceBiology = preference.preferredSubjectMap[biology.id]?.preferredByDateTime

            Assert.assertEquals(5, allPointsOfPreferenceBiology)
            Assert.assertEquals(5, teacherPointsOfPreferenceBiology)
            Assert.assertEquals(0, placePointsOfPreferenceBiology)
            Assert.assertEquals(0, divisionPointsOfPreferenceBiology)
            Assert.assertEquals(0, dateTimePointsOfPreferenceBiology)
        }


        @Test
        fun `test calculate place preferences by teacher`() {
            preference.calculateByTeacher(smithTeacher)

            val allPointsOfPreference22 = preference.preferredPlaceMap[place22.id]?.points
            val teacherPointsOfPreference22 = preference.preferredPlaceMap[place22.id]?.preferredByTeacher
            val subjectPointsOfPreference22 = preference.preferredPlaceMap[place22.id]?.preferredBySubject
            val divisionPointsOfPreference22 = preference.preferredPlaceMap[place22.id]?.preferredByDivision
            val dateTimePointsOfPreference22 = preference.preferredPlaceMap[place22.id]?.preferredByDateTime

            Assert.assertEquals(10, allPointsOfPreference22)
            Assert.assertEquals(10, teacherPointsOfPreference22)
            Assert.assertEquals(0, subjectPointsOfPreference22)
            Assert.assertEquals(0, divisionPointsOfPreference22)
            Assert.assertEquals(0, dateTimePointsOfPreference22)

            val allPointsOfPreferenceGym = preference.preferredPlaceMap[placeGym.id]?.points
            val teacherPointsOfPreferenceGym = preference.preferredPlaceMap[placeGym.id]?.preferredByTeacher
            val subjectPointsOfPreferenceGym = preference.preferredPlaceMap[placeGym.id]?.preferredBySubject
            val divisionPointsOfPreferenceGym = preference.preferredPlaceMap[placeGym.id]?.preferredByDivision
            val dateTimePointsOfPreferenceGym = preference.preferredPlaceMap[placeGym.id]?.preferredByDateTime

            Assert.assertEquals(-10_000, allPointsOfPreferenceGym)
            Assert.assertEquals(-10_000, teacherPointsOfPreferenceGym)
            Assert.assertEquals(0, subjectPointsOfPreferenceGym)
            Assert.assertEquals(0, divisionPointsOfPreferenceGym)
            Assert.assertEquals(0, dateTimePointsOfPreferenceGym)
        }

        @Test
        fun `test calculate division preferences by teacher`() {
            preference.calculateByTeacher(smithTeacher)

            val allPointsOfPreferenceClass1A = preference.preferredDivisionMap[division1A.id]?.points
            val teacherPointsOfPreferenceClass1A = preference.preferredDivisionMap[division1A.id]?.preferredByTeacher
            val subjectPointsOfPreferenceClass1A = preference.preferredDivisionMap[division1A.id]?.preferredBySubject
            val divisionPointsOfPreferenceClass1A = preference.preferredDivisionMap[division1A.id]?.preferredByPlace
            val dateTimePointsOfPreferenceClass1A = preference.preferredDivisionMap[division1A.id]?.preferredByDateTime

            Assert.assertEquals(10, allPointsOfPreferenceClass1A)
            Assert.assertEquals(10, teacherPointsOfPreferenceClass1A)
            Assert.assertEquals(0, subjectPointsOfPreferenceClass1A)
            Assert.assertEquals(0, divisionPointsOfPreferenceClass1A)
            Assert.assertEquals(0, dateTimePointsOfPreferenceClass1A)

            val allPointsOfPreferenceClass1B = preference.preferredDivisionMap[division1B.id]?.points
            val teacherPointsOfPreferenceClass1B = preference.preferredDivisionMap[division1B.id]?.preferredByTeacher
            val subjectPointsOfPreferenceClass1B = preference.preferredDivisionMap[division1B.id]?.preferredBySubject
            val divisionPointsOfPreferenceClass1B = preference.preferredDivisionMap[division1B.id]?.preferredByPlace
            val dateTimePointsOfPreferenceClass1B = preference.preferredDivisionMap[division1B.id]?.preferredByDateTime

            Assert.assertEquals(5, allPointsOfPreferenceClass1B)
            Assert.assertEquals(5, teacherPointsOfPreferenceClass1B)
            Assert.assertEquals(0, subjectPointsOfPreferenceClass1B)
            Assert.assertEquals(0, divisionPointsOfPreferenceClass1B)
            Assert.assertEquals(0, dateTimePointsOfPreferenceClass1B)
        }

        @Test
        fun `test calculate lesson and day of week preferences by teacher`() {
            preference.calculateByTeacher(smithTeacher)

            val preferenceByLesson1AndMonday = preference.getPreferenceByLessonAndDay(DayOfWeek.MONDAY.value, lessonId = lesson1.id)!!.preference
            Assert.assertEquals(10, preferenceByLesson1AndMonday.points)
            Assert.assertEquals(10, preferenceByLesson1AndMonday.preferredByTeacher)
            Assert.assertEquals(0, preferenceByLesson1AndMonday.preferredByDivision)
            Assert.assertEquals(0, preferenceByLesson1AndMonday.preferredByPlace)
            Assert.assertEquals(0, preferenceByLesson1AndMonday.preferredBySubject)

            val preferenceByLesson2AndMonday = preference.getPreferenceByLessonAndDay(DayOfWeek.MONDAY.value, lessonId = lesson2.id)!!.preference
            Assert.assertEquals(5, preferenceByLesson2AndMonday.points)
            Assert.assertEquals(5, preferenceByLesson2AndMonday.preferredByTeacher)
            Assert.assertEquals(0, preferenceByLesson2AndMonday.preferredByDivision)
            Assert.assertEquals(0, preferenceByLesson2AndMonday.preferredByPlace)
            Assert.assertEquals(0, preferenceByLesson2AndMonday.preferredBySubject)

            val preferenceByLesson2AndFriday = preference.getPreferenceByLessonAndDay(DayOfWeek.FRIDAY.value, lessonId = lesson2.id)!!.preference
            Assert.assertEquals(0, preferenceByLesson2AndFriday.points)
            Assert.assertEquals(0, preferenceByLesson2AndFriday.preferredByTeacher)
            Assert.assertEquals(0, preferenceByLesson2AndFriday.preferredByDivision)
            Assert.assertEquals(0, preferenceByLesson2AndFriday.preferredByPlace)
            Assert.assertEquals(0, preferenceByLesson2AndFriday.preferredBySubject)
        }

        @Test
        fun `test calculate teacher preferences by teacher`() {
            preference.calculateByTeacher(smithTeacher)
            Assert.assertTrue(preference.preferredTeacherMap.all { it.value.points == 0 })
        }
    }


}
