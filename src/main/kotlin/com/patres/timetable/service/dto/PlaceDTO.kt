package com.patres.timetable.service.dto

import com.patres.timetable.service.dto.preference.*
import java.io.Serializable
import java.util.*
import javax.validation.constraints.NotNull

class PlaceDTO(

    @get:NotNull
    var name: String? = null,

    var numberOfSeats: Long? = null,

    var shortName: String? = null,

    var colorBackground: String? = null,

    var colorText: String? = null,

    var preferenceSubjectByPlace: Set<PreferenceSubjectByPlaceDTO> = HashSet(),

    var preferenceDivisionByPlace: Set<PreferenceDivisionByPlaceDTO> = HashSet(),

    var preferencesDataTimeForPlace: Set<PreferenceDataTimeForPlaceDTO> = HashSet(),

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
