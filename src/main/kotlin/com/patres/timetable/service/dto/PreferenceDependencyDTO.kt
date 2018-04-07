package com.patres.timetable.service.dto


import java.time.LocalDate

class PreferenceDependencyDTO(
    var subjectId: Long? = null,
    var teacherId: Long? = null,
    var divisionId: Long? = null,
    var placeId: Long? = null,
    var periodId: Long? = null,
    var lessonId: Long? = null,
    var date: LocalDate? = null,
    var inMonday: Boolean = false,
    var inTuesday: Boolean = false,
    var inWednesday: Boolean = false,
    var inThursday: Boolean = false,
    var inFriday: Boolean = false,
    var inSaturday: Boolean = false,
    var inSunday: Boolean = false,
    var everyWeek: Long = 1,
    var startWithWeek: Long = 1,
    var startTimeString: String? = null,
    var endTimeString: String? = null,
    var divisionOwnerId: Long? = null

)
