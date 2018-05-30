package com.patres.timetable.generator

import com.patres.timetable.domain.Division
import com.patres.timetable.domain.Lesson
import com.patres.timetable.domain.Timetable
import com.patres.timetable.excpetion.ApplicationException
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import java.time.DayOfWeek
import org.junit.rules.ExpectedException




open class TimetableGeneratorContainerTest {


    @get:Rule
    val exception = ExpectedException.none()

    private val lesson0 = Lesson(name = "0").apply {
        setStartTimeHHmmFormatted("07:10")
        setEndTimeHHmmFormatted("07:55")
    }
    private val lesson1 = Lesson(name = "1").apply {
        setStartTimeHHmmFormatted("08:00")
        setEndTimeHHmmFormatted("08:45")
    }
    private val lesson2 = Lesson(name = "2").apply {
        setStartTimeHHmmFormatted("08:50")
        setEndTimeHHmmFormatted("09:35")
    }
    private val lesson3 = Lesson(name = "3").apply {
        setStartTimeHHmmFormatted("09:45")
        setEndTimeHHmmFormatted("10:30")
    }
    private val lesson4 = Lesson(name = "4").apply {
        setStartTimeHHmmFormatted("10:35")
        setEndTimeHHmmFormatted("11:20")
    }
    private val lesson5 = Lesson(name = "5").apply {
        setStartTimeHHmmFormatted("11:25")
        setEndTimeHHmmFormatted("12:10")
    }
    private val lesson6 = Lesson(name = "6").apply {
        setStartTimeHHmmFormatted("12:30")
        setEndTimeHHmmFormatted("13:15")
    }
    private val lesson7 = Lesson(name = "7").apply {
        setStartTimeHHmmFormatted("13:20")
        setEndTimeHHmmFormatted("14:05")
    }
    private val lesson8 = Lesson(name = "8").apply {
        setStartTimeHHmmFormatted("14:10")
        setEndTimeHHmmFormatted("14:55")
    }
    private val lesson9 = Lesson(name = "9").apply {
        setStartTimeHHmmFormatted("15:00")
        setEndTimeHHmmFormatted("15:45")
    }
    private val lesson10 = Lesson(name = "10").apply {
        setStartTimeHHmmFormatted("15:50")
        setEndTimeHHmmFormatted("16:35")
    }
    private val lesson11 = Lesson(name = "11").apply {
        setStartTimeHHmmFormatted("16:40")
        setEndTimeHHmmFormatted("17:25")
    }
    private val lessons = setOf(lesson0, lesson1, lesson2, lesson3, lesson4, lesson5, lesson6, lesson7, lesson8, lesson9, lesson10, lesson11)

    private val class1A = Division()
    private val class2A = Division()

    @Test
    open fun `should return one windows fo one class`() {
        val timetables = mutableListOf(
            Timetable(lesson = lesson0, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),
            Timetable(lesson = lesson2, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A)
        )
        val timetableGeneratorContainer = TimetableGeneratorContainer(lessons = lessons).apply { timetablesFromCurriculum = timetables }
        val windows = timetableGeneratorContainer.findWidows()
        assertThat(windows.size).isEqualTo(1)
    }

    @Test
    open fun `should return zero windows for two class`() {
        val timetables = mutableListOf(
            Timetable(lesson = lesson0, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),
            Timetable(lesson = lesson2, dayOfWeek = DayOfWeek.MONDAY.value, division = class2A)
        )
        val timetableGeneratorContainer = TimetableGeneratorContainer(lessons = lessons).apply { timetablesFromCurriculum = timetables }
        val windows = timetableGeneratorContainer.findWidows()
        assertThat(windows.size).isEqualTo(0)
    }

    @Test
    open fun `should return six windows for two class`() {
        val timetables = mutableListOf(
            Timetable(lesson = lesson0, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),

            Timetable(lesson = lesson2, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),
            Timetable(lesson = lesson3, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),


            Timetable(lesson = lesson6, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),
            Timetable(lesson = lesson7, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),

            Timetable(lesson = lesson9, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),

            Timetable(lesson = lesson11, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),
            Timetable(lesson = lesson0, dayOfWeek = DayOfWeek.MONDAY.value, division = class2A),

            Timetable(lesson = lesson2, dayOfWeek = DayOfWeek.MONDAY.value, division = class2A)
        )
        val timetableGeneratorContainer = TimetableGeneratorContainer(lessons = lessons).apply { timetablesFromCurriculum = timetables }
        val windows = timetableGeneratorContainer.findWidows()
        assertThat(windows.size).isEqualTo(6)
    }


