package com.patres.timetable.service;

import com.patres.timetable.domain.Property;
import com.patres.timetable.repository.PropertyRepository;
import com.patres.timetable.service.dto.PropertyDTO;
import com.patres.timetable.service.mapper.PropertyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class PropertyService {

    private final Logger log = LoggerFactory.getLogger(PropertyService.class);

    private final PropertyRepository propertyRepository;

    private final PropertyMapper propertyMapper;

    public PropertyService(PropertyRepository propertyRepository, PropertyMapper propertyMapper) {
        this.propertyRepository = propertyRepository;
        this.propertyMapper = propertyMapper;
    }

    public PropertyDTO save(PropertyDTO propertyDTO) {
        log.debug("Request to save Property : {}", propertyDTO);
        Property property = propertyMapper.toEntity(propertyDTO);
        property = propertyRepository.save(property);
        return propertyMapper.toDto(property);
    }

    @Transactional(readOnly = true)
    public Page<PropertyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Property");
        return propertyRepository.findAll(pageable)
            .map(propertyMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<PropertyDTO> findByCurrentLogin(Pageable pageable) {
        log.debug("Request to get Property by current user");
        return propertyRepository.findByCurrentLogin(pageable)
            .map(propertyMapper::toDto);
    }

    @Transactional(readOnly = true)
    public PropertyDTO findOne(Long id) {
        log.debug("Request to get Property : {}", id);
        Property property = propertyRepository.findOne(id);
        return propertyMapper.toDto(property);
    }

    public void delete(Long id) {
        log.debug("Request to delete Property : {}", id);
        propertyRepository.delete(id);
    }
}
