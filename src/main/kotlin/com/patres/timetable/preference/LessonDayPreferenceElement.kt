package com.patres.timetable.preference

import com.patres.timetable.preference.hierarchy.PreferenceLessonAndDayOfWeekHierarchy

class LessonDayPreferenceElement(
    val dayOfWeek: Int,
    val lessonId: Long,
    val preference: PreferenceLessonAndDayOfWeekHierarchy
) : Comparable<LessonDayPreferenceElement> {

    override fun compareTo(other: LessonDayPreferenceElement): Int {
        return preference.points - other.preference.points
    }

}
