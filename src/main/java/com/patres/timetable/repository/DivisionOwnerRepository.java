package com.patres.timetable.repository;

import com.patres.timetable.domain.AbstractDivisionOwner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@NoRepositoryBean
public interface DivisionOwnerRepository<EntityType extends AbstractDivisionOwner> extends JpaRepository<EntityType, Long> {

    Page<EntityType> findByDivisionOwnerId(Pageable pageable, List<Long> divisionsId);

    @Query("select distinct entity from #{#entityName} entity inner join entity.divisionOwner divisions inner join divisions.users user where user.login IN ?#{principal.username}")
    Page<EntityType> findByCurrentLogin(Pageable pageable);

}