    @Test
    open fun `should return 1 group from findGroupsFromTimetablesWithThisSameDayAndDivision`() {
        val timetables = mutableListOf(
            Timetable(lesson = lesson0, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A)
        )
        val timetableGeneratorContainer = TimetableGeneratorContainer(lessons = lessons)
        val groups = timetableGeneratorContainer.findGroupsFromTimetablesWithThisSameDayAndDivision(timetables)
        assertThat(groups.size).isEqualTo(1)
        assertThat(groups.blocksWithoutWindow.first().timetables.contains(timetables.first()))
    }

    @Test
    open fun `should return 2 group from findGroupsFromTimetablesWithThisSameDayAndDivision`() {
        val timetables = mutableListOf(
            Timetable(lesson = lesson0, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),

            Timetable(lesson = lesson2, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),
            Timetable(lesson = lesson3, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A)
        )
        val wrongTimetable = Timetable(lesson = lesson3, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A)

        val timetableGeneratorContainer = TimetableGeneratorContainer(lessons = lessons)
        val groups = timetableGeneratorContainer.findGroupsFromTimetablesWithThisSameDayAndDivision(timetables)
        assertThat(groups.size).isEqualTo(2)
        assertThat(groups.blocksWithoutWindow.first().timetables.contains(timetables.first()))
        assertThat(groups.timetables.contains(timetables[1]))
        assertThat(groups.timetables.contains(timetables[2]))
        assertThat(!groups.timetables.contains(wrongTimetable))
    }

    @Test
    open fun `should return 4 group from findGroupsFromTimetablesWithThisSameDayAndDivision`() {
        val timetables = mutableListOf(
            Timetable(lesson = lesson0, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),

            Timetable(lesson = lesson2, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),
            Timetable(lesson = lesson3, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),
            Timetable(lesson = lesson4, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),

            Timetable(lesson = lesson8, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),
            Timetable(lesson = lesson9, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),

            Timetable(lesson = lesson11, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A)
        )

        val timetableGeneratorContainer = TimetableGeneratorContainer(lessons = lessons)
        val groups = timetableGeneratorContainer.findGroupsFromTimetablesWithThisSameDayAndDivision(timetables)
        assertThat(groups.size).isEqualTo(4)
    }

    @Test
    open fun `should throw exception when send different division to findGroupsFromTimetablesWithThisSameDayAndDivision`() {
        val timetables = mutableListOf(
            Timetable(lesson = lesson0, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),
            Timetable(lesson = lesson2, dayOfWeek = DayOfWeek.MONDAY.value, division = class2A),
            Timetable(lesson = lesson3, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A)
        )

        val timetableGeneratorContainer = TimetableGeneratorContainer(lessons = lessons)
        exception.expect(ApplicationException::class.java)
        timetableGeneratorContainer.findGroupsFromTimetablesWithThisSameDayAndDivision(timetables)
    }

    @Test
    open fun `should throw exception when send different day to findGroupsFromTimetablesWithThisSameDayAndDivision`() {
        val timetables = mutableListOf(
            Timetable(lesson = lesson0, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),
            Timetable(lesson = lesson2, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),
            Timetable(lesson = lesson3, dayOfWeek = DayOfWeek.FRIDAY.value, division = class1A)
        )

        val timetableGeneratorContainer = TimetableGeneratorContainer(lessons = lessons)
        exception.expect(ApplicationException::class.java)
        timetableGeneratorContainer.findGroupsFromTimetablesWithThisSameDayAndDivision(timetables)
    }


