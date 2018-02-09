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
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
class Property(

    @get:NotNull
    @Column(name = "property_key", nullable = false)
    var propertyKey: String? = null,

    @Column(name = "property_value")
    var propertyValue: String? = null

) : AbstractDivisionOwner(), Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }
        val property = other as Property?
        if (property!!.id == null || id == null) {
            return false
        }
        return id == property.id
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }

}