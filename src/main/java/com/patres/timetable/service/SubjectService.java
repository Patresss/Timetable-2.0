package com.patres.timetable.service;

import com.patres.timetable.domain.Subject;
import com.patres.timetable.repository.SubjectRepository;
import com.patres.timetable.service.dto.PlaceDTO;
import com.patres.timetable.service.dto.SubjectDTO;
import com.patres.timetable.service.mapper.SubjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class SubjectService {

    private final Logger log = LoggerFactory.getLogger(SubjectService.class);

    private final SubjectRepository subjectRepository;

    private final SubjectMapper subjectMapper;

    public SubjectService(SubjectRepository subjectRepository, SubjectMapper subjectMapper) {
        this.subjectRepository = subjectRepository;
        this.subjectMapper = subjectMapper;
    }

    public SubjectDTO save(SubjectDTO subjectDTO) {
        log.debug("Request to save Subject : {}", subjectDTO);
        Subject subject = subjectMapper.toEntity(subjectDTO);
        subject = subjectRepository.save(subject);
        return subjectMapper.toDto(subject);
    }

    @Transactional(readOnly = true)
    public Page<SubjectDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Subjects");
        return subjectRepository.findAll(pageable)
            .map(subjectMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<SubjectDTO> findByDivisionsId(Pageable pageable, List<Long> divisionsId) {
        log.debug("Request to get Subject by Divisions id");
        return subjectRepository.findByDivisionId(pageable, divisionsId)
            .map(subjectMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<SubjectDTO> findByCurrentLogin(Pageable pageable) {
        log.debug("Request to get Subject by current user");
        return subjectRepository.findByCurrentLogin(pageable)
            .map(subjectMapper::toDto);
    }

    @Transactional(readOnly = true)
    public SubjectDTO findOne(Long id) {
        log.debug("Request to get Subject : {}", id);
        Subject subject = subjectRepository.findOne(id);
        return subjectMapper.toDto(subject);
    }

    public void delete(Long id) {
        log.debug("Request to delete Subject : {}", id);
        subjectRepository.delete(id);
    }
}
