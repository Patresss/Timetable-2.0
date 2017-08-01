package com.patres.timetable.repository;

import com.patres.timetable.domain.Subject;
import com.patres.timetable.domain.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Subject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubjectRepository extends JpaRepository<Subject,Long> {

    Page<Subject> findByDivisionId(Pageable pageable, List<Long> divisionsId);

    @Query("select distinct subject from Subject subject inner join subject.division divisions inner join divisions.users user where user.login IN ?#{principal.username}")
    Page<Subject> findByCurrentLogin(Pageable pageable);


}
