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
public class TeacherService extends EntityService<Teacher, TeacherDTO, TeacherRepository> {

    private final Logger log = LoggerFactory.getLogger(EntityService.class);

    public TeacherService(TeacherRepository entityRepository, EntityMapper<Teacher, TeacherDTO> entityMapper) {
        super(entityRepository, entityMapper);
    }

    @Transactional(readOnly = true)
    public Page<TeacherDTO> findByDivisionOwnerId(Pageable pageable, List<Long> divisionsId) {
        log.debug("Request to get Teachers by Division owners id");
        return entityRepository.findByDivisionOwnerId(pageable, divisionsId)
            .map(entityMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<TeacherDTO> findByCurrentLogin(Pageable pageable) {
        log.debug("Request to get all Teachers by current user");
        Page<Teacher> teachers;
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            teachers = entityRepository.findAll(pageable);
        } else {
            teachers = entityRepository.findByCurrentLogin(pageable);
        }
        return teachers.map(entityMapper::toDto);
    }

    @Transactional(readOnly = true)
    public TeacherDTO findOne(Long id) {
        log.debug("Request to get Teacher : {}", id);
        Teacher teacher = entityRepository.findOneWithEagerRelationships(id);
        return entityMapper.toDto(teacher);
    }


}
