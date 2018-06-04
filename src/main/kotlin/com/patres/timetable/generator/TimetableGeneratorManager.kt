package com.patres.timetable.generator

import com.patres.timetable.generator.report.GenerateReport
import com.patres.timetable.repository.*
import com.patres.timetable.repository.preference.PreferenceDataTimeForPlaceRepository
import com.patres.timetable.service.mapper.GenerateReportMapper
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import kotlin.system.measureTimeMillis


@Component
open class TimetableGeneratorManager(
    private var placeRepository: PlaceRepository,
    private var teacherRepository: TeacherRepository,
    private var subjectRepository: SubjectRepository,
    private var divisionRepository: DivisionRepository,
    private var lessonRepository: LessonRepository,
    private var curriculumListRepository: CurriculumListRepository,
    private var preferenceDataTimeForPlaceRepository: PreferenceDataTimeForPlaceRepository) {

    @Transactional
    open fun generate(curriculumListId: Long): GenerateReport {
        val generateReport: GenerateReport
        var timetableGeneratorContainer: TimetableGeneratorContainer? = null
        val calculatePreferenceTimeImMs = measureTimeMillis {
            val curriculumListEntity = curriculumListRepository.findOneWithEagerRelationships(curriculumListId)
            val schoolId = curriculumListEntity?.divisionOwner?.id

            schoolId?.let {
                val places = placeRepository.findByDivisionOwnerId(schoolId)
                val teachers = teacherRepository.findByDivisionOwnerId(schoolId)
                val subjects = subjectRepository.findByDivisionOwnerId(schoolId)
                val divisions = divisionRepository.findByDivisionOwnerId(schoolId)
                val lessons = lessonRepository.findByDivisionOwnerId(schoolId)
                val preferencesDataTimeForPlace = preferenceDataTimeForPlaceRepository.findByPlaceIdIn(places.mapNotNull { it.id }).toSet()

                timetableGeneratorContainer = TimetableGeneratorContainer(curriculumListEntity = curriculumListEntity, places = places, teachers = teachers, subjects = subjects, divisions = divisions, lessons = lessons, preferencesDataTimeForPlace = preferencesDataTimeForPlace)
            }
        }
        generateReport = timetableGeneratorContainer?.generate()?: GenerateReport()
        generateReport.calculatePreferenceTimeImMs = calculatePreferenceTimeImMs
        generateReport.timetables.sortedBy { it.points }
        return generateReport
    }

}
