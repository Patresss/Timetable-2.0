package com.patres.timetable.repository;

import com.patres.timetable.domain.Interval;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Set;


/**
 * Spring Data JPA repository for the Interval entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IntervalRepository extends JpaRepository<Interval,Long> {

    Page<Interval> findByPeriodId(Pageable pageable, Long periodId);
}
