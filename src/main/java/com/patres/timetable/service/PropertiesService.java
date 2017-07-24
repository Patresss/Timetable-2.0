package com.patres.timetable.service;

import com.patres.timetable.domain.Properties;
import com.patres.timetable.repository.PropertiesRepository;
import com.patres.timetable.repository.search.PropertiesSearchRepository;
import com.patres.timetable.service.dto.PropertiesDTO;
import com.patres.timetable.service.mapper.PropertiesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Properties.
 */
@Service
@Transactional
public class PropertiesService {

    private final Logger log = LoggerFactory.getLogger(PropertiesService.class);

    private final PropertiesRepository propertiesRepository;

    private final PropertiesMapper propertiesMapper;

    private final PropertiesSearchRepository propertiesSearchRepository;

    public PropertiesService(PropertiesRepository propertiesRepository, PropertiesMapper propertiesMapper, PropertiesSearchRepository propertiesSearchRepository) {
        this.propertiesRepository = propertiesRepository;
        this.propertiesMapper = propertiesMapper;
        this.propertiesSearchRepository = propertiesSearchRepository;
    }

    /**
     * Save a properties.
     *
     * @param propertiesDTO the entity to save
     * @return the persisted entity
     */
    public PropertiesDTO save(PropertiesDTO propertiesDTO) {
        log.debug("Request to save Properties : {}", propertiesDTO);
        Properties properties = propertiesMapper.toEntity(propertiesDTO);
        properties = propertiesRepository.save(properties);
        PropertiesDTO result = propertiesMapper.toDto(properties);
        propertiesSearchRepository.save(properties);
        return result;
    }

    /**
     *  Get all the properties.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PropertiesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Properties");
        return propertiesRepository.findAll(pageable)
            .map(propertiesMapper::toDto);
    }

    /**
     *  Get one properties by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public PropertiesDTO findOne(Long id) {
        log.debug("Request to get Properties : {}", id);
        Properties properties = propertiesRepository.findOne(id);
        return propertiesMapper.toDto(properties);
    }

    /**
     *  Delete the  properties by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Properties : {}", id);
        propertiesRepository.delete(id);
        propertiesSearchRepository.delete(id);
    }

    /**
     * Search for the properties corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PropertiesDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Properties for query {}", query);
        Page<Properties> result = propertiesSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(propertiesMapper::toDto);
    }
}
