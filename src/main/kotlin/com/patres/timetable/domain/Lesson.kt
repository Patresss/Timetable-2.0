package com.patres.timetable.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.patres.timetable.domain.preference.PreferenceDataTimeForTeacher
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import java.io.Serializable
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.validation.constraints.NotNull
import kotlin.collections.HashSet

@Entity
@Table(name = "lesson")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private var timetables: Set<Timetable> = HashSet(),

    @OneToMany(mappedBy = "lesson", orphanRemoval = true)
    var preferenceDateTimeForTeachers: Set<PreferenceDataTimeForTeacher> = HashSet(),

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

    override fun toString(): String {
        return "Lesson(name=$name, startTime=${getStartTimeHHmmFormatted()}, endTime=${getEndTimeHHmmFormatted()})"
    }

}
