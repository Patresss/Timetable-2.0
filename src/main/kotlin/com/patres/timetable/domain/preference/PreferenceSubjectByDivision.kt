package com.patres.timetable.domain.preference

import com.patres.timetable.domain.AbstractApplicationEntity
import com.patres.timetable.domain.Division
import com.patres.timetable.domain.Subject
import com.patres.timetable.domain.Teacher
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import org.jetbrains.annotations.NotNull
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "preference_subject_by_division")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
class PreferenceSubjectByDivision(

    @get:NotNull
    @ManyToOne
    @JoinColumn(name = "subject_id")
    var subject: Subject? = null,

    @get:NotNull
    @ManyToOne
    @JoinColumn(name = "division_id")
    var division: Division? = null,

    @Column(name = "points", nullable = false)
    var points: Int = 0

) : AbstractApplicationEntity(), Serializable
