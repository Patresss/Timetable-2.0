package com.patres.timetable.service.mapper

import com.patres.timetable.domain.Curriculum
import com.patres.timetable.repository.*
import com.patres.timetable.service.dto.CurriculumDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class CurriculumMapper : EntityMapper<Curriculum, CurriculumDTO>() {

    @Autowired
    private lateinit var divisionRepository: DivisionRepository

    @Autowired
    private lateinit var teacherRepository: TeacherRepository

    @Autowired
    private lateinit var subjectRepository: SubjectRepository

    @Autowired
    private lateinit var lessonRepository: LessonRepository

    @Autowired
    private lateinit var placeRepository: PlaceRepository

    @Autowired
    private lateinit var divisionMapper: DivisionMapper

    override fun toEntity(entityDto: CurriculumDTO): Curriculum {
        return Curriculum()
            .apply {
                type = entityDto.type
                numberOfActivities = entityDto.numberOfActivities
                everyWeek = entityDto.everyWeek
                startWithWeek = entityDto.startWithWeek
                entityDto.divisionId?.let {
                    division = divisionRepository.getOne(entityDto.divisionId)
                    divisionOwner = division?.divisionOwner
                }
                entityDto.teacherId?.let { teacher = teacherRepository.getOne(entityDto.teacherId) }
                entityDto.subjectId?.let { subject = subjectRepository.getOne(entityDto.subjectId) }
                entityDto.lessonId?.let { lesson = lessonRepository.getOne(entityDto.lessonId) }
                entityDto.placeId?.let { place = placeRepository.getOne(entityDto.placeId) }
                id = entityDto.id
            }
    }

    override fun toDto(entity: Curriculum): CurriculumDTO {
        return CurriculumDTO()
            .apply {
                divisionName = curriculumDivisionName(entity)
                lessonId = curriculumLessonId(entity)
                teacherFullName = entity.teacher?.getFullName()
                subjectId = curriculumSubjectId(entity)
                subjectName = curriculumSubjectName(entity)
                lessonId = curriculumLessonId(entity)
                lessonName = curriculumLessonName(entity)
                teacherId = curriculumTeacherId(entity)
                teacherFullName = entity.teacher?.getFullName()
                divisionId = curriculumDivisionId(entity)
                divisionName = curriculumDivisionName(entity)
                placeId = curriculumPlaceId(entity)
                placeName = curriculumPlaceName(entity)
                id = entity.id
                numberOfActivities = entity.numberOfActivities
                everyWeek = entity.everyWeek
                startWithWeek = entity.startWithWeek
                divisionOwnerName = divisionMapper.getDivisionOwnerName(entity.divisionOwner)
                divisionOwnerId = divisionMapper.getDivisionOwnerId(entity.divisionOwner)
                name = entity.name?: "D: $divisionName  | S: $subjectName  | T: $teacherFullName  | ID: $id"
            }
    }

    private fun curriculumPlaceId(curriculum: Curriculum?): Long? {
        if (curriculum == null) {
            return null
        }
        val place = curriculum.place ?: return null
        return place.id
    }

    private fun curriculumDivisionName(curriculum: Curriculum?): String? {
        if (curriculum == null) {
            return null
        }
        val division = curriculum.division ?: return null
        return division.name
    }

    private fun curriculumLessonId(curriculum: Curriculum?): Long? {
        if (curriculum == null) {
            return null
        }
        val lesson = curriculum.lesson ?: return null
        return lesson.id
    }


    private fun curriculumSubjectId(curriculum: Curriculum?): Long? {
        if (curriculum == null) {
            return null
        }
        val subject = curriculum.subject ?: return null
        return subject.id
    }

    private fun curriculumLessonName(curriculum: Curriculum?): String? {
        if (curriculum == null) {
            return null
        }
        val lesson = curriculum.lesson ?: return null
        return lesson.name
    }

    private fun curriculumTeacherId(curriculum: Curriculum?): Long? {
        if (curriculum == null) {
            return null
        }
        val teacher = curriculum.teacher ?: return null
        return teacher.id
    }

    private fun curriculumDivisionId(curriculum: Curriculum?): Long? {
        if (curriculum == null) {
            return null
        }
        val division = curriculum.division ?: return null
        return division.id
    }

    private fun curriculumPlaceName(curriculum: Curriculum?): String? {
        if (curriculum == null) {
            return null
        }
        val place = curriculum.place ?: return null
        return place.name
    }

    private fun curriculumSubjectName(curriculum: Curriculum?): String? {
        if (curriculum == null) {
            return null
        }
        val subject = curriculum.subject ?: return null
        return subject.name
    }
}
