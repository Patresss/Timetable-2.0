package com.patres.timetable.repository.preference

import com.patres.timetable.domain.preference.PreferenceSubjectByPlace
import com.patres.timetable.domain.preference.PreferenceSubjectByTeacher
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PreferenceSubjectByPlaceRepository : JpaRepository<PreferenceSubjectByPlace, Long> {

    fun findBySubjectId(subjectId: Long): Set<PreferenceSubjectByPlace>
    fun findByPlaceId(placeId: Long): Set<PreferenceSubjectByPlace>
}
