package com.patres.timetable.repository;

import com.patres.timetable.domain.Division;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface DivisionRepository extends JpaRepository<Division,Long> {

    @Query("select distinct division from Division division left join fetch division.parents left join fetch division.users left join fetch division.preferredTeachers left join fetch division.preferredSubjects")
    List<Division> findAllWithEagerRelationships();

    @Query("select division from Division division left join fetch division.parents left join fetch division.users left join fetch division.preferredTeachers left join fetch division.preferredSubjects where division.id =:id")
    Division findOneWithEagerRelationships(@Param("id") Long id);

}
