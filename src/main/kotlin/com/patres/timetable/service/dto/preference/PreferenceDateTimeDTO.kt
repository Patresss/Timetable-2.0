package com.patres.timetable.service.dto.preference


import com.patres.timetable.service.dto.AbstractApplicationEntityDTO
import java.io.Serializable
import javax.validation.constraints.NotNull

abstract class PreferenceDateTimeDTO(

    @get:NotNull
    var name: String? = null,

    @get:NotNull
    var lessonId: Long? = null,
    var lessonName: String = "",

    @get:NotNull
    var dayOfWeek: Int? = null,

    var points: Int = 0

) : AbstractApplicationEntityDTO(), Serializable
