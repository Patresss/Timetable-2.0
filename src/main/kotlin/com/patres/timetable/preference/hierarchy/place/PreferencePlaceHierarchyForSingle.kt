package com.patres.timetable.preference.hierarchy.place

import com.patres.timetable.preference.container.single.PreferenceContainerForSingle
import com.patres.timetable.preference.hierarchy.PreferenceHierarchy

class PreferencePlaceHierarchyForSingle(
    placeId: Long,
    private val preferenceContainerForSingle: PreferenceContainerForSingle) : PreferencePlaceHierarchy(placeId, preferenceContainerForSingle) {

    override fun calculatePreferredByDateTime(): Int = preferenceContainerForSingle.preferenceDateTimeForPlace.find { it.place?.id == placeId }?.points ?: 0
    override fun calculateTaken(): Int = if (preferenceContainerForSingle.takenTimetables.any { it.place?.id == placeId }) PreferenceHierarchy.TAKEN else 0
    override fun calculateTooSmallPlace(): Int = if (preferenceContainerForSingle.tooSmallPlaceId.contains(placeId)) PreferenceHierarchy.TOO_SMALL_PLACE else 0

}
