package com.patres.timetable.service.mapper.preference.hierarchy

import com.patres.timetable.preference.hierarchy.lessonAndDay.PreferenceLessonAndDayOfWeekHierarchy
import com.patres.timetable.service.dto.preference.hierarchy.PreferenceLessonAndDayOfWeekHierarchyDTO
import com.patres.timetable.service.mapper.EntityMapperToDto
import org.springframework.stereotype.Service

@Service
open class PreferenceLessonAndDayOfWeekHierarchyMapper : EntityMapperToDto<PreferenceLessonAndDayOfWeekHierarchy, PreferenceLessonAndDayOfWeekHierarchyDTO>() {

    override fun toDto(entity: PreferenceLessonAndDayOfWeekHierarchy): PreferenceLessonAndDayOfWeekHierarchyDTO {
        return PreferenceLessonAndDayOfWeekHierarchyDTO(
            lessonId = entity.lessonId,
            dayOfWeek = entity.dayOfWeek,
            preferredByPlace = entity.preferredByPlace,
            preferredByDivision = entity.preferredByDivision,
            preferredBySubject = entity.preferredBySubject,
            preferredByTeacher = entity.preferredByTeacher,
            takenByPlace = entity.takenByPlace,
            takenByTeacher = entity.takenByTeacher,
            takenByDivision = entity.takenByDivision
        )
    }

}
