package com.patres.timetable.domain.preference

import com.patres.timetable.domain.Lesson
import com.patres.timetable.domain.Place
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "preference_data_time_for_place")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
class PreferenceDataTimeForPlace(

    @ManyToOne
    @JoinColumn(name = "place_id")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var place: Place? = null,

    lesson: Lesson? = null,

    dayOfWeek: Int? = null,

    points: Int = 0

) : PreferenceForDataTime(lesson, dayOfWeek, points), Serializable
