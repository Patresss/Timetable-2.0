package com.patres.timetable.repository;

import com.patres.timetable.domain.Timetable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

@SuppressWarnings("unused")
@Repository
public interface TimetableRepository extends JpaRepository<Timetable,Long> {

}
