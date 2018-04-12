package com.patres.timetable.repository

import com.patres.timetable.domain.AbstractDivisionOwner
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@NoRepositoryBean
interface DivisionOwnerRepository<EntityType : AbstractDivisionOwner> : JpaRepository<EntityType, Long> {

    fun findByDivisionOwnerId(pageable: Pageable, divisionsId: List<Long>): Page<EntityType>

    fun findByDivisionOwnerId(divisionsId: List<Long>): Set<EntityType>

    @Query("select distinct entity from #{#entityName} entity inner join entity.divisionOwner divisions inner join divisions.users user where user.login IN ?#{principal.username}")
    fun findByCurrentLogin(pageable: Pageable): Page<EntityType>

    @Query("select CASE WHEN count(division) > 0 THEN true ELSE false END from Division division inner join division.users user on user.login IN ?#{principal.username} WHERE :newDivisionOwner = division.id")
    fun userHasPrivilegeToAddEntity(@Param("newDivisionOwner") newDivisionOwner: Long?): Boolean

    @Query("select CASE WHEN :newDivisionOwner IN (select division from Division division inner join division.users user on user.login IN ?#{principal.username}) AND count(entity) > 0 THEN true ELSE false END from #{#entityName} entity inner join entity.divisionOwner divisions on entity.id = :entityId AND divisions.id IN (select division from Division division inner join division.users user on user.login IN ?#{principal.username})")
    fun userHasPrivilegeToModifyEntity(@Param("entityId") entityId: Long?, @Param("newDivisionOwner") newDivisionOwner: Long?): Boolean

    @Query("select CASE WHEN count(entity) > 0 THEN true ELSE false END from #{#entityName} entity inner join entity.divisionOwner divisions on entity.id = :entityId AND divisions.id IN (select division from Division division inner join division.users user on user.login IN ?#{principal.username})")
    fun userHasPrivilegeToDeleteEntity(@Param("entityId") entityId: Long?): Boolean


}
