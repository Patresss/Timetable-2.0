package com.patres.timetable.service.mapper.preference.hierarchy

import com.patres.timetable.preference.hierarchy.place.PreferencePlaceHierarchy
import com.patres.timetable.service.dto.preference.hierarchy.PreferencePlaceHierarchyDTO
import com.patres.timetable.service.mapper.EntityMapperToDto
import org.springframework.stereotype.Service

@Service
open class PreferencePlaceHierarchyMapper : EntityMapperToDto<PreferencePlaceHierarchy, PreferencePlaceHierarchyDTO>() {

    override fun toDto(entity: PreferencePlaceHierarchy): PreferencePlaceHierarchyDTO {
        return PreferencePlaceHierarchyDTO(
            placeId = entity.placeId,
            preferredBySubject = entity.preferredBySubject,
            preferredByTeacher = entity.preferredByTeacher,
            preferredByDivision = entity.preferredByDivision,
            preferredByDateTime = entity.preferredByDateTime,
            tooSmallPlace = entity.tooSmallPlace,
            taken = entity.taken
        )
    }

}
