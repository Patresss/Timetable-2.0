package com.patres.timetable.repository

import com.patres.timetable.domain.Interval
import com.patres.timetable.domain.Period
import com.patres.timetable.domain.Teacher
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import org.springframework.data.jpa.repository.*

@Repository
interface IntervalRepository : JpaRepository<Interval, Long> {

    fun findByPeriodId(periodId: Long): Set<Interval>

    fun findFirstByPeriodIdAndIncludedStateTrueOrderByStartDate(periodId: Long): Interval

}
