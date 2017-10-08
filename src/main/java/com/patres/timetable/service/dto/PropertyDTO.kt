package com.patres.timetable.service.dto


import java.io.Serializable
import javax.validation.constraints.NotNull

class PropertyDTO(

    @get:NotNull
    var propertyKey: String? = null,

    @get:NotNull
    var propertyValue: String? = null

) : AbstractDivisionOwnerDTO(), Serializable
