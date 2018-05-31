package com.patres.timetable.service.dto


import com.patres.timetable.domain.enumeration.DivisionType
import com.patres.timetable.domain.preference.PreferenceSubjectByDivision
import com.patres.timetable.domain.preference.PreferenceTeacherByDivision
import com.patres.timetable.service.dto.preference.PreferenceDataTimeForDivisionDTO
import com.patres.timetable.service.dto.preference.PreferenceDataTimeForPlaceDTO
import com.patres.timetable.service.dto.preference.PreferenceSubjectByDivisionDTO
import com.patres.timetable.service.dto.preference.PreferenceTeacherByDivisionDTO
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import java.io.Serializable
import java.util.*
import javax.persistence.CascadeType
import javax.persistence.OneToMany
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

    var preferencesDataTimeForDivision: Set<PreferenceDataTimeForDivisionDTO> = HashSet(),

    divisionOwnerId: Long? = null

) : AbstractDivisionOwnerDTO(divisionOwnerId = divisionOwnerId), Serializable
