package com.patres.timetable.preference.hierarchy.teacher

import com.patres.timetable.preference.container.PreferenceContainerForGlobal
import com.patres.timetable.preference.hierarchy.PreferenceHierarchy

class PreferenceTeacherHierarchyForGlobal(
    teacherId: Long,
    preferenceContainerForGlobal: PreferenceContainerForGlobal) : PreferenceTeacherHierarchy(teacherId, preferenceContainerForGlobal) {

    private val schoolData = preferenceContainerForGlobal.schoolDataToPreference

    override fun calculatePreferredByDateTime(): Int = schoolData.preferenceDateTimeForTeacher.find { it.teacher?.id == teacherId }?.points ?: 0

    override fun calculateTaken(): Int =
        if (schoolData.takenTimetables.any { teacherId == preferenceContainer.selectTeacher?.id && it.lesson == preferenceContainer.selectLesson && it.dayOfWeek == preferenceContainer.selectDayOfWeek?.value })
            PreferenceHierarchy.TAKEN
        else
            0
}
