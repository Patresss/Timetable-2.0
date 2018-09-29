package com.patres.timetable.service.dto.preference.relation

import com.patres.timetable.service.dto.AbstractApplicationEntityDTO
import java.io.Serializable
import javax.validation.constraints.NotNull

class PreferenceTeacherByPlaceDTO(

    @get:NotNull
    var placeId: Long? = null,
    var placeName: String = "",

    @get:NotNull
    var teacherId: Long? = null,
    var teacherFullName: String = "",
    var teacherName: String = "",
    var teacherSurname: String = "",
    var teacherDegree: String = "",

    var points: Int = 0

) : AbstractApplicationEntityDTO(), Serializable
