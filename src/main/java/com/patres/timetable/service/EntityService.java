package com.patres.timetable.service;

import com.patres.timetable.domain.AbstractApplicationEntity;
import com.patres.timetable.service.mapper.EntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;

@Service
@Transactional
public abstract class EntityService<EntityType extends AbstractApplicationEntity, EntityDtoType, EntityRepositoryType extends JpaRepository<EntityType, Long>> {

    private final Logger log = LoggerFactory.getLogger(EntityService.class);

    protected EntityRepositoryType entityRepository;

    protected EntityMapper<EntityType, EntityDtoType> entityMapper;

    public EntityService(EntityRepositoryType entityRepository, EntityMapper<EntityType, EntityDtoType> entityMapper) {
        this.entityRepository = entityRepository;
        this.entityMapper = entityMapper;
    }

    public EntityDtoType save(EntityDtoType entityDto) {
        log.debug("Request to save {} : {}", getEntityName(), entityDto);
        EntityType entity = entityMapper.toEntity(entityDto);
        entity = entityRepository.save(entity);
        return entityMapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    public EntityDtoType findOne(Long id) {
        log.debug("Request to get {} : {}", getEntityName(), id);
        EntityType entity = entityRepository.findOne(id);
        return entityMapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    public Page<EntityDtoType> findAll(Pageable pageable) {
        log.debug("Request to get all {}", getEntityName());
        return entityRepository.findAll(pageable).map(entityMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete {} : {}", getEntityName(), id);
        entityRepository.delete(id);
    }

    @SuppressWarnings("unchecked")
    public String getEntityName() {
        // This only works if the subclass directly subclasses this class
        Class<EntityType> entityTypeClass = (Class<EntityType>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return entityTypeClass.getSimpleName();
    }
}
