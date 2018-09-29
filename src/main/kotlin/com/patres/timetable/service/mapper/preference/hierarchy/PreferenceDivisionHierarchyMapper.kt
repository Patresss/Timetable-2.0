package com.patres.timetable.service.mapper.preference.hierarchy

import com.patres.timetable.preference.hierarchy.division.PreferenceDivisionHierarchy
import com.patres.timetable.service.dto.preference.hierarchy.PreferenceDivisionHierarchyDTO
import com.patres.timetable.service.mapper.EntityMapperToDto
import org.springframework.stereotype.Service

@Service
open class PreferenceDivisionHierarchyMapper : EntityMapperToDto<PreferenceDivisionHierarchy, PreferenceDivisionHierarchyDTO>() {

    override fun toDto(entity: PreferenceDivisionHierarchy): PreferenceDivisionHierarchyDTO {
        return PreferenceDivisionHierarchyDTO(
            divisionId = entity.divisionId,
            preferredBySubject = entity.preferredBySubject,
            preferredByPlace = entity.preferredByPlace,
            preferredByTeacher = entity.preferredByTeacher,
            preferredByDateTime = entity.preferredByDateTime,
            taken = entity.taken
        )
    }

}
