package com.patres.timetable.repository.preference

import com.patres.timetable.domain.preference.PreferenceDivisionByPlace
import com.patres.timetable.domain.preference.PreferenceSubjectByPlace
import com.patres.timetable.domain.preference.PreferenceSubjectByTeacher
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PreferenceDivisionByPlaceRepository : JpaRepository<PreferenceDivisionByPlace, Long> {

    fun findByDivisionId(divisionId: Long): Set<PreferenceDivisionByPlace>
    fun findByPlaceId(placeId: Long): Set<PreferenceDivisionByPlace>
}
