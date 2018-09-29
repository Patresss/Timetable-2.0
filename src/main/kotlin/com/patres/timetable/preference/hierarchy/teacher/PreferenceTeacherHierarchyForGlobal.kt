package com.patres.timetable.preference.hierarchy.teacher

import com.patres.timetable.preference.container.global.PreferenceContainerForGlobal

class PreferenceTeacherHierarchyForGlobal(
    teacherId: Long,
    preferenceContainerForGlobal: PreferenceContainerForGlobal) : PreferenceTeacherHierarchy(teacherId, preferenceContainerForGlobal) {

    private val schoolDataToPreference = preferenceContainerForGlobal.schoolDataToPreference

    override fun calculatePreferredByDateTime(): Int = schoolDataToPreference.preferenceDateTimeForTeacher.find { it.teacher?.id == teacherId }?.points ?: 0
    override fun calculateTaken(): Int = 0 // TODO
}
