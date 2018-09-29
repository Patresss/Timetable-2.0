package com.patres.timetable.service.dto.preference.hierarchy

class PreferencePlaceHierarchyDTO(
    val placeId: Long,
    var preferredByTeacher: Int = 0,
    var preferredBySubject: Int = 0,
    var preferredByDivision: Int = 0,
    var preferredByDateTime: Int = 0,
    var tooSmallPlace : Int = 0,
    var taken : Int = 0
) : PreferenceHierarchyDTO() {


    override var points = 0
        get() = preferencePoints + taken + tooSmallPlace

    override var preferencePoints = 0
        get() = preferredByDateTime + preferredByTeacher + preferredBySubject + preferredByDivision + tooSmallPlace

}
