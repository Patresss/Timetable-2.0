package com.patres.timetable.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import java.io.Serializable
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Entity
@Table(name = "lesson")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
class Lesson(

    @get:NotNull
    @Column(name = "name", nullable = false)
    var name: String? = null,

    @get:NotNull
    @Column(name = "start_time", nullable = false)
    var startTime: Long? = null,

    @get:NotNull
    @Column(name = "end_time", nullable = false)
    var endTime: Long? = null,

    @OneToMany(mappedBy = "lesson")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private var timetables: Set<Timetable> = HashSet(),

    divisionOwner: Division? = null

) : AbstractDivisionOwner(divisionOwner = divisionOwner), Serializable {

    companion object {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")!!

        fun getTimeHHmmFormatted(seconds: Long?): String {
            seconds?.let { time ->
                return LocalTime.ofSecondOfDay(time).format(formatter)
            }
            return "00:00"
        }

        fun getSecondsFromString(time: String) = LocalTime.parse(time).toSecondOfDay().toLong()
    }

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
        val lesson = other as Lesson?
        if (lesson!!.id == null || id == null) {
            return false
        }
        return id == lesson.id
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }

}
