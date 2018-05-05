package com.patres.timetable.preference

import com.patres.timetable.domain.*
import com.patres.timetable.domain.enumeration.DivisionType
import com.patres.timetable.domain.preference.PreferenceDataTimeForDivision
import com.patres.timetable.domain.preference.PreferenceDataTimeForPlace
import com.patres.timetable.domain.preference.PreferenceDataTimeForSubject
import com.patres.timetable.domain.preference.PreferenceDataTimeForTeacher
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

    fun calculateAllForTimetable(timetable: Timetable, tooSmallPlaceId: Set<Long>) {
        timetable.teacher?.let { calculateByTeacher(it) }
        timetable.subject?.let { calculateBySubject(it) }
        timetable.place?.let { calculateByPlace(it) }
        timetable.division?.let { calculateByDivision(it) }
        timetable.division?.let { calculateTooSmallPlace(tooSmallPlaceId) }
    }

    fun getPreferenceByLessonAndDay(dayOfWeek: Int?, lessonId: Long?): LessonDayOfWeekPreferenceElement? {
        return preferredLessonAndDayOfWeekSet.find { it.dayOfWeek == dayOfWeek && it.lessonId == lessonId }
    }

    fun getPreferenceByLessonAndDay(timetable: Timetable?): LessonDayOfWeekPreferenceElement? {
        return preferredLessonAndDayOfWeekSet.find { it.dayOfWeek == timetable?.dayOfWeek && it.lessonId == timetable.lesson?.id }
    }

    fun getPreferencePointsByLessonAndDay(timetable: Timetable?): Int? {
        return getPreferenceByLessonAndDay(timetable)?.preference?.points
    }


    fun calculateTaken(takenTimetable: Set<Timetable>) {
        val takenPlacesId = takenTimetable.mapNotNull { it.place?.id }.toSet()
        takenPlacesId.forEach { preferredPlaceMap[it]?.taken = PreferenceHierarchy.TAKEN }

        val takenTeachersId = takenTimetable.mapNotNull { it.teacher?.id }.toSet()
        takenTeachersId.forEach { preferredTeacherMap[it]?.taken = PreferenceHierarchy.TAKEN }

        val takenDivisions = takenTimetable.mapNotNull { it.division }.toSet()
        takenDivisions.forEach {
            preferredDivisionMap[it.id]?.taken = PreferenceHierarchy.TAKEN
            it.parents.filter { it.divisionType == DivisionType.CLASS }.forEach {
                preferredDivisionMap[it.id]?.taken = PreferenceHierarchy.TAKEN
            }
            it.children.filter { it.divisionType == DivisionType.SUBGROUP }.forEach {
                preferredDivisionMap[it.id]?.taken = PreferenceHierarchy.TAKEN
            }
        }
    }

    fun calculateTakenPlace(takenTimetable: Set<Timetable>) {
        val takenPlacesId = takenTimetable.mapNotNull { it.place?.id }.toSet()
        takenPlacesId.forEach { preferredPlaceMap[it]?.taken = PreferenceHierarchy.TAKEN }
    }

    fun calculateTakenLessonAndDayOfWeekByTeacher(teacher: Teacher, takenTimetable: Set<Timetable>) {
        preferredLessonAndDayOfWeekSet.forEach { preferredLessonAndDayOfWeek ->
            val isTaken = takenTimetable.any { timetable -> timetable.dayOfWeek == preferredLessonAndDayOfWeek.dayOfWeek && timetable.lesson?.id == preferredLessonAndDayOfWeek.lessonId && timetable.teacher?.id == teacher.id }
            if (isTaken) {
                preferredLessonAndDayOfWeek.preference.takenByTeacher = PreferenceHierarchy.TAKEN
            } else {
                preferredLessonAndDayOfWeek.preference.takenByTeacher = 0
            }
        }
    }

    fun calculateTakenLessonAndDayOfWeekByPlace(place: Place, takenTimetable: Set<Timetable>) {
        preferredLessonAndDayOfWeekSet.forEach { preferredLessonAndDayOfWeek ->
            val isTaken = takenTimetable.any { timetable -> timetable.dayOfWeek == preferredLessonAndDayOfWeek.dayOfWeek && timetable.lesson?.id == preferredLessonAndDayOfWeek.lessonId && timetable.place?.id == place.id }
            if (isTaken) {
                preferredLessonAndDayOfWeek.preference.takenByPlace = PreferenceHierarchy.TAKEN
            } else {
                preferredLessonAndDayOfWeek.preference.takenByPlace = 0
            }
        }
    }

    fun calculateTakenLessonAndDayOfWeekByDivision(division: Division, takenTimetable: Set<Timetable>) {
        preferredLessonAndDayOfWeekSet.forEach { preferredLessonAndDayOfWeek ->
            val isTaken = takenTimetable.any { timetable ->
                    timetable.dayOfWeek == preferredLessonAndDayOfWeek.dayOfWeek &&
                    timetable.lesson?.id == preferredLessonAndDayOfWeek.lessonId &&
                    (timetable.division?.id == division.id || division.parents.any { it.id == timetable.division?.id } || division.children.any { it.id == timetable.division?.id })
            }
            if (isTaken) {
                preferredLessonAndDayOfWeek.preference.takenByDivision = PreferenceHierarchy.TAKEN
            } else {
                preferredLessonAndDayOfWeek.preference.takenByDivision = 0
            }
        }
    }

    fun calculateByTeacher(teacher: Teacher) {
        calculateSubjectsByTeacher(teacher)
        calculateDivisionsByTeacher(teacher)
        calculatePlacesByTeacher(teacher)
        calculateLessonAndDayOfWeekByTeacher(teacher)
    }

    fun calculateByPlace(place: Place) {
        calculateTeachersByPlace(place)
        calculateDivisionsByPlace(place)
        calculateSubjectsByPlace(place)
        calculateLessonAndDayOfWeekByPlace(place)
    }

    fun calculateByDivision(division: Division) {
        calculateTeachersByDivision(division)
        calculatePlacesByDivision(division)
        calculateSubjectsByDivision(division)
        calculateLessonAndDayOfWeekByDivision(division)
    }

    fun calculateBySubject(subject: Subject) {
        calculateTeachersBySubject(subject)
        calculateDivisionsBySubject(subject)
        calculatePlacesBySubject(subject)
        calculateLessonAndDayOfWeekBySubject(subject)
    }

    fun calculateTooSmallPlace(idOfTooSmallPlaces: Set<Long>) {
        preferredPlaceMap.forEach { id, preferenceHierarchy ->
            if (idOfTooSmallPlaces.contains(id)) {
                preferenceHierarchy.tooSmallPlace = PreferenceHierarchy.TOO_SMALL_PLACE
            } else {
                preferenceHierarchy.tooSmallPlace = 0
            }
        }
    }

    fun calculateTeacherByLessonAndDayOfWeek(preferenceDataTimes: Set<PreferenceDataTimeForTeacher>) {
        preferredTeacherMap.forEach { id, preferenceHierarchy ->
            val preferenceDataTime = preferenceDataTimes.find { it.teacher?.id == id }
            if (preferenceDataTime != null) {
                preferenceHierarchy.preferredByDataTime = preferenceDataTime.points
            } else {
                preferenceHierarchy.preferredByDataTime = 0
            }
        }
    }

    fun calculatePlaceByLessonAndDayOfWeek(preferenceDataTimes: Set<PreferenceDataTimeForPlace>) {
        preferredPlaceMap.forEach { id, preferenceHierarchy ->
            val preferenceDataTime = preferenceDataTimes.find { it.place?.id == id }
            if (preferenceDataTime != null) {
                preferenceHierarchy.preferredByDataTime = preferenceDataTime.points
            } else {
                preferenceHierarchy.preferredByDataTime = 0
            }
        }
    }

    fun calculateDivisionByLessonAndDayOfWeek(preferenceDataTimes: Set<PreferenceDataTimeForDivision>) {
        preferredDivisionMap.forEach { id, preferenceHierarchy ->
            val preferenceDataTime = preferenceDataTimes.find { it.division?.id == id }
            if (preferenceDataTime != null) {
                preferenceHierarchy.preferredByDataTime = preferenceDataTime.points
            } else {
                preferenceHierarchy.preferredByDataTime = 0
            }
        }
    }

    fun calculateSubjectByLessonAndDayOfWeek(preferenceDataTimes: Set<PreferenceDataTimeForSubject>) {
        preferredSubjectMap.forEach { id, preferenceHierarchy ->
            val preferenceDataTime = preferenceDataTimes.find { it.subject?.id == id }
            if (preferenceDataTime != null) {
                preferenceHierarchy.preferredByDataTime = preferenceDataTime.points
            } else {
                preferenceHierarchy.preferredByDataTime = 0
            }
        }
    }

    private fun calculateLessonAndDayOfWeekByTeacher(teacher: Teacher) {
        preferredLessonAndDayOfWeekSet.forEach { lessonDayPreferenceElement ->
            val preferenceDataTime = teacher.preferenceDataTimeForTeachers
                .find { preference -> preference.lesson?.id == lessonDayPreferenceElement.lessonId && preference.dayOfWeek == lessonDayPreferenceElement.dayOfWeek }
            if (preferenceDataTime != null) {
                lessonDayPreferenceElement.preference.preferredByTeacher = preferenceDataTime.points
            } else {
                lessonDayPreferenceElement.preference.preferredByTeacher = 0
            }
        }
    }

    private fun calculateLessonAndDayOfWeekByPlace(place: Place) {
        preferredLessonAndDayOfWeekSet.forEach { lessonDayPreferenceElement ->
            val preferenceDataTime = place.preferencesDataTimeForPlace
                .find { preference -> preference.lesson?.id == lessonDayPreferenceElement.lessonId && preference.dayOfWeek == lessonDayPreferenceElement.dayOfWeek }
            if (preferenceDataTime != null) {
                lessonDayPreferenceElement.preference.preferredByPlace = preferenceDataTime.points
            } else {
                lessonDayPreferenceElement.preference.preferredByPlace = 0
            }
        }
    }


    private fun calculateLessonAndDayOfWeekByDivision(division: Division) {
        preferredLessonAndDayOfWeekSet.forEach { lessonDayPreferenceElement ->
            val preferenceDataTime = division.preferencesDataTimeForDivision
                .find { preference -> preference.lesson?.id == lessonDayPreferenceElement.lessonId && preference.dayOfWeek == lessonDayPreferenceElement.dayOfWeek }
            if (preferenceDataTime != null) {
                lessonDayPreferenceElement.preference.preferredByDivision = preferenceDataTime.points
            } else {
                lessonDayPreferenceElement.preference.preferredByDivision = 0
            }
        }
    }

    private fun calculateLessonAndDayOfWeekBySubject(subject: Subject) {
        preferredLessonAndDayOfWeekSet.forEach { lessonDayPreferenceElement ->
            val preferenceDataTime = subject.preferencesDataTimeForSubject
                .find { preference -> preference.lesson?.id == lessonDayPreferenceElement.lessonId && preference.dayOfWeek == lessonDayPreferenceElement.dayOfWeek }
            if (preferenceDataTime != null) {
                lessonDayPreferenceElement.preference.preferredBySubject = preferenceDataTime.points
            } else {
                lessonDayPreferenceElement.preference.preferredBySubject = 0
            }
        }
    }


    private fun calculatePlacesByTeacher(teacher: Teacher) {
        preferredPlaceMap.forEach { id, preferenceHierarchy ->
            val preferredPlaces = teacher.preferenceTeacherByPlace.find { it.place?.id == id }
            if (preferredPlaces != null) {
                preferenceHierarchy.preferredByTeacher = preferredPlaces.points
            } else {
                preferenceHierarchy.preferredByTeacher = 0
            }
        }
    }

    private fun calculateDivisionsByTeacher(teacher: Teacher) {
        preferredDivisionMap.forEach { id, preferenceHierarchy ->
            val preferredDivisions = teacher.preferencesTeacherByDivision.find { it.division?.id == id }
            if (preferredDivisions != null) {
                preferenceHierarchy.preferredByTeacher = preferredDivisions.points
            } else {
                preferenceHierarchy.preferredByTeacher = 0
            }
        }
    }

    private fun calculateSubjectsByTeacher(teacher: Teacher) {
        preferredSubjectMap.forEach { id, preferenceHierarchy ->
            val preferenceSubject = teacher.preferenceSubjectByTeacher.find { it.subject?.id == id }
            if (preferenceSubject != null) {
                preferenceHierarchy.preferredByTeacher = preferenceSubject.points
            } else {
                preferenceHierarchy.preferredByTeacher = 0
            }
        }
    }

    private fun calculateSubjectsByPlace(place: Place) {
        preferredSubjectMap.forEach { id, preferenceHierarchy ->
            val preferenceSubject = place.preferenceSubjectByPlace.find { it.subject?.id == id }
            if (preferenceSubject != null) {
                preferenceHierarchy.preferredByPlace = preferenceSubject.points
            } else {
                preferenceHierarchy.preferredByPlace = 0
            }
        }
    }

    private fun calculateDivisionsByPlace(place: Place) {
        preferredDivisionMap.forEach { id, preferenceHierarchy ->
            val preferenceDivision = place.preferenceDivisionByPlace.find { it.division?.id == id }
            if (preferenceDivision != null) {
                preferenceHierarchy.preferredByPlace = preferenceDivision.points
            } else {
                preferenceHierarchy.preferredByPlace = 0
            }
        }
    }

    private fun calculateTeachersByPlace(place: Place) {
        preferredTeacherMap.forEach { id, preferenceHierarchy ->
            val preferenceTeacher = place.preferenceTeacherByPlace.find { it.teacher?.id == id }
            if (preferenceTeacher != null) {
                preferenceHierarchy.preferredByPlace = preferenceTeacher.points
            } else {
                preferenceHierarchy.preferredByPlace = 0
            }
        }
    }

    private fun calculateSubjectsByDivision(division: Division) {
        preferredSubjectMap.forEach { id, preferenceHierarchy ->
            val preferredSubjects = division.preferencesSubjectByDivision.find { it.subject?.id == id }
            if (preferredSubjects != null) {
                preferenceHierarchy.preferredByDivision = preferredSubjects.points
            } else {
                preferenceHierarchy.preferredByDivision = 0
            }
        }
    }

    private fun calculatePlacesByDivision(division: Division) {
        preferredPlaceMap.forEach { id, preferenceHierarchy ->
            val preferredPlaces = division.preferenceDivisionByPlace.find { it.place?.id == id }
            if (preferredPlaces != null) {
                preferenceHierarchy.preferredByDivision = preferredPlaces.points
            } else {
                preferenceHierarchy.preferredByDivision = 0
            }
        }
    }

    private fun calculateTeachersByDivision(division: Division) {
        preferredTeacherMap.forEach { id, preferenceHierarchy ->
            val preferredTeachers = division.preferencesTeacherByDivision.find { it.teacher?.id == id }
            if (preferredTeachers != null) {
                preferenceHierarchy.preferredByDivision = preferredTeachers.points
            } else {
                preferenceHierarchy.preferredByDivision = 0
            }
        }
    }

    private fun calculatePlacesBySubject(subject: Subject) {
        preferredPlaceMap.forEach { id, preferenceHierarchy ->
            val preferredPlaces = subject.preferenceSubjectByPlace.find { it.place?.id == id }
            if (preferredPlaces != null) {
                preferenceHierarchy.preferredBySubject = preferredPlaces.points
            } else {
                preferenceHierarchy.preferredBySubject = 0
            }
        }
    }

    private fun calculateDivisionsBySubject(subject: Subject) {
        preferredDivisionMap.forEach { id, preferenceHierarchy ->
            val preferredDivisions = subject.preferencesSubjectByDivision.find { it.division?.id == id }
            if (preferredDivisions != null) {
                preferenceHierarchy.preferredBySubject = preferredDivisions.points
            } else {
                preferenceHierarchy.preferredBySubject = 0
            }
        }
    }

    private fun calculateTeachersBySubject(subject: Subject) {
        preferredTeacherMap.forEach { id, preferenceHierarchy ->
            val preferredTeachers = subject.preferenceSubjectByTeacher.find { it.teacher?.id == id }
            if (preferredTeachers != null) {
                preferenceHierarchy.preferredBySubject = preferredTeachers.points
            } else {
                preferenceHierarchy.preferredBySubject = 0
            }
        }
    }


}
