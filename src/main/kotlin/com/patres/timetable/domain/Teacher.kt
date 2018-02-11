package com.patres.timetable.domain

import com.fasterxml.jackson.annotation.JsonIgnore
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
    @JoinTable(name = "teacher_preferred_subject", joinColumns = arrayOf(JoinColumn(name = "teachers_id", referencedColumnName = "id")), inverseJoinColumns = arrayOf(JoinColumn(name = "preferred_subjects_id", referencedColumnName = "id")))
    var preferredSubjects: Set<Subject> = HashSet(),

    @ManyToMany(mappedBy = "preferredTeachers")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var preferredDivisions: Set<Division> = HashSet(),

    @ManyToMany(mappedBy = "preferredTeachers")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var preferredPlaces: Set<Place> = HashSet(),

    divisionOwner: Division? = null

) : AbstractDivisionOwner(divisionOwner = divisionOwner), Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }
        val teacher = other as Teacher?
        if (teacher!!.id == null || id == null) {
            return false
        }
        return id == teacher.id
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }

}
