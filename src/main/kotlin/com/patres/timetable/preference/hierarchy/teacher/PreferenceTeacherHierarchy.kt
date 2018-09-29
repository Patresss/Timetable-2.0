package com.patres.timetable.preference.hierarchy.teacher

import com.patres.timetable.preference.container.PreferenceContainer
import com.patres.timetable.preference.hierarchy.PreferenceHierarchy


abstract class PreferenceTeacherHierarchy(
    val teacherId: Long,
    private val preferenceContainer: PreferenceContainer) : PreferenceHierarchy() {

    var preferredBySubject: Int = 0
        get() = preferenceContainer.selectSubject?.preferenceSubjectByTeacher?.find { it.teacher?.id == teacherId }?.points ?: 0

    var preferredByPlace: Int = 0
        get() = preferenceContainer.selectPlace?.preferenceTeacherByPlace?.find { it.teacher?.id == teacherId }?.points ?: 0

    var preferredByDivision: Int = 0
        get() = preferenceContainer.selectDivision?.preferencesTeacherByDivision?.find { it.teacher?.id == teacherId }?.points ?: 0

    var preferredByDateTime = 0
        get() = calculatePreferredByDateTime()

    var taken = 0
        get() = calculateTaken()

    override var points = 0
        get() = preferencePoints + taken

    override var preferencePoints = 0
        get() = preferredByDateTime + preferredBySubject + preferredByPlace + preferredByDivision

    abstract fun calculatePreferredByDateTime(): Int
    abstract fun calculateTaken(): Int

}
