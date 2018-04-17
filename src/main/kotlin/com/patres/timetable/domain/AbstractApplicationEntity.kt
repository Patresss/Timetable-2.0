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

        private val formatter = DateTimeFormatter.ofPattern("HH:mm")!!

        fun getTimeHHmmFormatted(seconds: Long?): String? {
            seconds?.let { time ->
                return LocalTime.ofSecondOfDay(time).format(formatter)
            }
            return null
        }

        fun getSecondsFromString(time: String) = LocalTime.parse(time).toSecondOfDay().toLong()
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    var id: Long? = null

}
