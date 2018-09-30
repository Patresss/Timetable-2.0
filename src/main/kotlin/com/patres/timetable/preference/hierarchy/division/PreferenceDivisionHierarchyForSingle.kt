package com.patres.timetable.preference.hierarchy.division

import com.patres.timetable.preference.container.PreferenceContainerForSingle
import com.patres.timetable.preference.hierarchy.PreferenceHierarchy

class PreferenceDivisionHierarchyForSingle(
    divisionId: Long,
    private val preferenceContainerForSingle: PreferenceContainerForSingle) : PreferenceDivisionHierarchy(divisionId, preferenceContainerForSingle) {

   override fun calculatePreferredByDateTime(): Int = preferenceContainerForSingle.preferenceDateTimeForDivision.find { it.division?.id == divisionId }?.points ?: 0
   override fun calculateTaken(): Int = if (preferenceContainerForSingle.takenTimetables.any { it.division?.id == divisionId }) PreferenceHierarchy.TAKEN else 0

}
