package com.patres.timetable.domain.preference

import com.patres.timetable.domain.Division
import com.patres.timetable.domain.Subject
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import org.jetbrains.annotations.NotNull
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "preference_subject_by_division")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
class PreferenceSubjectByDivision(

    @get:NotNull
    @ManyToOne
    @JoinColumn(name = "subject_id")
    var subject: Subject? = null,

    @get:NotNull
    @ManyToOne
    @JoinColumn(name = "division_id")
    var division: Division? = null,

    points: Int = 0

) : PreferenceRelation(points), Serializable
