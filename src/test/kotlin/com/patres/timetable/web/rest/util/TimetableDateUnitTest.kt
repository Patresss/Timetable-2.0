package com.patres.timetable.web.rest.util

import com.patres.timetable.service.dto.TimetableDTO
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class TimetableDateUnitTest {

    @Test
    fun `should return true if timetable is week day`() {
        val monday = LocalDate.of(2018, 2, 12)
        val timetable = TimetableDTO().apply { inMonday = true }

        val result = TimetableDateUtil.canAddByWeekDay(monday, timetable)
        val expected = true
        assertEquals(expected, result)
    }

    @Test
    fun `should return false if timetable is not week day`() {
        val tuesday = LocalDate.of(2018, 2, 13)
        val timetable = TimetableDTO().apply { inTuesday = false }

        val result = TimetableDateUtil.canAddByWeekDay(tuesday, timetable)
        val expected = false
        assertEquals(expected, result)
    }

    @Test
    fun `should return true if timetable has this same date`() {
        val tuesday = LocalDate.of(2018, 2, 13)
        val timetable = TimetableDTO().apply { date = tuesday }

        val result = TimetableDateUtil.canAddByWeekDay(tuesday, timetable)
        val expected = true
        assertEquals(expected, result)
    }

    @Test
    fun `should return false if timetable has different date`() {
        val tuesday = LocalDate.of(2018, 2, 13)
        val timetable = TimetableDTO().apply { date = LocalDate.of(2018, 2, 14) }

        val result = TimetableDateUtil.canAddByWeekDay(tuesday, timetable)
        val expected = false
        assertEquals(expected, result)
    }

    @Test
    fun `should return true if timetable is every week`() {
        val fistMonday = LocalDate.of(2018, 2, 5)
        val date = LocalDate.of(2018, 2, 7)
        val everyWeek = 1L
        val startWithWeek = 1L

        val result = TimetableDateUtil.canAddByEveryDay(date, fistMonday, everyWeek, startWithWeek)
        val expected = true
        assertEquals(expected, result)
    }

    @Test
    fun `should return true if timetable is every 2 week and start week is 1 with date in 1 week`() {
        val fistMonday = LocalDate.of(2018, 2, 5)
        val date = LocalDate.of(2018, 2, 7)
        val everyWeek = 2L
        val startWithWeek = 1L

        val result = TimetableDateUtil.canAddByEveryDay(date, fistMonday, everyWeek, startWithWeek)
        val expected = true
        assertEquals(expected, result)
    }

    @Test
    fun `should return false if timetable is every 2 week and start week is 1 with date in 2 week`() {
        val fistMonday = LocalDate.of(2018, 2, 5)
        val date = LocalDate.of(2018, 2, 14)
        val everyWeek = 2L
        val startWithWeek = 1L

        val result = TimetableDateUtil.canAddByEveryDay(date, fistMonday, everyWeek, startWithWeek)
        val expected = false
        assertEquals(expected, result)
    }

    @Test
    fun `should return true if timetable is every 2 week and start week is 2 with date in 2 week`() {
        val fistMonday = LocalDate.of(2018, 2, 5)
        val date = LocalDate.of(2018, 2, 14)
        val everyWeek = 2L
        val startWithWeek = 2L

        val result = TimetableDateUtil.canAddByEveryDay(date, fistMonday, everyWeek, startWithWeek)
        val expected = true
        assertEquals(expected, result)
    }

    @Test
    fun `should return false if timetable is every 2 week and start week is 2 with date in 1 week`() {
        val fistMonday = LocalDate.of(2018, 2, 5)
        val date = LocalDate.of(2018, 2, 7)
        val everyWeek = 2L
        val startWithWeek = 2L

        val result = TimetableDateUtil.canAddByEveryDay(date, fistMonday, everyWeek, startWithWeek)
        val expected = false
        assertEquals(expected, result)
    }
}
