package com.patres.timetable.domain

import javax.persistence.*
import java.io.Serializable
import java.util.Objects

@MappedSuperclass
abstract class AbstractApplicationEntity : Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    var id: Long? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }
        val entity = o as AbstractApplicationEntity?
        if (entity!!.id == null || id == null) {
            return false
        }
        return id == entity.id
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }

    companion object {
        private const val serialVersionUID = 7296270053887329327L
    }

}
