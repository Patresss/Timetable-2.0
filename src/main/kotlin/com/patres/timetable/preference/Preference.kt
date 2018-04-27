package com.patres.timetable.preference

import com.patres.timetable.preference.hierarchy.*
import java.time.DayOfWeek

class Preference(
    teachersId: Set<Long> = emptySet(),
    subjectsId: Set<Long> = emptySet(),
    placesId: Set<Long> = emptySet(),
    divisionsId: Set<Long> = emptySet(),
    lessonsId: Set<Long> = emptySet()
) {

    val preferredTeacherMap = teachersId.map { it to PreferenceTeacherHierarchy() }.toMap().toSortedMap()
    val preferredSubjectMap = subjectsId.map { it to PreferenceSubjectHierarchy() }.toMap().toSortedMap()
    val preferredPlaceMap = placesId.map { it to PreferencePlaceHierarchy() }.toMap().toSortedMap()
    val preferredDivisionMap = divisionsId.map { it to PreferenceDivisionHierarchy() }.toMap().toSortedMap()
    val preferredLessonAndDayOfWeekSet = HashSet<LessonDayOfWeekPreferenceElement>()

    init {
        DayOfWeek.values().forEach { dayOfWeek ->
            lessonsId.forEach { lessonId ->
                preferredLessonAndDayOfWeekSet.add(LessonDayOfWeekPreferenceElement(dayOfWeek = dayOfWeek.value, lessonId = lessonId, preference = PreferenceLessonAndDayOfWeekHierarchy()))
            }
        }
    }

    fun getPreferenceByLessonAndDay(dayOfWeek: Int, lessonId: Long): LessonDayOfWeekPreferenceElement? {
        return preferredLessonAndDayOfWeekSet.find { it.dayOfWeek == dayOfWeek && it.lessonId == lessonId }
    }



}
