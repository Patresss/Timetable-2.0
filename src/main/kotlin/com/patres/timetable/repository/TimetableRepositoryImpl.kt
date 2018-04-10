package com.patres.timetable.repository

import com.patres.timetable.domain.Timetable
import com.patres.timetable.preference.PreferenceDependency
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext


open class TimetableRepositoryImpl : TimetableCustomRepository {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    companion object {
        const val DATE_PARAMETER_NAME = "<date>"
        const val TAKEN_TIMETABLE_QUERY = """
                    SELECT DISTINCT
                      timetable
                    FROM
                      Timetable timetable
                      LEFT JOIN FETCH timetable.period period
                      LEFT JOIN FETCH period.intervalTimes interval
                      LEFT JOIN FETCH timetable.lesson lesson
                      LEFT JOIN FETCH timetable.division division
                      LEFT JOIN FETCH timetable.subject subject
                      LEFT JOIN FETCH timetable.place place
                      LEFT JOIN FETCH timetable.teacher teacher
                    WHERE
                      timetable.divisionOwner.id = :divisionOwnerId AND
                       ((lesson IS NOT NULL AND lesson.startTime >= :startTime AND lesson.endTime <= :endTime) OR
                       (lesson IS NULL AND timetable.startTime >= :startTime AND timetable.endTime <= :endTime))
                       """
        const val TAKEN_TIMETABLE_DATE_CRITERIA_SQL = """
                       ( timetable.date = '$DATE_PARAMETER_NAME' OR (
                        timetable.dayOfWeek = :dayOfWeek AND
                       interval.included = true AND
                        '$DATE_PARAMETER_NAME' BETWEEN interval.startDate AND interval.endDate) AND
                       interval.period NOT IN (SELECT intervalGlobal.period FROM Interval intervalGlobal WHERE intervalGlobal.included = false AND '$DATE_PARAMETER_NAME' BETWEEN intervalGlobal.startDate AND intervalGlobal.endDate) )
            """
    }

    override fun findTakenTimetable(preferenceDependency: PreferenceDependency, dates: Set<LocalDate>): Set<Timetable> {
        return if (dates.isEmpty()) {
            findTakenTimetableWithoutDate(preferenceDependency)
        } else {
            findTakenTimetableWithDates(preferenceDependency, dates)
        }
    }

    private fun findTakenTimetableWithoutDate(preferenceDependency: PreferenceDependency) : Set<Timetable> {
        return entityManager.createQuery(TAKEN_TIMETABLE_QUERY, Timetable::class.java)
            .setParameter("divisionOwnerId", preferenceDependency.divisionOwnerId)
            .setParameter("startTime", preferenceDependency.startTime)
            .setParameter("endTime", preferenceDependency.endTime)
            .resultList
            .toSet()
    }

    private fun findTakenTimetableWithDates(preferenceDependency: PreferenceDependency, dates: Set<LocalDate>) : Set<Timetable> {
        return entityManager.createQuery(createTakenQueryWithDates(dates), Timetable::class.java)
            .setParameter("divisionOwnerId", preferenceDependency.divisionOwnerId)
            .setParameter("startTime", preferenceDependency.startTime)
            .setParameter("endTime", preferenceDependency.endTime)
            .setParameter("dayOfWeek", preferenceDependency.dayOfWeek)
            .resultList
            .toSet()
    }

    private fun createTakenQueryWithDates(dates: Set<LocalDate>): String {
        return dates.joinToString(separator = " OR ", prefix = "$TAKEN_TIMETABLE_QUERY AND (", postfix = ")") { date -> TAKEN_TIMETABLE_DATE_CRITERIA_SQL.replace(DATE_PARAMETER_NAME, date.format(DateTimeFormatter.ISO_LOCAL_DATE)) }
    }
}
