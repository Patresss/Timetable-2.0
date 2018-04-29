package com.patres.timetable.repository.preference

import com.patres.timetable.domain.preference.PreferenceTeacherByDivision
import com.patres.timetable.domain.preference.PreferenceTeacherByPlace
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PreferenceTeacherByDivisionRepository : JpaRepository<PreferenceTeacherByDivision, Long> {

    fun findByTeacherId(teacherId: Long): Set<PreferenceTeacherByDivision>
    fun findByDivisionId(divisionId: Long): Set<PreferenceTeacherByDivision>
}
