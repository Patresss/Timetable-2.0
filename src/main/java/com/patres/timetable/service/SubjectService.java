package com.patres.timetable.service;

import com.patres.timetable.domain.Subject;
import com.patres.timetable.repository.SubjectRepository;
import com.patres.timetable.service.dto.SubjectDTO;
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
public class SubjectService extends DivisionOwnerService<Subject, SubjectDTO, SubjectRepository> {

    public SubjectService(SubjectRepository entityRepository, EntityMapper<Subject, SubjectDTO> entityMapper) {
        super(entityRepository, entityMapper);
    }

}
