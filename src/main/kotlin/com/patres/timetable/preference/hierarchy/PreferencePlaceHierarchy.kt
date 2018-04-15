package com.patres.timetable.preference.hierarchy

class PreferencePlaceHierarchy : PreferenceHierarchy() {

    var preferredByTeacher = 0
    var preferredBySubject = 0
    var preferredByDivision = 0
    var preferredByDataTime = 0

    var tooSmallPlace = 0
    var taken = 0

    override var points = 0
        get() = preferredByDataTime + preferredByTeacher + preferredBySubject + preferredByDivision + tooSmallPlace + taken

}
