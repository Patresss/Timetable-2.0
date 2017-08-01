package com.patres.timetable.repository;

import com.patres.timetable.domain.Properties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@SuppressWarnings("unused")
@Repository
public interface PropertiesRepository extends JpaRepository<Properties, Long> {

    @Query("select distinct prop from Properties prop inner join prop.division divisions inner join divisions.users user where user.login IN ?#{principal.username}")
    Page<Properties> findByCurrentLogin(Pageable pageable);

}
