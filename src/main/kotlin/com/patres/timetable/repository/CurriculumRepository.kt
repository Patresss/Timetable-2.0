package com.patres.timetable.repository

import com.patres.timetable.domain.Curriculum
import com.patres.timetable.domain.Interval
import com.patres.timetable.domain.Period
import com.patres.timetable.domain.Teacher
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import org.springframework.data.jpa.repository.*

@Repository
interface CurriculumRepository : JpaRepository<Curriculum, Long> {

    fun findByCurriculumListId(periodId: Long): Set<Curriculum>

}
