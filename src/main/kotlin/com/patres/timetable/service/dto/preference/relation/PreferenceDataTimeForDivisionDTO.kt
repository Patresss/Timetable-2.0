package com.patres.timetable.service.dto.preference.relation

import java.io.Serializable
import javax.validation.constraints.NotNull

class PreferenceDataTimeForDivisionDTO(

    @get:NotNull
    var divisionId: Long? = null,
    var divisionName: String = "",
    lessonId: Long? = null,
    lessonName: String = "",
    dayOfWeek: Int? = null,
    points: Int = 0

) : PreferenceDateTimeDTO(
    lessonId = lessonId,
    lessonName = lessonName,
    dayOfWeek = dayOfWeek,
    points = points
), Serializable
