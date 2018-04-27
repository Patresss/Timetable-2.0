package com.patres.timetable.generator

import com.patres.timetable.domain.Timetable
import com.patres.timetable.repository.*
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
    private var timetableRepository: TimetableRepository) {

    @Transactional
    open fun generate(curriculumListId: Long, generatorStrategyType: TimetableGeneratorStrategyType): List<Timetable> {
        val curriculumListEntity = curriculumListRepository.findOneWithEagerRelationships(curriculumListId)
        val schoolId = curriculumListEntity?.divisionOwner?.id

        val timetablesFromCurriculum = schoolId?.let {
            val places = placeRepository.findByDivisionOwnerId(schoolId)
            val teachers = teacherRepository.findByDivisionOwnerId(schoolId)
            val subjects = subjectRepository.findByDivisionOwnerId(schoolId)
            val divisions = divisionRepository.findByDivisionOwnerId(schoolId)
            val lessons = lessonRepository.findByDivisionOwnerId(schoolId)

            val timetableGeneratorContainer = TimetableGeneratorContainer(curriculumListEntity = curriculumListEntity, places = places, teachers = teachers, subjects = subjects, divisions = divisions, lessons = lessons)
            timetableGeneratorContainer.generate()
        }

        return timetablesFromCurriculum ?: emptyList()
    }

}
