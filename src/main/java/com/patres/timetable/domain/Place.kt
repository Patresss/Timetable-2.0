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
data class Place(

    @Column(name = "name", nullable = false)
    var name: String = "",

    @Column(name = "number_of_seats")
    var numberOfSeats: Long = Long.MAX_VALUE,

    @Column(name = "short_name")
    var shortName: String? = null,

    @Column(name = "color_background")
    var colorBackground: String? = null,

    @Column(name = "color_text")
    var colorText: String? = null,

    @OneToMany(mappedBy = "place")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var timetables: MutableSet<Timetable> = HashSet(),

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "place_preferred_subject", joinColumns = arrayOf(JoinColumn(name = "places_id", referencedColumnName = "id")), inverseJoinColumns = arrayOf(JoinColumn(name = "preferred_subjects_id", referencedColumnName = "id")))
    var preferredSubjects: MutableSet<Subject> = HashSet(),

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "place_preferred_division", joinColumns = arrayOf(JoinColumn(name = "places_id", referencedColumnName = "id")), inverseJoinColumns = arrayOf(JoinColumn(name = "preferred_divisions_id", referencedColumnName = "id")))
    var preferredDivisions: MutableSet<Division> = HashSet(),

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "place_preferred_teacher", joinColumns = arrayOf(JoinColumn(name = "places_id", referencedColumnName = "id")), inverseJoinColumns = arrayOf(JoinColumn(name = "preferred_teachers_id", referencedColumnName = "id")))
    var preferredTeachers: MutableSet<Teacher> = HashSet()
    ) : AbstractDivisionOwner(), Serializable {


    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }
        val place = o as Place?
        if (place!!.id == null || id == null) {
            return false
        }
        return id == place.id
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }


}
