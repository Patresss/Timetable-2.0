package com.patres.timetable.domain

import javax.persistence.ManyToOne
import javax.persistence.MappedSuperclass
import javax.validation.constraints.NotNull
import java.io.Serializable

@MappedSuperclass
abstract class AbstractDivisionOwner : AbstractApplicationEntity(), Serializable {

    @ManyToOne
    @NotNull
    var divisionOwner: Division? = null

    fun divisionOwner(divisionOwner: Division): AbstractDivisionOwner {
        this.divisionOwner = divisionOwner
        return this
    }

    companion object {

        private const val serialVersionUID = 8302717018638550091L
    }

}
