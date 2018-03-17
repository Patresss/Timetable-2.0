package com.patres.timetable.repository

import com.patres.timetable.domain.Place
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface PlaceRepository : DivisionOwnerRepository<Place> {

    @Query("select distinct place from Place place left join fetch place.preferredSubjects left join fetch place.preferredDivisions left join fetch place.preferredTeachers")
    fun findAllWithEagerRelationships(): List<Place>

    @Query("select place from Place place left join fetch place.preferredSubjects left join fetch place.preferredDivisions left join fetch place.preferredTeachers where place.id =:id")
    fun findOneWithEagerRelationships(@Param("id") id: Long?): Place?

}
