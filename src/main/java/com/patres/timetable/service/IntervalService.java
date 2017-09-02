package com.patres.timetable.service;

import com.patres.timetable.domain.Interval;
import com.patres.timetable.repository.IntervalRepository;
import com.patres.timetable.service.dto.IntervalDTO;
import com.patres.timetable.service.mapper.EntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class IntervalService extends EntityService<Interval, IntervalDTO, IntervalRepository> {

    private final Logger log = LoggerFactory.getLogger(IntervalService.class);

    public IntervalService(IntervalRepository entityRepository, EntityMapper<Interval, IntervalDTO> entityMapper) {
        super(entityRepository, entityMapper);
    }

    @Transactional(readOnly = true)
    public Page<IntervalDTO> findByPeriodId(Pageable pageable, Long periodId) {
        log.debug("Request to get Intervals by periodId");
        return entityRepository.findByPeriodId(pageable, periodId).map(entityMapper::toDto);
    }

}
