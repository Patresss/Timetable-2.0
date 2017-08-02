package com.patres.timetable.repository;

import com.patres.timetable.domain.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@SuppressWarnings("unused")
@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    @Query("select distinct prop from Property prop inner join prop.division divisions inner join divisions.users user where user.login IN ?#{principal.username}")
    Page<Property> findByCurrentLogin(Pageable pageable);

}
