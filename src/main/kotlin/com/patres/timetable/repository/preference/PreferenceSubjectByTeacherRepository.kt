package com.patres.timetable.repository.preference

import com.patres.timetable.domain.preference.PreferenceSubjectByTeacher
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PreferenceSubjectByTeacherRepository : JpaRepository<PreferenceSubjectByTeacher, Long> {

    fun findByTeacherId(teacherId: Long): Set<PreferenceSubjectByTeacher>
    fun findBySubjectId(subjectId: Long): Set<PreferenceSubjectByTeacher>
}
