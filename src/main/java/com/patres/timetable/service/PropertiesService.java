package com.patres.timetable.service;

import com.patres.timetable.domain.Properties;
import com.patres.timetable.repository.PropertiesRepository;
import com.patres.timetable.service.dto.PlaceDTO;
import com.patres.timetable.service.dto.PropertiesDTO;
import com.patres.timetable.service.mapper.PropertiesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class PropertiesService {

    private final Logger log = LoggerFactory.getLogger(PropertiesService.class);

    private final PropertiesRepository propertiesRepository;

    private final PropertiesMapper propertiesMapper;

    public PropertiesService(PropertiesRepository propertiesRepository, PropertiesMapper propertiesMapper) {
        this.propertiesRepository = propertiesRepository;
        this.propertiesMapper = propertiesMapper;
    }

    public PropertiesDTO save(PropertiesDTO propertiesDTO) {
        log.debug("Request to save Properties : {}", propertiesDTO);
        Properties properties = propertiesMapper.toEntity(propertiesDTO);
        properties = propertiesRepository.save(properties);
        return propertiesMapper.toDto(properties);
    }

    @Transactional(readOnly = true)
    public Page<PropertiesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Properties");
        return propertiesRepository.findAll(pageable)
            .map(propertiesMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<PropertiesDTO> findByCurrentLogin(Pageable pageable) {
        log.debug("Request to get Properties by current user");
        return propertiesRepository.findByCurrentLogin(pageable)
            .map(propertiesMapper::toDto);
    }

    @Transactional(readOnly = true)
    public PropertiesDTO findOne(Long id) {
        log.debug("Request to get Properties : {}", id);
        Properties properties = propertiesRepository.findOne(id);
        return propertiesMapper.toDto(properties);
    }

    public void delete(Long id) {
        log.debug("Request to delete Properties : {}", id);
        propertiesRepository.delete(id);
    }
}
