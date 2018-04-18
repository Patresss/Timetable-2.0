package com.patres.timetable.service.mapper.preference

import com.patres.timetable.domain.preference.PreferenceDivisionByPlace
import com.patres.timetable.repository.PlaceRepository
import com.patres.timetable.repository.DivisionRepository
import com.patres.timetable.service.dto.preference.PreferenceDivisionByPlaceDTO
import com.patres.timetable.service.mapper.EntityMapper
import com.patres.timetable.service.util.getOneOrNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class PreferenceDivisionByPlaceMapper : EntityMapper<PreferenceDivisionByPlace, PreferenceDivisionByPlaceDTO>() {

    @Autowired
    private lateinit var placeRepository: PlaceRepository

    @Autowired
    private lateinit var divisionRepository: DivisionRepository

    override fun toEntity(entityDto: PreferenceDivisionByPlaceDTO): PreferenceDivisionByPlace {
        return PreferenceDivisionByPlace(
            place = placeRepository.getOneOrNull(entityDto.placeId),
            division = divisionRepository.getOneOrNull(entityDto.divisionId),
            points = entityDto.points
        ).apply {
            id = entityDto.id
        }
    }

    override fun toDto(entity: PreferenceDivisionByPlace): PreferenceDivisionByPlaceDTO {
        return PreferenceDivisionByPlaceDTO(
            placeId = entity.place?.id,
            placeName = entity.place?.name ?: "",
            divisionId = entity.division?.id,
            divisionName = entity.division?.name ?: "",
            points = entity.points)
            .apply {
                id = entity.id
            }
    }

}
