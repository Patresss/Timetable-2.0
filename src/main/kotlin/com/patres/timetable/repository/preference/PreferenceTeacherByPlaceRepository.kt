package com.patres.timetable.repository.preference

import com.patres.timetable.domain.preference.PreferenceTeacherByPlace
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PreferenceTeacherByPlaceRepository : JpaRepository<PreferenceTeacherByPlace, Long> {

    fun findByTeacherId(teacherId: Long): Set<PreferenceTeacherByPlace>
    fun findByPlaceId(placeId: Long): Set<PreferenceTeacherByPlace>
}
