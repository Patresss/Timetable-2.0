package com.patres.timetable.repository.preference

import com.patres.timetable.domain.preference.PreferenceDataTimeForTeacher
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PreferenceDataTimeForTeacherRepository : JpaRepository<PreferenceDataTimeForTeacher, Long> {

    fun findByDayOfWeekAndLessonId(dayOfWeek: Int, lessonId: Long): Set<PreferenceDataTimeForTeacher>
}


