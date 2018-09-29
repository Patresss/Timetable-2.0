package com.patres.timetable.service.mapper.preference

import com.patres.timetable.preference.container.PreferenceContainer
import com.patres.timetable.service.dto.preference.PreferenceContainerDTO
import com.patres.timetable.service.mapper.EntityMapperToDto
import com.patres.timetable.service.mapper.preference.hierarchy.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class PreferenceContainerMapper : EntityMapperToDto<PreferenceContainer, PreferenceContainerDTO>() {

    @Autowired
    private lateinit var preferenceTeacherHierarchyMapper: PreferenceTeacherHierarchyMapper

    @Autowired
    private lateinit var preferenceSubjectHierarchyMapper: PreferenceSubjectHierarchyMapper

    @Autowired
    private lateinit var preferencePlaceHierarchyMapper: PreferencePlaceHierarchyMapper

    @Autowired
    private lateinit var preferenceDivisionHierarchyMapper: PreferenceDivisionHierarchyMapper

    @Autowired
    private lateinit var preferenceLessonAndDayOfWeekHierarchyMapper: PreferenceLessonAndDayOfWeekHierarchyMapper


    override fun toDto(entity: PreferenceContainer): PreferenceContainerDTO {
        return PreferenceContainerDTO(
            preferredTeacherMap = entity.preferredTeacherMap.map { entry -> entry.key to preferenceTeacherHierarchyMapper.toDto(entry.value) }.toMap(),
            preferredSubjectMap = entity.preferredSubjectMap.map { entry -> entry.key to preferenceSubjectHierarchyMapper.toDto(entry.value) }.toMap(),
            preferredPlaceMap = entity.preferredPlaceMap.map { entry -> entry.key to preferencePlaceHierarchyMapper.toDto(entry.value) }.toMap(),
            preferredDivisionMap = entity.preferredDivisionMap.map { entry -> entry.key to preferenceDivisionHierarchyMapper.toDto(entry.value) }.toMap(),
            preferredLessonAndDayOfWeekSet = preferenceLessonAndDayOfWeekHierarchyMapper.entitySetToEntityDTOSet(entity.preferredLessonAndDayOfWeekSet)
        )
    }

}
