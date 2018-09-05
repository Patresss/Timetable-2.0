package com.patres.timetable.preference.hierarchy

class PreferenceTimeAndPlaceHierarchy : PreferenceHierarchy() {

    var preferredBySubject = 0
    var preferredByTeacher = 0
    var preferredByDivision = 0
    var preferredByPlace = 0
    var preferredByTime = 0

    var takenByPlace = 0
    var takenByTeacher = 0
    var takenByDivision = 0

    var windowHandicap = 0
    var subgroupHandicap = 0
    var handicap = 0
        get() = windowHandicap + subgroupHandicap

    override var points = 0
        get() = preferredByDivision + preferredBySubject + preferredByTeacher + preferredByTime + takenByPlace + takenByTeacher + takenByDivision

    var pointsWithHandicap = 0
        get() = points + handicap

}
