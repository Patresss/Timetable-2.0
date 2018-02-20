package com.patres.timetable.domain

import javax.persistence.*
import java.io.Serializable
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Objects

@MappedSuperclass
abstract class AbstractApplicationEntity : Serializable {

    companion object {
        private const val serialVersionUID = 7296270053887329327L

        val formatter = DateTimeFormatter.ofPattern("HH:mm")!!

        fun getTimeHHmmFormatted(seconds: Long?): String {
            seconds?.let { time ->
                return LocalTime.ofSecondOfDay(time).format(formatter)
            }
            return "00:00"
        }

        fun getSecondsFromString(time: String) = LocalTime.parse(time).toSecondOfDay().toLong()
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    var id: Long? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }
        val entity = other as AbstractApplicationEntity?
        if (entity!!.id == null || id == null) {
            return false
        }
        return id == entity.id
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }

}
