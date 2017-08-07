package com.patres.timetable.service;

import com.patres.timetable.domain.Teacher;
import com.patres.timetable.repository.TeacherRepository;
import com.patres.timetable.service.dto.TeacherDTO;
import com.patres.timetable.service.mapper.TeacherMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Service Implementation for managing Teacher.
 */
@Service
@Transactional
public class TeacherService {

    private final Logger log = LoggerFactory.getLogger(TeacherService.class);

    private final TeacherRepository teacherRepository;

    private final TeacherMapper teacherMapper;

    public TeacherService(TeacherRepository teacherRepository, TeacherMapper teacherMapper) {
        this.teacherRepository = teacherRepository;
        this.teacherMapper = teacherMapper;
    }

    public TeacherDTO save(TeacherDTO teacherDTO) {
        log.debug("Request to save Teacher : {}", teacherDTO);
        Teacher teacher = teacherMapper.toEntity(teacherDTO);
        teacher = teacherRepository.save(teacher);
        return teacherMapper.toDto(teacher);
    }

    @Transactional(readOnly = true)
    public Page<TeacherDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Teachers");
        return teacherRepository.findAll(pageable)
            .map(teacherMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<TeacherDTO> findByDivisionOwnerId(Pageable pageable, List<Long> divisionsId) {
        log.debug("Request to get Teachers by Division owners id");
        return teacherRepository.findByDivisionOwnerId(pageable, divisionsId)
            .map(teacherMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<TeacherDTO> findByCurrentLogin(Pageable pageable) {
        log.debug("Request to get all Teachers by current user");
        return teacherRepository.findByCurrentLogin(pageable)
            .map(teacherMapper::toDto);
    }

    @Transactional(readOnly = true)
    public TeacherDTO findOne(Long id) {
        log.debug("Request to get Teacher : {}", id);
        Teacher teacher = teacherRepository.findOneWithEagerRelationships(id);
        return teacherMapper.toDto(teacher);
    }

    public void delete(Long id) {
        log.debug("Request to delete Teacher : {}", id);
        teacherRepository.delete(id);
    }
}
