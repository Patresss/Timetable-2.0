package com.patres.timetable.service.dto.preference.relation

import java.io.Serializable
import javax.validation.constraints.NotNull

class PreferenceDataTimeForPlaceDTO(

    @get:NotNull
    var placeId: Long? = null,
    var placeName: String = "",
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
