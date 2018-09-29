package com.patres.timetable.preference.hierarchy.place

import com.patres.timetable.preference.container.global.PreferenceContainerForGlobal
import com.patres.timetable.preference.hierarchy.PreferenceHierarchy

class PreferencePlaceHierarchyForGlobal(
    placeId: Long,
    preferenceContainerForGlobal: PreferenceContainerForGlobal) : PreferencePlaceHierarchy(placeId, preferenceContainerForGlobal) {

    private val schoolDataToPreference = preferenceContainerForGlobal.schoolDataToPreference

    val place = schoolDataToPreference.places.find { it.id == placeId }

    override fun calculatePreferredByDateTime(): Int = schoolDataToPreference.preferenceDateTimeForPlace.find { it.place?.id == placeId }?.points ?: 0
    override fun calculateTaken(): Int = 0 // TODO
    override fun calculateTooSmallPlace(): Int {
        return if (place?.numberOfSeats ?: Long.MAX_VALUE < preferenceContainer.selectDivision?.numberOfPeople ?: -1) {
            PreferenceHierarchy.TOO_SMALL_PLACE
        } else {
            0
        }
    }
}
