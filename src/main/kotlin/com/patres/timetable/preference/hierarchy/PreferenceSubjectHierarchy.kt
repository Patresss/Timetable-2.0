package com.patres.timetable.preference.hierarchy

class PreferenceSubjectHierarchy : PreferenceHierarchy() {

    var preferredByTeacher = 0
    var preferredByPlace = 0
    var preferredByDivision = 0
    var preferredByDateTime = 0

    override var points = 0
        get() = preferencePoints

    override var preferencePoints = 0
        get() = preferredByDateTime + preferredByTeacher + preferredByPlace + preferredByDivision
}
