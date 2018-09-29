package com.patres.timetable.preference.container.single

import com.patres.timetable.domain.Timetable
import com.patres.timetable.domain.preference.PreferenceDataTimeForDivision
import com.patres.timetable.domain.preference.PreferenceDataTimeForPlace
import com.patres.timetable.domain.preference.PreferenceDataTimeForSubject
import com.patres.timetable.domain.preference.PreferenceDataTimeForTeacher
import com.patres.timetable.preference.container.PreferenceContainer
import com.patres.timetable.preference.hierarchy.division.PreferenceDivisionHierarchyForSingle
import com.patres.timetable.preference.hierarchy.lessonAndDay.PreferenceLessonAndDayHierarchyForSingle
import com.patres.timetable.preference.hierarchy.place.PreferencePlaceHierarchyForSingle
import com.patres.timetable.preference.hierarchy.subject.PreferenceSubjectHierarchyForSingle
import com.patres.timetable.preference.hierarchy.teacher.PreferenceTeacherHierarchyForSingle
import java.time.DayOfWeek

class PreferenceContainerForSingle(
    val placesId: Set<Long>,
    val teachersId: Set<Long>,
    val divisionsId: Set<Long>,
    val subjectsId: Set<Long>,
    val lessonsId: Set<Long>
) : PreferenceContainer() {

    override val preferredTeacherMap = teachersId.map { it to PreferenceTeacherHierarchyForSingle(teacherId = it, preferenceContainerForSingle = this) }.toMap().toSortedMap()
    override val preferredSubjectMap = subjectsId.map { it to PreferenceSubjectHierarchyForSingle(subjectId = it, preferenceContainerForSingle = this) }.toMap().toSortedMap()
    override val preferredPlaceMap = placesId.map { it to PreferencePlaceHierarchyForSingle(placeId = it, preferenceContainerForSingle = this) }.toMap().toSortedMap()
    override val preferredDivisionMap = divisionsId.map { it to PreferenceDivisionHierarchyForSingle(divisionId = it, preferenceContainerForSingle = this) }.toMap().toSortedMap()
    override val preferredLessonAndDayOfWeekSet = HashSet<PreferenceLessonAndDayHierarchyForSingle>()

    var tooSmallPlaceId: Set<Long> = emptySet()
    var takenTimetables: Set<Timetable> = emptySet()

    var preferenceDateTimeForTeacher: Set<PreferenceDataTimeForTeacher> = emptySet()
    var preferenceDateTimeForSubject: Set<PreferenceDataTimeForSubject> = emptySet()
    var preferenceDateTimeForDivision: Set<PreferenceDataTimeForDivision> = emptySet()
    var preferenceDateTimeForPlace: Set<PreferenceDataTimeForPlace> = emptySet()

    init {
        DayOfWeek.values().forEach { dayOfWeek ->
            lessonsId.forEach { lessonId ->
                preferredLessonAndDayOfWeekSet.add(PreferenceLessonAndDayHierarchyForSingle(dayOfWeek = dayOfWeek, lessonId = lessonId, preferenceContainerForSingle = this))
            }
        }
    }

}
