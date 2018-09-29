package com.patres.timetable.service.mapper.preference.hierarchy

import com.patres.timetable.preference.hierarchy.teacher.PreferenceTeacherHierarchy
import com.patres.timetable.service.dto.preference.hierarchy.PreferenceTeacherHierarchyDTO
import com.patres.timetable.service.mapper.EntityMapperToDto
import org.springframework.stereotype.Service

@Service
open class PreferenceTeacherHierarchyMapper : EntityMapperToDto<PreferenceTeacherHierarchy, PreferenceTeacherHierarchyDTO>() {

    override fun toDto(entity: PreferenceTeacherHierarchy): PreferenceTeacherHierarchyDTO {
        return PreferenceTeacherHierarchyDTO(
            teacherId = entity.teacherId,
            preferredBySubject = entity.preferredBySubject,
            preferredByPlace = entity.preferredByPlace,
            preferredByDivision = entity.preferredByDivision,
            preferredByDateTime = entity.preferredByDateTime,
            taken = entity.taken
        )
    }

}
