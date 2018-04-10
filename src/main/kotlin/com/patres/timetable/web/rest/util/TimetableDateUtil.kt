package com.patres.timetable.web.rest.util

import com.patres.timetable.domain.Period
import com.patres.timetable.preference.PreferenceDependency
import com.patres.timetable.service.dto.TimetableDTO
import org.slf4j.LoggerFactory
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters
import java.util.*

object TimetableDateUtil {

    private val log = LoggerFactory.getLogger(TimetableDateUtil::class.java)


    fun getAllDatesByPreferenceDependency(preferenceDependency: PreferenceDependency): Set<LocalDate> {
        return when {
            preferenceDependency.period != null -> getAllDatesFromPeriod(preferenceDependency)
            preferenceDependency.date != null -> setOf(preferenceDependency.date).filterNotNull().toSet()
            else -> emptySet()
        }
    }

    fun canAddByWeekDay(date: LocalDate, timetable: TimetableDTO): Boolean {
        return if (timetable.date != null) {
            timetable.date == date
        } else {
            date.dayOfWeek.value == timetable.dayOfWeek
        }
    }

    fun canAddByWeekDay(date: LocalDate, preferenceDependency: PreferenceDependency): Boolean {
        return if (preferenceDependency.date != null) {
            preferenceDependency.date == date
        } else {
            date.dayOfWeek.value == preferenceDependency.dayOfWeek
        }
    }

    fun canAddByEveryDays(dates: Set<LocalDate>, firstDateFromPeriod: LocalDate?, startWithWeek: Long, everyWeek: Long): Boolean {
        if (firstDateFromPeriod == null) {
            return false
        }
        log.debug("Can be add: date={}, firstDateFromPeriod={}, startWithWeek={}, everyWeek={}", dates, firstDateFromPeriod, startWithWeek, everyWeek)
        var firstMonday = if (firstDateFromPeriod.dayOfWeek == DayOfWeek.MONDAY) {
            firstDateFromPeriod
        } else {
            firstDateFromPeriod.with(TemporalAdjusters.previous(DayOfWeek.MONDAY))
        }
        firstMonday = firstMonday.plusDays(7 * (startWithWeek - 1))

        return dates.any { date ->
            val weekNumber = ChronoUnit.WEEKS.between(firstMonday, date)
            !firstMonday.isAfter(date) && weekNumber % everyWeek == 0L
        }
    }

    private fun getDatesFromPeriod(period: Period): Set<LocalDate> {
        val dates = HashSet<LocalDate>()
        val intervals = period.intervalTimes
        intervals.sortedBy { it.included }

        intervals.forEach { interval ->
            if (interval.startDate != null && interval.endDate != null) {
                var date = interval.startDate
                while (date?.isBefore(interval.endDate) == true) {
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

    private fun getAllDatesFromPeriod(preferenceDependency: PreferenceDependency): Set<LocalDate> {
        preferenceDependency.period?.let { period ->
            val startWithWeek = preferenceDependency.startWithWeek
            val everyWeek = preferenceDependency.everyWeek
            val datesFromPeriod = getDatesFromPeriod(period)
            val availableDates = HashSet<LocalDate>()
            val firstDateFromPeriod = period.intervalTimes.filter { it.included }.sortedBy { it.startDate }.first().startDate
            if (firstDateFromPeriod != null) {
                var firstMonday = if (firstDateFromPeriod.dayOfWeek == DayOfWeek.MONDAY) {
                    firstDateFromPeriod
                } else {
                    firstDateFromPeriod.with(TemporalAdjusters.previous(DayOfWeek.MONDAY))
                }
                firstMonday = firstMonday.plusDays(7 * (startWithWeek - 1))

                for (localDate in datesFromPeriod) {
                    val weekNumber = ChronoUnit.WEEKS.between(firstMonday, localDate)
                    if (!firstMonday.isAfter(localDate) && weekNumber % everyWeek == 0L) {
                        availableDates.add(localDate)
                    }
                }
                return availableDates.filter { canAddByWeekDay(it, preferenceDependency) }.toSet()
            }
        }

        return emptySet()
    }

}
