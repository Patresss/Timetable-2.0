package com.patres.timetable.service.mapper.preference

import com.patres.timetable.domain.preference.PreferenceDataTimeForSubject
import com.patres.timetable.repository.LessonRepository
import com.patres.timetable.repository.SubjectRepository
import com.patres.timetable.service.dto.preference.PreferenceDataTimeForSubjectDTO
import com.patres.timetable.service.mapper.EntityMapper
import com.patres.timetable.service.util.getOneOrNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class PreferenceDataTimeForSubjectMapper : EntityMapper<PreferenceDataTimeForSubject, PreferenceDataTimeForSubjectDTO>() {

    @Autowired
    private lateinit var subjectRepository: SubjectRepository

    @Autowired
    private lateinit var lessonRepository: LessonRepository

    override fun toEntity(entityDto: PreferenceDataTimeForSubjectDTO): PreferenceDataTimeForSubject {
        return PreferenceDataTimeForSubject(
            subject = subjectRepository.getOneOrNull(entityDto.subjectId),
            lesson = lessonRepository.getOneOrNull(entityDto.lessonId),
            dayOfWeek = entityDto.dayOfWeek,
            points = entityDto.points
        ).apply {
            id = entityDto.id
        }

    }

    override fun toDto(entity: PreferenceDataTimeForSubject): PreferenceDataTimeForSubjectDTO {
        return PreferenceDataTimeForSubjectDTO(
            subjectId = entity.subject?.id,
            subjectName = entity.subject?.name ?: "",
            lessonId = entity.lesson?.id,
            lessonName = entity.lesson?.name ?: "",
            dayOfWeek = entity.dayOfWeek,
            points = entity.points)
            .apply {
                id = entity.id
            }
    }

}
