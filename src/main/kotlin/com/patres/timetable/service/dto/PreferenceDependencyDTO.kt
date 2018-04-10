package com.patres.timetable.service.dto


import java.time.LocalDate

class PreferenceDependencyDTO(
    var subjectId: Long? = null,
    var teacherId: Long? = null,
    var divisionId: Long? = null,
    var placeId: Long? = null,
    var periodId: Long? = null,
    var lessonId: Long? = null,
    var notTimetableId: Long? = null,
    var date: LocalDate? = null,
    var dayOfWeek: Int? = null,
    var everyWeek: Long = 1,
    var startWithWeek: Long = 1,
    var startTimeString: String? = null,
    var endTimeString: String? = null,
    var divisionOwnerId: Long? = null

)
