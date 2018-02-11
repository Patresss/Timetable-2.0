package com.patres.timetable.service.dto


import java.io.Serializable
import java.util.*
import javax.validation.constraints.NotNull

class PeriodDTO(

    @get:NotNull
    var name: String? = null,

    var intervalTimes: Set<IntervalDTO> = HashSet(),

    divisionOwnerId: Long? = null

) : AbstractDivisionOwnerDTO(divisionOwnerId = divisionOwnerId), Serializable
