package com.patres.timetable.generator

import com.patres.timetable.domain.Timetable
import com.patres.timetable.generator.report.GenerateReport
import com.patres.timetable.repository.*
import com.patres.timetable.repository.preference.PreferenceDataTimeForPlaceRepository
import com.patres.timetable.service.mapper.GenerateReportMapper
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional


@Component
open class TimetableGeneratorManager(
    private var placeRepository: PlaceRepository,
    private var teacherRepository: TeacherRepository,
    private var subjectRepository: SubjectRepository,
    private var divisionRepository: DivisionRepository,
    private var lessonRepository: LessonRepository,
    private var curriculumListRepository: CurriculumListRepository,
    private var preferenceDataTimeForPlaceRepository: PreferenceDataTimeForPlaceRepository,
    private var generateReportMapper: GenerateReportMapper) {

    @Transactional
    open fun generate(curriculumListId: Long): GenerateReport {
        val curriculumListEntity = curriculumListRepository.findOneWithEagerRelationships(curriculumListId)
        val schoolId = curriculumListEntity?.divisionOwner?.id

        val generateReport = schoolId?.let {
            val places = placeRepository.findByDivisionOwnerId(schoolId)
            val teachers = teacherRepository.findByDivisionOwnerId(schoolId)
            val subjects = subjectRepository.findByDivisionOwnerId(schoolId)
            val divisions = divisionRepository.findByDivisionOwnerId(schoolId)
            val lessons = lessonRepository.findByDivisionOwnerId(schoolId)
            val preferencesDataTimeForPlace = preferenceDataTimeForPlaceRepository.findByPlaceIdIn(places.mapNotNull { it.id }).toSet()

            val timetableGeneratorContainer = TimetableGeneratorContainer(curriculumListEntity = curriculumListEntity, places = places, teachers = teachers, subjects = subjects, divisions = divisions, lessons = lessons, preferencesDataTimeForPlace = preferencesDataTimeForPlace)
            timetableGeneratorContainer.generate()
        }
        generateReport?.timetables?.sortedBy { it.points }
        return generateReport?: GenerateReport()
    }

}
