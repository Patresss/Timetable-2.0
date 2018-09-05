package com.patres.timetable.preference

import com.patres.timetable.domain.*
import com.patres.timetable.domain.preference.PreferenceDataTimeForDivision
import com.patres.timetable.domain.preference.PreferenceDataTimeForPlace
import com.patres.timetable.domain.preference.PreferenceDataTimeForSubject
import com.patres.timetable.domain.preference.PreferenceDataTimeForTeacher
import com.patres.timetable.repository.*
import com.patres.timetable.repository.preference.PreferenceDataTimeForDivisionRepository
import com.patres.timetable.repository.preference.PreferenceDataTimeForSubjectRepository
import com.patres.timetable.repository.preference.PreferenceDataTimeForPlaceRepository
import com.patres.timetable.repository.preference.PreferenceDataTimeForTeacherRepository
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
    private var preferenceDataTimeForSubjectRepository: PreferenceDataTimeForSubjectRepository,
    private var preferenceDataTimeForDivisionRepository: PreferenceDataTimeForDivisionRepository,
    private var preferenceDataTimeForPlaceRepository: PreferenceDataTimeForPlaceRepository,
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
        preference.calculatePreferences(
            teacher = preferenceDependency.teacher,
            subject = preferenceDependency.subject,
            place = preferenceDependency.place,
            division = preferenceDependency.division)
        preferenceDependency.division?.let { preference.calculateTooSmallPlace(getIdOfTooSmallPlacesFromDatabase(it)) }
        preference.calculateTaken(getTakenTimetableFromDatabase(preferenceDependency))

        val lessonId = preferenceDependency.lesson?.id
        val dayOfWeek = preferenceDependency.dayOfWeek
        if (lessonId != null && dayOfWeek != null) {
            val preferenceDataTimeForTeacher = getPreferenceDataTimeForTeacherFromDatabase(dayOfWeek, lessonId)
            val preferenceDataTimeForSubject = getPreferenceDataTimeForSubjectFromDatabase(dayOfWeek, lessonId)
            val preferenceDataTimeForDivision = getPreferenceDataTimeForDivisionFromDatabase(dayOfWeek, lessonId)
            val preferenceDataTimeForPlace = getPreferenceDataTimeForPlaceFromDatabase(dayOfWeek, lessonId)
            preference.calculateByLessonAndDayOfWeek(preferenceDataTimeForTeacher, preferenceDataTimeForSubject, preferenceDataTimeForDivision, preferenceDataTimeForPlace)
        }

        val takenTimetable = getTakenTimetableForLessonAndDayOfWeekFromDatabase(preferenceDependency)
        preferenceDependency.teacher?.let {  preference.calculateTakenLessonAndDayOfWeekByTeacher(it, takenTimetable) }
        preferenceDependency.place?.let {  preference.calculateTakenLessonAndDayOfWeekByPlace(it, takenTimetable) }
        preferenceDependency.division?.let {  preference.calculateTakenLessonAndDayOfWeekByDivision(it, takenTimetable) }
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
            val divisions = preferenceDependency.division?.calculateAllTakenDivisionFromDivision()?.mapNotNull { it.id }?.toSet()?: emptySet()

            var timetablesInThisTime = timetableRepository.findTakenByPeriod(preferenceDependency.divisionOwnerId, periodId, preferenceDependency.teacher?.id, divisions, preferenceDependency.place?.id, preferenceDependency.subject?.id)
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

    private fun getPreferenceDataTimeForDivisionFromDatabase(dayOfWeek: Int, lessonId: Long): Set<PreferenceDataTimeForDivision> {
        return preferenceDataTimeForDivisionRepository.findByDayOfWeekAndLessonId(dayOfWeek, lessonId)
    }

    private fun getPreferenceDataTimeForPlaceFromDatabase(dayOfWeek: Int, lessonId: Long): Set<PreferenceDataTimeForPlace> {
        return preferenceDataTimeForPlaceRepository.findByDayOfWeekAndLessonId(dayOfWeek, lessonId)
    }

    private fun getPreferenceDataTimeForSubjectFromDatabase(dayOfWeek: Int, lessonId: Long): Set<PreferenceDataTimeForSubject> {
        return preferenceDataTimeForSubjectRepository.findByDayOfWeekAndLessonId(dayOfWeek, lessonId)
    }



}
