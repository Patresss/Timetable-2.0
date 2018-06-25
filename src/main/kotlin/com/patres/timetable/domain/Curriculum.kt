package com.patres.timetable.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.patres.timetable.domain.enumeration.EventType
import com.patres.timetable.preference.Preference
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import java.io.Serializable
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "curriculum")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
class Curriculum(

    @Column(name = "name")
    var name: String? = null,

    @get:NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false)
    var type: EventType = EventType.LESSON,

    @Column(name = "activities_number")
    var numberOfActivities: Long = 0,

    @Column(name = "every_week")
    var everyWeek: Long = 1,

    @Column(name = "start_with_week")
    var startWithWeek: Long = 1,

    @ManyToOne
    var place: Place? = null,

    @ManyToOne
    var subject: Subject? = null,

    @ManyToOne
    var teacher: Teacher? = null,

    @ManyToOne
    var division: Division? = null,

    @ManyToOne
    var lesson: Lesson? = null,

    @ManyToMany(mappedBy = "curriculums")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    var curriculumnListes: Set<CurriculumList> = HashSet(),

    divisionOwner: Division? = null

) : AbstractDivisionOwner(divisionOwner = divisionOwner), Serializable
