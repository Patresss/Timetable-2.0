package com.patres.timetable.service.dto


import java.io.Serializable
import java.time.LocalDate

class IntervalDTO(

    var included: Boolean = true,

    var startDate: LocalDate? = null,

    var endDate: LocalDate? = null,

    var periodId: Long? = null,

    var periodName: String? = null

) : AbstractApplicationEntityDTO(), Serializable
