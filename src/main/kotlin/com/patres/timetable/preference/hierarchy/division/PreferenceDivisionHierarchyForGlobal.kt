package com.patres.timetable.preference.hierarchy.division

import com.patres.timetable.preference.container.global.PreferenceContainerForGlobal

class PreferenceDivisionHierarchyForGlobal(
    divisionId: Long,
    preferenceContainerForGlobal: PreferenceContainerForGlobal) : PreferenceDivisionHierarchy(divisionId, preferenceContainerForGlobal) {

    private val schoolDataToPreference = preferenceContainerForGlobal.schoolDataToPreference

    override fun calculatePreferredByDateTime(): Int = schoolDataToPreference.preferenceDateTimeForDivision.find { it.division?.id == divisionId }?.points ?: 0
    override fun calculateTaken(): Int = 0 // TODO
}
