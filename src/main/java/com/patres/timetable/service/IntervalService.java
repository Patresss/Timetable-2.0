package com.patres.timetable.service;

import com.patres.timetable.domain.Interval;
import com.patres.timetable.repository.IntervalRepository;
import com.patres.timetable.repository.search.IntervalSearchRepository;
import com.patres.timetable.service.dto.IntervalDTO;
import com.patres.timetable.service.mapper.IntervalMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Interval.
 */
@Service
@Transactional
public class IntervalService {

    private final Logger log = LoggerFactory.getLogger(IntervalService.class);

    private final IntervalRepository intervalRepository;

    private final IntervalMapper intervalMapper;

    private final IntervalSearchRepository intervalSearchRepository;

    public IntervalService(IntervalRepository intervalRepository, IntervalMapper intervalMapper, IntervalSearchRepository intervalSearchRepository) {
        this.intervalRepository = intervalRepository;
        this.intervalMapper = intervalMapper;
        this.intervalSearchRepository = intervalSearchRepository;
    }

    /**
     * Save a interval.
     *
     * @param intervalDTO the entity to save
     * @return the persisted entity
     */
    public IntervalDTO save(IntervalDTO intervalDTO) {
        log.debug("Request to save Interval : {}", intervalDTO);
        Interval interval = intervalMapper.toEntity(intervalDTO);
        interval = intervalRepository.save(interval);
        IntervalDTO result = intervalMapper.toDto(interval);
        intervalSearchRepository.save(interval);
        return result;
    }

    /**
     *  Get all the intervals.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<IntervalDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Intervals");
        return intervalRepository.findAll(pageable)
            .map(intervalMapper::toDto);
    }

    /**
     *  Get one interval by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public IntervalDTO findOne(Long id) {
        log.debug("Request to get Interval : {}", id);
        Interval interval = intervalRepository.findOne(id);
        return intervalMapper.toDto(interval);
    }

    /**
     *  Delete the  interval by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Interval : {}", id);
        intervalRepository.delete(id);
        intervalSearchRepository.delete(id);
    }

    /**
     * Search for the interval corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<IntervalDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Intervals for query {}", query);
        Page<Interval> result = intervalSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(intervalMapper::toDto);
    }
}
