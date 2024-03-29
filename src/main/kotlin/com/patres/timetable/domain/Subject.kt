package com.patres.timetable.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.patres.timetable.domain.preference.PreferenceDataTimeForSubject
import com.patres.timetable.domain.preference.PreferenceSubjectByDivision
import com.patres.timetable.domain.preference.PreferenceSubjectByPlace
import com.patres.timetable.domain.preference.PreferenceSubjectByTeacher
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import java.io.Serializable
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "subject")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
class Subject(

    @get:NotNull
    @Column(name = "name", nullable = false)
    var name: String? = null,

    @Column(name = "short_name")
    var shortName: String? = null,

    @Column(name = "color_background")
    var colorBackground: String? = null,

    @Column(name = "color_text")
    var colorText: String? = null,

    @OneToMany(mappedBy = "subject")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    var timetables: Set<Timetable> = HashSet(),

    @OneToMany(cascade = [(CascadeType.ALL)], mappedBy = "subject", orphanRemoval = true)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    var preferenceSubjectByTeacher: Set<PreferenceSubjectByTeacher> = HashSet(),

    @OneToMany(cascade = [(CascadeType.ALL)], mappedBy = "subject", orphanRemoval = true)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    var preferenceSubjectByPlace: Set<PreferenceSubjectByPlace> = HashSet(),

    @OneToMany(cascade = [(CascadeType.ALL)], mappedBy = "subject", orphanRemoval = true)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    var preferencesSubjectByDivision: Set<PreferenceSubjectByDivision> = HashSet(),

    @OneToMany(cascade = [(CascadeType.ALL)], mappedBy = "subject", orphanRemoval = true, fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    var preferencesDateTimeForSubject: Set<PreferenceDataTimeForSubject> = HashSet()

) : AbstractDivisionOwner(), Serializable {

    override fun toString(): String {
        return "Subject(name=$name)"
    }
}
