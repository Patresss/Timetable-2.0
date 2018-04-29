package com.patres.timetable.repository.preference

import com.patres.timetable.domain.preference.PreferenceDataTimeForPlace
import com.patres.timetable.domain.preference.PreferenceDataTimeForTeacher
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PreferenceDataTimeForPlaceRepository : JpaRepository<PreferenceDataTimeForPlace, Long> {

    fun findByDayOfWeekAndLessonId(dayOfWeek: Int, lessonId: Long): Set<PreferenceDataTimeForPlace>

    fun findByPlaceIdIn(placeId: List<Long>): Set<PreferenceDataTimeForPlace>

    fun findByPlaceId(placeId: Long): Set<PreferenceDataTimeForPlace>
}


