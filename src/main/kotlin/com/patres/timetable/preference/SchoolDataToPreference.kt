package com.patres.timetable.preference

import com.patres.timetable.domain.*
import com.patres.timetable.domain.preference.PreferenceDataTimeForDivision
import com.patres.timetable.domain.preference.PreferenceDataTimeForPlace
import com.patres.timetable.domain.preference.PreferenceDataTimeForSubject
import com.patres.timetable.domain.preference.PreferenceDataTimeForTeacher
import com.patres.timetable.preference.hierarchy.*
import java.time.DayOfWeek

class SchoolDataToPreference(
    val divisionOwnerId: Long,

    val teachersId: Set<Long> = emptySet(),
    val subjectsId: Set<Long> = emptySet(),
    val placesId: Set<Long> = emptySet(),
    val divisionsId: Set<Long> = emptySet(),
    val lessonsId: Set<Long> = emptySet(),
    var places: Set<Place> = emptySet(),

    var takenTimetables: Set<Timetable> = emptySet(),

    var preferenceDateTimeForTeacher: Set<PreferenceDataTimeForTeacher> = emptySet(),
    var preferenceDateTimeForSubject: Set<PreferenceDataTimeForSubject> = emptySet(),
    var preferenceDateTimeForDivision: Set<PreferenceDataTimeForDivision> = emptySet(),
    var preferenceDateTimeForPlace: Set<PreferenceDataTimeForPlace> = emptySet()
)
