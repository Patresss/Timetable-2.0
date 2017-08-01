package com.patres.timetable.service;

import com.patres.timetable.domain.Period;
import com.patres.timetable.repository.PeriodRepository;
import com.patres.timetable.service.dto.LessonDTO;
import com.patres.timetable.service.dto.PeriodDTO;
import com.patres.timetable.service.mapper.PeriodMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class PeriodService {

    private final Logger log = LoggerFactory.getLogger(PeriodService.class);

    private final PeriodRepository periodRepository;

    private final PeriodMapper periodMapper;

    public PeriodService(PeriodRepository periodRepository, PeriodMapper periodMapper) {
        this.periodRepository = periodRepository;
        this.periodMapper = periodMapper;
    }

    public PeriodDTO save(PeriodDTO periodDTO) {
        log.debug("Request to save Period : {}", periodDTO);
        Period period = periodMapper.toEntity(periodDTO);
        period = periodRepository.save(period);
        return periodMapper.toDto(period);
    }

    @Transactional(readOnly = true)
    public Page<PeriodDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Periods");
        return periodRepository.findAll(pageable)
            .map(periodMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<PeriodDTO> findByDivisionsId(Pageable pageable, List<Long> divisionsId) {
        log.debug("Request to get Period by Divisions id");
        return periodRepository.findByDivisionId(pageable, divisionsId)
            .map(periodMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<PeriodDTO> findByCurrentLogin(Pageable pageable) {
        log.debug("Request to get all Period by current user");
        return periodRepository.findByCurrentLogin(pageable)
            .map(periodMapper::toDto);
    }

    @Transactional(readOnly = true)
    public PeriodDTO findOne(Long id) {
        log.debug("Request to get Period : {}", id);
        Period period = periodRepository.findOne(id);
        return periodMapper.toDto(period);
    }

    public void delete(Long id) {
        log.debug("Request to delete Period : {}", id);
        periodRepository.delete(id);
    }
}
