package com.patres.timetable.service;

import com.patres.timetable.domain.Timetable;
import com.patres.timetable.repository.TimetableRepository;
import com.patres.timetable.repository.search.TimetableSearchRepository;
import com.patres.timetable.service.dto.TimetableDTO;
import com.patres.timetable.service.mapper.TimetableMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Timetable.
 */
@Service
@Transactional
public class TimetableService {

    private final Logger log = LoggerFactory.getLogger(TimetableService.class);

    private final TimetableRepository timetableRepository;

    private final TimetableMapper timetableMapper;

    private final TimetableSearchRepository timetableSearchRepository;

    public TimetableService(TimetableRepository timetableRepository, TimetableMapper timetableMapper, TimetableSearchRepository timetableSearchRepository) {
        this.timetableRepository = timetableRepository;
        this.timetableMapper = timetableMapper;
        this.timetableSearchRepository = timetableSearchRepository;
    }

    /**
     * Save a timetable.
     *
     * @param timetableDTO the entity to save
     * @return the persisted entity
     */
    public TimetableDTO save(TimetableDTO timetableDTO) {
        log.debug("Request to save Timetable : {}", timetableDTO);
        Timetable timetable = timetableMapper.toEntity(timetableDTO);
        timetable = timetableRepository.save(timetable);
        TimetableDTO result = timetableMapper.toDto(timetable);
        timetableSearchRepository.save(timetable);
        return result;
    }

    /**
     *  Get all the timetables.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TimetableDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Timetables");
        return timetableRepository.findAll(pageable)
            .map(timetableMapper::toDto);
    }

    /**
     *  Get one timetable by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public TimetableDTO findOne(Long id) {
        log.debug("Request to get Timetable : {}", id);
        Timetable timetable = timetableRepository.findOne(id);
        return timetableMapper.toDto(timetable);
    }

    /**
     *  Delete the  timetable by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Timetable : {}", id);
        timetableRepository.delete(id);
        timetableSearchRepository.delete(id);
    }

    /**
     * Search for the timetable corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TimetableDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Timetables for query {}", query);
        Page<Timetable> result = timetableSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(timetableMapper::toDto);
    }
}
