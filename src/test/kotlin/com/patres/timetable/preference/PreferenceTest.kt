package com.patres.timetable.preference

import com.patres.timetable.domain.*
import com.patres.timetable.domain.preference.*
import org.junit.Assert
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.time.DayOfWeek

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PreferenceTest {

    private val smithTeacher = Teacher().apply {
        id = 1001
        surname = "Smith"
    }

    private val johnsonTeacher = Teacher().apply {
        id = 1002
        surname = "Johnson"
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
        // Preference: Teacher <-> Subject
        val preferenceSmithMath = PreferenceSubjectByTeacher(subject = maths, teacher = smithTeacher, points = 10)
        val preferenceSmithBiology = PreferenceSubjectByTeacher(subject = biology, teacher = smithTeacher, points = 5)
        val preferenceJohnsonMath = PreferenceSubjectByTeacher(subject = maths, teacher = johnsonTeacher, points = -10_000)

        // Preference: Teacher <-> Place
        val preferenceSmithPlace22 = PreferenceTeacherByPlace(teacher = smithTeacher, place = place22, points = 10)
        val preferenceSmithPlaceGym = PreferenceTeacherByPlace(teacher = smithTeacher, place = placeGym, points = -10_000)
        val preferenceJohnsonPlace22 = PreferenceTeacherByPlace(teacher = johnsonTeacher, place = place22, points = 3)

        // Preference: Teacher <-> Division
        val preferenceSmithDivision1A = PreferenceTeacherByDivision(teacher = smithTeacher, division = division1A, points = 10)
        val preferenceSmithDivision1B = PreferenceTeacherByDivision(teacher = smithTeacher, division = division1B, points = 5)
        val preferenceJohnsonDivision1A = PreferenceTeacherByDivision(teacher = johnsonTeacher, division = division1A, points = 3)

        // Preference: Subject <-> Place
        val preferenceMathPlace22 = PreferenceSubjectByPlace(subject = maths, place = place22, points = 10)
        val preferenceMathPlaceGym = PreferenceSubjectByPlace(subject = maths, place = placeGym, points = -10_000)
        val preferenceBiologyPlace22 = PreferenceSubjectByPlace(subject = biology, place = place22, points = 2)

        // Preference: Subject <-> Division
        val preferenceMathDivision1A = PreferenceSubjectByDivision(subject = maths, division = division1A, points = 3)
        val preferenceMathDivision1B = PreferenceSubjectByDivision(subject = maths, division = division1B, points = -2)
        val preferenceBiologyDivision1A = PreferenceSubjectByDivision(subject = biology, division = division1A, points = 1)

        // Preference: Division <-> Place
        val preferenceDivision1APlace22 = PreferenceDivisionByPlace(division = division1A, place = place22, points = 2)
        val preferenceDivision1APlaceGym = PreferenceDivisionByPlace(division = division1A, place = placeGym, points = 1)
        val preferenceDivision1BPlace22 = PreferenceDivisionByPlace(division = division1B, place = place22, points = 1)

        // Preference: Teacher <-> LessonAndDayOfWeek
        val preferenceSmithLesson1Monday = PreferenceDataTimeForTeacher(teacher = smithTeacher, lesson = lesson1, dayOfWeek = DayOfWeek.MONDAY.value, points = 10)
        val preferenceSmithLesson2Monday = PreferenceDataTimeForTeacher(teacher = smithTeacher, lesson = lesson2, dayOfWeek = DayOfWeek.MONDAY.value, points = 5)
        val preferenceJohnsonLesson1Monday = PreferenceDataTimeForTeacher(teacher = johnsonTeacher, lesson = lesson1, dayOfWeek = DayOfWeek.MONDAY.value, points = 2)

        // Preference: Subject <-> LessonAndDayOfWeek
        val preferenceMathLesson1Monday = PreferenceDataTimeForSubject(subject = maths, lesson = lesson1, dayOfWeek = DayOfWeek.MONDAY.value, points = 5)
        val preferenceMathLesson2Monday = PreferenceDataTimeForSubject(subject = maths, lesson = lesson2, dayOfWeek = DayOfWeek.MONDAY.value, points = 3)
        val preferenceBiologyLesson1Monday = PreferenceDataTimeForSubject(subject = maths, lesson = lesson1, dayOfWeek = DayOfWeek.MONDAY.value, points = 1)

        // Preference: Division <-> LessonAndDayOfWeek
        val preferenceDivision1ALesson1Monday = PreferenceDataTimeForDivision(division = division1A, lesson = lesson1, dayOfWeek = DayOfWeek.MONDAY.value, points = -5)
        val preferenceDivision1ALesson2Monday = PreferenceDataTimeForDivision(division = division1A, lesson = lesson2, dayOfWeek = DayOfWeek.MONDAY.value, points = 2)
        val preferenceDivision1BLesson1Monday = PreferenceDataTimeForDivision(division = division1B, lesson = lesson1, dayOfWeek = DayOfWeek.MONDAY.value, points = -3)

        // Preference: Place <-> LessonAndDayOfWeek
        val preferencePlace22Lesson1Monday = PreferenceDataTimeForPlace(place = place22, lesson = lesson1, dayOfWeek = DayOfWeek.MONDAY.value, points = 1)
        val preferencePlace22Lesson2Monday = PreferenceDataTimeForPlace(place = place22, lesson = lesson2, dayOfWeek = DayOfWeek.MONDAY.value, points = 3)
        val preferencePlaceGymLesson1Monday = PreferenceDataTimeForPlace(place = placeGym, lesson = lesson1, dayOfWeek = DayOfWeek.MONDAY.value, points = -1)

        smithTeacher.apply {
            preferenceSubjectByTeacher = setOf(
                preferenceSmithMath,
                preferenceSmithBiology
            )
            preferenceTeacherByPlace = setOf(
                preferenceSmithPlace22,
                preferenceSmithPlaceGym
            )
            preferencesTeacherByDivision = setOf(
                preferenceSmithDivision1A,
                preferenceSmithDivision1B
            )
            preferenceDataTimeForTeachers = setOf(
                preferenceSmithLesson1Monday,
                preferenceSmithLesson2Monday
            )
        }

        maths.apply {
            preferenceSubjectByTeacher = setOf(
                preferenceSmithMath,
                preferenceJohnsonMath
            )
            preferenceSubjectByPlace = setOf(
                preferenceMathPlace22,
                preferenceMathPlaceGym
            )
            preferencesSubjectByDivision = setOf(
                preferenceMathDivision1A,
                preferenceMathDivision1B
            )
            preferencesDateTimeForSubject = setOf(
                preferenceMathLesson1Monday,
                preferenceMathLesson2Monday
            )
        }

        division1A.apply {
            preferencesSubjectByDivision = setOf(
                preferenceMathDivision1A,
                preferenceBiologyDivision1A
            )
            preferencesTeacherByDivision = setOf(
                preferenceSmithDivision1A,
                preferenceJohnsonDivision1A
            )
            preferenceDivisionByPlace = setOf(
                preferenceDivision1APlace22,
                preferenceDivision1APlaceGym
            )
            preferencesDateTimeForDivision = setOf(
                preferenceDivision1ALesson1Monday,
                preferenceDivision1ALesson2Monday
            )
        }

        place22.apply {
            preferenceTeacherByPlace = setOf(
                preferenceSmithPlace22,
                preferenceJohnsonPlace22
            )
            preferenceSubjectByPlace = setOf(
                preferenceMathPlace22,
                preferenceBiologyPlace22
            )
            preferenceDivisionByPlace = setOf(
                preferenceDivision1APlace22,
                preferenceDivision1BPlace22
            )
            preferencesDateTimeForPlace = setOf(
                preferencePlace22Lesson1Monday,
                preferencePlace22Lesson2Monday
            )
        }
    }

    private lateinit var preference: Preference


    @BeforeEach
    internal fun setUp() {
        preference = Preference(
            teachersId = setOf(smithTeacher.id!!, johnsonTeacher.id!!),
            subjectsId = setOf(maths.id!!, biology.id!!),
            placesId = setOf(place22.id!!, placeGym.id!!),
            divisionsId = setOf(division1A.id!!, division1B.id!!),
            lessonsId = setOf(lesson1.id!!, lesson2.id!!)
        )
    }


    @Nested
    inner class CalculateByTeacherCase {

        @Test
        fun `test calculate subject preferences`() {
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
        fun `test calculate place preferences`() {
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
        fun `test calculate division preferences`() {
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
        fun `test calculate teacher preferences`() {
            preference.calculateByTeacher(smithTeacher)
            Assert.assertTrue(preference.preferredTeacherMap.all { it.value.points == 0 })
        }

        @Test
        fun `test calculate lesson and day of week preferences`() {
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
    }


    @Nested
    inner class CalculateBySubjectCase {
        @Test
        fun `test calculate subject preferences`() {
            preference.calculateBySubject(maths)
            Assert.assertTrue(preference.preferredSubjectMap.all { it.value.points == 0 })
        }

        @Test
        fun `test calculate place preferences`() {
            preference.calculateBySubject(maths)

            val preferencePlace22 = preference.preferredPlaceMap[place22.id]
            preferencePlace22!!.run {
                Assert.assertEquals(10, points)
                Assert.assertEquals(0, preferredByDivision)
                Assert.assertEquals(10, preferredBySubject)
                Assert.assertEquals(0, preferredByTeacher)
                Assert.assertEquals(0, preferredByDateTime)
            }

            val preferencePlaceGym = preference.preferredPlaceMap[placeGym.id]
            preferencePlaceGym!!.run {
                Assert.assertEquals(-10_000, points)
                Assert.assertEquals(0, preferredByDivision)
                Assert.assertEquals(-10_000, preferredBySubject)
                Assert.assertEquals(0, preferredByTeacher)
                Assert.assertEquals(0, preferredByDateTime)
            }
        }

        @Test
        fun `test calculate division preferences`() {
            preference.calculateBySubject(maths)

            val preferenceDivision1A = preference.preferredDivisionMap[division1A.id]
            preferenceDivision1A!!.run {
                Assert.assertEquals(3, points)
                Assert.assertEquals(0, preferredByPlace)
                Assert.assertEquals(3, preferredBySubject)
                Assert.assertEquals(0, preferredByTeacher)
                Assert.assertEquals(0, preferredByDateTime)
            }

            val preferenceDivision1B = preference.preferredDivisionMap[division1B.id]
            preferenceDivision1B!!.run {
                Assert.assertEquals(-2, points)
                Assert.assertEquals(0, preferredByPlace)
                Assert.assertEquals(-2, preferredBySubject)
                Assert.assertEquals(0, preferredByTeacher)
                Assert.assertEquals(0, preferredByDateTime)
            }
        }

        @Test
        fun `test calculate teacher preferences`() {
            preference.calculateBySubject(maths)

            val preferenceSmithTeacher = preference.preferredTeacherMap[smithTeacher.id]
            preferenceSmithTeacher!!.run {
                Assert.assertEquals(10, points)
                Assert.assertEquals(0, preferredByPlace)
                Assert.assertEquals(10, preferredBySubject)
                Assert.assertEquals(0, preferredByDivision)
                Assert.assertEquals(0, preferredByDateTime)
            }

            val preferenceJohnsonTeacher = preference.preferredTeacherMap[johnsonTeacher.id]
            preferenceJohnsonTeacher!!.run {
                Assert.assertEquals(-10_000, points)
                Assert.assertEquals(0, preferredByPlace)
                Assert.assertEquals(-10_000, preferredBySubject)
                Assert.assertEquals(0, preferredByDivision)
                Assert.assertEquals(0, preferredByDateTime)
            }
        }

        @Test
        fun `test calculate lesson and day of week preferences`() {
            preference.calculateBySubject(maths)

            val preferenceByLesson1AndMonday = preference.getPreferenceByLessonAndDay(DayOfWeek.MONDAY.value, lessonId = lesson1.id)!!.preference
            preferenceByLesson1AndMonday.run {
                Assert.assertEquals(5,points)
                Assert.assertEquals(0, preferredByDivision)
                Assert.assertEquals(0, preferredByPlace)
                Assert.assertEquals(5, preferredBySubject)
                Assert.assertEquals(0,preferredByTeacher)
            }

            val preferenceByLesson2AndMonday = preference.getPreferenceByLessonAndDay(DayOfWeek.MONDAY.value, lessonId = lesson2.id)!!.preference
            preferenceByLesson2AndMonday.run {
                Assert.assertEquals(3,points)
                Assert.assertEquals(0, preferredByDivision)
                Assert.assertEquals(0, preferredByPlace)
                Assert.assertEquals(3, preferredBySubject)
                Assert.assertEquals(0,preferredByTeacher)
            }
        }
    }

    @Nested
    inner class CalculateByPlaceCase {
        @Test
        fun `test calculate subject preferences`() {
            preference.calculateByPlace(place22)

            val preferenceMath = preference.preferredSubjectMap[maths.id]
            preferenceMath!!.run {
                Assert.assertEquals(10, points)
                Assert.assertEquals(0, preferredByDivision)
                Assert.assertEquals(10, preferredByPlace)
                Assert.assertEquals(0, preferredByTeacher)
                Assert.assertEquals(0, preferredByDateTime)
            }

            val preferenceBiology = preference.preferredSubjectMap[biology.id]
            preferenceBiology!!.run {
                Assert.assertEquals(2, points)
                Assert.assertEquals(0, preferredByDivision)
                Assert.assertEquals(2, preferredByPlace)
                Assert.assertEquals(0, preferredByTeacher)
                Assert.assertEquals(0, preferredByDateTime)
            }
        }


        @Test
        fun `test calculate place preferences`() {
            preference.calculateByPlace(place22)
            Assert.assertTrue(preference.preferredPlaceMap.all { it.value.points == 0 })
        }

        @Test
        fun `test calculate division preferences`() {
            preference.calculateByPlace(place22)

            val preferenceDivision1A = preference.preferredDivisionMap[division1A.id]
            preferenceDivision1A!!.run {
                Assert.assertEquals(2, points)
                Assert.assertEquals(2, preferredByPlace)
                Assert.assertEquals(0, preferredBySubject)
                Assert.assertEquals(0, preferredByTeacher)
                Assert.assertEquals(0, preferredByDateTime)
            }

            val preferenceDivision1B = preference.preferredDivisionMap[division1B.id]
            preferenceDivision1B!!.run {
                Assert.assertEquals(1, points)
                Assert.assertEquals(1, preferredByPlace)
                Assert.assertEquals(0, preferredBySubject)
                Assert.assertEquals(0, preferredByTeacher)
                Assert.assertEquals(0, preferredByDateTime)
            }
        }

        @Test
        fun `test calculate teacher preferences`() {
            preference.calculateByPlace(place22)

            val preferenceSmithTeacher = preference.preferredTeacherMap[smithTeacher.id]
            preferenceSmithTeacher!!.run {
                Assert.assertEquals(10, points)
                Assert.assertEquals(0, preferredByDivision)
                Assert.assertEquals(10, preferredByPlace)
                Assert.assertEquals(0, preferredBySubject)
                Assert.assertEquals(0, preferredByDateTime)
            }

            val preferenceJohnsonTeacher = preference.preferredTeacherMap[johnsonTeacher.id]
            preferenceJohnsonTeacher!!.run {
                Assert.assertEquals(3, points)
                Assert.assertEquals(0, preferredByDivision)
                Assert.assertEquals(3, preferredByPlace)
                Assert.assertEquals(0, preferredBySubject)
                Assert.assertEquals(0, preferredByDateTime)
            }
        }

        @Test
        fun `test calculate lesson and day of week preferences`() {
            preference.calculateByPlace(place22)

            val preferenceByLesson1AndMonday = preference.getPreferenceByLessonAndDay(DayOfWeek.MONDAY.value, lessonId = lesson1.id)!!.preference
            preferenceByLesson1AndMonday.run {
                Assert.assertEquals(1,points)
                Assert.assertEquals(0, preferredByDivision)
                Assert.assertEquals(1, preferredByPlace)
                Assert.assertEquals(0, preferredBySubject)
                Assert.assertEquals(0, preferredByTeacher)
            }

            val preferenceByLesson2AndMonday = preference.getPreferenceByLessonAndDay(DayOfWeek.MONDAY.value, lessonId = lesson2.id)!!.preference
            preferenceByLesson2AndMonday.run {
                Assert.assertEquals(3,points)
                Assert.assertEquals(0, preferredByDivision)
                Assert.assertEquals(3, preferredByPlace)
                Assert.assertEquals(0, preferredBySubject)
                Assert.assertEquals(0,preferredByTeacher)
            }
        }
    }

    @Nested
    inner class CalculateByDivisionCase {
        @Test
        fun `test calculate subject preferences`() {
            preference.calculateByDivision(division1A)

            val preferenceMath = preference.preferredSubjectMap[maths.id]
            preferenceMath!!.run {
                Assert.assertEquals(3, points)
                Assert.assertEquals(3, preferredByDivision)
                Assert.assertEquals(0, preferredByPlace)
                Assert.assertEquals(0, preferredByTeacher)
                Assert.assertEquals(0, preferredByDateTime)
            }

            val preferenceBiology = preference.preferredSubjectMap[biology.id]
            preferenceBiology!!.run {
                Assert.assertEquals(1, points)
                Assert.assertEquals(1, preferredByDivision)
                Assert.assertEquals(0, preferredByPlace)
                Assert.assertEquals(0, preferredByTeacher)
                Assert.assertEquals(0, preferredByDateTime)
            }
        }

        @Test
        fun `test calculate place preferences`() {
            preference.calculateByDivision(division1A)

            val preferencePlace22 = preference.preferredPlaceMap[place22.id]
            preferencePlace22!!.run {
                Assert.assertEquals(2, points)
                Assert.assertEquals(2, preferredByDivision)
                Assert.assertEquals(0, preferredBySubject)
                Assert.assertEquals(0, preferredByTeacher)
                Assert.assertEquals(0, preferredByDateTime)
            }

            val preferencePlaceGym = preference.preferredPlaceMap[placeGym.id]
            preferencePlaceGym!!.run {
                Assert.assertEquals(1, points)
                Assert.assertEquals(1, preferredByDivision)
                Assert.assertEquals(0, preferredBySubject)
                Assert.assertEquals(0, preferredByTeacher)
                Assert.assertEquals(0, preferredByDateTime)
            }
        }

        @Test
        fun `test calculate division preferences`() {
            preference.calculateByDivision(division1A)
            Assert.assertTrue(preference.preferredDivisionMap.all { it.value.points == 0 })
        }

        @Test
        fun `test calculate teacher preferences`() {
            preference.calculateByDivision(division1A)

            val preferenceSmith = preference.preferredTeacherMap[smithTeacher.id]
            preferenceSmith!!.run {
                Assert.assertEquals(10, points)
                Assert.assertEquals(10, preferredByDivision)
                Assert.assertEquals(0, preferredBySubject)
                Assert.assertEquals(0, preferredByPlace)
                Assert.assertEquals(0, preferredByDateTime)
            }

            val preferenceJohnson = preference.preferredTeacherMap[johnsonTeacher.id]
            preferenceJohnson!!.run {
                Assert.assertEquals(3, points)
                Assert.assertEquals(3, preferredByDivision)
                Assert.assertEquals(0, preferredBySubject)
                Assert.assertEquals(0, preferredByPlace)
                Assert.assertEquals(0, preferredByDateTime)
            }
        }

        @Test
        fun `test calculate lesson and day of week preferences`() {
            preference.calculateByDivision(division1A)

            val preferenceByLesson1AndMonday = preference.getPreferenceByLessonAndDay(DayOfWeek.MONDAY.value, lessonId = lesson1.id)!!.preference
            preferenceByLesson1AndMonday.run {
                Assert.assertEquals(-5,points)
                Assert.assertEquals(-5, preferredByDivision)
                Assert.assertEquals(0, preferredByPlace)
                Assert.assertEquals(0, preferredBySubject)
                Assert.assertEquals(0, preferredByTeacher)
            }

            val preferenceByLesson2AndMonday = preference.getPreferenceByLessonAndDay(DayOfWeek.MONDAY.value, lessonId = lesson2.id)!!.preference
            preferenceByLesson2AndMonday.run {
                Assert.assertEquals(2,points)
                Assert.assertEquals(2, preferredByDivision)
                Assert.assertEquals(0, preferredByPlace)
                Assert.assertEquals(0, preferredBySubject)
                Assert.assertEquals(0,preferredByTeacher)
            }
        }
    }


    @Nested
    inner class CalculateByLessonAndDayOfWeekCase {
        @Test
        fun `test calculate subject preferences`() {
        }


        @Test
        fun `test calculate place preferences`() {

        }

        @Test
        fun `test calculate division preferences`() {

        }

        @Test
        fun `test calculate teacher preferences`() {
        }

        @Test
        fun `test calculate lesson and day of week preferences`() {

        }
    }


}
