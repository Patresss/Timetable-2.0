package com.patres.timetable.preference

import com.patres.timetable.domain.*
import com.patres.timetable.preference.hierarchy.PreferenceHierarchy
import com.patres.timetable.repository.*
import com.patres.timetable.web.rest.util.TimetableDateUtil
import org.springframework.stereotype.Component

@Component
open class PreferenceManager(
    private var placeRepository: PlaceRepository,
    private var teacherRepository: TeacherRepository,
    private var subjectRepository: SubjectRepository,
    private var divisionRepository: DivisionRepository,
    private var lessonRepository: LessonRepository,
    private var timetableRepository: TimetableRepository
) {

    fun createPreference(divisionOwnerId: Long): Preference {
        val placesId = placeRepository.findIdByDivisionOwnerId(divisionOwnerId)
        val teachersId = teacherRepository.findIdByDivisionOwnerId(divisionOwnerId)
        val divisionsId = divisionRepository.findIdByDivisionOwnerId(divisionOwnerId)
        val subjectsId = subjectRepository.findIdByDivisionOwnerId(divisionOwnerId)
        val lessonsId = lessonRepository.findIdByDivisionOwnerId(divisionOwnerId)
        return Preference(placesId = placesId, teachersId = teachersId, divisionsId = divisionsId, subjectsId = subjectsId, lessonsId = lessonsId)
    }

    fun calculateAll(preference: Preference, preferenceDependency: PreferenceDependency) {
        preferenceDependency.teacher?.let { calculateByTeacher(preference, it) }
        preferenceDependency.subject?.let { calculateBySubject(preference, it) }
        preferenceDependency.place?.let { calculateByPlace(preference, it) }
        preferenceDependency.division?.let { calculateByDivision(preference, it, getIdOfTooSmallPlacesFromDatabase(it)) }
        calculateTaken(preference, getTakenTimetableFromDatabase(preferenceDependency))
    }

    private fun getTakenTimetableFromDatabase(preferenceDependency: PreferenceDependency): Set<Timetable> {
        val dates = TimetableDateUtil.getAllDatesByPreferenceDependency(preferenceDependency)
        var timetablesInThisTime = timetableRepository.findTakenTimetable(preferenceDependency, dates)
        if (preferenceDependency.notTimetableId != null) {
            timetablesInThisTime = timetablesInThisTime.filter { it.id != preferenceDependency.notTimetableId }.toSet()
        }
        return timetablesInThisTime.filter { it.period == null || TimetableDateUtil.canAddByEveryDays(dates, it.period?.getFirstDay(), it.startWithWeek, it.everyWeek) }.toSet()
    }

    fun calculateTaken(preference: Preference, takenTimetable: Set<Timetable>) {
        val takenPlacesId = takenTimetable.mapNotNull { it.place?.id }.toSet()
        takenPlacesId.forEach { preference.preferredPlaceMap[it]?.taken = PreferenceHierarchy.TAKEN }

        val takenTeachersId = takenTimetable.mapNotNull { it.teacher?.id }.toSet()
        takenTeachersId.forEach { preference.preferredTeacherMap[it]?.taken = PreferenceHierarchy.TAKEN }

        val takenDivisionsId = takenTimetable.mapNotNull { it.division?.id }.toSet()
        takenDivisionsId.forEach { preference.preferredDivisionMap[it]?.taken = PreferenceHierarchy.TAKEN }
    }

    fun calculateByTeacher(preference: Preference, teacher: Teacher) {
        val preferredSubjects = teacher.preferredSubjects.mapNotNull { it.id }.toSet()
        preference.preferredSubjectMap.forEach { id, preferenceHierarchy ->
            if (preferredSubjects.contains(id)) {
                preferenceHierarchy.preferredByTeacher = PreferenceHierarchy.PREFFERRED_POINTS
            } else {
                preferenceHierarchy.preferredByTeacher = 0
            }
        }

        val preferredDivisions = teacher.preferredDivisions.mapNotNull { it.id }.toSet()
        preference.preferredDivisionMap.forEach { id, preferenceHierarchy ->
            if (preferredDivisions.contains(id)) {
                preferenceHierarchy.preferredByTeacher = PreferenceHierarchy.PREFFERRED_POINTS
            } else {
                preferenceHierarchy.preferredByTeacher = 0
            }
        }

        val preferredPlaces = teacher.preferredPlaces.mapNotNull { it.id }.toSet()
        preference.preferredPlaceMap.forEach { id, preferenceHierarchy ->
            if (preferredPlaces.contains(id)) {
                preferenceHierarchy.preferredByTeacher = PreferenceHierarchy.PREFFERRED_POINTS
            } else {
                preferenceHierarchy.preferredByTeacher = 0
            }
        }

        preference.preferredLessonAndDayOfWeekSet.forEach { lessonDayPreferenceElement ->
            val preferenceDataTimeForTeacher = teacher.preferenceDataTimeForTeachers
                .find { teacherPreference -> teacherPreference.lesson?.id == lessonDayPreferenceElement.lessonId && teacherPreference.dayOfWeek == lessonDayPreferenceElement.dayOfWeek }
            if (preferenceDataTimeForTeacher != null) {
                lessonDayPreferenceElement.preference.preferredByTeacher = preferenceDataTimeForTeacher.points
            } else {
                lessonDayPreferenceElement.preference.preferredByTeacher = 0
            }
        }
    }

    fun calculateByPlace(preference: Preference, place: Place) {
        val preferredTeachers = place.preferredTeachers.mapNotNull { it.id }.toSet()
        preference.preferredTeacherMap.forEach { id, preferenceHierarchy ->
            if (preferredTeachers.contains(id)) {
                preferenceHierarchy.preferredByPlace = PreferenceHierarchy.PREFFERRED_POINTS
            } else {
                preferenceHierarchy.preferredByPlace = 0
            }
        }

        val preferredDivisions = place.preferredDivisions.mapNotNull { it.id }.toSet()
        preference.preferredDivisionMap.forEach { id, preferenceHierarchy ->
            if (preferredDivisions.contains(id)) {
                preferenceHierarchy.preferredByPlace = PreferenceHierarchy.PREFFERRED_POINTS
            } else {
                preferenceHierarchy.preferredByPlace = 0
            }
        }

        val preferredSubjects = place.preferredSubjects.mapNotNull { it.id }.toSet()
        preference.preferredSubjectMap.forEach { id, preferenceHierarchy ->
            if (preferredSubjects.contains(id)) {
                preferenceHierarchy.preferredByPlace = PreferenceHierarchy.PREFFERRED_POINTS
            } else {
                preferenceHierarchy.preferredByPlace = 0
            }
        }
    }


    fun calculateByDivision(preference: Preference, division: Division, idOfTooSmallPlaces: Set<Long>) {
        val preferredTeachers = division.preferredTeachers.mapNotNull { it.id }.toSet()
        preference.preferredTeacherMap.forEach { id, preferenceHierarchy ->
            if (preferredTeachers.contains(id)) {
                preferenceHierarchy.preferredByDivision = PreferenceHierarchy.PREFFERRED_POINTS
            } else {
                preferenceHierarchy.preferredByDivision = 0
            }
        }

        val preferredPlaces = division.preferredPlaces.mapNotNull { it.id }.toSet()
        preference.preferredPlaceMap.forEach { id, preferenceHierarchy ->
            if (preferredPlaces.contains(id)) {
                preferenceHierarchy.preferredByDivision = PreferenceHierarchy.PREFFERRED_POINTS
            } else {
                preferenceHierarchy.preferredByDivision = 0
            }
        }

        val preferredSubjects = division.preferredSubjects.mapNotNull { it.id }.toSet()
        preference.preferredSubjectMap.forEach { id, preferenceHierarchy ->
            if (preferredSubjects.contains(id)) {
                preferenceHierarchy.preferredByDivision = PreferenceHierarchy.PREFFERRED_POINTS
            } else {
                preferenceHierarchy.preferredByDivision = 0
            }
        }
        calculateTooSmallPlace(preference, idOfTooSmallPlaces)
    }

    fun getIdOfTooSmallPlacesFromDatabase(division: Division): Set<Long> {
        division.numberOfPeople?.let { numberOfPeople ->
            return placeRepository.findIdByDivisionOwnerIdAndNumberOfSeatsLessThan(division.divisionOwner?.id, numberOfPeople)
        }
        return emptySet()
    }

    fun calculateTooSmallPlace(preference: Preference, idOfTooSmallPlaces: Set<Long>) {
        preference.preferredPlaceMap.forEach { id, preferenceHierarchy ->
            if (idOfTooSmallPlaces.contains(id)) {
                preferenceHierarchy.tooSmallPlace = PreferenceHierarchy.TOO_SMALL_PLACE
            } else {
                preferenceHierarchy.tooSmallPlace = 0
            }
        }
    }

    fun calculateBySubject(preference: Preference, subject: Subject) {
        val preferredTeachers = subject.preferredTeachers.mapNotNull { it.id }.toSet()
        preference.preferredTeacherMap.forEach { id, preferenceHierarchy ->
            if (preferredTeachers.contains(id)) {
                preferenceHierarchy.preferredBySubject = PreferenceHierarchy.PREFFERRED_POINTS
            } else {
                preferenceHierarchy.preferredBySubject = 0
            }
        }

        val preferredDivisions = subject.preferredDivisions.mapNotNull { it.id }.toSet()
        preference.preferredDivisionMap.forEach { id, preferenceHierarchy ->
            if (preferredDivisions.contains(id)) {
                preferenceHierarchy.preferredBySubject = PreferenceHierarchy.PREFFERRED_POINTS
            } else {
                preferenceHierarchy.preferredBySubject = 0
            }
        }

        val preferredPlaces = subject.preferredPlaces.mapNotNull { it.id }.toSet()
        preference.preferredPlaceMap.forEach { id, preferenceHierarchy ->
            if (preferredPlaces.contains(id)) {
                preferenceHierarchy.preferredBySubject = PreferenceHierarchy.PREFFERRED_POINTS
            } else {
                preferenceHierarchy.preferredBySubject = 0
            }
        }
    }

}
