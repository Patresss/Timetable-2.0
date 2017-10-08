package com.patres.timetable.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import java.io.Serializable
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
    private var timetables: Set<Timetable> = HashSet()

) : AbstractDivisionOwner(), Serializable {

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }
        val lesson = o as Lesson?
        if (lesson!!.id == null || id == null) {
            return false
        }
        return id == lesson.id
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }

}
