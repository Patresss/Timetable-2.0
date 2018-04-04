package com.patres.timetable.preference

import java.util.*

class Preference {

    val preferredTeacherMap = TreeMap<Long, PreferenceHierarchy>()
    val preferredSubjectMap = TreeMap<Long, PreferenceHierarchy>()
    val preferredPlaceMap = TreeMap<Long, PreferenceHierarchy>()
    val preferredDivisionMap = TreeMap<Long, PreferenceHierarchy>()

    fun getTeacherPreferenceHierarchy(teachersId: Set<Long>): MutableCollection<PreferenceHierarchy> {
        return getPlaceHierarchy(preferredTeacherMap, teachersId)
    }

    fun geDivisionPreferenceHierarchy(divisionsId: Set<Long>): MutableCollection<PreferenceHierarchy> {
        return getPlaceHierarchy(preferredDivisionMap, divisionsId)
    }

    fun getSubjectPreferenceHierarchy(subjectsId: Set<Long>): MutableCollection<PreferenceHierarchy> {
        return getPlaceHierarchy(preferredSubjectMap, subjectsId)
    }

    fun getPlacePreferenceHierarchy(placesId: Set<Long>): MutableCollection<PreferenceHierarchy> {
        return getPlaceHierarchy(preferredPlaceMap, placesId)
    }

    private fun getPlaceHierarchy(map: TreeMap<Long, PreferenceHierarchy>, ids: Set<Long>): MutableCollection<PreferenceHierarchy> {
        ids.filter { !map.contains(it) }.forEach { map[it] = PreferenceHierarchy() }
        return map.values
    }


}
