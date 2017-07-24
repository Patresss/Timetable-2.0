package com.patres.timetable.repository;

import com.patres.timetable.domain.Properties;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Properties entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PropertiesRepository extends JpaRepository<Properties,Long> {
    
}
