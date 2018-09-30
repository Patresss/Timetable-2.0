package com.patres.timetable.preference.container

import com.patres.timetable.preference.PreferencePairPlaceLessonAndDay
import com.patres.timetable.preference.SchoolDataToPreference
import com.patres.timetable.preference.hierarchy.division.PreferenceDivisionHierarchyForGlobal
import com.patres.timetable.preference.hierarchy.lessonAndDay.PreferenceLessonAndDayHierarchyForGlobal
import com.patres.timetable.preference.hierarchy.place.PreferencePlaceHierarchyForGlobal
import com.patres.timetable.preference.hierarchy.subject.PreferenceSubjectHierarchyForGlobal
import com.patres.timetable.preference.hierarchy.teacher.PreferenceTeacherHierarchyForGlobal
import java.time.DayOfWeek
import java.util.ArrayList

class PreferenceContainerForGlobal(
    val schoolDataToPreference: SchoolDataToPreference
) : PreferenceContainer() {

    override val preferredTeacherMap = schoolDataToPreference.teachersId.map { it to PreferenceTeacherHierarchyForGlobal(teacherId = it, preferenceContainerForGlobal = this) }.toMap()
    override val preferredSubjectMap = schoolDataToPreference.subjectsId.map { it to PreferenceSubjectHierarchyForGlobal(subjectId = it, preferenceContainerForGlobal = this) }.toMap()
    override val preferredPlaceMap = schoolDataToPreference.placesId.map { it to PreferencePlaceHierarchyForGlobal(placeId = it, preferenceContainerForGlobal = this) }.toMap()
    override val preferredDivisionMap = schoolDataToPreference.divisionsId.map { it to PreferenceDivisionHierarchyForGlobal(divisionId = it, preferenceContainerForGlobal = this) }.toMap()
    override val preferredLessonAndDayOfWeekSet = HashSet<PreferenceLessonAndDayHierarchyForGlobal>()

    var preferredPairPlaceLessonAndDay: ArrayList<PreferencePairPlaceLessonAndDay> = ArrayList()

    init {
        DayOfWeek.values().forEach { dayOfWeek ->
            schoolDataToPreference.lessonsId.forEach { lessonId ->
                preferredLessonAndDayOfWeekSet.add(PreferenceLessonAndDayHierarchyForGlobal(dayOfWeek = dayOfWeek, lessonId = lessonId, preferenceContainerForGlobal = this))
            }
        }

        preferredPlaceMap.forEach { _, preferredPlace ->
            preferredLessonAndDayOfWeekSet.forEach { preferredLessonAndDay ->
                preferredPairPlaceLessonAndDay.add(PreferencePairPlaceLessonAndDay(preferredPlace, preferredLessonAndDay))
            }
        }
    }


    fun sortPreferredPairPlaceLessonAndDay() {
        preferredLessonAndDayOfWeekSet.forEach { it.calculateCalculatedPoints() }
        preferredPlaceMap.forEach { it.value.calculateCalculatedPoints() }
        preferredPairPlaceLessonAndDay = ArrayList(preferredPairPlaceLessonAndDay.sortedByDescending { it.preferencePlaceHierarchy.calculatedPoints + it.preferenceLessonAndDayHierarchy.calculatedPoints })
    }

    fun calculateTheBestPairPlaceLessonAndDay() : PreferencePairPlaceLessonAndDay? {
        preferredLessonAndDayOfWeekSet.forEach { it.calculateCalculatedPoints() }
        preferredPlaceMap.forEach { it.value.calculateCalculatedPoints() }
        return preferredPairPlaceLessonAndDay.maxBy { it.preferencePlaceHierarchy.calculatedPoints + it.preferenceLessonAndDayHierarchy.calculatedPoints }
    }

}
