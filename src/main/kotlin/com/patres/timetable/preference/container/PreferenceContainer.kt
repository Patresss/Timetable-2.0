package com.patres.timetable.preference.container

import com.patres.timetable.domain.*
import com.patres.timetable.preference.hierarchy.division.PreferenceDivisionHierarchy
import com.patres.timetable.preference.hierarchy.lessonAndDay.PreferenceLessonAndDayOfWeekHierarchy
import com.patres.timetable.preference.hierarchy.place.PreferencePlaceHierarchy
import com.patres.timetable.preference.hierarchy.subject.PreferenceSubjectHierarchy
import com.patres.timetable.preference.hierarchy.teacher.PreferenceTeacherHierarchy
import java.time.DayOfWeek

open class PreferenceContainer {

    open val preferredTeacherMap = emptyMap<Long, PreferenceTeacherHierarchy>()
    open val preferredSubjectMap = emptyMap<Long, PreferenceSubjectHierarchy>()
    open val preferredPlaceMap = emptyMap<Long, PreferencePlaceHierarchy>()
    open val preferredDivisionMap = emptyMap<Long, PreferenceDivisionHierarchy>()
    open val preferredLessonAndDayOfWeekSet = emptySet<PreferenceLessonAndDayOfWeekHierarchy>()

    var selectPlace: Place? = null
    var selectDivision: Division? = null
    var selectTeacher: Teacher? = null
    var selectSubject: Subject? = null
    var selectLesson: Lesson? = null
    var selectDayOfWeek: DayOfWeek? = null

    fun getPreferenceByLessonAndDay(dayOfWeek: Int?, lessonId: Long?): PreferenceLessonAndDayOfWeekHierarchy? {
        return preferredLessonAndDayOfWeekSet.find { it.dayOfWeek.value == dayOfWeek && it.lessonId == lessonId }
    }

}
