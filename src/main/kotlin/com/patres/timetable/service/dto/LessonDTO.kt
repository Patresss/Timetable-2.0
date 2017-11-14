package com.patres.timetable.service.dto

import java.io.Serializable
import javax.validation.constraints.NotNull

class LessonDTO(

    @get:NotNull
    var name: String? = null,

    @get:NotNull
    var startTime: Long? = null,

    @get:NotNull
    var endTime: Long? = null

) : AbstractDivisionOwnerDTO(), Serializable
