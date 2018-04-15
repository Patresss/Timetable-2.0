package com.patres.timetable.preference.hierarchy

class PreferenceSubjectHierarchy : PreferenceHierarchy() {

    var preferredByTeacher = 0
    var preferredByPlace = 0
    var preferredByDivision = 0
    var preferredByDataTime = 0

    override var points = 0
        get() = preferredByDataTime + preferredByTeacher + preferredByPlace + preferredByDivision

}
