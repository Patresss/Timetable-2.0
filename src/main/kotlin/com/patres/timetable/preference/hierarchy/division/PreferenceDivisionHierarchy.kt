package com.patres.timetable.preference.hierarchy.division

import com.patres.timetable.preference.container.PreferenceContainer
import com.patres.timetable.preference.hierarchy.PreferenceHierarchy

abstract class PreferenceDivisionHierarchy(
    val divisionId: Long,
    val preferenceContainer: PreferenceContainer) : PreferenceHierarchy() {

    var preferredBySubject = 0
        get() = preferenceContainer.selectSubject?.preferencesSubjectByDivision?.find { it.division?.id == divisionId }?.points ?: 0

    var preferredByPlace = 0
        get() = preferenceContainer.selectPlace?.preferenceDivisionByPlace?.find { it.division?.id == divisionId }?.points ?: 0

    var preferredByTeacher = 0
        get() = preferenceContainer.selectTeacher?.preferencesTeacherByDivision?.find { it.division?.id == divisionId }?.points ?: 0

    var preferredByDateTime = 0
        get() = calculatePreferredByDateTime()

    var taken = 0
        get() = calculateTaken()

    override var points = 0
        get() = preferencePoints + taken

    override var preferencePoints = 0
        get() = preferredByDateTime + preferredBySubject + preferredByPlace + preferredByTeacher

    abstract fun calculatePreferredByDateTime(): Int
    abstract fun calculateTaken(): Int
}
