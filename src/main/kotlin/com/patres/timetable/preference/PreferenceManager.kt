package com.patres.timetable.preference

import com.patres.timetable.domain.*
import com.patres.timetable.domain.preference.PreferenceDataTimeForTeacher
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
    private var preferenceDataTimeForTeacherRepository: PreferenceDataTimeForTeacherRepository,
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

        val lessonId = preferenceDependency.lesson?.id
        val dayOfWeek = preferenceDependency.dayOfWeek
        if (lessonId != null && dayOfWeek != null) {
            val preferenceDataTimeForTeacherFromDatabase = getPreferenceDataTimeForTeacherFromDatabase(dayOfWeek, lessonId)
            calculateByLessonAndDayOfWeek(preference, preferenceDataTimeForTeacherFromDatabase)
        }

        val takenTimetable = getTakenTimetableForLessonAndDayOfWeekFromDatabase(preferenceDependency)
        preferenceDependency.teacher?.let { calculateTakenLessonAndDayOfWeekByTeacher(preference, it, takenTimetable) }
        preferenceDependency.place?.let { calculateTakenLessonAndDayOfWeekByPlace(preference, it, takenTimetable) }
        preferenceDependency.division?.let { calculateTakenLessonAndDayOfWeekByDivision(preference, it, takenTimetable) }
    }

    fun calculateTaken(preference: Preference, takenTimetable: Set<Timetable>) {
        val takenPlacesId = takenTimetable.mapNotNull { it.place?.id }.toSet()
        takenPlacesId.forEach { preference.preferredPlaceMap[it]?.taken = PreferenceHierarchy.TAKEN }

        val takenTeachersId = takenTimetable.mapNotNull { it.teacher?.id }.toSet()
        takenTeachersId.forEach { preference.preferredTeacherMap[it]?.taken = PreferenceHierarchy.TAKEN }

        val takenDivisionsId = takenTimetable.mapNotNull { it.division?.id }.toSet()
        takenDivisionsId.forEach { preference.preferredDivisionMap[it]?.taken = PreferenceHierarchy.TAKEN }
    }

    fun calculateTakenLessonAndDayOfWeekByTeacher(preference: Preference, teacher: Teacher, takenTimetable: Set<Timetable>) {
        preference.preferredLessonAndDayOfWeekSet.forEach { preferredLessonAndDayOfWeek ->
            val isTaken = takenTimetable.any { timetable -> timetable.dayOfWeek == preferredLessonAndDayOfWeek.dayOfWeek && timetable.lesson?.id == preferredLessonAndDayOfWeek.lessonId && timetable.teacher?.id == teacher.id }
            if (isTaken) {
                preferredLessonAndDayOfWeek.preference.takenByTeacher = PreferenceHierarchy.TAKEN
            }
        }
    }

    fun calculateTakenLessonAndDayOfWeekByPlace(preference: Preference, place: Place, takenTimetable: Set<Timetable>) {
        preference.preferredLessonAndDayOfWeekSet.forEach { preferredLessonAndDayOfWeek ->
            val isTaken = takenTimetable.any { timetable -> timetable.dayOfWeek == preferredLessonAndDayOfWeek.dayOfWeek && timetable.lesson?.id == preferredLessonAndDayOfWeek.lessonId && timetable.place?.id == place.id }
            if (isTaken) {
                preferredLessonAndDayOfWeek.preference.takenByPlace = PreferenceHierarchy.TAKEN
            }
        }
    }

    fun calculateTakenLessonAndDayOfWeekByDivision(preference: Preference, division: Division, takenTimetable: Set<Timetable>) {
        preference.preferredLessonAndDayOfWeekSet.forEach { preferredLessonAndDayOfWeek ->
            val isTaken = takenTimetable.any { timetable -> timetable.dayOfWeek == preferredLessonAndDayOfWeek.dayOfWeek && timetable.lesson?.id == preferredLessonAndDayOfWeek.lessonId && timetable.division?.id == division.id }
            if (isTaken) {
                preferredLessonAndDayOfWeek.preference.takenByDivision = PreferenceHierarchy.TAKEN
            }
        }
    }

    fun calculateByTeacher(preference: Preference, teacher: Teacher) {
        calculateSubjectsByTeacher(preference, teacher)
        calculateDivisionsByTeacher(preference, teacher)
        calculatePlacesByTeacher(preference, teacher)
        calculateLessonAndDayOfWeekByTeacher(preference, teacher)
    }

    fun calculateByPlace(preference: Preference, place: Place) {
        calculateTeachersByPlace(preference, place)
        calculateDivisionsByPlace(preference, place)
        calculateSubjectsByPlace(preference, place)
        calculateLessonAndDayOfWeekByPlace(preference, place)
    }

    fun calculateByDivision(preference: Preference, division: Division, idOfTooSmallPlaces: Set<Long>) {
        calculateTeachersByDivision(preference, division)
        calculatePlacesByDivision(preference, division)
        calculateSubjectsByDivision(preference, division)
        calculateTooSmallPlace(preference, idOfTooSmallPlaces)
        calculateLessonAndDayOfWeekByDivision(preference, division)
    }

    fun calculateBySubject(preference: Preference, subject: Subject) {
        calculateTeachersBySubject(preference, subject)
        calculateDivisionsBySubject(preference, subject)
        calculatePlacesBySubject(preference, subject)
        calculateLessonAndDayOfWeekBySubject(preference, subject)
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

    fun calculateByLessonAndDayOfWeek(preference: Preference, preferenceDataTimeForTeachers: Set<PreferenceDataTimeForTeacher>) {
        preference.preferredTeacherMap.forEach { id, preferenceHierarchy ->
            val preferenceDataTimeForTeacher = preferenceDataTimeForTeachers.find { it.teacher?.id == id }
            if (preferenceDataTimeForTeacher != null) {
                preferenceHierarchy.preferredByDataTime = preferenceDataTimeForTeacher.points
            } else {
                preferenceHierarchy.preferredByDataTime = 0
            }
        }
    }


    private fun calculateLessonAndDayOfWeekByTeacher(preference: Preference, teacher: Teacher) {
        preference.preferredLessonAndDayOfWeekSet.forEach { lessonDayPreferenceElement ->
            val preferenceDataTime = teacher.preferenceDataTimeForTeachers
                .find { preference -> preference.lesson?.id == lessonDayPreferenceElement.lessonId && preference.dayOfWeek == lessonDayPreferenceElement.dayOfWeek }
            if (preferenceDataTime != null) {
                lessonDayPreferenceElement.preference.preferredByTeacher = preferenceDataTime.points
            } else {
                lessonDayPreferenceElement.preference.preferredByTeacher = 0
            }
        }
    }

    private fun calculateLessonAndDayOfWeekByPlace(preference: Preference, place: Place) {
        preference.preferredLessonAndDayOfWeekSet.forEach { lessonDayPreferenceElement ->
            val preferenceDataTime = place.preferencesDataTimeForPlace
                .find { preference -> preference.lesson?.id == lessonDayPreferenceElement.lessonId && preference.dayOfWeek == lessonDayPreferenceElement.dayOfWeek }
            if (preferenceDataTime != null) {
                lessonDayPreferenceElement.preference.preferredByPlace = preferenceDataTime.points
            } else {
                lessonDayPreferenceElement.preference.preferredByPlace = 0
            }
        }
    }


    private fun calculateLessonAndDayOfWeekByDivision(preference: Preference, division: Division) {
        preference.preferredLessonAndDayOfWeekSet.forEach { lessonDayPreferenceElement ->
            val preferenceDataTime = division.preferencesDataTimeForDivision
                .find { preference -> preference.lesson?.id == lessonDayPreferenceElement.lessonId && preference.dayOfWeek == lessonDayPreferenceElement.dayOfWeek }
            if (preferenceDataTime != null) {
                lessonDayPreferenceElement.preference.preferredByDivision = preferenceDataTime.points
            } else {
                lessonDayPreferenceElement.preference.preferredByDivision = 0
            }
        }
    }

    private fun calculateLessonAndDayOfWeekBySubject(preference: Preference, subject: Subject) {
        preference.preferredLessonAndDayOfWeekSet.forEach { lessonDayPreferenceElement ->
            val preferenceDataTime = subject.preferencesDataTimeForSubject
                .find { preference -> preference.lesson?.id == lessonDayPreferenceElement.lessonId && preference.dayOfWeek == lessonDayPreferenceElement.dayOfWeek }
            if (preferenceDataTime != null) {
                lessonDayPreferenceElement.preference.preferredBySubject = preferenceDataTime.points
            } else {
                lessonDayPreferenceElement.preference.preferredBySubject = 0
            }
        }
    }


    private fun calculatePlacesByTeacher(preference: Preference, teacher: Teacher) {
        preference.preferredPlaceMap.forEach { id, preferenceHierarchy ->
            val preferredPlaces = teacher.preferenceTeacherByPlace.find { it.place?.id == id }
            if (preferredPlaces != null) {
                preferenceHierarchy.preferredByTeacher = preferredPlaces.points
            } else {
                preferenceHierarchy.preferredByTeacher = 0
            }
        }
    }

    private fun calculateDivisionsByTeacher(preference: Preference, teacher: Teacher) {
        preference.preferredDivisionMap.forEach { id, preferenceHierarchy ->
            val preferredDivisions = teacher.preferencesTeacherByDivision.find { it.division?.id == id }
            if (preferredDivisions != null) {
                preferenceHierarchy.preferredByTeacher = preferredDivisions.points
            } else {
                preferenceHierarchy.preferredByTeacher = 0
            }
        }
    }

    private fun calculateSubjectsByTeacher(preference: Preference, teacher: Teacher) {
        preference.preferredSubjectMap.forEach { id, preferenceHierarchy ->
            val preferenceSubject = teacher.preferenceSubjectByTeacher.find { it.subject?.id == id }
            if (preferenceSubject != null) {
                preferenceHierarchy.preferredByTeacher = preferenceSubject.points
            } else {
                preferenceHierarchy.preferredByTeacher = 0
            }
        }
    }

    private fun calculateSubjectsByPlace(preference: Preference, place: Place) {
        preference.preferredSubjectMap.forEach { id, preferenceHierarchy ->
            val preferenceSubject = place.preferenceSubjectByPlace.find { it.subject?.id == id }
            if (preferenceSubject != null) {
                preferenceHierarchy.preferredByPlace = preferenceSubject.points
            } else {
                preferenceHierarchy.preferredByPlace = 0
            }
        }
    }

    private fun calculateDivisionsByPlace(preference: Preference, place: Place) {
        preference.preferredDivisionMap.forEach { id, preferenceHierarchy ->
            val preferenceDivision = place.preferenceDivisionByPlace.find { it.division?.id == id }
            if (preferenceDivision != null) {
                preferenceHierarchy.preferredByPlace = preferenceDivision.points
            } else {
                preferenceHierarchy.preferredByPlace = 0
            }
        }
    }

    private fun calculateTeachersByPlace(preference: Preference, place: Place) {
        preference.preferredTeacherMap.forEach { id, preferenceHierarchy ->
            val preferenceTeacher = place.preferenceTeacherByPlace.find { it.teacher?.id == id }
            if (preferenceTeacher != null) {
                preferenceHierarchy.preferredByPlace = preferenceTeacher.points
            } else {
                preferenceHierarchy.preferredByPlace = 0
            }
        }
    }

    private fun calculateSubjectsByDivision(preference: Preference, division: Division) {
        preference.preferredSubjectMap.forEach { id, preferenceHierarchy ->
            val preferredSubjects = division.preferencesSubjectByDivision.find { it.subject?.id == id }
            if (preferredSubjects != null) {
                preferenceHierarchy.preferredByDivision = preferredSubjects.points
            } else {
                preferenceHierarchy.preferredByDivision = 0
            }
        }
    }

    private fun calculatePlacesByDivision(preference: Preference, division: Division) {
        preference.preferredPlaceMap.forEach { id, preferenceHierarchy ->
            val preferredPlaces = division.preferenceDivisionByPlace.find { it.place?.id == id }
            if (preferredPlaces != null) {
                preferenceHierarchy.preferredByDivision = preferredPlaces.points
            } else {
                preferenceHierarchy.preferredByDivision = 0
            }
        }
    }

    private fun calculateTeachersByDivision(preference: Preference, division: Division) {
        preference.preferredTeacherMap.forEach { id, preferenceHierarchy ->
            val preferredTeachers = division.preferencesTeacherByDivision.find { it.teacher?.id == id }
            if (preferredTeachers != null) {
                preferenceHierarchy.preferredByDivision = preferredTeachers.points
            } else {
                preferenceHierarchy.preferredByDivision = 0
            }
        }
    }

    private fun calculatePlacesBySubject(preference: Preference, subject: Subject) {
        preference.preferredPlaceMap.forEach { id, preferenceHierarchy ->
            val preferredPlaces = subject.preferenceSubjectByPlace.find { it.place?.id == id }
            if (preferredPlaces != null) {
                preferenceHierarchy.preferredBySubject = preferredPlaces.points
            } else {
                preferenceHierarchy.preferredBySubject = 0
            }
        }
    }

    private fun calculateDivisionsBySubject(preference: Preference, subject: Subject) {
        preference.preferredDivisionMap.forEach { id, preferenceHierarchy ->
            val preferredDivisions = subject.preferencesSubjectByDivision.find { it.division?.id == id }
            if (preferredDivisions != null) {
                preferenceHierarchy.preferredBySubject = preferredDivisions.points
            } else {
                preferenceHierarchy.preferredBySubject = 0
            }
        }
    }

    private fun calculateTeachersBySubject(preference: Preference, subject: Subject) {
        preference.preferredTeacherMap.forEach { id, preferenceHierarchy ->
            val preferredTeachers = subject.preferenceSubjectByTeacher.find { it.teacher?.id == id }
            if (preferredTeachers != null) {
                preferenceHierarchy.preferredBySubject = preferredTeachers.points
            } else {
                preferenceHierarchy.preferredBySubject = 0
            }
        }
    }


    private fun getIdOfTooSmallPlacesFromDatabase(division: Division): Set<Long> {
        division.numberOfPeople?.let { numberOfPeople ->
            return placeRepository.findIdByDivisionOwnerIdAndNumberOfSeatsLessThan(division.divisionOwner?.id, numberOfPeople)
        }
        return emptySet()
    }

    private fun getTakenTimetableFromDatabase(preferenceDependency: PreferenceDependency): Set<Timetable> {
        val dates = TimetableDateUtil.getAllDatesByPreferenceDependency(preferenceDependency)
        var timetablesInThisTime = timetableRepository.findTakenTimetable(preferenceDependency, dates)
        if (preferenceDependency.notTimetableId != null) {
            timetablesInThisTime = timetablesInThisTime.filter { it.id != preferenceDependency.notTimetableId }.toSet()
        }
        return timetablesInThisTime.filter { it.period == null || TimetableDateUtil.canAddByEveryDays(dates, it.period?.getFirstDay(), it.startWithWeek, it.everyWeek) }.toSet()
    }

    private fun getTakenTimetableForLessonAndDayOfWeekFromDatabase(preferenceDependency: PreferenceDependency): Set<Timetable> {
        preferenceDependency.period?.id?.let { periodId ->
            val dates = TimetableDateUtil.getAllDatesByPreferenceDependency(preferenceDependency)
            var timetablesInThisTime = timetableRepository.findTakenByPeriod(preferenceDependency.divisionOwnerId, periodId, preferenceDependency.teacher?.id, preferenceDependency.division?.id, preferenceDependency.place?.id, preferenceDependency.subject?.id)
            if (preferenceDependency.notTimetableId != null) {
                timetablesInThisTime = timetablesInThisTime.filter { it.id != preferenceDependency.notTimetableId }.toSet()
            }
            return timetablesInThisTime.filter { it.period == null || TimetableDateUtil.canAddByEveryDays(dates, it.period?.getFirstDay(), it.startWithWeek, it.everyWeek) }.toSet()
        }
        return emptySet()
    }

    private fun getPreferenceDataTimeForTeacherFromDatabase(dayOfWeek: Int, lessonId: Long): Set<PreferenceDataTimeForTeacher> {
        return preferenceDataTimeForTeacherRepository.findByDayOfWeekAndLessonId(dayOfWeek, lessonId)
    }


}
