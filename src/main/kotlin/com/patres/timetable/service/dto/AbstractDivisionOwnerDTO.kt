package com.patres.timetable.service.dto

import javax.validation.constraints.NotNull
import java.io.Serializable

abstract class AbstractDivisionOwnerDTO : AbstractApplicationEntityDTO(), Serializable {

    @get:NotNull
    var divisionOwnerId: Long? = null

    var divisionOwnerName: String? = null

}
