package com.patres.timetable.repository.preference

import com.patres.timetable.domain.preference.PreferenceDataTimeForDivision
import com.patres.timetable.domain.preference.PreferenceDataTimeForTeacher
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PreferenceDataTimeForDivisionRepository : JpaRepository<PreferenceDataTimeForDivision, Long> {

    fun findByDayOfWeekAndLessonId(dayOfWeek: Int, lessonId: Long): Set<PreferenceDataTimeForDivision>

    fun findByDivisionId(divisionId: Long): Set<PreferenceDataTimeForDivision>
}


