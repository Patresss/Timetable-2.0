package com.patres.timetable.service;

import com.patres.timetable.domain.Division;
import com.patres.timetable.repository.DivisionRepository;
import com.patres.timetable.service.dto.DivisionDTO;
import com.patres.timetable.service.mapper.EntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DivisionService extends EntityService<Division, DivisionDTO, DivisionRepository> {

    private final Logger log = LoggerFactory.getLogger(DivisionService.class);

    public DivisionService(DivisionRepository entityRepository, EntityMapper<Division, DivisionDTO> entityMapper) {
        super(entityRepository, entityMapper);
    }

    @Transactional(readOnly = true)
    public DivisionDTO findOne(Long id) {
        log.debug("Request to get Division : {}", id);
        Division division = entityRepository.findOneWithEagerRelationships(id);
        return entityMapper.toDto(division);
    }
}
