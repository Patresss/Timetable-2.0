package com.patres.timetable.domain.preference

import com.patres.timetable.domain.Lesson
import com.patres.timetable.domain.Teacher
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "preference_data_time_for_teacher")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
class PreferenceDataTimeForTeacher(

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    var teacher: Teacher? = null,

    lesson: Lesson? = null,

    dayOfWeek: Int? = null,

    points: Int = 0

) : PreferenceForDataTime(lesson, dayOfWeek, points), Serializable
