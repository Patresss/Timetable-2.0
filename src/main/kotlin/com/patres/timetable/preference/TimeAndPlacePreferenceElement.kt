package com.patres.timetable.preference

import com.patres.timetable.preference.hierarchy.PreferenceTimeAndPlaceHierarchy

class TimeAndPlacePreferenceElement(
    val dayOfWeek: Int,
    val lessonId: Long,
    val placeId: Long,
    val preference: PreferenceTimeAndPlaceHierarchy = PreferenceTimeAndPlaceHierarchy()
) : Comparable<TimeAndPlacePreferenceElement> {

    override fun compareTo(other: TimeAndPlacePreferenceElement): Int {
        return preference.points - other.preference.points
    }

}
