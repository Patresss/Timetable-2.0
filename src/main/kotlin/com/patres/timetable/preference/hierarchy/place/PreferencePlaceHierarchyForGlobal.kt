package com.patres.timetable.preference.hierarchy.place

import com.patres.timetable.preference.container.PreferenceContainerForGlobal
import com.patres.timetable.preference.hierarchy.PreferenceHierarchy

class PreferencePlaceHierarchyForGlobal(
    placeId: Long,
    preferenceContainerForGlobal: PreferenceContainerForGlobal) : PreferencePlaceHierarchy(placeId, preferenceContainerForGlobal) {

    private val schoolData = preferenceContainerForGlobal.schoolDataToPreference

    val place = schoolData.places.find { it.id == placeId }

    override fun calculatePreferredByDateTime(): Int = schoolData.preferenceDateTimeForPlace.find { it.place?.id == placeId }?.points ?: 0

    override fun calculateTaken(): Int =
        if (schoolData.takenTimetables.any { placeId == preferenceContainer.selectPlace?.id && it.lesson == preferenceContainer.selectLesson && it.dayOfWeek == preferenceContainer.selectDayOfWeek?.value })
            PreferenceHierarchy.TAKEN
        else
            0

    override fun calculateTooSmallPlace(): Int {
        return if (place?.numberOfSeats ?: Long.MAX_VALUE < preferenceContainer.selectDivision?.numberOfPeople ?: -1) {
            PreferenceHierarchy.TOO_SMALL_PLACE
        } else {
            0
        }
    }

    var calculatedPoints = 0

    fun calculateCalculatedPoints() {
        calculatedPoints = points
    }
}
