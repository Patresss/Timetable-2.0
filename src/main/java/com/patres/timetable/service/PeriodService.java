package com.patres.timetable.service;

import com.patres.timetable.domain.Period;
import com.patres.timetable.repository.PeriodRepository;
import com.patres.timetable.repository.search.PeriodSearchRepository;
import com.patres.timetable.service.dto.PeriodDTO;
import com.patres.timetable.service.mapper.PeriodMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Period.
 */
@Service
@Transactional
public class PeriodService {

    private final Logger log = LoggerFactory.getLogger(PeriodService.class);

    private final PeriodRepository periodRepository;

    private final PeriodMapper periodMapper;

    private final PeriodSearchRepository periodSearchRepository;

    public PeriodService(PeriodRepository periodRepository, PeriodMapper periodMapper, PeriodSearchRepository periodSearchRepository) {
        this.periodRepository = periodRepository;
        this.periodMapper = periodMapper;
        this.periodSearchRepository = periodSearchRepository;
    }

    /**
     * Save a period.
     *
     * @param periodDTO the entity to save
     * @return the persisted entity
     */
    public PeriodDTO save(PeriodDTO periodDTO) {
        log.debug("Request to save Period : {}", periodDTO);
        Period period = periodMapper.toEntity(periodDTO);
        period = periodRepository.save(period);
        PeriodDTO result = periodMapper.toDto(period);
        periodSearchRepository.save(period);
        return result;
    }

    /**
     *  Get all the periods.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PeriodDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Periods");
        return periodRepository.findAll(pageable)
            .map(periodMapper::toDto);
    }

    /**
     *  Get one period by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public PeriodDTO findOne(Long id) {
        log.debug("Request to get Period : {}", id);
        Period period = periodRepository.findOne(id);
        return periodMapper.toDto(period);
    }

    /**
     *  Delete the  period by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Period : {}", id);
        periodRepository.delete(id);
        periodSearchRepository.delete(id);
    }

    /**
     * Search for the period corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PeriodDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Periods for query {}", query);
        Page<Period> result = periodSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(periodMapper::toDto);
    }
}
