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
@Table(name = "curriculum")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
class Curriculum(

    @Column(name = "start_time")
    var startTime: Long? = null,

    @Column(name = "end_time")
    var endTime: Long? = null,

    @get:NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false)
    var type: EventType = EventType.LESSON,

    @Column(name = "every_week")
    var everyWeek: Long = 1,

    @Column(name = "start_with_week")
    var startWithWeek: Long = 1,

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
    var curriculumList: CurriculumList? = null


) : AbstractApplicationEntity(), Serializable {

    fun getStartTimeHHmmFormatted(): String {
        return getTimeHHmmFormatted(startTime)
    }

    fun setStartTimeHHmmFormatted(time: String) {
        startTime = getSecondsFromString(time)
    }

    fun getEndTimeHHmmFormatted(): String {
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
        val curruculum = other as Curriculum?
        if (curruculum!!.id == null || id == null) {
            return false
        }
        return id == curruculum.id
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }

}