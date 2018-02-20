package com.patres.timetable.service.dto


import com.patres.timetable.domain.enumeration.EventType
import java.io.Serializable
import java.time.LocalDate
import java.util.*
import javax.validation.constraints.NotNull

class TimetableDTO(

    @get:NotNull
    var title: String? = null,

    var startTime: String? = null,

    var endTime: String? = null,

    var startDate: LocalDate? = null,

    var endDate: LocalDate? = null,

    var date: LocalDate? = null,

    @get:NotNull
    var type: EventType? = null,

    var everyWeek: Long = 1,

    var startWithWeek: Long = 1,

    var description: String? = null,

    var colorBackground: String? = null,

    var colorText: String? = null,

    var inMonday: Boolean = false,

    var inTuesday: Boolean = false,

    var inWednesday: Boolean = false,

    var inThursday: Boolean = false,

    var inFriday: Boolean = false,

    var inSaturday: Boolean = false,

    var inSunday: Boolean = false
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

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }

        val timetableDTO = other as TimetableDTO?
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
            ", inMonday='" + inMonday + "'" +
            ", inTuesday='" + inTuesday + "'" +
            ", inWednesday='" + inWednesday + "'" +
            ", inThursday='" + inThursday + "'" +
            ", inFriday='" + inFriday + "'" +
            ", inSaturday='" + inSaturday + "'" +
            ", inSunday='" + inSunday + "'" +
            "}"
    }
}
