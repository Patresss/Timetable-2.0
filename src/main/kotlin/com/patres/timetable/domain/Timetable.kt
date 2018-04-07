package com.patres.timetable.domain

import com.patres.timetable.domain.enumeration.EventType
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import java.io.Serializable
import java.time.LocalDate
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "timetable")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
class Timetable(

    @get:NotNull
    @Column(name = "title", nullable = false)
    var title: String? = null,

    @Column(name = "start_time")
    var startTime: Long? = null,

    @Column(name = "end_time")
    var endTime: Long? = null,

    @Column(name = "start_date")
    var startDate: LocalDate? = null,

    @Column(name = "end_date")
    var endDate: LocalDate? = null,

    @Column(name = "date")
    var date: LocalDate? = null,

    @get:NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    var type: EventType? = null,

    @Column(name = "every_week")
    var everyWeek: Long = 1,

    @Column(name = "start_with_week")
    var startWithWeek: Long = 1,

    @Column(name = "description")
    var description: String? = null,

    @Column(name = "color_background")
    var colorBackground: String? = null,

    @Column(name = "color_text")
    var colorText: String? = null,

    @Column(name = "in_monday")
    var inMonday: Boolean = false,

    @Column(name = "in_tuesday")
    var inTuesday: Boolean = false,

    @Column(name = "in_wednesday")
    var inWednesday: Boolean = false,

    @Column(name = "in_thursday")
    var inThursday: Boolean = false,

    @Column(name = "in_friday")
    var inFriday: Boolean = false,

    @Column(name = "in_saturday")
    var inSaturday: Boolean = false,

    @Column(name = "in_sunday")
    var inSunday: Boolean = false,

    @ManyToOne
    var place: Place? = null,

    @ManyToOne
    var subject: Subject? = null,

    @ManyToOne
    var teacher: Teacher? = null,

    @ManyToOne
    var division: Division? = null,

    @ManyToOne
    var lesson: Lesson? = null,

    @ManyToOne
    var period: Period? = null,

    divisionOwner: Division? = null

) : AbstractDivisionOwner(divisionOwner = divisionOwner), Serializable {

    fun getStartTimeHHmmFormatted(): String? {
        return getTimeHHmmFormatted(startTime)
    }

    fun setStartTimeHHmmFormatted(time: String) {
        startTime = getSecondsFromString(time)
    }

    fun getEndTimeHHmmFormatted(): String? {
        return getTimeHHmmFormatted(endTime)
    }

    fun setEndTimeHHmmFormatted(time: String) {
        endTime = getSecondsFromString(time)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }
        val timetable = other as Timetable?
        if (timetable!!.id == null || id == null) {
            return false
        }
        return id == timetable.id
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }

}
