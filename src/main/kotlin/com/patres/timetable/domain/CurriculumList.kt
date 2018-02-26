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
    var name: String? = null,

    @OneToMany(cascade = [(CascadeType.ALL)], orphanRemoval = true, mappedBy = "curriculumList")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var curriculums: Set<Curriculum> = HashSet(),

    @Column(name = "start_date")
    var startDate: LocalDate? = null,

    @Column(name = "end_date")
    var endDate: LocalDate? = null,

    @ManyToOne
    var period: Period? = null,

    divisionOwner: Division? = null

) : AbstractDivisionOwner(divisionOwner = divisionOwner), Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }
        val curriculumList = other as CurriculumList?
        if (curriculumList!!.id == null || id == null) {
            return false
        }
        return id == curriculumList.id
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }

}
