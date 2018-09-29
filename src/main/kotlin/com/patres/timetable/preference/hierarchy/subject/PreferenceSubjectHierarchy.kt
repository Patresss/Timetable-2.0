package com.patres.timetable.preference.hierarchy.subject

import com.patres.timetable.preference.container.PreferenceContainer
import com.patres.timetable.preference.hierarchy.PreferenceHierarchy


abstract class PreferenceSubjectHierarchy(
    val subjectId: Long,
    val preferenceContainer: PreferenceContainer) : PreferenceHierarchy() {

    var preferredByTeacher = 0
        get() = preferenceContainer.selectTeacher?.preferenceSubjectByTeacher?.find { it.subject?.id == subjectId }?.points ?: 0

    var preferredByPlace = 0
        get() = preferenceContainer.selectPlace?.preferenceSubjectByPlace?.find { it.subject?.id == subjectId }?.points ?: 0

    var preferredByDivision = 0
        get() = preferenceContainer.selectDivision?.preferencesSubjectByDivision?.find { it.subject?.id == subjectId }?.points ?: 0

    var preferredByDateTime = 0
        get() = calculatePreferredByDateTime()

    var taken = 0

    override var points = 0
        get() = preferencePoints + taken

    override var preferencePoints = 0
        get() = preferredByDateTime + preferredByTeacher + preferredByPlace + preferredByDivision

    abstract fun calculatePreferredByDateTime(): Int
}
