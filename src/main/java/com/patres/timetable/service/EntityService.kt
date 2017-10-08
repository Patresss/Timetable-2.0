package com.patres.timetable.service

import com.patres.timetable.domain.AbstractApplicationEntity
import com.patres.timetable.service.mapper.EntityMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.reflect.ParameterizedType

@Service
@Transactional
abstract class EntityService<EntityType : AbstractApplicationEntity, EntityDtoType, EntityRepositoryType : JpaRepository<EntityType, Long>>(protected var entityRepository: EntityRepositoryType, protected var entityMapper: EntityMapper<EntityType, EntityDtoType>) {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(EntityService::class.java)
    }

    // This only works if the subclass directly subclasses this class
    open fun getEntityName(): String? {
        val entityTypeClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<EntityType>
        return entityTypeClass.simpleName
    }

    open fun save(entityDto: EntityDtoType): EntityDtoType {
        log.debug("Request to save {} : {}", getEntityName(), entityDto)
        var entity = entityMapper.toEntity(entityDto)
        entity = entityRepository.save(entity)
        return entityMapper.toDto(entity)
    }


    @Transactional(readOnly = true)
    open fun findOne(id: Long?): EntityDtoType? {
        log.debug("Request to get {} : {}", getEntityName(), id)
        val entity = entityRepository.findOne(id)
        return if (entity != null) entityMapper.toDto(entity) else null
    }

    @Transactional(readOnly = true)
    open fun findAll(pageable: Pageable): Page<EntityDtoType> {
        log.debug("Request to get all {}", getEntityName())
        return entityRepository.findAll(pageable).map { entityMapper.toDto(it) }
    }

    open fun delete(id: Long?) {
        log.debug("Request to delete {} : {}", getEntityName(), id)
        entityRepository.delete(id)
    }
}
