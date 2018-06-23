package com.patres.timetable.preference

import com.patres.timetable.domain.*
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

    fun calculateFullPreferencePoints(timetable: Timetable): Int {
        val pointsFromTeacher = timetable.teacher?.id?.let { preferredTeacherMap[it]?.points } ?: 0
        val pointsFromSubject = timetable.subject?.id?.let { preferredSubjectMap[it]?.points } ?: 0
        val pointsFromDivision = timetable.division?.id?.let { preferredDivisionMap[it]?.points } ?: 0
        val pointsFromPlace = timetable.place?.id?.let { preferredPlaceMap[it]?.points } ?: 0
        val preferenceFromTime = getPreferenceByLessonAndDay(timetable.dayOfWeek, timetable.lesson?.id)?.preference
        val pointsFromTime = preferenceFromTime?.points ?: 0
        timetable.preferenceTimetableHierarchy = PreferenceTimetableHierarchy().apply {
            preferredBySubject = pointsFromSubject
            preferredByPlace = pointsFromPlace
            preferredByTeacher = pointsFromTeacher
            preferredByDivision = pointsFromDivision
            preferredByLessonAndDayOfWeek = pointsFromTime
            takenByDivision = preferenceFromTime?.takenByDivision ?: 0
            takenByTeacher = preferenceFromTime?.takenByTeacher ?: 0
            takenByPlace = preferenceFromTime?.takenByPlace ?: 0
        }

        return timetable.preferenceTimetableHierarchy.points
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
        takenDivisions.forEach { division ->
            val divisions = division.calculateAllTakenDivisionFromDivision()
            divisions.forEach { preferredDivisionMap[it.id]?.taken = PreferenceHierarchy.TAKEN }
        }
    }

    fun calculateTakenPlace(takenTimetable: Set<Timetable>) {
        val takenPlacesId = takenTimetable.mapNotNull { it.place?.id }.toSet()
        preferredPlaceMap.forEach { id, preference -> preference.taken = 0 }
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
        val allTakenDivisionFromDivision = division.calculateAllTakenDivisionFromDivision()
        preferredLessonAndDayOfWeekSet.forEach { preferredLessonAndDayOfWeek ->
            val isTaken = takenTimetable.any { timetable ->
                timetable.dayOfWeek == preferredLessonAndDayOfWeek.dayOfWeek &&
                    timetable.lesson?.id == preferredLessonAndDayOfWeek.lessonId &&
                    allTakenDivisionFromDivision.contains(timetable.division)
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

    fun calculateByLessonAndDayOfWeek(preferenceForTeacher: Set<PreferenceDataTimeForTeacher>, preferenceForSubject: Set<PreferenceDataTimeForSubject>, preferenceForDivision:  Set<PreferenceDataTimeForDivision>, preferenceForPlace: Set<PreferenceDataTimeForPlace> ) {
        calculateTeacherByLessonAndDayOfWeek(preferenceForTeacher)
        calculatePlaceByLessonAndDayOfWeek(preferenceForPlace)
        calculateDivisionByLessonAndDayOfWeek(preferenceForDivision)
        calculateSubjectByLessonAndDayOfWeek(preferenceForSubject)
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
            val preference = preferenceDataTimes.find { it.teacher?.id == id }
            preferenceHierarchy.preferredByDateTime = preference?.points ?: 0
        }
    }

    fun calculatePlaceByLessonAndDayOfWeek(preferenceDataTimes: Set<PreferenceDataTimeForPlace>) {
        preferredPlaceMap.forEach { id, preferenceHierarchy ->
            val preference = preferenceDataTimes.find { it.place?.id == id }
            preferenceHierarchy.preferredByDateTime = preference?.points ?: 0
        }
    }

    fun calculateDivisionByLessonAndDayOfWeek(preferenceDataTimes: Set<PreferenceDataTimeForDivision>) {
        preferredDivisionMap.forEach { id, preferenceHierarchy ->
            val preference = preferenceDataTimes.find { it.division?.id == id }
            preferenceHierarchy.preferredByDateTime = preference?.points ?: 0
        }
    }

    fun calculateSubjectByLessonAndDayOfWeek(preferenceDataTimes: Set<PreferenceDataTimeForSubject>) {
        preferredSubjectMap.forEach { id, preferenceHierarchy ->
            val preference = preferenceDataTimes.find { it.subject?.id == id }
            preferenceHierarchy.preferredByDateTime = preference?.points ?: 0
        }
    }

    private fun calculateLessonAndDayOfWeekByTeacher(teacher: Teacher) {
        preferredLessonAndDayOfWeekSet.forEach { lessonDayPreferenceElement ->
            val preference = teacher.getPreferenceDateTime(lessonDayPreferenceElement)
            lessonDayPreferenceElement.preference.preferredByTeacher = preference?.points ?: 0
        }
    }

    private fun calculateLessonAndDayOfWeekByPlace(place: Place) {
        preferredLessonAndDayOfWeekSet.forEach { lessonDayPreferenceElement ->
            val preference = place.getPreferenceDateTime(lessonDayPreferenceElement)
            lessonDayPreferenceElement.preference.preferredByPlace = preference?.points ?: 0
        }
    }

    private fun calculateLessonAndDayOfWeekByDivision(division: Division) {
        preferredLessonAndDayOfWeekSet.forEach { lessonDayPreferenceElement ->
            val preference = division.getPreferenceDateTime(lessonDayPreferenceElement)
            lessonDayPreferenceElement.preference.preferredByDivision = preference?.points ?: 0
        }
    }

    private fun calculateLessonAndDayOfWeekBySubject(subject: Subject) {
        preferredLessonAndDayOfWeekSet.forEach { lessonDayPreferenceElement ->
            val preference = subject.getPreferenceDateTime(lessonDayPreferenceElement)
            lessonDayPreferenceElement.preference.preferredBySubject = preference?.points ?: 0
        }
    }

    private fun calculatePlacesByTeacher(teacher: Teacher) {
        preferredPlaceMap.forEach { id, preferenceHierarchy ->
            val preference = teacher.preferenceTeacherByPlace.find { it.place?.id == id }
            preferenceHierarchy.preferredByTeacher = preference?.points ?: 0
        }
    }

    private fun calculateDivisionsByTeacher(teacher: Teacher) {
        preferredDivisionMap.forEach { id, preferenceHierarchy ->
            val preference = teacher.preferencesTeacherByDivision.find { it.division?.id == id }
            preferenceHierarchy.preferredByTeacher = preference?.points ?: 0
        }
    }

    private fun calculateSubjectsByTeacher(teacher: Teacher) {
        preferredSubjectMap.forEach { id, preferenceHierarchy ->
            val preference = teacher.preferenceSubjectByTeacher.find { it.subject?.id == id }
            preferenceHierarchy.preferredByTeacher = preference?.points ?: 0
        }
    }

    private fun calculateSubjectsByPlace(place: Place) {
        preferredSubjectMap.forEach { id, preferenceHierarchy ->
            val preference = place.preferenceSubjectByPlace.find { it.subject?.id == id }
            preferenceHierarchy.preferredByPlace = preference?.points ?: 0
        }
    }

    private fun calculateDivisionsByPlace(place: Place) {
        preferredDivisionMap.forEach { id, preferenceHierarchy ->
            val preference = place.preferenceDivisionByPlace.find { it.division?.id == id }
            preferenceHierarchy.preferredByPlace = preference?.points ?: 0
        }
    }

    private fun calculateTeachersByPlace(place: Place) {
        preferredTeacherMap.forEach { id, preferenceHierarchy ->
            val preference = place.preferenceTeacherByPlace.find { it.teacher?.id == id }
            preferenceHierarchy.preferredByPlace = preference?.points ?: 0
        }
    }

    private fun calculateSubjectsByDivision(division: Division) {
        preferredSubjectMap.forEach { id, preferenceHierarchy ->
            val preference = division.preferencesSubjectByDivision.find { it.subject?.id == id }
            preferenceHierarchy.preferredByDivision = preference?.points ?: 0
        }
    }

    private fun calculatePlacesByDivision(division: Division) {
        preferredPlaceMap.forEach { id, preferenceHierarchy ->
            val preference = division.preferenceDivisionByPlace.find { it.place?.id == id }
            preferenceHierarchy.preferredByDivision = preference?.points ?: 0
        }
    }

    private fun calculateTeachersByDivision(division: Division) {
        preferredTeacherMap.forEach { id, preferenceHierarchy ->
            val preference = division.preferencesTeacherByDivision.find { it.teacher?.id == id }
            preferenceHierarchy.preferredByDivision = preference?.points ?: 0
        }
    }

    private fun calculatePlacesBySubject(subject: Subject) {
        preferredPlaceMap.forEach { id, preferenceHierarchy ->
            val preference = subject.preferenceSubjectByPlace.find { it.place?.id == id }
            preferenceHierarchy.preferredBySubject = preference?.points ?: 0
        }
    }

    private fun calculateDivisionsBySubject(subject: Subject) {
        preferredDivisionMap.forEach { id, preferenceHierarchy ->
            val preference = subject.preferencesSubjectByDivision.find { it.division?.id == id }
            preferenceHierarchy.preferredBySubject = preference?.points ?: 0
        }
    }

    private fun calculateTeachersBySubject(subject: Subject) {
        preferredTeacherMap.forEach { id, preferenceHierarchy ->
            val preferredTeachers = subject.preferenceSubjectByTeacher.find { it.teacher?.id == id }
            preferenceHierarchy.preferredBySubject = preferredTeachers?.points ?: 0
        }
    }


}
