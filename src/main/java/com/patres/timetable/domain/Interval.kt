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
data class Interval(

    @Column(name = "included")
    var isIncluded: Boolean? = null,

    @Column(name = "start_date")
    var startDate: LocalDate? = null,

    @Column(name = "end_date")
    var endDate: LocalDate? = null,

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
        val interval = o as Interval?
        if (interval!!.id == null || id == null) {
            return false
        }
        return id == interval.id
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }

}
