package com.patres.timetable.service.dto


import com.patres.timetable.domain.enumeration.EventType
import java.io.Serializable
import java.time.LocalDate
import java.util.*
import javax.validation.constraints.NotNull

data class CurriculumDTO(

    var startTime: String? = null,

    var endTime: String? = null,

    @get:NotNull
    var type: EventType = EventType.LESSON,

    var everyWeek: Long = 1,

    var startWithWeek: Long = 1,

    var subjectId: Long? = null,

    var subjectName: String? = null,

    var teacherId: Long? = null,

    var teacherSurname: String? = null,

    var divisionId: Long? = null,

    var divisionName: String? = null,

    var lessonId: Long? = null,

    var lessonName: String? = null,

    var placeId: Long? = null,

    var placeName: String? = null,

    var curriculumListId: Long? = null,

    var curriculumListName: String? = null

) : AbstractApplicationEntityDTO(), Serializable
