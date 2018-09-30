package com.patres.timetable.preference.hierarchy.subject

import com.patres.timetable.preference.container.PreferenceContainerForGlobal

class PreferenceSubjectHierarchyForGlobal(
    subjectId: Long,
    preferenceContainerForGlobal: PreferenceContainerForGlobal) : PreferenceSubjectHierarchy(subjectId, preferenceContainerForGlobal) {

    private val schoolDataToPreference = preferenceContainerForGlobal.schoolDataToPreference

    override fun calculatePreferredByDateTime(): Int = schoolDataToPreference.preferenceDateTimeForSubject.find { it.subject?.id == subjectId }?.points ?: 0
}
