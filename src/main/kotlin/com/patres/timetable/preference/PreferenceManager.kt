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
        preferenceDependency.place?.let { calculateTakenLessonAndDayOfWeekByPlace(preference, it, takenTimetable)  }
        preferenceDependency.division?.let { calculateTakenLessonAndDayOfWeekByDivision(preference, it, takenTimetable)  }
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
    }

    fun calculateByDivision(preference: Preference, division: Division, idOfTooSmallPlaces: Set<Long>) {
        calculateTeachersByDivision(preference, division)
        calculatePlacesByDivision(preference, division)
        calculateSubjectsByDivision(preference, division)
        calculateTooSmallPlace(preference, idOfTooSmallPlaces)
    }

    fun calculateBySubject(preference: Preference, subject: Subject) {
        calculateTeachersBySubject(preference, subject)
        calculateDivisionsBySubject(preference, subject)
        calculatePlacesBySubject(preference, subject)
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
            val preferenceDataTimeForTeacher = teacher.preferenceDataTimeForTeachers
                .find { teacherPreference -> teacherPreference.lesson?.id == lessonDayPreferenceElement.lessonId && teacherPreference.dayOfWeek == lessonDayPreferenceElement.dayOfWeek }
            if (preferenceDataTimeForTeacher != null) {
                lessonDayPreferenceElement.preference.preferredByTeacher = preferenceDataTimeForTeacher.points
            } else {
                lessonDayPreferenceElement.preference.preferredByTeacher = 0
            }
        }
    }

    private fun calculatePlacesByTeacher(preference: Preference, teacher: Teacher) {
        val preferredPlaces = teacher.preferredPlaces.mapNotNull { it.id }.toSet()
        preference.preferredPlaceMap.forEach { id, preferenceHierarchy ->
            if (preferredPlaces.contains(id)) {
                preferenceHierarchy.preferredByTeacher = PreferenceHierarchy.PREFERRED_POINTS
            } else {
                preferenceHierarchy.preferredByTeacher = 0
            }
        }
    }

    private fun calculateDivisionsByTeacher(preference: Preference, teacher: Teacher) {
        val preferredDivisions = teacher.preferredDivisions.mapNotNull { it.id }.toSet()
        preference.preferredDivisionMap.forEach { id, preferenceHierarchy ->
            if (preferredDivisions.contains(id)) {
                preferenceHierarchy.preferredByTeacher = PreferenceHierarchy.PREFERRED_POINTS
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
        val preferredSubjects = place.preferredSubjects.mapNotNull { it.id }.toSet()
        preference.preferredSubjectMap.forEach { id, preferenceHierarchy ->
            if (preferredSubjects.contains(id)) {
                preferenceHierarchy.preferredByPlace = PreferenceHierarchy.PREFERRED_POINTS
            } else {
                preferenceHierarchy.preferredByPlace = 0
            }
        }
    }

    private fun calculateDivisionsByPlace(preference: Preference, place: Place) {
        val preferredDivisions = place.preferredDivisions.mapNotNull { it.id }.toSet()
        preference.preferredDivisionMap.forEach { id, preferenceHierarchy ->
            if (preferredDivisions.contains(id)) {
                preferenceHierarchy.preferredByPlace = PreferenceHierarchy.PREFERRED_POINTS
            } else {
                preferenceHierarchy.preferredByPlace = 0
            }
        }
    }

    private fun calculateTeachersByPlace(preference: Preference, place: Place) {
        val preferredTeachers = place.preferredTeachers.mapNotNull { it.id }.toSet()
        preference.preferredTeacherMap.forEach { id, preferenceHierarchy ->
            if (preferredTeachers.contains(id)) {
                preferenceHierarchy.preferredByPlace = PreferenceHierarchy.PREFERRED_POINTS
            } else {
                preferenceHierarchy.preferredByPlace = 0
            }
        }
    }

    private fun calculateSubjectsByDivision(preference: Preference, division: Division) {
        val preferredSubjects = division.preferredSubjects.mapNotNull { it.id }.toSet()
        preference.preferredSubjectMap.forEach { id, preferenceHierarchy ->
            if (preferredSubjects.contains(id)) {
                preferenceHierarchy.preferredByDivision = PreferenceHierarchy.PREFERRED_POINTS
            } else {
                preferenceHierarchy.preferredByDivision = 0
            }
        }
    }

    private fun calculatePlacesByDivision(preference: Preference, division: Division) {
        val preferredPlaces = division.preferredPlaces.mapNotNull { it.id }.toSet()
        preference.preferredPlaceMap.forEach { id, preferenceHierarchy ->
            if (preferredPlaces.contains(id)) {
                preferenceHierarchy.preferredByDivision = PreferenceHierarchy.PREFERRED_POINTS
            } else {
                preferenceHierarchy.preferredByDivision = 0
            }
        }
    }

    private fun calculateTeachersByDivision(preference: Preference, division: Division) {
        val preferredTeachers = division.preferredTeachers.mapNotNull { it.id }.toSet()
        preference.preferredTeacherMap.forEach { id, preferenceHierarchy ->
            if (preferredTeachers.contains(id)) {
                preferenceHierarchy.preferredByDivision = PreferenceHierarchy.PREFERRED_POINTS
            } else {
                preferenceHierarchy.preferredByDivision = 0
            }
        }
    }

    private fun calculatePlacesBySubject(preference: Preference, subject: Subject) {
        val preferredPlaces = subject.preferredPlaces.mapNotNull { it.id }.toSet()
        preference.preferredPlaceMap.forEach { id, preferenceHierarchy ->
            if (preferredPlaces.contains(id)) {
                preferenceHierarchy.preferredBySubject = PreferenceHierarchy.PREFERRED_POINTS
            } else {
                preferenceHierarchy.preferredBySubject = 0
            }
        }
    }

    private fun calculateDivisionsBySubject(preference: Preference, subject: Subject) {
        val preferredDivisions = subject.preferredDivisions.mapNotNull { it.id }.toSet()
        preference.preferredDivisionMap.forEach { id, preferenceHierarchy ->
            if (preferredDivisions.contains(id)) {
                preferenceHierarchy.preferredBySubject = PreferenceHierarchy.PREFERRED_POINTS
            } else {
                preferenceHierarchy.preferredBySubject = 0
            }
        }
    }

    private fun calculateTeachersBySubject(preference: Preference, subject: Subject) {
        val preferredTeachers = subject.preferenceSubjectByTeacher.mapNotNull { it.id }.toSet()
        preference.preferredTeacherMap.forEach { id, preferenceHierarchy ->
            if (preferredTeachers.contains(id)) {
                preferenceHierarchy.preferredBySubject = PreferenceHierarchy.PREFERRED_POINTS
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
