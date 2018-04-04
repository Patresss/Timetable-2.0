package com.patres.timetable.preference

class PreferenceHierarchy {

    companion object : Comparator<PreferenceHierarchy> {
        val PREFFERRED_POINTS = 1
        val TAKEN = -10
        val TOO_SMALL_PLACE = -10

        override fun compare(a: PreferenceHierarchy, b: PreferenceHierarchy): Int = a.points - b.points
    }

    var preferredByTeacher = 0
    var preferredBySubject = 0
    var preferredByPlace = 0
    var preferredByDivision = 0

    var tooSmallPlace = 0
    var taken = 0

    var points = 0
        get() = preferredByTeacher + preferredBySubject + preferredByPlace + preferredByDivision + tooSmallPlace + taken

}
