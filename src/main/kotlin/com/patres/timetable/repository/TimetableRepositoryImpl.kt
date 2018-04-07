package com.patres.timetable.repository

import com.patres.timetable.domain.Timetable
import com.patres.timetable.preference.PreferenceDependency
import java.time.LocalDate
import java.util.*
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext


open class TimetableRepositoryImpl : TimetableCustomRepository {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    companion object {
        val DATE_PARAMETR_NAME = "<date>"
        val TAKEN_TIMETABLE_QUERY = """
                    SELECT DISTINCT
                      timetable
                    FROM
                      Timetable timetable
                      LEFT JOIN FETCH timetable.period period
                      LEFT JOIN FETCH period.intervalTimes interval
                      LEFT JOIN FETCH timetable.lesson lesson
                    WHERE
                      timetable.divisionOwner.id = :divisionOwnerId AND
                       ((lesson IS NOT NULL AND lesson.startTime >= :startTime AND lesson.endTime <= :endTime) OR
                       (lesson IS NULL AND timetable.startTime >= :startTime AND timetable.endTime <= :endTime)) AND
                       timetable.inMonday = :inMonday AND
                       timetable.inTuesday = :inTuesday AND
                       timetable.inWednesday = :inWednesday AND
                       timetable.inThursday = :inThursday AND
                       timetable.inFriday = :inFriday AND
                       timetable.inSaturday = :inSaturday AND
                       timetable.inSunday = :inSunday
                       """
        val TAKEN_TIMETABLE_DATE_CRITERIA_SQL = """
                       ( TIMETABLE.DATE = $DATE_PARAMETR_NAME OR
                       (interval.included = true AND
                        $DATE_PARAMETR_NAME BETWEEN INTERVAL.START_DATE AND INTERVAL.END_DATE) AND
                       INTERVAL.PERIOD_ID NOT IN (SELECT INTERVAL.PERIOD_ID FROM INTERVAL WHERE INTERVAL.INCLUDED = false AND $DATE_PARAMETR_NAME BETWEEN INTERVAL.START_DATE AND INTERVAL.END_DATE) )
            """
    }

    override fun findTakenTimetable(preferenceDependency: PreferenceDependency, dates: Set<LocalDate>): Set<Timetable> {
        return entityManager.createQuery(TAKEN_TIMETABLE_QUERY, Timetable::class.java)
            .setParameter("divisionOwnerId", preferenceDependency.divisionOwnerId)
            .setParameter("startTime", preferenceDependency.startTime)
            .setParameter("endTime", preferenceDependency.endTime)
            .setParameter("inMonday", preferenceDependency.inMonday)
            .setParameter("inTuesday", preferenceDependency.inTuesday)
            .setParameter("inWednesday", preferenceDependency.inWednesday)
            .setParameter("inThursday", preferenceDependency.inThursday)
            .setParameter("inFriday", preferenceDependency.inFriday)
            .setParameter("inSaturday", preferenceDependency.inSaturday)
            .setParameter("inSunday", preferenceDependency.inSunday)
            .resultList
            .toSet()
    }
}
