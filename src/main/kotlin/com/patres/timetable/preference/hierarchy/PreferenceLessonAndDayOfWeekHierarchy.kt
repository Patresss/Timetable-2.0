package com.patres.timetable.preference.hierarchy

class PreferenceLessonAndDayOfWeekHierarchy : PreferenceHierarchy() {

    var preferredBySubject = 0
    var preferredByPlace = 0
    var preferredByTeacher = 0
    var preferredByDivision = 0

    var taken = 0

    override var points = 0
        get() = preferredByDivision + preferredBySubject + preferredByPlace + preferredByTeacher + taken

}
