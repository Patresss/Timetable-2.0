package com.patres.timetable.preference.hierarchy

class PreferenceLessonAndDayOfWeekHierarchy : PreferenceHierarchy() {

    var preferredBySubject = 0
    var preferredByPlace = 0
    var preferredByTeacher = 0
    var preferredByDivision = 0

    var takenByPlace = 0
    var takenByTeacher = 0
    var takenByDivision = 0

    var windowHandicap = 0

    override var points = 0
        get() = preferredByDivision + preferredBySubject + preferredByPlace + preferredByTeacher + takenByPlace + takenByTeacher + takenByDivision

    var pointsWithWindowHandicap= 0
        get() = points + windowHandicap

    fun setTakenByAll() {
        takenByPlace = PreferenceHierarchy.TAKEN
        takenByTeacher = PreferenceHierarchy.TAKEN
        takenByDivision = PreferenceHierarchy.TAKEN
    }

    fun setFreeByAll() {
        takenByPlace = 0
        takenByTeacher = 0
        takenByDivision = 0
    }


}
