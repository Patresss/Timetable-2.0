package com.patres.timetable.service.dto


import java.io.Serializable
import java.time.LocalDate
import java.util.*
import javax.validation.constraints.NotNull

class CurriculumListDTO(

    @get:NotNull
    var name: String? = null,

    var curriculums: Set<CurriculumDTO> = HashSet(),

    var startDate: LocalDate? = null,

    var endDate: LocalDate? = null,

    var periodId: Long? = null,

    var periodName: String? = null,

    divisionOwnerId: Long? = null

) : AbstractDivisionOwnerDTO(divisionOwnerId = divisionOwnerId), Serializable {

    override fun toString(): String {
        return "CurriculumListDTO(name=$name, curriculums=$curriculums, startDate=$startDate, endDate=$endDate, periodId=$periodId, periodName=$periodName)"
    }
}
