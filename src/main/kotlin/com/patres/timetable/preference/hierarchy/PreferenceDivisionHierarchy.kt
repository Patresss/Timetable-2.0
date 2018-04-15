package com.patres.timetable.preference.hierarchy

class PreferenceDivisionHierarchy : PreferenceHierarchy() {

    var preferredBySubject = 0
    var preferredByPlace = 0
    var preferredByTeacher = 0
    var preferredByDataTime = 0

    var taken = 0

    override var points = 0
        get() = preferredByDataTime + preferredBySubject + preferredByPlace + preferredByTeacher + taken

}
