package com.patres.timetable.service;

import com.patres.timetable.domain.Teacher;
import com.patres.timetable.repository.TeacherRepository;
import com.patres.timetable.security.AuthoritiesConstants;
import com.patres.timetable.security.SecurityUtils;
import com.patres.timetable.service.dto.TeacherDTO;
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
public class TeacherService extends DivisionOwnerService<Teacher, TeacherDTO, TeacherRepository> {

    private final Logger log = LoggerFactory.getLogger(EntityService.class);

    public TeacherService(TeacherRepository entityRepository, EntityMapper<Teacher, TeacherDTO> entityMapper) {
        super(entityRepository, entityMapper);
    }

    @Transactional(readOnly = true)
    public TeacherDTO findOne(Long id) {
        log.debug("Request to get Teacher : {}", id);
        Teacher teacher = entityRepository.findOneWithEagerRelationships(id);
        return entityMapper.toDto(teacher);
    }


}
