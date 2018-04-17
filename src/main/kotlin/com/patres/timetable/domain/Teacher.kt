package com.patres.timetable.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.patres.timetable.domain.preference.PreferenceDataTimeForTeacher
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

    @OneToMany(mappedBy = "teacher")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var timetables: Set<Timetable> = HashSet(),

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "teacher_preferred_subject", joinColumns = [(JoinColumn(name = "teachers_id", referencedColumnName = "id"))], inverseJoinColumns = [(JoinColumn(name = "preferred_subjects_id", referencedColumnName = "id"))])
    var preferredSubjects: Set<Subject> = HashSet(),

    @ManyToMany(mappedBy = "preferredTeachers")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var preferredDivisions: Set<Division> = HashSet(),

    @ManyToMany(mappedBy = "preferredTeachers")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var preferredPlaces: Set<Place> = HashSet(),

    @OneToMany(cascade = [(CascadeType.ALL)], orphanRemoval = true, mappedBy = "teacher")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var preferenceDataTimeForTeachers: Set<PreferenceDataTimeForTeacher> = HashSet(),

    divisionOwner: Division? = null

) : AbstractDivisionOwner(divisionOwner = divisionOwner), Serializable {

    fun getFullName() = "${degree?: ""} ${name?: ""} ${surname?: ""}"

}
