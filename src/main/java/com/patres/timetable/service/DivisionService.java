package com.patres.timetable.service;

import com.patres.timetable.domain.Division;
import com.patres.timetable.repository.DivisionRepository;
import com.patres.timetable.service.dto.DivisionDTO;
import com.patres.timetable.service.mapper.DivisionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Division.
 */
@Service
@Transactional
public class DivisionService {

    private final Logger log = LoggerFactory.getLogger(DivisionService.class);

    private final DivisionRepository divisionRepository;

    private final DivisionMapper divisionMapper;

    public DivisionService(DivisionRepository divisionRepository, DivisionMapper divisionMapper) {
        this.divisionRepository = divisionRepository;
        this.divisionMapper = divisionMapper;
    }

    /**
     * Save a division.
     *
     * @param divisionDTO the entity to save
     * @return the persisted entity
     */
    public DivisionDTO save(DivisionDTO divisionDTO) {
        log.debug("Request to save Division : {}", divisionDTO);
        Division division = divisionMapper.toEntity(divisionDTO);
        division = divisionRepository.save(division);
        return divisionMapper.toDto(division);
    }

    /**
     *  Get all the divisions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DivisionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Divisions");
        return divisionRepository.findAll(pageable)
            .map(divisionMapper::toDto);
    }

    /**
     *  Get one division by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public DivisionDTO findOne(Long id) {
        log.debug("Request to get Division : {}", id);
        Division division = divisionRepository.findOneWithEagerRelationships(id);
        return divisionMapper.toDto(division);
    }

    /**
     *  Delete the  division by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Division : {}", id);
        divisionRepository.delete(id);
    }
}
