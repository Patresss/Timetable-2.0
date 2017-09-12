package com.patres.timetable.service;

import com.patres.timetable.domain.Timetable;
import com.patres.timetable.repository.TimetableRepository;
import com.patres.timetable.service.dto.TimetableDTO;
import com.patres.timetable.service.mapper.EntityMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TimetableService extends EntityService<Timetable, TimetableDTO, TimetableRepository> {

    public TimetableService(TimetableRepository entityRepository, EntityMapper<Timetable, TimetableDTO> entityMapper) {
        super(entityRepository, entityMapper);
    }
}
