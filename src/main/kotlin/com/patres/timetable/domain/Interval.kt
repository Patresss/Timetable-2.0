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

    @Column(name = "included_state")
    var includedState: Boolean = true,

    @Column(name = "start_date")
    var startDate: LocalDate? = null,

    @Column(name = "end_date")
    var endDate: LocalDate? = null,

    @ManyToOne
    var period: Period? = null

) : AbstractApplicationEntity(), Serializable {

    override fun toString(): String {
        return "Interval{" +
            "id= '$id'" +
            ", includedState='$includedState" +
            ", startDate='$startDate'" +
            ", endDate='$endDate'" +
            "}"
    }

}
