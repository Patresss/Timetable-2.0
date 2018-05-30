package com.patres.timetable.repository

import com.patres.timetable.domain.Place
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface PlaceRepository : DivisionOwnerRepository<Place> {

    @Query("""
        select distinct
            place
        from
            Place place
            left join fetch place.preferenceSubjectByPlace
            left join fetch place.preferenceDivisionByPlace
            left join fetch place.preferencesDataTimeForPlace

        """)
    fun findAllWithEagerRelationships(): List<Place>

    @Query("""
        select
            place
        from
            Place place
            left join fetch place.preferenceSubjectByPlace
            left join fetch place.preferenceDivisionByPlace
            left join fetch place.preferencesDataTimeForPlace
        where
            place.id =:id
        """)
    fun findOneWithEagerRelationships(@Param("id") id: Long?): Place?

    @Query("""
        select
            place
        from
            Place place
            left join fetch place.preferenceSubjectByPlace
            left join fetch place.preferenceDivisionByPlace
            left join fetch place.preferenceTeacherByPlace
            left join fetch place.preferencesDataTimeForPlace
        where
            place.id =:id
            """)
    fun findOneWithPreference(@Param("id") id: Long?): Place?


    @Query("""
        select
            place.id
        from
            Place place
        where
            place.divisionOwner.id = :divisionOwnerId and
            place.numberOfSeats < :numberOfSeats
        """)
    fun findIdByDivisionOwnerIdAndNumberOfSeatsLessThan(@Param("divisionOwnerId") divisionOwnerId: Long?, @Param("numberOfSeats") numberOfSeats: Long?): Set<Long>

}
