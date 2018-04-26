package com.patres.timetable.service.mapper.preference

import com.patres.timetable.domain.preference.PreferenceDataTimeForDivision
import com.patres.timetable.repository.LessonRepository
import com.patres.timetable.repository.DivisionRepository
import com.patres.timetable.service.dto.preference.PreferenceDataTimeForDivisionDTO
import com.patres.timetable.service.mapper.EntityMapper
import com.patres.timetable.service.util.getOneOrNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class PreferenceDataTimeForDivisionMapper : EntityMapper<PreferenceDataTimeForDivision, PreferenceDataTimeForDivisionDTO>() {

    @Autowired
    private lateinit var divisionRepository: DivisionRepository

    @Autowired
    private lateinit var lessonRepository: LessonRepository

    override fun toEntity(entityDto: PreferenceDataTimeForDivisionDTO): PreferenceDataTimeForDivision {
        return PreferenceDataTimeForDivision(
            division = divisionRepository.getOneOrNull(entityDto.divisionId),
            lesson = lessonRepository.getOneOrNull(entityDto.lessonId),
            dayOfWeek = entityDto.dayOfWeek,
            points = entityDto.points
        ).apply {
            id = entityDto.id
        }

    }

    override fun toDto(entity: PreferenceDataTimeForDivision): PreferenceDataTimeForDivisionDTO {
        return PreferenceDataTimeForDivisionDTO(
            divisionId = entity.division?.id,
            divisionName = entity.division?.name ?: "",
            lessonId = entity.lesson?.id,
            lessonName = entity.lesson?.name ?: "",
            dayOfWeek = entity.dayOfWeek,
            points = entity.points)
            .apply {
                id = entity.id
            }
    }

}
