package com.patres.timetable.service.dto


import com.patres.timetable.service.dto.preference.PreferenceDataTimeForSubjectDTO
import com.patres.timetable.service.dto.preference.PreferenceSubjectByPlaceDTO
import java.io.Serializable
import java.util.HashSet
import javax.validation.constraints.NotNull

class SubjectDTO(

    @get:NotNull
    var name: String? = null,

    var shortName: String? = null,

    var colorBackground: String? = null,

    var colorText: String? = null,

    var preferenceSubjectByPlace: Set<PreferenceSubjectByPlaceDTO> = HashSet(),

    var preferencesDataTimeForSubject: Set<PreferenceDataTimeForSubjectDTO> = HashSet(),

    divisionOwnerId: Long? = null

) : AbstractDivisionOwnerDTO(divisionOwnerId = divisionOwnerId), Serializable
