package com.patres.timetable.service.dto.preference

import com.patres.timetable.service.dto.AbstractApplicationEntityDTO
import java.io.Serializable
import javax.validation.constraints.NotNull

class PreferenceSubjectByTeacherDTO(

    @get:NotNull
    var teacherId: Long? = null,
    var teacherFullName: String = "",

    @get:NotNull
    var subjectId: Long? = null,
    var subjectName: String = "",

    var points: Int = 0

) : AbstractApplicationEntityDTO(), Serializable
