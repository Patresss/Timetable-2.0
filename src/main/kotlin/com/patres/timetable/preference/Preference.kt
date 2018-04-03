package com.patres.timetable.preference

import java.util.*

class Preference {

    private val preferredTeachersMap = TreeMap<Long, PreferenceHierarchy>()
    val preferredSubjectsMap = TreeMap<Long, PreferenceHierarchy>()
    val preferredPlaceMap = TreeMap<Long, PreferenceHierarchy>()
    val preferredDivisionMap = TreeMap<Long, PreferenceHierarchy>()

    fun getTeacherPreferenceHierarchy(teachersId: Set<Long>): MutableCollection<PreferenceHierarchy> {
        return getPlaceHierarchy(preferredTeachersMap, teachersId)
    }

    fun geDivisionPreferenceHierarchy(divisionsId: Set<Long>): MutableCollection<PreferenceHierarchy> {
        return getPlaceHierarchy(preferredDivisionMap, divisionsId)
    }

    fun getSubjectPreferenceHierarchy(subjectsId: Set<Long>): MutableCollection<PreferenceHierarchy> {
        return getPlaceHierarchy(preferredSubjectsMap, subjectsId)
    }

    fun getPlacePreferenceHierarchy(placesId: Set<Long>): MutableCollection<PreferenceHierarchy> {
        return getPlaceHierarchy(preferredPlaceMap, placesId)
    }

    private fun getPlaceHierarchy(map: TreeMap<Long, PreferenceHierarchy>, ids: Set<Long>): MutableCollection<PreferenceHierarchy> {
        ids.filter { !map.contains(it) }.forEach { map[it] = PreferenceHierarchy() }
        return map.values
    }


}
