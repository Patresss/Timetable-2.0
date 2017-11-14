package com.patres.timetable.domain

import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

/**
 * An authority (a security role) used by Spring Security.
 */
@Entity
@Table(name = "jhi_authority")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
class Authority(

    @get:NotNull
    @Size(min = 0, max = 50)
    @Id
    @Column(length = 50)
    var name: String = ""

) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }

        val authority = other as Authority?

        return !if (true) name != authority!!.name else true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

}
