package com.patres.timetable.web.rest.util

import com.patres.timetable.domain.Period
import com.patres.timetable.domain.Timetable
import com.patres.timetable.service.dto.TimetableDTO
import org.slf4j.LoggerFactory
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters
import java.util.*

object TimetableDate {

    private val log = LoggerFactory.getLogger(TimetableDate::class.java)


    private fun getDatesFromPeriod(period: Period): Set<LocalDate> {
        val dates = HashSet<LocalDate>()
        val intervals = period.intervalTimes
        intervals.sortedBy { it.included }

        intervals.forEach { interval ->
            if (interval.startDate != null && interval.endDate != null) {
                var date = interval.startDate
                while (date!!.isBefore(interval.endDate!!)) {
                    if (interval.included) {
                        dates.add(date)
                    } else {
                        dates.remove(date)
                    }
                    date = date.plusDays(1)
                }
            }
        }
        return dates
    }

    fun getAllDates(timetable: Timetable, firstDateFromPeriod: LocalDate): Set<LocalDate> {
        val datesFromPeriod = getDatesFromPeriod(timetable.period!!)
        val availableDates = HashSet<LocalDate>()
        val eachWeek = timetable.everyWeek!!
        val startWithWeek = timetable.startWithWeek!!

        var firstMonday: LocalDate
        if (firstDateFromPeriod.dayOfWeek == DayOfWeek.MONDAY) {
            firstMonday = firstDateFromPeriod
        } else {
            firstMonday = firstDateFromPeriod.with(TemporalAdjusters.previous(DayOfWeek.MONDAY))
        }
        firstMonday = firstMonday.plusDays(7 * (startWithWeek - 1))

        for (localDate in datesFromPeriod) {
            val weekNumber = ChronoUnit.WEEKS.between(firstMonday, localDate)
            if (!firstMonday.isAfter(localDate) && weekNumber % eachWeek == 0L) {
                availableDates.add(localDate)
            }

        }
        return availableDates
    }

    fun canAddByWeekDay(date: LocalDate, timetable: TimetableDTO): Boolean {
        return if(timetable.date != null) {
            timetable.date == date
        } else {
            (date.dayOfWeek == DayOfWeek.MONDAY && timetable.inMonday
                || date.dayOfWeek == DayOfWeek.TUESDAY && timetable.inTuesday
                || date.dayOfWeek == DayOfWeek.WEDNESDAY && timetable.inWednesday
                || date.dayOfWeek == DayOfWeek.THURSDAY && timetable.inThursday
                || date.dayOfWeek == DayOfWeek.FRIDAY && timetable.inFriday
                || date.dayOfWeek == DayOfWeek.SATURDAY && timetable.inSaturday
                || date.dayOfWeek == DayOfWeek.SUNDAY && timetable.inSunday)
        }
    }

    fun canAddByEveryDay(date: LocalDate, firstDateFromPeriod: LocalDate?, timetable: TimetableDTO): Boolean {
        if (firstDateFromPeriod == null) {
            return false
        }
        val startWithWeek = timetable.startWithWeek
        val everyWeek = timetable.everyWeek
        log.debug("Can be add: date={}, firstDateFromPeriod={}, startWithWeek={}, everyWeek={}", date, firstDateFromPeriod, startWithWeek, everyWeek)
        var firstMonday: LocalDate
        if (firstDateFromPeriod.dayOfWeek == DayOfWeek.MONDAY) {
            firstMonday = firstDateFromPeriod
        } else {
            firstMonday = firstDateFromPeriod.with(TemporalAdjusters.previous(DayOfWeek.MONDAY))
        }
        firstMonday = firstMonday.plusDays(7 * (startWithWeek - 1))

        val weekNumber = ChronoUnit.WEEKS.between(firstMonday, date)

        return !firstMonday.isAfter(date) && weekNumber % everyWeek == 0L
    }

}
