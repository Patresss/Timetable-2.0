package com.patres.timetable.preference.hierarchy.place

import com.patres.timetable.preference.container.PreferenceContainer
import com.patres.timetable.preference.hierarchy.PreferenceHierarchy

abstract class PreferencePlaceHierarchy(
    val placeId: Long,
    val preferenceContainer: PreferenceContainer) : PreferenceHierarchy() {


    var preferredByTeacher = 0
        get() = preferenceContainer.selectTeacher?.preferenceTeacherByPlace?.find { it.place?.id == placeId }?.points ?: 0

    var preferredBySubject = 0
        get() = preferenceContainer.selectSubject?.preferenceSubjectByPlace?.find { it.place?.id == placeId }?.points ?: 0

    var preferredByDivision = 0
        get() = preferenceContainer.selectDivision?.preferenceDivisionByPlace?.find { it.place?.id == placeId }?.points ?: 0

    var preferredByDateTime = 0
        get() = calculatePreferredByDateTime()

    var tooSmallPlace = 0
        get() = calculateTooSmallPlace()

    var taken = 0
        get() = calculateTaken()

    override var points = 0
        get() = preferencePoints + taken

    override var preferencePoints = 0
        get() = preferredByDateTime + preferredByTeacher + preferredBySubject + preferredByDivision + tooSmallPlace


    abstract fun calculatePreferredByDateTime(): Int
    abstract fun calculateTaken(): Int
    abstract fun calculateTooSmallPlace(): Int
}
