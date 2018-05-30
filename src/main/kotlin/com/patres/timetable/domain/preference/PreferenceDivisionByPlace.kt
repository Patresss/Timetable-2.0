package com.patres.timetable.domain.preference

import com.patres.timetable.domain.*
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import org.jetbrains.annotations.NotNull
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "preference_division_by_place")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
class PreferenceDivisionByPlace(

    @get:NotNull
    @ManyToOne
    @JoinColumn(name = "division_id")
    var division: Division? = null,

    @get:NotNull
    @ManyToOne
    @JoinColumn(name = "place_id")
    var place: Place? = null,

    @Column(name = "points", nullable = false)
    var points: Int = 0

) : AbstractApplicationEntity(), Serializable
