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

    @OneToMany(cascade = [(CascadeType.ALL)], mappedBy = "place", orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var preferenceSubjectByPlace: Set<PreferenceSubjectByPlace> = HashSet(),

    @OneToMany(cascade = [(CascadeType.ALL)], mappedBy = "place", orphanRemoval = true)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var preferenceTeacherByPlace: Set<PreferenceTeacherByPlace> = HashSet(),

    @OneToMany(cascade = [(CascadeType.ALL)], mappedBy = "place", orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var preferenceDivisionByPlace: Set<PreferenceDivisionByPlace> = HashSet(),


    @OneToMany(cascade = [(CascadeType.ALL)], mappedBy = "place", orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var preferencesDataTimeForPlace: Set<PreferenceDataTimeForPlace> = HashSet(),

    divisionOwner: Division? = null

) : AbstractDivisionOwner(divisionOwner = divisionOwner), Serializable {


    fun isPlaceTooSmallEnough(division: Division?): Boolean {
        numberOfSeats?.let {seats ->
            division?.numberOfPeople?.let {numberOfPeople ->
                return seats < numberOfPeople
            }
        }
        return true
    }

}
