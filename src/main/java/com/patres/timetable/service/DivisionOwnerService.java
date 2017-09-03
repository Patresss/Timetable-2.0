package com.patres.timetable.service;

import com.patres.timetable.domain.AbstractDivisionOwner;
import com.patres.timetable.repository.DivisionOwnerRepository;
import com.patres.timetable.security.AuthoritiesConstants;
import com.patres.timetable.security.SecurityUtils;
import com.patres.timetable.service.mapper.EntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public abstract class DivisionOwnerService<EntityType extends AbstractDivisionOwner, EntityDtoType, EntityRepositoryType extends DivisionOwnerRepository<EntityType>> extends EntityService<EntityType, EntityDtoType, EntityRepositoryType> {

    private final Logger log = LoggerFactory.getLogger(DivisionOwnerService.class);

    public DivisionOwnerService(EntityRepositoryType entityRepository, EntityMapper<EntityType, EntityDtoType> entityMapper) {
        super(entityRepository, entityMapper);
    }

    @Transactional(readOnly = true)
    public Page<EntityDtoType> findByDivisionOwnerId(Pageable pageable, List<Long> divisionsId) {
        log.debug("Request to get {} by Division owners id", getEntityName());
        return entityRepository.findByDivisionOwnerId(pageable, divisionsId).map(entityMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<EntityDtoType> findByCurrentLogin(Pageable pageable) {
        log.debug("Request to get all {} by current user", getEntityName());
        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            log.debug("Return {} for user with role {} ", getEntityName(), AuthoritiesConstants.ADMIN);
            return entityRepository.findAll(pageable).map(entityMapper::toDto);
        } else if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCHOOL_ADMIN)) {
            log.debug("Return {} for user with role {} ", getEntityName(), AuthoritiesConstants.SCHOOL_ADMIN);
            return entityRepository.findByCurrentLogin(pageable).map(entityMapper::toDto);
        } else {
            log.debug("Return empty list of {} for user without needed rule ", getEntityName());
            return new PageImpl<>(new ArrayList<>());
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public String getEntityName() {
        // This only works if the subclass directly subclasses this class
        Class<EntityType> entityTypeClass = (Class<EntityType>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return entityTypeClass.getSimpleName();
    }

}
