package com.patres.timetable.domain

import com.patres.timetable.domain.preference.PreferenceForDataTime
import com.patres.timetable.preference.LessonDayOfWeekPreferenceElement

interface PreferableDateTime {

    fun getPreferenceDateTime(lessonDayPreferenceElement: LessonDayOfWeekPreferenceElement) : PreferenceForDataTime?
}
