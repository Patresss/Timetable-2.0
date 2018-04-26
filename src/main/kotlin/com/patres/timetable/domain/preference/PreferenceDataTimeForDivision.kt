package com.patres.timetable.domain.preference

import com.patres.timetable.domain.Lesson
import com.patres.timetable.domain.Division
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "preference_data_time_for_division")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
class PreferenceDataTimeForDivision(

    @ManyToOne
    @JoinColumn(name = "division_id")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var division: Division? = null,

    lesson: Lesson? = null,

    dayOfWeek: Int? = null,

    points: Int = 0

) : PreferenceForDataTime(lesson, dayOfWeek, points), Serializable
