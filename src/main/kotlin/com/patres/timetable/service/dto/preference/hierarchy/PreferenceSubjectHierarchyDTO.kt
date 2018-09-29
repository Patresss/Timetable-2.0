package com.patres.timetable.service.dto.preference.hierarchy

class PreferenceSubjectHierarchyDTO(
    val subjectId: Long,
    var preferredByTeacher: Int = 0,
    var preferredByPlace: Int = 0,
    var preferredByDivision: Int = 0,
    var preferredByDateTime: Int = 0,
    var taken: Int = 0
) : PreferenceHierarchyDTO() {

    override var points = 0
        get() = preferencePoints + taken

    override var preferencePoints = 0
        get() = preferredByDateTime + preferredByTeacher + preferredByPlace + preferredByDivision
}
