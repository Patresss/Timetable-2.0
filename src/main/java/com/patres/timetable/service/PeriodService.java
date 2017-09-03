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
public class PeriodService extends DivisionOwnerService<Period, PeriodDTO, PeriodRepository> {

    public PeriodService(PeriodRepository entityRepository, EntityMapper<Period, PeriodDTO> entityMapper) {
        super(entityRepository, entityMapper);
    }

}
