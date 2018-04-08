package com.patres.timetable.preference

import java.util.*

class Preference {

    val preferredTeacherMap = TreeMap<Long, PreferenceHierarchy>()
    val preferredSubjectMap = TreeMap<Long, PreferenceHierarchy>()
    val preferredPlaceMap = TreeMap<Long, PreferenceHierarchy>()
    val preferredDivisionMap = TreeMap<Long, PreferenceHierarchy>()

    fun getTeacherPreferenceHierarchy(teachersId: Set<Long>): Collection<PreferenceHierarchy> {
        return getPreferenceHierarchy(preferredTeacherMap, teachersId)
    }

    fun getDivisionPreferenceHierarchy(divisionsId: Set<Long>): Collection<PreferenceHierarchy> {
        return getPreferenceHierarchy(preferredDivisionMap, divisionsId)
    }

    fun getSubjectPreferenceHierarchy(subjectsId: Set<Long>): Collection<PreferenceHierarchy> {
        return getPreferenceHierarchy(preferredSubjectMap, subjectsId)
    }

    fun getPlacePreferenceHierarchy(placesId: Set<Long>): Collection<PreferenceHierarchy> {
        return getPreferenceHierarchy(preferredPlaceMap, placesId)
    }

    private fun getPreferenceHierarchy(map: TreeMap<Long, PreferenceHierarchy>, ids: Set<Long>): Collection<PreferenceHierarchy> {
        ids.filter { !map.contains(it) }.forEach { map[it] = PreferenceHierarchy() }
        val filterKeys = map.filterKeys { key -> ids.contains(key) }
        return filterKeys.values
    }


}
