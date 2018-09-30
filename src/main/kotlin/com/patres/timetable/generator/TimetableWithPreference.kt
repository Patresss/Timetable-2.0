package com.patres.timetable.generator

import com.patres.timetable.domain.Timetable
import com.patres.timetable.preference.container.PreferenceContainerForGlobal

class TimetableWithPreference(
    val timetable: Timetable,
    val preferenceContainer: PreferenceContainerForGlobal
)
