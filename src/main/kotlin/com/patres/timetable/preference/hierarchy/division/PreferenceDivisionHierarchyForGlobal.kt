package com.patres.timetable.preference.hierarchy.division

import com.patres.timetable.preference.container.PreferenceContainerForGlobal
import com.patres.timetable.preference.hierarchy.PreferenceHierarchy

class PreferenceDivisionHierarchyForGlobal(
    divisionId: Long,
    preferenceContainerForGlobal: PreferenceContainerForGlobal) : PreferenceDivisionHierarchy(divisionId, preferenceContainerForGlobal) {

    private val schoolData = preferenceContainerForGlobal.schoolDataToPreference

    override fun calculatePreferredByDateTime(): Int = schoolData.preferenceDateTimeForDivision.find { it.division?.id == divisionId }?.points ?: 0
    override fun calculateTaken(): Int =
        if (schoolData.takenTimetables.any { divisionId == preferenceContainer.selectDivision?.id && it.lesson == preferenceContainer.selectLesson && it.dayOfWeek == preferenceContainer.selectDayOfWeek?.value })
            PreferenceHierarchy.TAKEN
        else
            0
}
