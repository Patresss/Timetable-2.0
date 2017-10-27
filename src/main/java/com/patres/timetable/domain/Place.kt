package com.patres.timetable.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import java.io.Serializable
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "place")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
class Place(

    @get:NotNull
    @Column(name = "name", nullable = false)
    var name: String? = null,

    @Column(name = "number_of_seats")
    var numberOfSeats: Long? = null,

    @Column(name = "short_name")
    var shortName: String? = null,

    @Column(name = "color_background")
    var colorBackground: String? = null,

    @Column(name = "color_text")
    var colorText: String? = null,

    @OneToMany(mappedBy = "place")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var timetables: Set<Timetable> = HashSet(),

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "place_preferred_subject", joinColumns = arrayOf(JoinColumn(name = "places_id", referencedColumnName = "id")), inverseJoinColumns = arrayOf(JoinColumn(name = "preferred_subjects_id", referencedColumnName = "id")))
    var preferredSubjects: Set<Subject> = HashSet(),

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "place_preferred_division", joinColumns = arrayOf(JoinColumn(name = "places_id", referencedColumnName = "id")), inverseJoinColumns = arrayOf(JoinColumn(name = "preferred_divisions_id", referencedColumnName = "id")))
    var preferredDivisions: Set<Division> = HashSet(),

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "place_preferred_teacher", joinColumns = arrayOf(JoinColumn(name = "places_id", referencedColumnName = "id")), inverseJoinColumns = arrayOf(JoinColumn(name = "preferred_teachers_id", referencedColumnName = "id")))
    var preferredTeachers: Set<Teacher> = HashSet()
    ) : AbstractDivisionOwner(), Serializable {


    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }
        val place = other as Place?
        if (place!!.id == null || id == null) {
            return false
        }
        return id == place.id
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }


}
