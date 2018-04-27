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
            left join fetch place.preferenceTeacherByPlace
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
            left join fetch place.preferenceTeacherByPlace
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
            place
        from
            Place place
            left join fetch place.preferenceSubjectByPlace preferenceSubjectByPlace
            left join fetch preferenceSubjectByPlace.subject
            left join fetch preferenceSubjectByPlace.place
            left join fetch place.preferenceDivisionByPlace preferenceDivisionByPlace
            left join fetch preferenceDivisionByPlace.division
            left join fetch preferenceDivisionByPlace.place
            left join fetch place.preferenceTeacherByPlace preferenceTeacherByPlace
            left join fetch preferenceTeacherByPlace.teacher
            left join fetch preferenceTeacherByPlace.place
            left join fetch place.preferencesDataTimeForPlace preferencesDataTimeForPlace
            left join fetch preferencesDataTimeForPlace.lesson
            left join fetch preferencesDataTimeForPlace.place
        where
            place.divisionOwner.id =:divisionOwnerId
            """)
    fun findByDivisionOwnerIdWithPreference(@Param("divisionOwnerId") id: Long?): Set<Place>?
//
//    @Query("""
//        select
//            place
//        from
//            Place place
//        where
//            place.divisionOwner.id =:divisionOwnerId
//            """)
//    fun tmp1(@Param("divisionOwnerId") id: Long?): Set<Place>?
//
//    @Query("""
//        select
//            place
//        from
//            Place place
//            left join fetch place.preferenceSubjectByPlace
//        where
//            place.divisionOwner.id =:divisionOwnerId
//            """)
//    fun tmp2(@Param("divisionOwnerId") id: Long?): Set<Place>?
//
//    @Query("""
//        select
//            place
//        from
//            Place place
//            left join fetch place.preferenceSubjectByPlace
//            left join fetch place.preferenceDivisionByPlace
//        where
//            place.divisionOwner.id =:divisionOwnerId
//            """)
//    fun tmp3(@Param("divisionOwnerId") id: Long?): Set<Place>?
//
//    @Query("""
//        select
//            place
//        from
//            Place place
//            left join fetch place.preferenceSubjectByPlace preferenceSubjectByPlace
//            left join fetch preferenceSubjectByPlace.subject
//            left join fetch preferenceSubjectByPlace.place
//            left join fetch place.preferenceDivisionByPlace preferenceDivisionByPlace
//            left join fetch preferenceDivisionByPlace.division
//            left join fetch preferenceDivisionByPlace.place
//        where
//            place.divisionOwner.id =:divisionOwnerId
//            """)
//    fun tmp3more(@Param("divisionOwnerId") id: Long?): Set<Place>?
//
//    @Query("""
//        select
//            place
//        from
//            Place place
//            left join fetch place.preferenceSubjectByPlace
//            left join fetch place.preferenceDivisionByPlace
//            left join fetch place.preferenceTeacherByPlace
//        where
//            place.divisionOwner.id =:divisionOwnerId
//            """)
//    fun tmp4(@Param("divisionOwnerId") id: Long?): Set<Place>?
//
//    @Query("""
//        select
//            place
//        from
//            Place place
//            left join fetch place.preferenceSubjectByPlace
//            left join fetch place.preferenceDivisionByPlace
//            left join fetch place.preferenceTeacherByPlace
//        where
//            place.divisionOwner.id =:divisionOwnerId
//            """)
//    fun tmp4more(@Param("divisionOwnerId") id: Long?): Set<Place>?
//
//
//
//    @Query("""
//        select
//            place
//        from
//            Place place
//            left join fetch place.preferencesDataTimeForPlace
//        where
//            place.divisionOwner.id =:divisionOwnerId
//            """)
//    fun tmp5(@Param("divisionOwnerId") id: Long?): Set<Place>?
//

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
