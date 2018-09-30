package com.patres.timetable.preference.hierarchy.lessonAndDay

import com.patres.timetable.domain.Timetable
import com.patres.timetable.preference.container.PreferenceContainerForGlobal
import com.patres.timetable.preference.hierarchy.PreferenceHierarchy
import java.time.DayOfWeek

class PreferenceLessonAndDayHierarchyForGlobal(
    lessonId: Long,
    dayOfWeek: DayOfWeek,
    preferenceContainerForGlobal: PreferenceContainerForGlobal) : PreferenceLessonAndDayOfWeekHierarchy(lessonId, dayOfWeek, preferenceContainerForGlobal) {

    private val schoolData = preferenceContainerForGlobal.schoolDataToPreference

    var calculatedPoints = 0

    override fun calculateTakenByPlace(): Int {
        return if (takenTimetables().any { it.place?.id == preferenceContainer.selectPlace?.id }) PreferenceHierarchy.TAKEN else 0
    }

    override fun calculateTakenByTeacher(): Int {
        return if (takenTimetables().any { it.teacher?.id == preferenceContainer.selectTeacher?.id }) PreferenceHierarchy.TAKEN else 0
    }

    override fun calculateTakenByDivision(): Int {
        return if (takenTimetables().any { it.division?.id == preferenceContainer.selectDivision?.id }) PreferenceHierarchy.TAKEN else 0
    }

    fun takenTimetables():Set<Timetable> = schoolData.takenTimetables.filter { it.lesson?.id == lessonId && it.dayOfWeek == dayOfWeek.value }.toSet()

    fun calculateCalculatedPoints() {
        calculatedPoints = points
    }

}
