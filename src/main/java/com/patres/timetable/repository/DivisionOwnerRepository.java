package com.patres.timetable.repository;

import com.patres.timetable.domain.AbstractDivisionOwner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@NoRepositoryBean
public interface DivisionOwnerRepository<EntityType extends AbstractDivisionOwner> extends JpaRepository<EntityType, Long> {

    Page<EntityType> findByDivisionOwnerId(Pageable pageable, List<Long> divisionsId);

    @Query("select distinct entity from #{#entityName} entity inner join entity.divisionOwner divisions inner join divisions.users user where user.login IN ?#{principal.username}")
    Page<EntityType> findByCurrentLogin(Pageable pageable);
//
//    @Query("select division from Division division inner join division.users where user.login IN ?#{principal.username}")
//    boolean currentLoginHasPriviligeToEntity(Long entityId);

    @Query("select CASE WHEN count(division) > 0 THEN true ELSE false END from Division division inner join division.users user on user.login IN ?#{principal.username} WHERE :newDivisionOwner = division.id")
    boolean userHasPrivilegeToAddEntity(@Param("newDivisionOwner") Long newDivisionOwner);

    @Query("select CASE WHEN :newDivisionOwner IN (select division from Division division inner join division.users user on user.login IN ?#{principal.username}) AND count(entity) > 0 THEN true ELSE false END from #{#entityName} entity inner join entity.divisionOwner divisions on entity.id = :entityId AND divisions.id IN (select division from Division division inner join division.users user on user.login IN ?#{principal.username})")
    boolean userHasPrivilegeToModifyEntity(@Param("entityId") Long entityId, @Param("newDivisionOwner") Long newDivisionOwner);

    @Query("select CASE WHEN count(entity) > 0 THEN true ELSE false END from #{#entityName} entity inner join entity.divisionOwner divisions on entity.id = :entityId AND divisions.id IN (select division from Division division inner join division.users user on user.login IN ?#{principal.username})")
    boolean userHasPrivilegeToDeleteEntity(@Param("entityId") Long entityId);


}
