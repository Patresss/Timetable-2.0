package com.patres.timetable.repository

import com.patres.timetable.domain.Division
import com.patres.timetable.domain.enumeration.DivisionType
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface DivisionRepository : DivisionOwnerRepository<Division> {

    @Query("""
        select distinct
            division
        from
            Division division
            left join fetch division.parents
            left join fetch division.users
            left join fetch division.preferredTeachers
            left join fetch division.preferredSubjects
            left join fetch division.preferenceDivisionByPlace
            left join fetch division.preferencesDataTimeForDivision
        """)
    fun findAllWithEagerRelationships(): List<Division>

    @Query("""
        select
            division
        from
            Division division
            left join fetch division.parents
            left join fetch division.users
            left join fetch division.preferredTeachers
            left join fetch division.preferredSubjects
            left join fetch division.preferenceDivisionByPlace
            left join fetch division.preferencesDataTimeForDivision

        where
            division.id =:id
        """)
    fun findOneWithEagerRelationships(@Param("id") id: Long?): Division?

    @Query("""
        select
            division
        from
            Division division
            left join fetch division.preferredTeachers
            left join fetch division.preferredSubjects
            left join fetch division.preferenceDivisionByPlace
            left join fetch division.preferencesDataTimeForDivision
        where
            division.id =:id
        """)
    fun findOneWithPreference(@Param("id") id: Long?): Division?

    fun findAllByDivisionType(divisionType: DivisionType): List<Division>

    fun findByParentsIdAndDivisionTypeOrderByName(parentId: Long, divisionType: DivisionType): List<Division>

}
