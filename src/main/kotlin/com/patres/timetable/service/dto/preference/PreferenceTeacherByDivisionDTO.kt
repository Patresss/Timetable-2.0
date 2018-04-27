package com.patres.timetable.service.dto.preference

import com.patres.timetable.service.dto.AbstractApplicationEntityDTO
import java.io.Serializable
import javax.validation.constraints.NotNull

class PreferenceTeacherByDivisionDTO(

    @get:NotNull
    var teacherId: Long? = null,
    var teacherFullName: String = "",
    var teacherName: String = "",
    var teacherSurname: String = "",
    var teacherDegree: String = "",

    @get:NotNull
    var divisionId: Long? = null,
    var divisionName: String = "",

    var points: Int = 0

) : AbstractApplicationEntityDTO(), Serializable
