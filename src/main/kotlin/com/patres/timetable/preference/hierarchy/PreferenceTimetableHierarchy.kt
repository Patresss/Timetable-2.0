package com.patres.timetable.preference.hierarchy

class PreferenceTimetableHierarchy : PreferenceHierarchy() {

    var preferredBySubject = 0
    var preferredByPlace = 0
    var preferredByTeacher = 0
    var preferredByDivision = 0
    var preferredByLessonAndDayOfWeek = 0

    var takenByPlace = 0
    var takenByTeacher = 0
    var takenByDivision = 0

    override var points = 0
        get() = preferredByDivision + preferredBySubject + preferredByPlace + preferredByTeacher + preferredByLessonAndDayOfWeek

}
