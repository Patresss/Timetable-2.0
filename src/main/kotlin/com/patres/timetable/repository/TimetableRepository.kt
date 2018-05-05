package com.patres.timetable.repository

import com.patres.timetable.domain.Timetable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate


@Repository
interface TimetableRepository : DivisionOwnerRepository<Timetable>, TimetableCustomRepository {

    @Query("select distinct timetable from Timetable timetable left join fetch timetable.period  period left join fetch period.intervalTimes interval left join fetch timetable.division division WHERE division.id IN (:divisionId) AND (timetable.date = :date OR (interval.includedState is true AND :date BETWEEN interval.startDate AND interval.endDate)  AND interval.period NOT IN (SELECT interval.period from interval WHERE interval.includedState is false AND :date BETWEEN interval.startDate AND interval.endDate))")
    fun findByDivisionListAndDateFromPeriod(@Param("date") date: LocalDate, @Param("divisionId") divisionId: List<Long>): Set<Timetable>

    @Query("select distinct timetable from Timetable timetable left join fetch timetable.period  period left join fetch period.intervalTimes interval left join fetch timetable.place place WHERE place.id = :placeId AND (timetable.date = :date OR (interval.includedState is true AND :date BETWEEN interval.startDate AND interval.endDate)  AND interval.period NOT IN (SELECT interval.period from interval WHERE interval.includedState is false AND :date BETWEEN interval.startDate AND interval.endDate))")
    fun findByPlaceIdAndDateFromPeriod(@Param("date") date: LocalDate, @Param("placeId") placeId: Long?): Set<Timetable>

    @Query("select distinct timetable from Timetable timetable left join fetch timetable.period  period left join fetch period.intervalTimes interval left join fetch timetable.teacher teacher WHERE teacher.id = :teacherId AND (timetable.date = :date OR (interval.includedState is true AND :date BETWEEN interval.startDate AND interval.endDate)  AND interval.period NOT IN (SELECT interval.period from interval WHERE interval.includedState is false AND :date BETWEEN interval.startDate AND interval.endDate))")
    fun findByTeacherIdAndDateFromPeriod(@Param("date") date: LocalDate, @Param("teacherId") teacherId: Long?): Set<Timetable>

    @Query("""
        select distinct
            timetable
        from
            Timetable timetable
            left join fetch timetable.period period
            left join fetch period.intervalTimes interval
            left join fetch timetable.lesson lesson
        WHERE
            timetable.divisionOwner.id = :divisionOwnerId AND
            period.id = :periodId AND (
                timetable.teacher.id = :teacherId OR
                timetable.division.id IN (:divisionsId) OR
                timetable.place.id = :placeId OR
                timetable.subject.id = :subjectId)
                """)
    fun findTakenByPeriod(
        @Param("divisionOwnerId") divisionOwnerId: Long?,
        @Param("periodId") periodId: Long?,
        @Param("teacherId") teacherId: Long?,
        @Param("divisionsId") divisionId: Set<Long>,
        @Param("placeId") placeId: Long?,
        @Param("subjectId") subjectId: Long?
    ): Set<Timetable>

}
