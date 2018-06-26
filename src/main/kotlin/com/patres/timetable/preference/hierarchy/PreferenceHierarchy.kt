package com.patres.timetable.preference.hierarchy

abstract class PreferenceHierarchy {

    companion object : Comparator<PreferenceHierarchy> {
        const val HANDICAP = 10
        const val SMALL_HANDICAP = 1
        const val TAKEN = -10_000
        const val CAN_BE_USED = -100
        const val TOO_SMALL_PLACE = -10_000
        const val UNACCEPTED_EVENT = -1_000

        override fun compare(a: PreferenceHierarchy, b: PreferenceHierarchy): Int = a.points - b.points
    }

    open var points = 0
    open var preferencePoints = 0

}
