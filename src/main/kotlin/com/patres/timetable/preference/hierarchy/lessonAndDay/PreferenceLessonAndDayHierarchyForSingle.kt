package com.patres.timetable.preference.hierarchy.lessonAndDay

import com.patres.timetable.preference.container.PreferenceContainerForSingle
import com.patres.timetable.preference.hierarchy.PreferenceHierarchy
import java.time.DayOfWeek

class PreferenceLessonAndDayHierarchyForSingle(
    lessonId: Long,
    dayOfWeek: DayOfWeek,
    private val preferenceContainerForSingle: PreferenceContainerForSingle) : PreferenceLessonAndDayOfWeekHierarchy(lessonId, dayOfWeek, preferenceContainerForSingle) {

    override fun calculateTakenByPlace(): Int = if (preferenceContainerForSingle.takenTimetables.any { it.place?.id == preferenceContainerForSingle.selectPlace?.id }) PreferenceHierarchy.TAKEN else 0
    override fun calculateTakenByTeacher(): Int = if (preferenceContainerForSingle.takenTimetables.any { it.teacher?.id == preferenceContainerForSingle.selectTeacher?.id }) PreferenceHierarchy.TAKEN else 0
    override fun calculateTakenByDivision(): Int = if (preferenceContainerForSingle.takenTimetables.any { it.division?.id == preferenceContainerForSingle.selectDivision?.id }) PreferenceHierarchy.TAKEN else 0

}
