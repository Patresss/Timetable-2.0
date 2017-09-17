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
data class Timetable(

    @NotNull
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

    @Column(name = "jhi_date")
    var date: LocalDate? = null,

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type", nullable = false)
    var type: EventType? = null,

    @Column(name = "every_week")
    var everyWeek: Long? = null,

    @Column(name = "start_with_week")
    var startWithWeek: Long? = null,

    @Column(name = "description")
    var description: String? = null,

    @Column(name = "color_background")
    var colorBackground: String? = null,

    @Column(name = "color_text")
    var colorText: String? = null,

    @Column(name = "in_monday")
    var isInMonday: Boolean? = null,

    @Column(name = "in_tuesday")
    var isInTuesday: Boolean? = null,

    @Column(name = "in_wednesday")
    var isInWednesday: Boolean? = null,

    @Column(name = "in_thursday")
    var isInThursday: Boolean? = null,

    @Column(name = "in_friday")
    var isInFriday: Boolean? = null,

    @Column(name = "in_saturday")
    var isInSaturday: Boolean? = null,

    @Column(name = "in_sunday")
    var isInSunday: Boolean? = null,

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
    var period: Period? = null

) : AbstractApplicationEntity(), Serializable {

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }
        val timetable = o as Timetable?
        if (timetable!!.id == null || id == null) {
            return false
        }
        return id == timetable.id
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }

}
