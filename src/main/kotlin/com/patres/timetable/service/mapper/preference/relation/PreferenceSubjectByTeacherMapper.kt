package com.patres.timetable.service.mapper.preference.relation

import com.patres.timetable.domain.preference.PreferenceSubjectByTeacher
import com.patres.timetable.repository.SubjectRepository
import com.patres.timetable.repository.TeacherRepository
import com.patres.timetable.service.dto.preference.relation.PreferenceSubjectByTeacherDTO
import com.patres.timetable.service.mapper.EntityMapper
import com.patres.timetable.service.util.getOneOrNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class PreferenceSubjectByTeacherMapper : EntityMapper<PreferenceSubjectByTeacher, PreferenceSubjectByTeacherDTO>() {

    @Autowired
    private lateinit var teacherRepository: TeacherRepository

    @Autowired
    private lateinit var subjectRepository: SubjectRepository

    override fun toEntity(entityDto: PreferenceSubjectByTeacherDTO): PreferenceSubjectByTeacher {
        return PreferenceSubjectByTeacher(
            teacher = teacherRepository.getOneOrNull(entityDto.teacherId),
            subject = subjectRepository.getOneOrNull(entityDto.subjectId),
            points = entityDto.points
        ).apply {
            id = entityDto.id
        }

    }

    override fun toDto(entity: PreferenceSubjectByTeacher): PreferenceSubjectByTeacherDTO {
        return PreferenceSubjectByTeacherDTO(
            teacherId = entity.teacher?.id,
            teacherFullName = entity.teacher?.getFullName() ?: "",
            subjectId = entity.subject?.id,
            subjectName = entity.subject?.name ?: "",
            points = entity.points)
            .apply {
                id = entity.id
            }
    }

}
