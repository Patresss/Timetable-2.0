package com.patres.timetable.domain

import com.patres.timetable.domain.enumeration.EventType
import com.patres.timetable.preference.hierarchy.PreferenceTimetableHierarchy
import com.patres.timetable.service.dto.preference.PreferenceContainerDTO
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import java.io.Serializable
import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "timetable")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
class Timetable(

    @Column(name = "title")
    var title: String? = null,

    @Column(name = "start_time")
    var startTime: Long? = null,

    @Column(name = "end_time")
    var endTime: Long? = null,

    @Column(name = "start_date")
    var startDate: LocalDate? = null,

    @Column(name = "end_date")
    var endDate: LocalDate? = null,

    @Column(name = "date")
    var date: LocalDate? = null,

    @get:NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    var type: EventType? = EventType.LESSON,

    @Column(name = "every_week")
    var everyWeek: Long = 1,

    @Column(name = "start_with_week")
    var startWithWeek: Long = 1,

    @Column(name = "description")
    var description: String? = null,

    @Column(name = "color_background")
    var colorBackground: String? = null,

    @Column(name = "color_text")
    var colorText: String? = null,

    @Column(name = "day_of_week")
    var dayOfWeek: Int? = null,

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

    @ManyToOne
    var period: Period? = null,

    @Transient
    var preference: PreferenceContainerDTO = PreferenceContainerDTO(),

    @Transient
    var preferenceTimetableHierarchy: PreferenceTimetableHierarchy = PreferenceTimetableHierarchy(),

    @Transient
    var points: Int = 0,

    divisionOwner: Division? = null

) : AbstractDivisionOwner(divisionOwner = divisionOwner), Serializable {

    constructor(curriculum: Curriculum, period: Period?) : this(
        divisionOwner = curriculum.divisionOwner,
        place = curriculum.place,
        subject = curriculum.subject,
        teacher = curriculum.teacher,
        division = curriculum.division,
        lesson = curriculum.lesson,
        period = period
    )

    fun getStartTimeHHmmFormatted(): String? {
        return getTimeHHmmFormatted(startTime)
    }

    fun setStartTimeHHmmFormatted(time: String) {
        startTime = getSecondsFromString(time)
    }

    fun getEndTimeHHmmFormatted(): String? {
        return getTimeHHmmFormatted(endTime)
    }

    fun setEndTimeHHmmFormatted(time: String) {
        endTime = getSecondsFromString(time)
    }

    override fun toString(): String {
        return "Timetable(id = $id, dayOfWeek=$dayOfWeek, placeId=${place?.id}, placeName=${place?.name}, subjectId=${subject?.id}, subjectName=${subject?.name}, divisionId=${division?.id}, divisionName=${division?.name}, teacherId=${teacher?.id}, teacherName=${teacher?.getFullName()}, periodId=${period?.id}, periodName=${period?.name}, lessonId=${lesson?.id}, lessonName=${lesson?.name},points=$points)"
    }


}
