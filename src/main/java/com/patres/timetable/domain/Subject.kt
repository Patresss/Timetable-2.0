package com.patres.timetable.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import java.io.Serializable
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "subject")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
data class Subject(

    @Column(name = "name", nullable = false)
    var name: String = "",

    @Column(name = "short_name")
    var shortName: String? = null,

    @Column(name = "color_background")
    var colorBackground: String? = null,

    @Column(name = "color_text")
    var colorText: String? = null,

    @OneToMany(mappedBy = "subject")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var timetables: MutableSet<Timetable> = HashSet(),

    @ManyToMany(mappedBy = "preferredSubjects")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var preferredTeachers: MutableSet<Teacher> = HashSet(),

    @ManyToMany(mappedBy = "preferredSubjects")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var preferredDivisions: MutableSet<Division> = HashSet(),

    @ManyToMany(mappedBy = "preferredSubjects")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var preferredPlaces: MutableSet<Place> = HashSet()

) : AbstractDivisionOwner(), Serializable {


    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }
        val subject = o as Subject?
        if (subject!!.id == null || id == null) {
            return false
        }
        return id == subject.id
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }

}
