package com.patres.timetable.repository

import com.patres.timetable.domain.Place
import com.patres.timetable.domain.Teacher
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import org.springframework.data.jpa.repository.*
import org.springframework.data.repository.query.Param

@Repository
interface PlaceRepository : DivisionOwnerRepository<Place> {

    @Query("select distinct place from Place place left join fetch place.preferredSubjects left join fetch place.preferredDivisions left join fetch place.preferredTeachers")
    fun findAllWithEagerRelationships(): List<Place>

    @Query("select place from Place place left join fetch place.preferredSubjects left join fetch place.preferredDivisions left join fetch place.preferredTeachers where place.id =:id")
    fun findOneWithEagerRelationships(@Param("id") id: Long?): Place

}
