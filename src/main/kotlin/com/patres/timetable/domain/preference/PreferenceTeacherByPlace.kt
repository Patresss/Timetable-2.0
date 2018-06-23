package com.patres.timetable.domain.preference

import com.patres.timetable.domain.Place
import com.patres.timetable.domain.Teacher
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import org.jetbrains.annotations.NotNull
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "preference_teacher_by_place")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
class PreferenceTeacherByPlace(

    @get:NotNull
    @ManyToOne
    @JoinColumn(name = "place_id")
    var place: Place? = null,

    @get:NotNull
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    var teacher: Teacher? = null,

    points: Int = 0

) : PreferenceRelation(points), Serializable
