package com.patres.timetable.preference

import com.patres.timetable.domain.*
import java.time.LocalDate

data class PreferenceDependency (
    var subject: Subject? = null,
    var teacher: Teacher? = null,
    var division: Division? = null,
    var place: Place? = null,
    var period: Period? = null,
    var lesson: Lesson? = null,
    var notTimetableId: Long? = null,
    var date: LocalDate? = null,
    var dayOfWeek: Int? = null,
    var everyWeek: Long = 1,
    var startWithWeek: Long = 1,
    var startTime: Long? = null,
    var endTime: Long? = null,
    var divisionOwnerId: Long? = null
) {

    fun getStartTimeHHmmFormatted(): String? {
        return AbstractApplicationEntity.getTimeHHmmFormatted(startTime)
    }

    fun setStartTimeHHmmFormatted(time: String) {
        startTime = AbstractApplicationEntity.getSecondsFromString(time)
    }

    fun getEndTimeHHmmFormatted(): String? {
        return AbstractApplicationEntity.getTimeHHmmFormatted(endTime)
    }

    fun setEndTimeHHmmFormatted(time: String) {
        endTime = AbstractApplicationEntity.getSecondsFromString(time)
    }

}
