package com.patres.timetable.domain

import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import java.io.Serializable
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Entity
@Table(name = "property")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
class Property(

    @get:NotNull
    @Column(name = "property_key", nullable = false)
    var propertyKey: String? = null,

    @Column(name = "property_value")
    var propertyValue: String? = null

) : AbstractDivisionOwner(), Serializable
