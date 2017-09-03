package com.patres.timetable.repository;

import com.patres.timetable.domain.Period;
import com.patres.timetable.domain.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface PeriodRepository extends DivisionOwnerRepository<Period> {

}
