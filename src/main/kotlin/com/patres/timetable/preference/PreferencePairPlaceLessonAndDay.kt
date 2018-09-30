package com.patres.timetable.preference

import com.patres.timetable.preference.hierarchy.lessonAndDay.PreferenceLessonAndDayHierarchyForGlobal
import com.patres.timetable.preference.hierarchy.place.PreferencePlaceHierarchyForGlobal

class PreferencePairPlaceLessonAndDay(
    val preferencePlaceHierarchy: PreferencePlaceHierarchyForGlobal,
    val preferenceLessonAndDayHierarchy: PreferenceLessonAndDayHierarchyForGlobal
)
