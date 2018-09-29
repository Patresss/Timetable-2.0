package com.patres.timetable.service.dto.preference.relation

import java.io.Serializable
import javax.validation.constraints.NotNull

class PreferenceDataTimeForSubjectDTO(

    @get:NotNull
    var subjectId: Long? = null,
    var subjectName: String = "",
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