    @Test
    open fun `should return the biggest group from findAndSetupTheBiggestGroupsByDivision`() {
        val timetables = mutableListOf(
            Timetable(lesson = lesson0, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),

            Timetable(lesson = lesson2, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),
            Timetable(lesson = lesson3, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),
            Timetable(lesson = lesson4, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),

            Timetable(lesson = lesson8, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),
            Timetable(lesson = lesson9, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),

            Timetable(lesson = lesson11, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A)
        )
        val timetableGeneratorContainer = TimetableGeneratorContainer(lessons = lessons)
        val groups = timetableGeneratorContainer.findAndSetupTheBiggestGroupsByDivision(timetables)
        assertThat(groups.first().timetables.size).isEqualTo(3)
    }


    @Test
    open fun `should setup the biggest group in middle from findAndSetupTheBiggestGroupsByDivision`() {
        val timetables = mutableListOf(
            Timetable(lesson = lesson0, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),

            Timetable(lesson = lesson2, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),
            Timetable(lesson = lesson3, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),
            Timetable(lesson = lesson4, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),

            Timetable(lesson = lesson8, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),
            Timetable(lesson = lesson9, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),

            Timetable(lesson = lesson11, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A)
        )
        val timetableGeneratorContainer = TimetableGeneratorContainer(lessons = lessons)
        val groups = timetableGeneratorContainer.findAndSetupTheBiggestGroupsByDivision(timetables)
        assertThat(groups.first().hasBlockBefore).isEqualTo(true)
        assertThat(groups.first().hasBlockAfter).isEqualTo(true)
    }

    @Test
    open fun `should setup the biggest group when is in begin findAndSetupTheBiggestGroupsByDivision`() {
        val timetables = mutableListOf(
            Timetable(lesson = lesson0, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),
            Timetable(lesson = lesson1, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),
            Timetable(lesson = lesson2, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),
            Timetable(lesson = lesson3, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),
            Timetable(lesson = lesson4, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),

            Timetable(lesson = lesson8, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),
            Timetable(lesson = lesson9, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),

            Timetable(lesson = lesson11, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A)
        )
        val timetableGeneratorContainer = TimetableGeneratorContainer(lessons = lessons)
        val groups = timetableGeneratorContainer.findAndSetupTheBiggestGroupsByDivision(timetables)
        assertThat(groups.first().hasBlockBefore).isEqualTo(false)
        assertThat(groups.first().hasBlockAfter).isEqualTo(true)
    }

    @Test
    open fun `should setup the biggest group when is in the end findAndSetupTheBiggestGroupsByDivision`() {
        val timetables = mutableListOf(
            Timetable(lesson = lesson0, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),

            Timetable(lesson = lesson2, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),
            Timetable(lesson = lesson3, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),
            Timetable(lesson = lesson4, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A)
        )
        val timetableGeneratorContainer = TimetableGeneratorContainer(lessons = lessons)
        val groups = timetableGeneratorContainer.findAndSetupTheBiggestGroupsByDivision(timetables)
        assertThat(groups.first().hasBlockBefore).isEqualTo(true)
        assertThat(groups.first().hasBlockAfter).isEqualTo(false)
    }

    @Test
    open fun `should return the biggest and earlier group from findAndSetupTheBiggestGroupsByDivision`() {
        val timetables = mutableListOf(
            Timetable(lesson = lesson0, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),

            Timetable(lesson = lesson2, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),
            Timetable(lesson = lesson3, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),
            Timetable(lesson = lesson4, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),

            Timetable(lesson = lesson8, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),
            Timetable(lesson = lesson9, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A),
            Timetable(lesson = lesson10, dayOfWeek = DayOfWeek.MONDAY.value, division = class1A)
        )
        val timetableGeneratorContainer = TimetableGeneratorContainer(lessons = lessons)
        val groups = timetableGeneratorContainer.findAndSetupTheBiggestGroupsByDivision(timetables)
        assertThat(groups.first().timetables.size).isEqualTo(3)
        assertThat(groups.first().timetables.any{ it.lesson == lesson2}).isEqualTo(true)
        assertThat(groups.first().timetables.any{ it.lesson == lesson3}).isEqualTo(true)
        assertThat(groups.first().timetables.any{ it.lesson == lesson4}).isEqualTo(true)
        assertThat(groups.first().timetables.any{ it.lesson == lesson8}).isEqualTo(false)
        assertThat(groups.first().timetables.any{ it.lesson == lesson9}).isEqualTo(false)
        assertThat(groups.first().timetables.any{ it.lesson == lesson10}).isEqualTo(false)
    }
}
