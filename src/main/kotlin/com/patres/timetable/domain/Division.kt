package com.patres.timetable.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.patres.timetable.domain.enumeration.DivisionType
import com.patres.timetable.domain.preference.PreferenceDataTimeForDivision
import com.patres.timetable.domain.preference.PreferenceDivisionByPlace
import com.patres.timetable.domain.preference.PreferenceSubjectByDivision
import com.patres.timetable.domain.preference.PreferenceTeacherByDivision
import com.patres.timetable.preference.LessonDayOfWeekPreferenceElement
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import java.io.Serializable
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull
import kotlin.jvm.Transient

@Entity
@Table(name = "division")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
class Division(

    @get:NotNull
    @Column(name = "name", nullable = false)
    var name: String? = null,

    @Column(name = "short_name")
    var shortName: String? = null,

    @Column(name = "number_of_people")
    var numberOfPeople: Long? = null,

    @get:NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "division_type", nullable = false)
    var divisionType: DivisionType? = null,

    @Column(name = "color_background")
    var colorBackground: String? = null,

    @Column(name = "color_text")
    var colorText: String? = null,

    @OneToMany(mappedBy = "division")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var timetables: Set<Timetable> = HashSet(),

    @OneToMany(mappedBy = "divisionOwner")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var divisionPlaces: Set<Place> = HashSet(),

    @OneToMany(mappedBy = "divisionOwner")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var divisionTeachers: Set<Teacher> = HashSet(),

    @OneToMany(mappedBy = "divisionOwner")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var divisionSubjects: Set<Subject> = HashSet(),

    @OneToMany(mappedBy = "divisionOwner")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var divisionLessons: Set<Lesson> = HashSet(),

    @OneToMany(mappedBy = "divisionOwner")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var divisionPeriods: Set<Period> = HashSet(),

    @OneToMany(mappedBy = "divisionOwner")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var divisionProperties: Set<Property> = HashSet(),

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "division_parent", joinColumns = [(JoinColumn(name = "divisions_id", referencedColumnName = "id"))], inverseJoinColumns = [(JoinColumn(name = "parents_id", referencedColumnName = "id"))])
    var parents: Set<Division> = HashSet(),

    @ManyToMany
    @JoinTable(name = "division_parent", joinColumns = [(JoinColumn(name = "parents_id", referencedColumnName = "id"))], inverseJoinColumns = [(JoinColumn(name = "divisions_id", referencedColumnName = "id"))])
    var children: Set<Division> = HashSet(),

    @OneToMany(mappedBy = "school")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var users: Set<User> = HashSet(),

    @OneToMany(cascade = [(CascadeType.ALL)], mappedBy = "division", orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var preferencesSubjectByDivision: Set<PreferenceSubjectByDivision> = HashSet(),

    @OneToMany(cascade = [(CascadeType.ALL)], mappedBy = "division", orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var preferencesTeacherByDivision: Set<PreferenceTeacherByDivision> = HashSet(),

    @OneToMany(cascade = [(CascadeType.ALL)], mappedBy = "division", orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var preferenceDivisionByPlace: Set<PreferenceDivisionByPlace> = HashSet(),

    @OneToMany(cascade = [(CascadeType.ALL)], mappedBy = "division", orphanRemoval = true, fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var preferencesDateTimeForDivision: Set<PreferenceDataTimeForDivision> = HashSet(),

    divisionOwner: Division? = null

) : AbstractDivisionOwner(divisionOwner), Serializable {

    fun getPreferenceDataTime(lessonDayPreferenceElement: LessonDayOfWeekPreferenceElement) = preferencesDateTimeForDivision.find { preference -> preference.lesson?.id == lessonDayPreferenceElement.lessonId && preference.dayOfWeek == lessonDayPreferenceElement.dayOfWeek }


    @Transient
    var subgroups:Set<Division> = emptySet()
        get() = children.filter { DivisionType.SUBGROUP == it.divisionType }.toSet()

    fun calculateContainersWithSetOfSubgroup(): Set<Division> {
        return parents.filter { it.divisionType == DivisionType.SET_OF_SUBGROUPS }.toSet()
    }

    fun calculateAllTakenDivisionFromDivision(): Set<Division> {
        val takenDivisions = HashSet<Division>()
            val setOfGroups = calculateContainersWithSetOfSubgroup()
            takenDivisions.add(this)
            parents
                .filter { parent -> parent.divisionType == DivisionType.CLASS }
                .forEach { parent ->
                    takenDivisions.add(parent)
                    parent.children
                        .filter { it.divisionType == DivisionType.SUBGROUP && it.parents.intersect(setOfGroups).isEmpty() }
                        .forEach { child -> takenDivisions.add(child) }
                }
            children
                .filter { child -> child.divisionType == DivisionType.SUBGROUP }
                .forEach { child -> takenDivisions.add(child) }
        return takenDivisions
    }

    override fun toString(): String {
        return "Division{" +
            "id= $id" +
            ", name=' $name" +
            ", shortName='$shortName" +
            ", numberOfPeople='$numberOfPeople" +
            ", divisionType='$divisionType" +
            ", colorBackground='$colorBackground" +
            ", colorText='$colorText" +
            "}"
    }

}
