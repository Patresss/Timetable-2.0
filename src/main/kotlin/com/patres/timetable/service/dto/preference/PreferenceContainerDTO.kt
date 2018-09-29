package com.patres.timetable.service.dto.preference

import com.patres.timetable.service.dto.preference.hierarchy.*

class PreferenceContainerDTO(
    var preferredTeacherMap: Map<Long, PreferenceTeacherHierarchyDTO> = emptyMap(),
    var preferredSubjectMap: Map<Long, PreferenceSubjectHierarchyDTO> = emptyMap(),
    var preferredPlaceMap: Map<Long, PreferencePlaceHierarchyDTO> = emptyMap(),
    var preferredDivisionMap: Map<Long, PreferenceDivisionHierarchyDTO> = emptyMap(),
    var preferredLessonAndDayOfWeekSet: Set<PreferenceLessonAndDayOfWeekHierarchyDTO> = emptySet()
)
