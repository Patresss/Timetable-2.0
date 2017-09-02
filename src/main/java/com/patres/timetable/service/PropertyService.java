package com.patres.timetable.service;

import com.patres.timetable.domain.Property;
import com.patres.timetable.repository.PropertyRepository;
import com.patres.timetable.service.dto.PropertyDTO;
import com.patres.timetable.service.mapper.EntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class PropertyService extends EntityService<Property, PropertyDTO, PropertyRepository> {

    private final Logger log = LoggerFactory.getLogger(PropertyService.class);

    public PropertyService(PropertyRepository entityRepository, EntityMapper<Property, PropertyDTO> entityMapper) {
        super(entityRepository, entityMapper);
    }

    @Transactional(readOnly = true)
    public Page<PropertyDTO> findByCurrentLogin(Pageable pageable) {
        log.debug("Request to get Property by current user");
        return entityRepository.findByCurrentLogin(pageable).map(entityMapper::toDto);
    }

}
