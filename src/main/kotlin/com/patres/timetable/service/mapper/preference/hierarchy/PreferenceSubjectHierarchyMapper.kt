package com.patres.timetable.service.mapper.preference.hierarchy

import com.patres.timetable.preference.hierarchy.subject.PreferenceSubjectHierarchy
import com.patres.timetable.service.dto.preference.hierarchy.PreferenceSubjectHierarchyDTO
import com.patres.timetable.service.mapper.EntityMapperToDto
import org.springframework.stereotype.Service

@Service
open class PreferenceSubjectHierarchyMapper : EntityMapperToDto<PreferenceSubjectHierarchy, PreferenceSubjectHierarchyDTO>() {

    override fun toDto(entity: PreferenceSubjectHierarchy): PreferenceSubjectHierarchyDTO {
        return PreferenceSubjectHierarchyDTO(
            subjectId = entity.subjectId,
            preferredByTeacher = entity.preferredByTeacher,
            preferredByPlace = entity.preferredByPlace,
            preferredByDivision = entity.preferredByDivision,
            preferredByDateTime = entity.preferredByDateTime,
            taken = entity.taken
        )
    }
}
