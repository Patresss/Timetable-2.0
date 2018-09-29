package com.patres.timetable.service.dto


import com.patres.timetable.domain.enumeration.DivisionType
import com.patres.timetable.service.dto.preference.relation.PreferenceDataTimeForDivisionDTO
import com.patres.timetable.service.dto.preference.relation.PreferenceDivisionByPlaceDTO
import com.patres.timetable.service.dto.preference.relation.PreferenceSubjectByDivisionDTO
import com.patres.timetable.service.dto.preference.relation.PreferenceTeacherByDivisionDTO
import java.io.Serializable
import java.util.*
import javax.validation.constraints.NotNull

class DivisionDTO(

    @get:NotNull
    var name: String? = null,

    var shortName: String? = null,

    var numberOfPeople: Long? = null,

    @get:NotNull
    var divisionType: DivisionType? = null,

    var colorBackground: String? = null,

    var colorText: String? = null,

    var parents: Set<DivisionDTO> = HashSet(),

    var preferencesSubjectByDivision: Set<PreferenceSubjectByDivisionDTO> = HashSet(),

    var preferencesTeacherByDivision: Set<PreferenceTeacherByDivisionDTO> = HashSet(),

    var preferenceDivisionByPlace: Set<PreferenceDivisionByPlaceDTO> = HashSet(),

    var preferencesDataTimeForDivision: Set<PreferenceDataTimeForDivisionDTO> = HashSet(),

    divisionOwnerId: Long? = null

) : AbstractDivisionOwnerDTO(divisionOwnerId = divisionOwnerId), Serializable
