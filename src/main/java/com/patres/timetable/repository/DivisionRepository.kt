package com.patres.timetable.repository

import com.patres.timetable.domain.Division
import org.springframework.stereotype.Repository

import org.springframework.data.jpa.repository.*
import org.springframework.data.repository.query.Param

@Repository
interface DivisionRepository : JpaRepository<Division, Long> {

    @Query("select distinct division from Division division left join fetch division.parents left join fetch division.users left join fetch division.preferredTeachers left join fetch division.preferredSubjects")
    fun findAllWithEagerRelationships(): List<Division>

    @Query("select division from Division division left join fetch division.parents left join fetch division.users left join fetch division.preferredTeachers left join fetch division.preferredSubjects where division.id =:id")
    fun findOneWithEagerRelationships(@Param("id") id: Long?): Division

}
