package com.patres.timetable.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.patres.timetable.domain.preference.*
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

    @OneToMany(cascade = [(CascadeType.ALL)], mappedBy = "teacher", orphanRemoval = true, fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var preferenceSubjectByTeacher: Set<PreferenceSubjectByTeacher> = HashSet(),

    @OneToMany(cascade = [(CascadeType.ALL)], mappedBy = "teacher", orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var preferencesTeacherByDivision: Set<PreferenceTeacherByDivision> = HashSet(),

    @OneToMany(cascade = [(CascadeType.ALL)], mappedBy = "teacher", orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var preferenceTeacherByPlace: Set<PreferenceTeacherByPlace> = HashSet(),

    @OneToMany(cascade = [(CascadeType.ALL)], mappedBy = "teacher", orphanRemoval = true, fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var preferenceDataTimeForTeachers: Set<PreferenceDataTimeForTeacher> = HashSet(),

    divisionOwner: Division? = null

) : AbstractDivisionOwner(divisionOwner = divisionOwner), Serializable {

    fun getFullName() = "${degree ?: ""} ${name ?: ""} ${surname ?: ""}"

}
