package com.patres.timetable.service.dto


import java.io.Serializable
import java.util.*
import javax.validation.constraints.NotNull

class TeacherDTO(

    @get:NotNull
    var name: String? = null,

    @get:NotNull
    var surname: String? = null,

    var degree: String? = null,

    var shortName: String? = null,

    var fullName: String? = null,

    var preferredSubjects: Set<SubjectDTO> = HashSet(),

    divisionOwnerId: Long? = null

) : AbstractDivisionOwnerDTO(divisionOwnerId = divisionOwnerId), Serializable
