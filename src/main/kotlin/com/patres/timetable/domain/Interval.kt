package com.patres.timetable.domain

import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import java.io.Serializable
import java.time.LocalDate
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "interval")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
class Interval(

    @Column(name = "included")
    var included: Boolean = true,

    @Column(name = "start_date")
    var startDate: LocalDate? = null,

    @Column(name = "end_date")
    var endDate: LocalDate? = null,

    @ManyToOne
    var period: Period? = null

) : AbstractApplicationEntity(), Serializable {


    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }
        val interval = other as Interval?
        if (interval!!.id == null || id == null) {
            return false
        }
        return id == interval.id
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }

    override fun toString(): String {
        return "Interval{" +
            "id= '$id'" +
            ", included='$included" +
            ", startDate='$startDate'" +
            ", endDate='$endDate'" +
            "}"
    }

}
