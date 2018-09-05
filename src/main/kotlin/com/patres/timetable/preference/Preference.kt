package com.patres.timetable.preference

import com.fasterxml.jackson.annotation.JsonIgnore
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
    val preferredAvailableTimeAndPlace = HashSet<TimeAndPlacePreferenceElement>()  // TODO implements

    @JsonIgnore
    var selectPlace: Place? = null
    @JsonIgnore
    var selectDivision: Division? = null
    @JsonIgnore
    var selectTeacher: Teacher? = null
    @JsonIgnore
    var selectSubject: Subject? = null
    @JsonIgnore
    var selectLesson: Lesson? = null
    @JsonIgnore
    var selectDayOfWeek: DayOfWeek? = null

    init {
        DayOfWeek.values().forEach { dayOfWeek ->
            lessonsId.forEach { lessonId ->
                preferredLessonAndDayOfWeekSet.add(LessonDayOfWeekPreferenceElement(dayOfWeek = dayOfWeek.value, lessonId = lessonId))
                placesId.forEach { placeId ->
                    preferredAvailableTimeAndPlace.add(TimeAndPlacePreferenceElement(dayOfWeek = dayOfWeek.value, lessonId = lessonId, placeId = placeId))
                }
            }
        }
    }
//
//
//    fun calculateAvailableTimeAndPlace() {
////        preferredPlaceMap.forEach { placeEntry ->
////            preferredAvailableTimeAndPlace
////                .filter { it.placeId == placeEntry.key }
////                .forEach { it.preference.takenByPlace = placeEntry.value.taken }
////        }
//
//        preferredLessonAndDayOfWeekSet.forEach { lessonDatOfWeekPrefElement ->
//            preferredAvailableTimeAndPlace
//                .filter { it.lessonId == lessonDatOfWeekPrefElement.lessonId && it.dayOfWeek == lessonDatOfWeekPrefElement.dayOfWeek }
//                .forEach {
//                    it.preference.takenByDivision = lessonDatOfWeekPrefElement.preference.takenByDivision
//                    it.preference.takenByTeacher = lessonDatOfWeekPrefElement.preference.takenByDivision
//                    it.preference.takenByPlace = lessonDatOfWeekPrefElement.preference.takenByDivision
//
//                    it.preference.takenByPlace = lessonDatOfWeekPrefElement.preference.p
//                }
//        }
//    }


    fun calculateAllForTimetable(timetable: Timetable, tooSmallPlaceId: Set<Long>) {
        setSelectedPropertiesByTimetable(timetable)
        calculateByTeacher()
        calculateBySubject()
        calculateByPlace()
        calculateByDivision()
        calculateTooSmallPlace(tooSmallPlaceId)
    }

    fun calculatePreferences(place: Place? = null, division: Division? = null, subject: Subject? = null, teacher: Teacher? = null) {
        place?.takeIf { it != selectPlace }?.let {
            selectPlace = it
            calculateByPlace()
        }
        subject?.takeIf { it != selectSubject }?.let {
            selectSubject = it
            calculateBySubject()
        }
        teacher?.takeIf { it != selectTeacher }?.let {
            selectTeacher = it
            calculateByTeacher()
        }
        division?.takeIf { it != selectDivision }?.let {
            selectDivision = it
            calculateByDivision()
        }
    }

    private fun setSelectedPropertiesByTimetable(timetable: Timetable) {
        selectPlace = timetable.place
        selectDivision = timetable.division
        selectTeacher = timetable.teacher
        selectSubject = timetable.subject
        selectLesson = timetable.lesson
        selectDayOfWeek = timetable.dayOfWeek?.let { DayOfWeek.of(it) }
    }

    fun calculateFullPreferencePoints(timetable: Timetable): Int {
        val pointsFromTeacher = timetable.teacher?.id?.let { preferredTeacherMap[it]?.preferencePoints } ?: 0
        val pointsFromSubject = timetable.subject?.id?.let { preferredSubjectMap[it]?.preferencePoints } ?: 0
        val pointsFromDivision = timetable.division?.id?.let { preferredDivisionMap[it]?.preferencePoints } ?: 0
        val pointsFromPlace = timetable.place?.id?.let { preferredPlaceMap[it]?.preferencePoints } ?: 0
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

    fun calculateByTeacher() {
        calculateSubjectsByTeacher()
        calculateDivisionsByTeacher()
        calculatePlacesByTeacher()
        calculateLessonAndDayOfWeekByTeacher()
    }

    fun calculateByPlace() {
        calculateTeachersByPlace()
        calculateDivisionsByPlace()
        calculateSubjectsByPlace()
        calculateLessonAndDayOfWeekByPlace()
    }

    fun calculateByDivision() {
        calculateTeachersByDivision()
        calculatePlacesByDivision()
        calculateSubjectsByDivision()
        calculateLessonAndDayOfWeekByDivision()
    }

    fun calculateBySubject() {
        calculateTeachersBySubject()
        calculateDivisionsBySubject()
        calculatePlacesBySubject()
        calculateLessonAndDayOfWeekBySubject()
    }

    fun calculateByLessonAndDayOfWeek(preferenceForTeacher: Set<PreferenceDataTimeForTeacher>, preferenceForSubject: Set<PreferenceDataTimeForSubject>, preferenceForDivision: Set<PreferenceDataTimeForDivision>, preferenceForPlace: Set<PreferenceDataTimeForPlace>) {
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

    private fun calculateLessonAndDayOfWeekByTeacher() {
        selectTeacher?.let { teacher ->
            preferredLessonAndDayOfWeekSet.forEach { lessonDayPreferenceElement ->
                val preference = teacher.getPreferenceDateTime(lessonDayPreferenceElement)
                lessonDayPreferenceElement.preference.preferredByTeacher = preference?.points ?: 0
            }
        }
    }

    private fun calculateLessonAndDayOfWeekByPlace() {
        selectPlace?.let { place ->
            preferredLessonAndDayOfWeekSet.forEach { lessonDayPreferenceElement ->
                val preference = place.getPreferenceDateTime(lessonDayPreferenceElement)
                lessonDayPreferenceElement.preference.preferredByPlace = preference?.points ?: 0
            }
        }
    }

    private fun calculateLessonAndDayOfWeekByDivision() {
        selectDivision?.let { division ->
            preferredLessonAndDayOfWeekSet.forEach { lessonDayPreferenceElement ->
                val preference = division.getPreferenceDateTime(lessonDayPreferenceElement)
                lessonDayPreferenceElement.preference.preferredByDivision = preference?.points ?: 0
            }
        }
    }

    private fun calculateLessonAndDayOfWeekBySubject() {
        selectSubject?.let { subject ->
            preferredLessonAndDayOfWeekSet.forEach { lessonDayPreferenceElement ->
                val preference = subject.getPreferenceDateTime(lessonDayPreferenceElement)
                lessonDayPreferenceElement.preference.preferredBySubject = preference?.points ?: 0
            }
        }
    }

    private fun calculatePlacesByTeacher() {
        selectTeacher?.let { teacher ->
            preferredPlaceMap.forEach { id, preferenceHierarchy ->
                val preference = teacher.preferenceTeacherByPlace.find { it.place?.id == id }
                preferenceHierarchy.preferredByTeacher = preference?.points ?: 0
            }
        }
    }

    private fun calculateDivisionsByTeacher() {
        selectTeacher?.let { teacher ->
            preferredDivisionMap.forEach { id, preferenceHierarchy ->
                val preference = teacher.preferencesTeacherByDivision.find { it.division?.id == id }
                preferenceHierarchy.preferredByTeacher = preference?.points ?: 0
            }
        }
    }

    private fun calculateSubjectsByTeacher() {
        selectTeacher?.let { teacher ->
            preferredSubjectMap.forEach { id, preferenceHierarchy ->
                val preference = teacher.preferenceSubjectByTeacher.find { it.subject?.id == id }
                preferenceHierarchy.preferredByTeacher = preference?.points ?: 0
            }
        }
    }

    private fun calculateSubjectsByPlace() {
        selectPlace?.let { place ->
            preferredSubjectMap.forEach { id, preferenceHierarchy ->
                val preference = place.preferenceSubjectByPlace.find { it.subject?.id == id }
                preferenceHierarchy.preferredByPlace = preference?.points ?: 0
            }
        }
    }

    private fun calculateDivisionsByPlace() {
        selectPlace?.let { place ->
            preferredDivisionMap.forEach { id, preferenceHierarchy ->
                val preference = place.preferenceDivisionByPlace.find { it.division?.id == id }
                preferenceHierarchy.preferredByPlace = preference?.points ?: 0
            }
        }
    }

    private fun calculateTeachersByPlace() {
        selectPlace?.let { place ->
            preferredTeacherMap.forEach { id, preferenceHierarchy ->
                val preference = place.preferenceTeacherByPlace.find { it.teacher?.id == id }
                preferenceHierarchy.preferredByPlace = preference?.points ?: 0
            }
        }
    }

    private fun calculateSubjectsByDivision() {
        selectDivision?.let { division ->
            preferredSubjectMap.forEach { id, preferenceHierarchy ->
                val preference = division.preferencesSubjectByDivision.find { it.subject?.id == id }
                preferenceHierarchy.preferredByDivision = preference?.points ?: 0
            }
        }
    }

    private fun calculatePlacesByDivision() {
        selectDivision?.let { division ->

            preferredPlaceMap.forEach { id, preferenceHierarchy ->
                val preference = division.preferenceDivisionByPlace.find { it.place?.id == id }
                preferenceHierarchy.preferredByDivision = preference?.points ?: 0
            }
        }
    }

    private fun calculateTeachersByDivision() {
        selectDivision?.let { division ->
            preferredTeacherMap.forEach { id, preferenceHierarchy ->
                val preference = division.preferencesTeacherByDivision.find { it.teacher?.id == id }
                preferenceHierarchy.preferredByDivision = preference?.points ?: 0
            }

        }
    }

    private fun calculatePlacesBySubject() {
        selectSubject?.let { subject ->
            preferredPlaceMap.forEach { id, preferenceHierarchy ->
                val preference = subject.preferenceSubjectByPlace.find { it.place?.id == id }
                preferenceHierarchy.preferredBySubject = preference?.points ?: 0
            }
        }
    }

    private fun calculateDivisionsBySubject() {
        selectSubject?.let { subject ->
            preferredDivisionMap.forEach { id, preferenceHierarchy ->
                val preference = subject.preferencesSubjectByDivision.find { it.division?.id == id }
                preferenceHierarchy.preferredBySubject = preference?.points ?: 0
            }
        }
    }

    private fun calculateTeachersBySubject() {
        selectSubject?.let { subject ->
            preferredTeacherMap.forEach { id, preferenceHierarchy ->
                val preferredTeachers = subject.preferenceSubjectByTeacher.find { it.teacher?.id == id }
                preferenceHierarchy.preferredBySubject = preferredTeachers?.points ?: 0
            }
        }
    }

}
