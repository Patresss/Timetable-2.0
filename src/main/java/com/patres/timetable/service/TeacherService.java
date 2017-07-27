package com.patres.timetable.service;

import com.patres.timetable.domain.Teacher;
import com.patres.timetable.repository.TeacherRepository;
import com.patres.timetable.repository.search.TeacherSearchRepository;
import com.patres.timetable.service.dto.TeacherDTO;
import com.patres.timetable.service.mapper.TeacherMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Teacher.
 */
@Service
@Transactional
public class TeacherService {

    private final Logger log = LoggerFactory.getLogger(TeacherService.class);

    private final TeacherRepository teacherRepository;

    private final TeacherMapper teacherMapper;

    private final TeacherSearchRepository teacherSearchRepository;

    public TeacherService(TeacherRepository teacherRepository, TeacherMapper teacherMapper, TeacherSearchRepository teacherSearchRepository) {
        this.teacherRepository = teacherRepository;
        this.teacherMapper = teacherMapper;
        this.teacherSearchRepository = teacherSearchRepository;
    }

    /**
     * Save a teacher.
     *
     * @param teacherDTO the entity to save
     * @return the persisted entity
     */
    public TeacherDTO save(TeacherDTO teacherDTO) {
        log.debug("Request to save Teacher : {}", teacherDTO);
        Teacher teacher = teacherMapper.toEntity(teacherDTO);
        teacher = teacherRepository.save(teacher);
        TeacherDTO result = teacherMapper.toDto(teacher);
        teacherSearchRepository.save(teacher);
        return result;
    }

    /**
     *  Get all the teachers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TeacherDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Teachers");
        return teacherRepository.findAll(pageable)
            .map(teacherMapper::toDto);
    }


    @Transactional(readOnly = true)
    public Page<TeacherDTO> findByDivisionInCurrentUser(Pageable pageable) {
        log.debug("Request to get all Teachers");
        return teacherRepository.findByDivisionInCurrentUser(pageable)
            .map(teacherMapper::toDto);
    }

    /**
     *  Get one teacher by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public TeacherDTO findOne(Long id) {
        log.debug("Request to get Teacher : {}", id);
        Teacher teacher = teacherRepository.findOneWithEagerRelationships(id);
        return teacherMapper.toDto(teacher);
    }

    /**
     *  Delete the  teacher by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Teacher : {}", id);
        teacherRepository.delete(id);
        teacherSearchRepository.delete(id);
    }

    /**
     * Search for the teacher corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TeacherDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Teachers for query {}", query);
        Page<Teacher> result = teacherSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(teacherMapper::toDto);
    }
}
