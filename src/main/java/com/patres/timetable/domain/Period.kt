package com.patres.timetable.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy

import javax.persistence.*
import java.io.Serializable
import java.util.HashSet
import java.util.Objects

@Entity
@Table(name = "period")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
data class Period(

    @Column(name = "name", nullable = false)
    var name: String = "",

    @OneToMany(cascade = arrayOf(CascadeType.ALL), orphanRemoval = true, mappedBy = "period")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var intervalTimes: MutableSet<Interval> = HashSet(),

    @OneToMany(mappedBy = "period")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var timetables: MutableSet<Timetable> = HashSet()

) : AbstractDivisionOwner(), Serializable {

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }
        val period = o as Period?
        if (period!!.id == null || id == null) {
            return false
        }
        return id == period.id
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }

}
