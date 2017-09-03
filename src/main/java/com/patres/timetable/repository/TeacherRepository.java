package com.patres.timetable.repository;

import com.patres.timetable.domain.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface TeacherRepository extends DivisionOwnerRepository<Teacher> {

    @Query("select distinct teacher from Teacher teacher left join fetch teacher.preferredSubjects")
    List<Teacher> findAllWithEagerRelationships();

    @Query("select teacher from Teacher teacher left join fetch teacher.preferredSubjects where teacher.id =:id")
    Teacher findOneWithEagerRelationships(@Param("id") Long id);

}
