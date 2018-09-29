package com.patres.timetable.service.mapper.preference.relation

import com.patres.timetable.domain.preference.PreferenceDataTimeForPlace
import com.patres.timetable.repository.LessonRepository
import com.patres.timetable.repository.PlaceRepository
import com.patres.timetable.service.dto.preference.relation.PreferenceDataTimeForPlaceDTO
import com.patres.timetable.service.mapper.EntityMapper
import com.patres.timetable.service.util.getOneOrNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class PreferenceDataTimeForPlaceMapper : EntityMapper<PreferenceDataTimeForPlace, PreferenceDataTimeForPlaceDTO>() {

    @Autowired
    private lateinit var placeRepository: PlaceRepository

    @Autowired
    private lateinit var lessonRepository: LessonRepository

    override fun toEntity(entityDto: PreferenceDataTimeForPlaceDTO): PreferenceDataTimeForPlace {
        return PreferenceDataTimeForPlace(
            place = placeRepository.getOneOrNull(entityDto.placeId),
            lesson = lessonRepository.getOneOrNull(entityDto.lessonId),
            dayOfWeek = entityDto.dayOfWeek,
            points = entityDto.points
        ).apply {
            id = entityDto.id
        }

    }

    override fun toDto(entity: PreferenceDataTimeForPlace): PreferenceDataTimeForPlaceDTO {
        return PreferenceDataTimeForPlaceDTO(
            placeId = entity.place?.id,
            placeName = entity.place?.name ?: "",
            lessonId = entity.lesson?.id,
            lessonName = entity.lesson?.name ?: "",
            dayOfWeek = entity.dayOfWeek,
            points = entity.points)
            .apply {
                id = entity.id
            }
    }

}
