package com.patres.timetable.domain.preference

import com.patres.timetable.domain.Lesson
import org.jetbrains.annotations.NotNull
import java.io.Serializable
import javax.persistence.*

@MappedSuperclass
abstract class PreferenceForDataTime(

    @get:NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id")
    var lesson: Lesson? = null,

    @Column(name = "day_of_week", nullable = false)
    var dayOfWeek: Int? = null,

    points: Int = 0

) : PreferenceRelation(points), Serializable
