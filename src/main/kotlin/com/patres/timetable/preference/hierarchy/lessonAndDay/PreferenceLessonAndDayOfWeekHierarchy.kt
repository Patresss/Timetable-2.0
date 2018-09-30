package com.patres.timetable.preference.hierarchy.lessonAndDay

import com.patres.timetable.preference.container.PreferenceContainer
import com.patres.timetable.preference.hierarchy.PreferenceHierarchy
import java.time.DayOfWeek

abstract class PreferenceLessonAndDayOfWeekHierarchy(
    val lessonId: Long,
    val dayOfWeek: DayOfWeek,
    val preferenceContainer: PreferenceContainer
) : PreferenceHierarchy() {

    var preferredBySubject = 0
        get() = preferenceContainer.selectSubject?.preferencesDateTimeForSubject?.find { it.dayOfWeek == dayOfWeek.value && it.lesson?.id == lessonId }?.points ?: 0

    var preferredByPlace = 0
        get() = preferenceContainer.selectPlace?.preferencesDateTimeForPlace?.find { it.dayOfWeek == dayOfWeek.value && it.lesson?.id == lessonId }?.points ?: 0

    var preferredByTeacher = 0
        get() = preferenceContainer.selectTeacher?.preferenceDateTimeForTeachers?.find { it.dayOfWeek == dayOfWeek.value && it.lesson?.id == lessonId }?.points ?: 0

    var preferredByDivision = 0
        get() = preferenceContainer.selectDivision?.preferencesDateTimeForDivision?.find { it.dayOfWeek == dayOfWeek.value && it.lesson?.id == lessonId }?.points ?: 0

    var takenByPlace = 0
        get() = calculateTakenByPlace()
    var takenByTeacher = 0
        get() = calculateTakenByTeacher()
    var takenByDivision = 0
        get() = calculateTakenByDivision()

    override var points = 0
        get() = preferencePoints + takenByPlace + takenByTeacher + takenByDivision

    override var preferencePoints = 0
        get() = preferredByDivision + preferredBySubject + preferredByPlace + preferredByTeacher


    abstract fun calculateTakenByPlace(): Int
    abstract fun calculateTakenByTeacher(): Int
    abstract fun calculateTakenByDivision(): Int

}
