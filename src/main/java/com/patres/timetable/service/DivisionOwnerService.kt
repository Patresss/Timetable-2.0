package com.patres.timetable.service

import com.patres.timetable.domain.AbstractDivisionOwner
import com.patres.timetable.repository.DivisionOwnerRepository
import com.patres.timetable.security.AuthoritiesConstants
import com.patres.timetable.security.SecurityUtils
import com.patres.timetable.service.dto.AbstractDivisionOwnerDTO
import com.patres.timetable.service.mapper.EntityMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import java.lang.reflect.ParameterizedType
import java.util.ArrayList


@Service
@Transactional
abstract class DivisionOwnerService<EntityType : AbstractDivisionOwner, EntityDtoType : AbstractDivisionOwnerDTO, EntityRepositoryType : DivisionOwnerRepository<EntityType>>(entityRepository: EntityRepositoryType, entityMapper: EntityMapper<EntityType, EntityDtoType>) : EntityService<EntityType, EntityDtoType, EntityRepositoryType>(entityRepository, entityMapper) {

    companion object {
        private val log = LoggerFactory.getLogger(DivisionOwnerService::class.java)
    }

    @Transactional(readOnly = true)
    open fun findByDivisionOwnerId(pageable: Pageable, divisionsId: List<Long>): Page<EntityDtoType> {
        log.debug("Request to get {} by Division owners id", getEntityName())
        return entityRepository.findByDivisionOwnerId(pageable, divisionsId).map{ entityMapper.toDto(it) }
    }

    @Transactional(readOnly = true)
    open fun findByCurrentLogin(pageable: Pageable): Page<EntityDtoType> {
        log.debug("Request to get all {} by current user", getEntityName())
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            log.debug("Return {} for user with role {} ", getEntityName(), AuthoritiesConstants.ADMIN)
            return entityRepository.findAll(pageable).map{ entityMapper.toDto(it) }
        } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCHOOL_ADMIN)) {
            log.debug("Return {} for user with role {} ", getEntityName(), AuthoritiesConstants.SCHOOL_ADMIN)
            return entityRepository.findByCurrentLogin(pageable).map{ entityMapper.toDto(it) }
        } else {
            log.debug("Return empty list of {} for user without needed rule ", getEntityName())
            return PageImpl(ArrayList())
        }
    }

    override fun getEntityName(): String? {
        // This only works if the subclass directly subclasses this class
        val entityTypeClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<EntityType>
        return entityTypeClass.simpleName
    }

    fun hasPriviligeToAddEntity(entityDto: AbstractDivisionOwnerDTO): Boolean {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            return true
        } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCHOOL_ADMIN)) {
            val divisionOwnerId = entityDto.divisionOwnerId
            return entityRepository.userHasPrivilegeToAddEntity(divisionOwnerId)
        } else {
            return false
        }
    }

    fun hasPriviligeToModifyEntity(entityDto: AbstractDivisionOwnerDTO): Boolean {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            return true
        } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCHOOL_ADMIN)) {
            val entityId = entityDto.id
            val divisionOwnerId = entityDto.divisionOwnerId
            return entityRepository.userHasPrivilegeToModifyEntity(entityId, divisionOwnerId)
        } else {
            return false
        }
    }

    fun hasPriviligeToDeleteEntity(entityId: Long?): Boolean {
        return if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            true
        } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCHOOL_ADMIN)) {
            entityRepository.userHasPrivilegeToDeleteEntity(entityId)
        } else {
            false
        }

    }

}
