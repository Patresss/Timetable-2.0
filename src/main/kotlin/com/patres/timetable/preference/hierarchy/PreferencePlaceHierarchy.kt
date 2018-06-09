package com.patres.timetable.preference.hierarchy

class PreferencePlaceHierarchy : PreferenceHierarchy() {

    var preferredByTeacher = 0
    var preferredBySubject = 0
    var preferredByDivision = 0
    var preferredByDateTime = 0

    var tooSmallPlace = 0
    var taken = 0

    override var points = 0
        get() = preferredByDateTime + preferredByTeacher + preferredBySubject + preferredByDivision + tooSmallPlace + taken

}
