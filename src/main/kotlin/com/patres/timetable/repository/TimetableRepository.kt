package com.patres.timetable.repository

import com.patres.timetable.domain.Timetable
import org.springframework.stereotype.Repository

import org.springframework.data.jpa.repository.*
import org.springframework.data.repository.query.Param
import java.time.LocalDate


@Repository
interface TimetableRepository : JpaRepository<Timetable, Long> {

    @Query("select distinct timetable from Timetable timetable left join fetch timetable.period  period left join fetch period.intervalTimes interval left join fetch timetable.division division WHERE division.id IN (:divisionId) AND (timetable.date = :date OR (interval.included is true AND :date BETWEEN interval.startDate AND interval.endDate)  AND interval.period NOT IN (SELECT interval.period from interval WHERE interval.included is false AND :date BETWEEN interval.startDate AND interval.endDate))")
    fun findByDivisionListAndDateFromPeriod(@Param("date") date: LocalDate, @Param("divisionId") divisionId: List<Long>): Set<Timetable>

//    @Query("select distinct timetable from Timetable timetable left join fetch timetable.period  period left join fetch period.intervalTimes interval left join fetch timetable.place place WHERE place.id = :placeId AND (timetable.date = :date OR (interval.included is true AND :date BETWEEN interval.startDate AND interval.endDate)  AND interval.period NOT IN (SELECT interval.period from interval WHERE interval.included is false AND :date BETWEEN interval.startDate AND interval.endDate))")
//    fun findByPlaceIDAndDateFromPeriod(@Param("date") date: LocalDate, @Param("placeId") placeId: Long?): Set<Timetable>
//
//    @Query("select distinct timetable from Timetable timetable left join fetch timetable.period  period left join fetch period.intervalTimes interval left join fetch timetable.teacher teacher WHERE teacher.id = :teacherId AND (timetable.date = :date OR (interval.included is true AND :date BETWEEN interval.startDate AND interval.endDate)  AND interval.period NOT IN (SELECT interval.period from interval WHERE interval.included is false AND :date BETWEEN interval.startDate AND interval.endDate))")
//    fun findByTeacherIDAndDateFromPeriod(@Param("date") date: LocalDate, @Param("teacherId") teacherId: Long?): Set<Timetable>

}
