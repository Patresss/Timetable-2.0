package com.patres.timetable.domain

import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import java.io.Serializable
import java.time.LocalDate
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "curriculum_list")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
class CurriculumList(

    @get:NotNull
    @Column(name = "name")
    var name: String = "",

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "curriculum_list_curriculum", joinColumns = [(JoinColumn(name = "curriculum_list_id", referencedColumnName = "id"))], inverseJoinColumns = [(JoinColumn(name = "curriculum_id", referencedColumnName = "id"))])
    var curriculums: Set<Curriculum> = HashSet(),

    @ManyToOne
    var period: Period? = null,

    divisionOwner: Division? = null

) : AbstractDivisionOwner(divisionOwner = divisionOwner), Serializable
