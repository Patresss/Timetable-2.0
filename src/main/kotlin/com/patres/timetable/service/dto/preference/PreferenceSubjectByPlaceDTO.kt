package com.patres.timetable.service.dto.preference

import com.patres.timetable.service.dto.AbstractApplicationEntityDTO
import java.io.Serializable
import javax.validation.constraints.NotNull

class PreferenceSubjectByPlaceDTO(

    @get:NotNull
    var placeId: Long? = null,
    var placeName: String = "",

    @get:NotNull
    var subjectId: Long? = null,
    var subjectName: String = "",

    var points: Int = 0

) : AbstractApplicationEntityDTO(), Serializable
