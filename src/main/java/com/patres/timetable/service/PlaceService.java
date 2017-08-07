package com.patres.timetable.service;

import com.patres.timetable.domain.Place;
import com.patres.timetable.repository.PlaceRepository;
import com.patres.timetable.service.dto.PeriodDTO;
import com.patres.timetable.service.dto.PlaceDTO;
import com.patres.timetable.service.mapper.PlaceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class PlaceService {

    private final Logger log = LoggerFactory.getLogger(PlaceService.class);

    private final PlaceRepository placeRepository;

    private final PlaceMapper placeMapper;

    public PlaceService(PlaceRepository placeRepository, PlaceMapper placeMapper) {
        this.placeRepository = placeRepository;
        this.placeMapper = placeMapper;
    }

    public PlaceDTO save(PlaceDTO placeDTO) {
        log.debug("Request to save Place : {}", placeDTO);
        Place place = placeMapper.toEntity(placeDTO);
        place = placeRepository.save(place);
        return placeMapper.toDto(place);
    }

    @Transactional(readOnly = true)
    public Page<PlaceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Places");
        return placeRepository.findAll(pageable)
            .map(placeMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<PlaceDTO> findByDivisionOwnerId(Pageable pageable, List<Long> divisionsId) {
        log.debug("Request to get Place by Division owners id");
        return placeRepository.findByDivisionOwnerId(pageable, divisionsId)
            .map(placeMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<PlaceDTO> findByCurrentLogin(Pageable pageable) {
        log.debug("Request to get all Place by current user");
        return placeRepository.findByCurrentLogin(pageable)
            .map(placeMapper::toDto);
    }

    @Transactional(readOnly = true)
    public PlaceDTO findOne(Long id) {
        log.debug("Request to get Place : {}", id);
        Place place = placeRepository.findOneWithEagerRelationships(id);
        return placeMapper.toDto(place);
    }

    public void delete(Long id) {
        log.debug("Request to delete Place : {}", id);
        placeRepository.delete(id);
    }
}
