package com.patres.timetable.service.dto.generator

import com.patres.timetable.domain.Division
import com.patres.timetable.domain.Lesson

class WindowDTO(
    var dayOfWeek: Int? = null,
    var lessonName: String = "",
    var lessonId: Long? = null,
    var divisionName: String = "",
    var divisionId: Long? = null
)
