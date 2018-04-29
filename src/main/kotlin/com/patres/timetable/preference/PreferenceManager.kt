package com.patres.timetable.preference

import com.patres.timetable.domain.*
import com.patres.timetable.domain.preference.PreferenceDataTimeForTeacher
import com.patres.timetable.repository.*
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
        preferenceDependency.teacher?.let { preference.calculateByTeacher(it) }
        preferenceDependency.subject?.let { preference.calculateBySubject(it) }
        preferenceDependency.place?.let { preference.calculateByPlace(it) }
        preferenceDependency.division?.let { preference.calculateByDivision(it) }
        preferenceDependency.division?.let { preference.calculateTooSmallPlace(getIdOfTooSmallPlacesFromDatabase(it)) }
        preference.calculateTaken(getTakenTimetableFromDatabase(preferenceDependency))

        val lessonId = preferenceDependency.lesson?.id
        val dayOfWeek = preferenceDependency.dayOfWeek
        if (lessonId != null && dayOfWeek != null) {
            val preferenceDataTimeForTeacherFromDatabase = getPreferenceDataTimeForTeacherFromDatabase(dayOfWeek, lessonId)
            preference.calculateByLessonAndDayOfWeek(preferenceDataTimeForTeacherFromDatabase)
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
