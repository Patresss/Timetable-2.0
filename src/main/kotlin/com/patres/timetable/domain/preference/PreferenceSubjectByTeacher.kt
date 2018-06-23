package com.patres.timetable.domain.preference

import com.patres.timetable.domain.Subject
import com.patres.timetable.domain.Teacher
import org.jetbrains.annotations.NotNull
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "preference_subject_by_teacher")
class PreferenceSubjectByTeacher(

    @get:NotNull
    @ManyToOne
    @JoinColumn(name = "subject_id")
    var subject: Subject? = null,

    @get:NotNull
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    var teacher: Teacher? = null,

    points: Int = 0

) : PreferenceRelation(points), Serializable
