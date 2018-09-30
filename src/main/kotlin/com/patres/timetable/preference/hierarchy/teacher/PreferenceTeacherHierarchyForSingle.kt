package com.patres.timetable.preference.hierarchy.teacher

import com.patres.timetable.preference.container.PreferenceContainerForSingle
import com.patres.timetable.preference.hierarchy.PreferenceHierarchy

class PreferenceTeacherHierarchyForSingle(
    teacherId: Long,
    private val preferenceContainerForSingle: PreferenceContainerForSingle) : PreferenceTeacherHierarchy(teacherId, preferenceContainerForSingle) {

   override fun calculatePreferredByDateTime(): Int = preferenceContainerForSingle.preferenceDateTimeForTeacher.find { it.teacher?.id == teacherId }?.points ?: 0
   override fun calculateTaken(): Int = if (preferenceContainerForSingle.takenTimetables.any { it.teacher?.id == teacherId }) PreferenceHierarchy.TAKEN else 0

}
