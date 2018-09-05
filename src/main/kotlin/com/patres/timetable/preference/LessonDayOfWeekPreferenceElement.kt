package com.patres.timetable.preference

import com.patres.timetable.preference.hierarchy.PreferenceLessonAndDayOfWeekHierarchy

class LessonDayOfWeekPreferenceElement(
    val dayOfWeek: Int,
    val lessonId: Long,
    val preference: PreferenceLessonAndDayOfWeekHierarchy = PreferenceLessonAndDayOfWeekHierarchy()
) : Comparable<LessonDayOfWeekPreferenceElement> {

    override fun compareTo(other: LessonDayOfWeekPreferenceElement): Int {
        return preference.points - other.preference.points
    }

}
