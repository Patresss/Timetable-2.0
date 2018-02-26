package com.patres.timetable.service.mapper

import com.patres.timetable.domain.Curriculum
import com.patres.timetable.domain.CurriculumList
import com.patres.timetable.domain.Interval
import com.patres.timetable.domain.Timetable
import com.patres.timetable.repository.*
import com.patres.timetable.service.dto.CurriculumDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class CurriculumMapper : EntityMapper<Curriculum, CurriculumDTO>() {

    @Autowired
    private lateinit var curriculumListRepository: CurriculumListRepository

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

    override fun toEntity(entityDto: CurriculumDTO): Curriculum {
        return Curriculum()
            .apply {
                entityDto.startTime?.let {
                    setStartTimeHHmmFormatted(it)
                }
                entityDto.endTime?.let {
                    setEndTimeHHmmFormatted(it)
                }
                type = entityDto.type
                everyWeek = entityDto.everyWeek
                startWithWeek = entityDto.startWithWeek
                entityDto.divisionId?.let { division = divisionRepository.getOne(entityDto.divisionId) }
                entityDto.teacherId?.let { teacher = teacherRepository.getOne(entityDto.teacherId) }
                entityDto.subjectId?.let { subject = subjectRepository.getOne(entityDto.subjectId) }
                entityDto.lessonId?.let { lesson = lessonRepository.getOne(entityDto.lessonId) }
                entityDto.placeId?.let { place = placeRepository.getOne(entityDto.placeId) }
                entityDto.curriculumListId?.let { curriculumList = curriculumListRepository.getOne(entityDto.curriculumListId) }
                id = entityDto.id
            }
    }

    override fun toDto(entity: Curriculum): CurriculumDTO {
        return CurriculumDTO()
            .apply {
                placeId = curriculumPlaceId(entity)
                divisionName = curriculumDivisionName(entity)
                lessonId = curriculumLessonId(entity)
                teacherSurname = curriculumTeacherSurname(entity)
                subjectId = curriculumSubjectId(entity)
                lessonName = curriculumLessonName(entity)
                teacherId = curriculumTeacherId(entity)
                divisionId = curriculumDivisionId(entity)
                placeName = curriculumPlaceName(entity)
                subjectName = curriculumSubjectName(entity)
                id = entity.id
                if (entity.lesson != null) {
                    startTime = entity.lesson?.getStartTimeHHmmFormatted()
                    endTime = entity.lesson?.getEndTimeHHmmFormatted()
                } else {
                    startTime = entity.getStartTimeHHmmFormatted()
                    endTime = entity.getEndTimeHHmmFormatted()
                }
                everyWeek = entity.everyWeek
                startWithWeek = entity.startWithWeek
                curriculumListId = curriculumListId(entity)
                curriculumListName = curriculumListName(entity)
                id = entity.id
            }
    }

    private fun curriculumListId(curriculum: Curriculum?): Long? {
        if (curriculum == null) {
            return null
        }
        val curriculumList = curriculum.curriculumList ?: return null
        return curriculumList.id
    }

    private fun curriculumPlaceId(curriculum: Curriculum?): Long? {
        if (curriculum == null) {
            return null
        }
        val place = curriculum.place ?: return null
        return place.id
    }

    private fun curriculumListName(curriculum: Curriculum?): String? {
        if (curriculum == null) {
            return null
        }
        val curriculumList = curriculum.curriculumList ?: return null
        return curriculumList.name
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

    private fun curriculumTeacherSurname(curriculum: Curriculum?): String? {
        if (curriculum == null) {
            return null
        }
        val teacher = curriculum.teacher ?: return null
        return teacher.surname
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
