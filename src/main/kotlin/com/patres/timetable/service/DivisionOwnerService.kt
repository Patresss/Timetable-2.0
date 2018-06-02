package com.patres.timetable.service

import com.patres.timetable.domain.AbstractDivisionOwner
import com.patres.timetable.domain.Division
import com.patres.timetable.repository.DivisionOwnerRepository
import com.patres.timetable.repository.UserRepository
import com.patres.timetable.security.AuthoritiesConstants
import com.patres.timetable.security.SecurityUtils
import com.patres.timetable.service.dto.AbstractDivisionOwnerDTO
import com.patres.timetable.service.mapper.EntityMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.reflect.ParameterizedType
import java.util.*


@Service
@Transactional
abstract class DivisionOwnerService<EntityType : AbstractDivisionOwner, EntityDtoType : AbstractDivisionOwnerDTO, EntityRepositoryType : DivisionOwnerRepository<EntityType>>(entityRepository: EntityRepositoryType, entityMapper: EntityMapper<EntityType, EntityDtoType>) : EntityService<EntityType, EntityDtoType, EntityRepositoryType>(entityRepository, entityMapper) {

    @Autowired
    lateinit var userRepository: UserRepository

    companion object {
        private val log = LoggerFactory.getLogger(DivisionOwnerService::class.java)
    }

    override fun save(entityDto: EntityDtoType): EntityDtoType {
        DivisionOwnerService.log.debug("Request to save {} : {}", getEntityName(), entityDto)
        val login = SecurityUtils.getCurrentUserLogin()
        login?.let {
            val userFromRepository = userRepository.findOneByLogin(login)
            if (userFromRepository?.authorities?.map { it.name }?.contains(AuthoritiesConstants.SCHOOL_ADMIN) == true && userFromRepository.school?.id != null) {
                entityDto.divisionOwnerId = userFromRepository.school?.id
            }
        }
        var entity = entityMapper.toEntity(entityDto)
        entity = entityRepository.save(entity)
        return entityMapper.toDto(entity)
    }

    @Transactional(readOnly = true)
    open fun calculateDefaultEntityBySchoolId(entity: EntityType, schoolId: Long): EntityDtoType {
        entity.divisionOwner = Division().apply { id =  schoolId}
        return entityMapper.toDto(entity)
    }

    @Transactional(readOnly = true)
    open fun findByDivisionOwnerId(pageable: Pageable, divisionsId: List<Long>): Page<EntityDtoType> {
        log.debug("Request to get {} by Division owners id", getEntityName())
        return entityRepository.findByDivisionOwnerId(pageable, divisionsId).map{ entityMapper.toDtoWithSampleForm(it) }
    }

    @Transactional(readOnly = true)
    open fun findByCurrentLogin(pageable: Pageable): Page<EntityDtoType> {
        log.debug("Request to get all {} by current user", getEntityName())
        return when {
            SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) -> {
                log.debug("Return {} for user with role {} ", getEntityName(), AuthoritiesConstants.ADMIN)
                entityRepository.findAll(pageable).map{ entityMapper.toDtoWithSampleForm(it) }
            }
            SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCHOOL_ADMIN) -> {
                log.debug("Return {} for user with role {} ", getEntityName(), AuthoritiesConstants.SCHOOL_ADMIN)
                entityRepository.findByCurrentLogin(pageable).map{ entityMapper.toDtoWithSampleForm(it) }
            }
            else -> {
                log.debug("Return empty list of {} for user without needed rule ", getEntityName())
                PageImpl(ArrayList())
            }
        }
    }

    override fun getEntityName(): String? {
        // This only works if the subclass directly subclasses this class
        val entityTypeClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<*>
        return entityTypeClass.simpleName
    }

    open fun hasPrivilegeToAddEntity(entityDto: AbstractDivisionOwnerDTO): Boolean {
        return when {
            SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) -> true
            SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCHOOL_ADMIN) -> {
                val login = SecurityUtils.getCurrentUserLogin()
                return login?.let {
                    val userFromRepository = userRepository.findOneByLogin(login)
                    entityDto.divisionOwnerId == userFromRepository?.school?.id
                }?: false
            }
            else -> false
        }
    }

//    open fun hasPrivilegeToModifyEntity(entityDto: AbstractDivisionOwnerDTO): Boolean {
//        return when {
//            SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) -> true
//            SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCHOOL_ADMIN) -> {
//                val entityId = entityDto.id
//                val divisionOwnerId = entityDto.divisionOwnerId
//                entityRepository.userHasPrivilegeToModifyEntity(entityId, divisionOwnerId)
//            }
//            else -> false
//        }
//    }


    // TODO add checking prevoius school
    open fun hasPrivilegeToModifyEntity(entityDto: AbstractDivisionOwnerDTO): Boolean {
        return when {
            SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) -> true
            SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCHOOL_ADMIN) -> {
                val login = SecurityUtils.getCurrentUserLogin()
                return login?.let {
                    val userFromRepository = userRepository.findOneByLogin(login)
                    entityDto.divisionOwnerId == userFromRepository?.school?.id
                }?: false
            }
            else -> false
        }
    }
    open fun hasPrivilegeToDeleteEntity(entityId: Long?): Boolean {
        return when {
            SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) -> true
            SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCHOOL_ADMIN) -> entityRepository.userHasPrivilegeToDeleteEntity(entityId)
            else -> false
        }

    }

}
