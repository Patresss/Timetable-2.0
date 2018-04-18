package com.patres.timetable.preference.hierarchy

abstract class PreferenceHierarchy {

    companion object : Comparator<PreferenceHierarchy> {
        const val PREFERRED_POINTS = 10
        const val TAKEN = -10000
        const val TOO_SMALL_PLACE = -10

        override fun compare(a: PreferenceHierarchy, b: PreferenceHierarchy): Int = a.points - b.points
    }

    open var points = 0

}
