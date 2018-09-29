package com.patres.timetable.preference.hierarchy.lessonAndDay

import com.patres.timetable.preference.container.global.PreferenceContainerForGlobal
import java.time.DayOfWeek

class PreferenceLessonAndDayHierarchyForGlobal(
    lessonId: Long,
    dayOfWeek: DayOfWeek,
    preferenceContainerForGlobal: PreferenceContainerForGlobal) : PreferenceLessonAndDayOfWeekHierarchy(lessonId, dayOfWeek, preferenceContainerForGlobal) {

    private val schoolDataToPreference = preferenceContainerForGlobal.schoolDataToPreference

    override fun calculateTakenByPlace(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun calculateTakenByTeacher(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun calculateTakenByDivision(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
