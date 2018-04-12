package com.patres.timetable.service.dto.preference

import java.io.Serializable
import javax.validation.constraints.NotNull

class PreferenceDataTimeForTeacherDTO(

    @get:NotNull
    var teacherId: Long? = null,
    var teacherFullName: String = "",
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
