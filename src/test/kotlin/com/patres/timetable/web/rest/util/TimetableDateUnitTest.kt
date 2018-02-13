package com.patres.timetable.web.rest.util

import com.patres.timetable.service.dto.TimetableDTO
import org.junit.Assert.*
import org.junit.Test
import java.time.LocalDate

class TimetableDateUnitTest {

    @Test
    fun `should return true if timetable is week day`() {
        val monday = LocalDate.of(2018, 2, 12)
        val timetable = TimetableDTO().apply { inMonday = true }

        val result = TimetableDate.canAddByWeekDay(monday, timetable)
        val expected = true
        assertEquals(expected, result)
    }

    @Test
    fun `should return false if timetable is not week day`() {
        val tuesday = LocalDate.of(2018, 2, 13)
        val timetable = TimetableDTO().apply { inTuesday = false }

        val result = TimetableDate.canAddByWeekDay(tuesday, timetable)
        val expected = false
        assertEquals(expected, result)
    }

    @Test
    fun `should return true if timetable has this same date`() {
        val tuesday = LocalDate.of(2018, 2, 13)
        val timetable = TimetableDTO().apply { date = tuesday }

        val result = TimetableDate.canAddByWeekDay(tuesday, timetable)
        val expected = true
        assertEquals(expected, result)
    }

    @Test
    fun `should return false if timetable has different date`() {
        val tuesday = LocalDate.of(2018, 2, 13)
        val timetable = TimetableDTO().apply { date = LocalDate.of(2018, 2, 14) }

        val result = TimetableDate.canAddByWeekDay(tuesday, timetable)
        val expected = false
        assertEquals(expected, result)
    }

    @Test
    fun `should return true if timetable is every week`() {
        val fistMonday = LocalDate.of(2018, 2, 5)
        val date = LocalDate.of(2018, 2, 7)
        val timetable = TimetableDTO().apply {
            everyWeek = 1
            startWithWeek = 1
            }

        val result = TimetableDate.canAddByEveryDay(date, fistMonday, timetable)
        val expected = true
        assertEquals(expected, result)
    }

    @Test
    fun `should return true if timetable is every 2 week and start week is 1 with date in 1 week`() {
        val fistMonday = LocalDate.of(2018, 2, 5)
        val date = LocalDate.of(2018, 2, 7)
        val timetable = TimetableDTO().apply {
            everyWeek = 2
            startWithWeek = 1
        }

        val result = TimetableDate.canAddByEveryDay(date, fistMonday, timetable)
        val expected = true
        assertEquals(expected, result)
    }

    @Test
    fun `should return false if timetable is every 2 week and start week is 1 with date in 2 week`() {
        val fistMonday = LocalDate.of(2018, 2, 5)
        val date = LocalDate.of(2018, 2, 14)
        val timetable = TimetableDTO().apply {
            everyWeek = 2
            startWithWeek = 1
        }

        val result = TimetableDate.canAddByEveryDay(date, fistMonday, timetable)
        val expected = false
        assertEquals(expected, result)
    }

    @Test
    fun `should return true if timetable is every 2 week and start week is 2 with date in 2 week`() {
        val fistMonday = LocalDate.of(2018, 2, 5)
        val date = LocalDate.of(2018, 2, 14)
        val timetable = TimetableDTO().apply {
            everyWeek = 2
            startWithWeek = 2
        }

        val result = TimetableDate.canAddByEveryDay(date, fistMonday, timetable)
        val expected = true
        assertEquals(expected, result)
    }

    @Test
    fun `should return false if timetable is every 2 week and start week is 2 with date in 1 week`() {
        val fistMonday = LocalDate.of(2018, 2, 5)
        val date = LocalDate.of(2018, 2, 7)
        val timetable = TimetableDTO().apply {
            everyWeek = 2
            startWithWeek = 2
        }

        val result = TimetableDate.canAddByEveryDay(date, fistMonday, timetable)
        val expected = false
        assertEquals(expected, result)
    }
}
