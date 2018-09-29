package com.patres.timetable.service.mapper.preference.relation

import com.patres.timetable.domain.preference.PreferenceSubjectByPlace
import com.patres.timetable.repository.PlaceRepository
import com.patres.timetable.repository.SubjectRepository
import com.patres.timetable.service.dto.preference.relation.PreferenceSubjectByPlaceDTO
import com.patres.timetable.service.mapper.EntityMapper
import com.patres.timetable.service.util.getOneOrNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class PreferenceSubjectByPlaceMapper : EntityMapper<PreferenceSubjectByPlace, PreferenceSubjectByPlaceDTO>() {

    @Autowired
    private lateinit var placeRepository: PlaceRepository

    @Autowired
    private lateinit var subjectRepository: SubjectRepository

    override fun toEntity(entityDto: PreferenceSubjectByPlaceDTO): PreferenceSubjectByPlace {
        return PreferenceSubjectByPlace(
            place = placeRepository.getOneOrNull(entityDto.placeId),
            subject = subjectRepository.getOneOrNull(entityDto.subjectId),
            points = entityDto.points
        ).apply {
            id = entityDto.id
        }
    }

    override fun toDto(entity: PreferenceSubjectByPlace): PreferenceSubjectByPlaceDTO {
        return PreferenceSubjectByPlaceDTO(
            placeId = entity.place?.id,
            placeName = entity.place?.name ?: "",
            subjectId = entity.subject?.id,
            subjectName = entity.subject?.name ?: "",
            points = entity.points)
            .apply {
                id = entity.id
            }
    }

}
