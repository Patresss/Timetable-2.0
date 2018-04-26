package com.patres.timetable.service.mapper.preference

import com.patres.timetable.domain.preference.PreferenceTeacherByPlace
import com.patres.timetable.repository.PlaceRepository
import com.patres.timetable.repository.TeacherRepository
import com.patres.timetable.service.dto.preference.PreferenceTeacherByPlaceDTO
import com.patres.timetable.service.mapper.EntityMapper
import com.patres.timetable.service.util.getOneOrNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class PreferenceTeacherByPlaceMapper : EntityMapper<PreferenceTeacherByPlace, PreferenceTeacherByPlaceDTO>() {

    @Autowired
    private lateinit var placeRepository: PlaceRepository

    @Autowired
    private lateinit var teacherRepository: TeacherRepository

    override fun toEntity(entityDto: PreferenceTeacherByPlaceDTO): PreferenceTeacherByPlace {
        return PreferenceTeacherByPlace(
            place = placeRepository.getOneOrNull(entityDto.placeId),
            teacher = teacherRepository.getOneOrNull(entityDto.teacherId),
            points = entityDto.points
        ).apply {
            id = entityDto.id
        }
    }

    override fun toDto(entity: PreferenceTeacherByPlace): PreferenceTeacherByPlaceDTO {
        return PreferenceTeacherByPlaceDTO(
            placeId = entity.place?.id,
            placeName = entity.place?.name ?: "",
            teacherId = entity.teacher?.id,
            teacherFullName = entity.teacher?.getFullName() ?: "",
            teacherName = entity.teacher?.name ?: "",
            teacherSurname = entity.teacher?.surname ?: "",
            teacherDegree = entity.teacher?.degree ?: "",
            points = entity.points)
            .apply {
                id = entity.id
            }
    }

}
