package com.patres.timetable.service.dto


import java.io.Serializable
import java.util.*

abstract class AbstractApplicationEntityDTO() : Serializable {
    var id: Long? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }
        val entity = other as AbstractApplicationEntityDTO?
        if (entity!!.id == null || id == null) {
            return false
        }
        return id == entity.id
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }


}
