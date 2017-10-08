package com.patres.timetable.service.dto


import com.patres.timetable.domain.enumeration.EventType
import java.io.Serializable
import java.time.LocalDate
import java.util.*
import javax.validation.constraints.NotNull

class TimetableDTO(

    @get:NotNull
    var title: String? = null,

    var startTime: Long? = null,

    var endTime: Long? = null,

    var startDate: LocalDate? = null,

    var endDate: LocalDate? = null,

    var date: LocalDate? = null,

    @get:NotNull
    var type: EventType? = null,

    var everyWeek: Long? = null,

    var startWithWeek: Long? = null,

    var description: String? = null,

    var colorBackground: String? = null,

    var colorText: String? = null,

    var isInMonday: Boolean? = null,

    var isInTuesday: Boolean? = null,

    var isInWednesday: Boolean? = null,

    var isInThursday: Boolean? = null,

    var isInFriday: Boolean? = null,

    var isInSaturday: Boolean? = null,

    var isInSunday: Boolean? = null
    ,
    var placeId: Long? = null,

    var placeName: String? = null,

    var subjectId: Long? = null,

    var subjectName: String? = null,

    var teacherId: Long? = null,

    var teacherSurname: String? = null,

    var divisionId: Long? = null,

    var divisionName: String? = null,

    var lessonId: Long? = null,

    var lessonName: String? = null,

    var periodId: Long? = null,

    var periodName: String? = null

) : AbstractApplicationEntityDTO(), Serializable {

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }

        val timetableDTO = o as TimetableDTO?
        if (timetableDTO!!.id == null || id == null) {
            return false
        }
        return id == timetableDTO.id
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }

    override fun toString(): String {
        return "TimetableDTO{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", startTime='" + startTime + "'" +
            ", endTime='" + endTime + "'" +
            ", startDate='" + startDate + "'" +
            ", endDate='" + endDate + "'" +
            ", date='" + date + "'" +
            ", type='" + type + "'" +
            ", everyWeek='" + everyWeek + "'" +
            ", startWithWeek='" + startWithWeek + "'" +
            ", description='" + description + "'" +
            ", colorBackground='" + colorBackground + "'" +
            ", colorText='" + colorText + "'" +
            ", inMonday='" + isInMonday + "'" +
            ", inTuesday='" + isInTuesday + "'" +
            ", inWednesday='" + isInWednesday + "'" +
            ", inThursday='" + isInThursday + "'" +
            ", inFriday='" + isInFriday + "'" +
            ", inSaturday='" + isInSaturday + "'" +
            ", inSunday='" + isInSunday + "'" +
            "}"
    }
}
