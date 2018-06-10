package com.patres.timetable.preference.hierarchy

class PreferenceTeacherHierarchy : PreferenceHierarchy() {

    var preferredBySubject = 0
    var preferredByPlace = 0
    var preferredByDivision = 0
    var preferredByDateTime = 0

    var taken = 0

    override var points = 0
        get() = preferredByDateTime + preferredBySubject + preferredByPlace + preferredByDivision + taken

}
