package com.patres.timetable.preference

import com.patres.timetable.domain.*
import java.time.LocalDate

data class PreferenceDependency (
    val subject: Subject? = null,
    val teacher: Teacher? = null,
    val division: Division? = null,
    val place: Place? = null,
    val period: Period? = null,
    val lesson: Lesson? = null,
    var notTimetableId: Long? = null,
    val date: LocalDate? = null,
    var inMonday: Boolean = false,
    var inTuesday: Boolean = false,
    var inWednesday: Boolean = false,
    var inThursday: Boolean = false,
    var inFriday: Boolean = false,
    var inSaturday: Boolean = false,
    var inSunday: Boolean = false,
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
