package com.patres.timetable.service.dto.preference.hierarchy

import java.time.DayOfWeek

class PreferenceLessonAndDayOfWeekHierarchyDTO(
    val lessonId: Long,
    val dayOfWeek: DayOfWeek,
    var preferredBySubject: Int  = 0,
    var preferredByPlace: Int  = 0,
    var preferredByTeacher: Int  = 0,
    var preferredByDivision: Int  = 0,
    var takenByPlace: Int  = 0,
    var takenByTeacher: Int  = 0,
    var takenByDivision: Int  = 0
) : PreferenceHierarchyDTO() {

    override var points = 0
        get() = preferencePoints+ takenByPlace + takenByTeacher + takenByDivision

    override var preferencePoints = 0
        get() = preferredByDivision + preferredBySubject + preferredByPlace + preferredByTeacher

}
