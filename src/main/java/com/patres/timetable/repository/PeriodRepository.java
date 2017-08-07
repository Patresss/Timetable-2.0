package com.patres.timetable.repository;

import com.patres.timetable.domain.Period;
import com.patres.timetable.domain.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Period entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PeriodRepository extends JpaRepository<Period,Long> {

    Page<Period> findByDivisionOwnerId(Pageable pageable, List<Long> divisionsId);

    @Query("select distinct period from Period period inner join period.divisionOwner divisions inner join divisions.users user where user.login IN ?#{principal.username}")
    Page<Period> findByCurrentLogin(Pageable pageable);

}
