package com.patres.timetable.domain.preference

import com.patres.timetable.domain.AbstractApplicationEntity
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class PreferenceRelation(

    @Column(name = "points", nullable = false)
    var points: Int = 0

) : AbstractApplicationEntity(), Serializable
