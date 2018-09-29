package com.patres.timetable.service.mapper.preference.relation

import com.patres.timetable.domain.preference.PreferenceSubjectByDivision
import com.patres.timetable.repository.DivisionRepository
import com.patres.timetable.repository.SubjectRepository
import com.patres.timetable.service.dto.preference.relation.PreferenceSubjectByDivisionDTO
import com.patres.timetable.service.mapper.EntityMapper
import com.patres.timetable.service.util.getOneOrNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class PreferenceSubjectByDivisionMapper : EntityMapper<PreferenceSubjectByDivision, PreferenceSubjectByDivisionDTO>() {

    @Autowired
    private lateinit var subjectRepository: SubjectRepository

    @Autowired
    private lateinit var divisionRepository: DivisionRepository

    override fun toEntity(entityDto: PreferenceSubjectByDivisionDTO): PreferenceSubjectByDivision {
        return PreferenceSubjectByDivision(
            subject = subjectRepository.getOneOrNull(entityDto.subjectId),
            division = divisionRepository.getOneOrNull(entityDto.divisionId),
            points = entityDto.points
        ).apply {
            id = entityDto.id
        }
    }

    override fun toDto(entity: PreferenceSubjectByDivision): PreferenceSubjectByDivisionDTO {
        return PreferenceSubjectByDivisionDTO(
            subjectId = entity.subject?.id,
            subjectName = entity.subject?.name ?: "",
            divisionId = entity.division?.id,
            divisionName = entity.division?.name ?: "",
            points = entity.points)
            .apply {
                id = entity.id
            }
    }

}
