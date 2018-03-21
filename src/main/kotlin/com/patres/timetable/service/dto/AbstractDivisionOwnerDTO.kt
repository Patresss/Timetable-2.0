package com.patres.timetable.service.dto

import java.io.Serializable

abstract class AbstractDivisionOwnerDTO(

    var divisionOwnerId: Long? = null,

    var divisionOwnerName: String? = null

) : AbstractApplicationEntityDTO(), Serializable
