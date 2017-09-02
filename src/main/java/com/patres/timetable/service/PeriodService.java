package com.patres.timetable.service;

import com.patres.timetable.domain.Period;
import com.patres.timetable.repository.PeriodRepository;
import com.patres.timetable.service.dto.PeriodDTO;
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
public class PeriodService extends EntityService<Period, PeriodDTO, PeriodRepository> {

    private final Logger log = LoggerFactory.getLogger(PeriodService.class);

    public PeriodService(PeriodRepository entityRepository, EntityMapper<Period, PeriodDTO> entityMapper) {
        super(entityRepository, entityMapper);
    }

    @Transactional(readOnly = true)
    public Page<PeriodDTO> findByDivisionOwnerId(Pageable pageable, List<Long> divisionsId) {
        log.debug("Request to get Period by Division owners id");
        return entityRepository.findByDivisionOwnerId(pageable, divisionsId).map(entityMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<PeriodDTO> findByCurrentLogin(Pageable pageable) {
        log.debug("Request to get all Period by current user");
        return entityRepository.findByCurrentLogin(pageable).map(entityMapper::toDto);
    }

}
