package com.patres.timetable.service.dto


import java.io.Serializable
import java.util.HashSet
import javax.validation.constraints.NotNull

class PlaceDTO(

    @get:NotNull
    var name: String? = null,

    var numberOfSeats: Long? = null,

    var shortName: String? = null,

    var colorBackground: String? = null,

    var colorText: String? = null,

    var preferredSubjects: Set<SubjectDTO> = HashSet(),

    var preferredDivisions: Set<DivisionDTO> = HashSet(),

    var preferredTeachers: Set<TeacherDTO> = HashSet(),

    divisionOwnerId: Long? = null

) : AbstractDivisionOwnerDTO(divisionOwnerId = divisionOwnerId), Serializable {

    override fun toString(): String {
        return "PlaceDTO{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", numberOfSeats='" + numberOfSeats + "'" +
                ", shortName='" + shortName + "'" +
                ", colorBackground='" + colorBackground + "'" +
                ", colorText='" + colorText + "'" +
                "}"
    }
}
