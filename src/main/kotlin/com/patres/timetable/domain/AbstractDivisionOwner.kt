package com.patres.timetable.domain

import javax.persistence.ManyToOne
import javax.persistence.MappedSuperclass
import javax.validation.constraints.NotNull
import java.io.Serializable

@MappedSuperclass
abstract class AbstractDivisionOwner(

    @ManyToOne
    open var divisionOwner: Division? = null

) : AbstractApplicationEntity()
