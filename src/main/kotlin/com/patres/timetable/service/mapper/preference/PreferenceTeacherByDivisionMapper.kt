package com.patres.timetable.service.mapper.preference

import com.patres.timetable.domain.preference.PreferenceTeacherByDivision
import com.patres.timetable.domain.preference.PreferenceTeacherByPlace
import com.patres.timetable.repository.DivisionRepository
import com.patres.timetable.repository.PlaceRepository
import com.patres.timetable.repository.TeacherRepository
import com.patres.timetable.service.dto.preference.PreferenceTeacherByDivisionDTO
import com.patres.timetable.service.dto.preference.PreferenceTeacherByPlaceDTO
import com.patres.timetable.service.mapper.EntityMapper
import com.patres.timetable.service.util.getOneOrNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class PreferenceTeacherByDivisionMapper : EntityMapper<PreferenceTeacherByDivision, PreferenceTeacherByDivisionDTO>() {

    @Autowired
    private lateinit var divisionRepository: DivisionRepository

    @Autowired
    private lateinit var teacherRepository: TeacherRepository

    override fun toEntity(entityDto: PreferenceTeacherByDivisionDTO): PreferenceTeacherByDivision {
        return PreferenceTeacherByDivision(
            division = divisionRepository.getOneOrNull(entityDto.divisionId),
            teacher = teacherRepository.getOneOrNull(entityDto.teacherId),
            points = entityDto.points
        ).apply {
            id = entityDto.id
        }
    }

    override fun toDto(entity: PreferenceTeacherByDivision): PreferenceTeacherByDivisionDTO {
        return PreferenceTeacherByDivisionDTO(
            divisionId = entity.division?.id,
            divisionName = entity.division?.name ?: "",
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
