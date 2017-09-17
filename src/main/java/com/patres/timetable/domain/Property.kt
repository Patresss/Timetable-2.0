package com.patres.timetable.domain

import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import java.io.Serializable
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "property")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
data class Property(

    @Column(name = "property_key", nullable = false)
    var propertyKey: String = "",

    @Column(name = "property_value")
    var propertyValue: String = ""

) : AbstractDivisionOwner(), Serializable {

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }
        val property = o as Property?
        if (property!!.id == null || id == null) {
            return false
        }
        return id == property.id
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }

}
