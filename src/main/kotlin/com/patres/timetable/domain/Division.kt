package com.patres.timetable.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.patres.timetable.domain.enumeration.DivisionType
import com.patres.timetable.domain.preference.*
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import java.io.Serializable
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

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
    @JoinTable(name = "division_parent", joinColumns = [(JoinColumn(name = "divisions_id", referencedColumnName = "id"))], inverseJoinColumns = arrayOf(JoinColumn(name = "parents_id", referencedColumnName = "id")))
    var parents: Set<Division> = HashSet(),

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "division_user", joinColumns = [(JoinColumn(name = "divisions_id", referencedColumnName = "id"))], inverseJoinColumns = arrayOf(JoinColumn(name = "users_id", referencedColumnName = "id")))
    var users: Set<User> = HashSet(),

    @OneToMany(cascade = [(CascadeType.ALL)], mappedBy = "division", orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var preferencesSubjectByDivision: Set<PreferenceSubjectByDivision> = HashSet(),

    @OneToMany(cascade = [(CascadeType.ALL)], mappedBy = "division", orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var preferencesTeacherByDivision: Set<PreferenceTeacherByDivision> = HashSet(),

    @OneToMany(cascade = [(CascadeType.ALL)], mappedBy = "division", orphanRemoval = true)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var preferenceDivisionByPlace: Set<PreferenceDivisionByPlace> = HashSet(),

    @OneToMany(cascade = [(CascadeType.ALL)], mappedBy = "division", orphanRemoval = true, fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var preferencesDataTimeForDivision: Set<PreferenceDataTimeForDivision> = HashSet(),

    divisionOwner: Division? = null

) : AbstractDivisionOwner(divisionOwner), Serializable {

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
