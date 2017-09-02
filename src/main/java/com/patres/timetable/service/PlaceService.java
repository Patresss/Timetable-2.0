package com.patres.timetable.service;

import com.patres.timetable.domain.Place;
import com.patres.timetable.repository.PlaceRepository;
import com.patres.timetable.service.dto.PlaceDTO;
import com.patres.timetable.service.mapper.EntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class PlaceService extends EntityService<Place, PlaceDTO, PlaceRepository> {

    private final Logger log = LoggerFactory.getLogger(PlaceService.class);

    public PlaceService(PlaceRepository entityRepository, EntityMapper<Place, PlaceDTO> entityMapper) {
        super(entityRepository, entityMapper);
    }

    @Transactional(readOnly = true)
    public Page<PlaceDTO> findByDivisionOwnerId(Pageable pageable, List<Long> divisionsId) {
        log.debug("Request to get Place by Division owners id");
        return entityRepository.findByDivisionOwnerId(pageable, divisionsId).map(entityMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<PlaceDTO> findByCurrentLogin(Pageable pageable) {
        log.debug("Request to get all Place by current user");
        return entityRepository.findByCurrentLogin(pageable).map(entityMapper::toDto);
    }

    @Transactional(readOnly = true)
    public PlaceDTO findOne(Long id) {
        log.debug("Request to get Place : {}", id);
        Place place = entityRepository.findOneWithEagerRelationships(id);
        return entityMapper.toDto(place);
    }

}
