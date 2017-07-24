package com.patres.timetable.repository;

import com.patres.timetable.domain.Interval;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Interval entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IntervalRepository extends JpaRepository<Interval,Long> {
    
}
