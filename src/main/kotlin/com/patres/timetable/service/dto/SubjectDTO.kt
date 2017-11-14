package com.patres.timetable.service.dto


import java.io.Serializable
import javax.validation.constraints.NotNull

class SubjectDTO(

    @get:NotNull
    var name: String? = null,

    var shortName: String? = null,

    var colorBackground: String? = null,

    var colorText: String? = null

) : AbstractDivisionOwnerDTO(), Serializable
