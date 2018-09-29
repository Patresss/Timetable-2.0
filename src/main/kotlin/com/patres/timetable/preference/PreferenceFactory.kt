package com.patres.timetable.preference

import com.patres.timetable.domain.Division
import com.patres.timetable.domain.Timetable
import com.patres.timetable.domain.preference.PreferenceDataTimeForDivision
import com.patres.timetable.domain.preference.PreferenceDataTimeForPlace
import com.patres.timetable.domain.preference.PreferenceDataTimeForSubject
import com.patres.timetable.domain.preference.PreferenceDataTimeForTeacher
import com.patres.timetable.preference.container.PreferenceContainer
import com.patres.timetable.preference.container.global.PreferenceContainerForGlobal
import com.patres.timetable.preference.container.single.PreferenceContainerForSingle
import com.patres.timetable.repository.*
import com.patres.timetable.repository.preference.PreferenceDataTimeForDivisionRepository
import com.patres.timetable.repository.preference.PreferenceDataTimeForPlaceRepository
import com.patres.timetable.repository.preference.PreferenceDataTimeForSubjectRepository
import com.patres.timetable.repository.preference.PreferenceDataTimeForTeacherRepository
import com.patres.timetable.service.dto.preference.PreferenceContainerDTO
import com.patres.timetable.service.mapper.preference.PreferenceContainerMapper
import com.patres.timetable.web.rest.util.TimetableDateUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.DayOfWeek

@Component
open class PreferenceFactory(
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

    @Autowired
    private lateinit var preferenceContainerMapper: PreferenceContainerMapper

    fun createSinglePreference(divisionOwnerId: Long, preferenceDependency: PreferenceDependency): PreferenceContainerDTO {
        val preferenceContainer = PreferenceContainerForSingle(
            placesId = placeRepository.findIdByDivisionOwnerId(divisionOwnerId),
            teachersId = teacherRepository.findIdByDivisionOwnerId(divisionOwnerId),
            divisionsId = divisionRepository.findIdByDivisionOwnerId(divisionOwnerId),
            subjectsId = subjectRepository.findIdByDivisionOwnerId(divisionOwnerId),
            lessonsId = lessonRepository.findIdByDivisionOwnerId(divisionOwnerId)
        )
        preferenceContainer.apply {
            selectTeacher = preferenceDependency.teacher
            selectSubject = preferenceDependency.subject
            selectPlace = preferenceDependency.place
            selectDivision = preferenceDependency.division
            selectLesson = preferenceDependency.lesson
            selectDayOfWeek = preferenceDependency.dayOfWeek?.let { DayOfWeek.of(it) }
            tooSmallPlaceId = preferenceDependency.division?.let { getIdOfTooSmallPlacesFromDatabase(it) } ?: emptySet()
            takenTimetables = getTakenTimetableFromDatabase(preferenceDependency)
        }
        val lessonId = preferenceDependency.lesson?.id
        val dayOfWeek = preferenceDependency.dayOfWeek
        if (lessonId != null && dayOfWeek != null) {
            preferenceContainer.apply {
                preferenceDateTimeForTeacher = getPreferenceDataTimeForTeacherFromDatabase(dayOfWeek, lessonId)
                preferenceDateTimeForSubject = getPreferenceDataTimeForSubjectFromDatabase(dayOfWeek, lessonId)
                preferenceDateTimeForDivision = getPreferenceDataTimeForDivisionFromDatabase(dayOfWeek, lessonId)
                preferenceDateTimeForPlace = getPreferenceDataTimeForPlaceFromDatabase(dayOfWeek, lessonId)
            }
        }
        return preferenceContainerMapper.toDto(preferenceContainer)
    }

    fun createGlobalPreference(divisionOwnerId: Long, preferenceDependency: PreferenceDependency, takeDataFromGlobalSchool: Boolean): PreferenceContainerDTO {
        val schoolDataToPreference = SchoolDataToPreference(
            divisionOwnerId = divisionOwnerId,
            placesId = placeRepository.findIdByDivisionOwnerId(divisionOwnerId),
            teachersId = teacherRepository.findIdByDivisionOwnerId(divisionOwnerId),
            divisionsId = divisionRepository.findIdByDivisionOwnerId(divisionOwnerId),
            subjectsId = subjectRepository.findIdByDivisionOwnerId(divisionOwnerId),
            lessonsId = lessonRepository.findIdByDivisionOwnerId(divisionOwnerId),

            places = placeRepository.findByDivisionOwnerId(divisionOwnerId),

            preferenceDateTimeForTeacher = getPreferenceDateTimeForTeacherFromDatabase(),
            preferenceDateTimeForSubject = getPreferenceDateTimeForSubjectFromDatabase(),
            preferenceDateTimeForDivision = getPreferenceDateTimeForDivisionFromDatabase(),
            preferenceDateTimeForPlace = getPreferenceDateTimeForPlaceFromDatabase()
        )

        val preferenceContainer = PreferenceContainerForGlobal(schoolDataToPreference)
        preferenceContainer.apply {
            selectTeacher = preferenceDependency.teacher
            selectSubject = preferenceDependency.subject
            selectPlace = preferenceDependency.place
            selectDivision = preferenceDependency.division
            selectLesson = preferenceDependency.lesson
            selectDayOfWeek = preferenceDependency.dayOfWeek?.let { DayOfWeek.of(it) }
        }

        return preferenceContainerMapper.toDto(preferenceContainer)
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


    private fun getPreferenceDateTimeForTeacherFromDatabase(): Set<PreferenceDataTimeForTeacher> {
        return preferenceDataTimeForTeacherRepository.findAll().toSet()
    }

    private fun getPreferenceDateTimeForDivisionFromDatabase(): Set<PreferenceDataTimeForDivision> {
        return preferenceDataTimeForDivisionRepository.findAll().toSet()
    }

    private fun getPreferenceDateTimeForPlaceFromDatabase(): Set<PreferenceDataTimeForPlace> {
        return preferenceDataTimeForPlaceRepository.findAll().toSet()
    }

    private fun getPreferenceDateTimeForSubjectFromDatabase(): Set<PreferenceDataTimeForSubject> {
        return preferenceDataTimeForSubjectRepository.findAll().toSet()
    }

}
