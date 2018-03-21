package com.patres.timetable.service.dto

import java.io.Serializable
import javax.validation.constraints.NotNull

class LessonDTO(

    @get:NotNull
    var name: String? = null,

    @get:NotNull
    var startTimeString: String? = null,

    @get:NotNull
    var endTimeString: String? = null,

    divisionOwnerId: Long? = null

) : AbstractDivisionOwnerDTO(divisionOwnerId = divisionOwnerId), Serializable
