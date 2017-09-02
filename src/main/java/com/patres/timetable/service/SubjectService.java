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
public class SubjectService extends EntityService<Subject, SubjectDTO, SubjectRepository> {

    private final Logger log = LoggerFactory.getLogger(SubjectService.class);

    public SubjectService(SubjectRepository entityRepository, EntityMapper<Subject, SubjectDTO> entityMapper) {
        super(entityRepository, entityMapper);
    }

    @Transactional(readOnly = true)
    public Page<SubjectDTO> findByDivisionOwnerId(Pageable pageable, List<Long> divisionsId) {
        log.debug("Request to get Subject by Division owners id");
        return entityRepository.findByDivisionOwnerId(pageable, divisionsId).map(entityMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<SubjectDTO> findByCurrentLogin(Pageable pageable) {
        log.debug("Request to get Subject by current user");
        return entityRepository.findByCurrentLogin(pageable).map(entityMapper::toDto);
    }

}
