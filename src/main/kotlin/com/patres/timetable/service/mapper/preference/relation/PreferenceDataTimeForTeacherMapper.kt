package com.patres.timetable.service.mapper.preference.relation

import com.patres.timetable.domain.preference.PreferenceDataTimeForTeacher
import com.patres.timetable.repository.LessonRepository
import com.patres.timetable.repository.TeacherRepository
import com.patres.timetable.service.dto.preference.relation.PreferenceDataTimeForTeacherDTO
import com.patres.timetable.service.mapper.EntityMapper
import com.patres.timetable.service.util.getOneOrNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class PreferenceDataTimeForTeacherMapper : EntityMapper<PreferenceDataTimeForTeacher, PreferenceDataTimeForTeacherDTO>() {

    @Autowired
    private lateinit var teacherRepository: TeacherRepository

    @Autowired
    private lateinit var lessonRepository: LessonRepository

    override fun toEntity(entityDto: PreferenceDataTimeForTeacherDTO): PreferenceDataTimeForTeacher {
        return PreferenceDataTimeForTeacher(
            teacher = teacherRepository.getOneOrNull(entityDto.teacherId),
            lesson = lessonRepository.getOneOrNull(entityDto.lessonId),
            dayOfWeek = entityDto.dayOfWeek,
            points = entityDto.points
        ).apply {
            id = entityDto.id
        }

    }

    override fun toDto(entity: PreferenceDataTimeForTeacher): PreferenceDataTimeForTeacherDTO {
        return PreferenceDataTimeForTeacherDTO(
            teacherId = entity.teacher?.id,
            teacherFullName = entity.teacher?.getFullName() ?: "",
            lessonId = entity.lesson?.id,
            lessonName = entity.lesson?.name ?: "",
            dayOfWeek = entity.dayOfWeek,
            points = entity.points)
            .apply {
                id = entity.id
            }
    }

}
