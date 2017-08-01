package com.patres.timetable.repository;

import com.patres.timetable.domain.Lesson;
import com.patres.timetable.domain.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Lesson entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LessonRepository extends JpaRepository<Lesson,Long> {

    Page<Lesson> findByDivisionId(Pageable pageable, List<Long> divisionsId);

    @Query("select distinct lesson from Lesson lesson inner join lesson.division divisions inner join divisions.users user where user.login IN ?#{principal.username}")
    Page<Lesson> findByCurrentLogin(Pageable pageable);
}
