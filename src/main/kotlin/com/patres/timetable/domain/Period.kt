package com.patres.timetable.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy

import javax.persistence.*
import java.io.Serializable
import java.time.LocalDate
import java.util.HashSet
import java.util.Objects
import javax.validation.constraints.NotNull

@Entity
@Table(name = "period")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
class Period(

    @get:NotNull
    @Column(name = "name", nullable = false)
    var name: String? = null,

    @OneToMany(cascade = [(CascadeType.ALL)], orphanRemoval = true, mappedBy = "period")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    var intervalTimes: Set<Interval> = HashSet(),

    @OneToMany(mappedBy = "period")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    var timetables: Set<Timetable> = HashSet(),

    divisionOwner: Division? = null

) : AbstractDivisionOwner(divisionOwner = divisionOwner), Serializable {

    fun getFirstDay(): LocalDate? {
        return intervalTimes.filter { it.includedState }.sortedBy { it.startDate }.first().startDate
    }

    override fun toString(): String {
        return "Period{" +
            "id='$id'" +
            ", name='$name'" +
            "}"
    }

}
