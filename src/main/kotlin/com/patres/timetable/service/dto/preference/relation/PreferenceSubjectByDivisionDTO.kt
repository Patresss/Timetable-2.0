package com.patres.timetable.service.dto.preference.relation

import com.patres.timetable.service.dto.AbstractApplicationEntityDTO
import java.io.Serializable
import javax.validation.constraints.NotNull

class PreferenceSubjectByDivisionDTO(

    @get:NotNull
    var subjectId: Long? = null,
    var subjectName: String = "",

    @get:NotNull
    var divisionId: Long? = null,
    var divisionName: String = "",

    var points: Int = 0

) : AbstractApplicationEntityDTO(), Serializable
