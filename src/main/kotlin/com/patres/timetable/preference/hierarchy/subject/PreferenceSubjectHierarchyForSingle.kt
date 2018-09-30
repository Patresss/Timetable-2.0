package com.patres.timetable.preference.hierarchy.subject

import com.patres.timetable.preference.container.PreferenceContainerForSingle

class PreferenceSubjectHierarchyForSingle(
    subjectId: Long,
    private val preferenceContainerForSingle: PreferenceContainerForSingle) : PreferenceSubjectHierarchy(subjectId, preferenceContainerForSingle) {

    override fun calculatePreferredByDateTime(): Int = preferenceContainerForSingle.preferenceDateTimeForSubject.find { it.subject?.id == subjectId }?.points ?: 0

}
