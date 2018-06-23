package com.patres.timetable.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.patres.timetable.domain.preference.PreferenceDataTimeForTeacher
import com.patres.timetable.domain.preference.PreferenceSubjectByTeacher
import com.patres.timetable.domain.preference.PreferenceTeacherByDivision
import com.patres.timetable.domain.preference.PreferenceTeacherByPlace
import com.patres.timetable.preference.LessonDayOfWeekPreferenceElement
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import java.io.Serializable
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "teacher")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
class Teacher(

    @get:NotNull
    @Column(name = "name", nullable = false)
    var name: String? = null,

    @get:NotNull
    @Column(name = "surname", nullable = false)
    var surname: String? = null,

    @Column(name = "jhi_degree")
    var degree: String? = null,

    @Column(name = "short_name")
    var shortName: String? = null,

    @Column(name = "color_background")
    var colorBackground: String? = null,

    @Column(name = "color_text")
    var colorText: String? = null,

    @OneToMany(mappedBy = "teacher")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var timetables: Set<Timetable> = HashSet(),

    @OneToMany(cascade = [(CascadeType.ALL)], mappedBy = "teacher", orphanRemoval = true, fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var preferenceSubjectByTeacher: Set<PreferenceSubjectByTeacher> = HashSet(),

    @OneToMany(cascade = [(CascadeType.ALL)], mappedBy = "teacher", orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var preferencesTeacherByDivision: Set<PreferenceTeacherByDivision> = HashSet(),

    @OneToMany(cascade = [(CascadeType.ALL)], mappedBy = "teacher", orphanRemoval = true, fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var preferenceTeacherByPlace: Set<PreferenceTeacherByPlace> = HashSet(),

    @OneToMany(cascade = [(CascadeType.ALL)], mappedBy = "teacher", orphanRemoval = true, fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var preferenceDateTimeForTeachers: Set<PreferenceDataTimeForTeacher> = HashSet(),

    divisionOwner: Division? = null

) : AbstractDivisionOwner(divisionOwner = divisionOwner), Serializable, PreferableDateTime {

    override fun getPreferenceDateTime(lessonDayPreferenceElement: LessonDayOfWeekPreferenceElement) = preferenceDateTimeForTeachers.find { preference -> preference.lesson?.id == lessonDayPreferenceElement.lessonId && preference.dayOfWeek == lessonDayPreferenceElement.dayOfWeek }

    fun getFullName() = "${degree ?: ""} ${name ?: ""} ${surname ?: ""}"
    override fun toString(): String {
        return "Teacher(fullName=${getFullName()})"
    }


}
