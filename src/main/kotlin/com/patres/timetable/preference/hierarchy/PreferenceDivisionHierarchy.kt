package com.patres.timetable.preference.hierarchy

class PreferenceDivisionHierarchy : PreferenceHierarchy() {

    var preferredBySubject = 0
    var preferredByPlace = 0
    var preferredByTeacher = 0
    var preferredByDateTime = 0

    var taken = 0

    override var points = 0
        get() = preferencePoints + taken

    override var preferencePoints = 0
        get() = preferredByDateTime + preferredBySubject + preferredByPlace + preferredByTeacher
}
