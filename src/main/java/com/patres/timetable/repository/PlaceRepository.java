package com.patres.timetable.repository;

import com.patres.timetable.domain.Place;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Place entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlaceRepository extends JpaRepository<Place,Long> {
    
    @Query("select distinct place from Place place left join fetch place.preferredSubjects left join fetch place.preferredDivisions left join fetch place.preferredTeachers")
    List<Place> findAllWithEagerRelationships();

    @Query("select place from Place place left join fetch place.preferredSubjects left join fetch place.preferredDivisions left join fetch place.preferredTeachers where place.id =:id")
    Place findOneWithEagerRelationships(@Param("id") Long id);
    
}
