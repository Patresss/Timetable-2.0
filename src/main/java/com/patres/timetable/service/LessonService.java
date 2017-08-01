package com.patres.timetable.service;

import com.patres.timetable.domain.Lesson;
import com.patres.timetable.repository.LessonRepository;
import com.patres.timetable.service.dto.LessonDTO;
import com.patres.timetable.service.mapper.LessonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class LessonService {

    private final Logger log = LoggerFactory.getLogger(LessonService.class);

    private final LessonRepository lessonRepository;

    private final LessonMapper lessonMapper;

    public LessonService(LessonRepository lessonRepository, LessonMapper lessonMapper) {
        this.lessonRepository = lessonRepository;
        this.lessonMapper = lessonMapper;
    }

    public LessonDTO save(LessonDTO lessonDTO) {
        log.debug("Request to save Lesson : {}", lessonDTO);
        Lesson lesson = lessonMapper.toEntity(lessonDTO);
        lesson = lessonRepository.save(lesson);
        return lessonMapper.toDto(lesson);
    }

    @Transactional(readOnly = true)
    public Page<LessonDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Lessons");
        return lessonRepository.findAll(pageable)
            .map(lessonMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<LessonDTO> findByDivisionsId(Pageable pageable, List<Long> divisionsId) {
        log.debug("Request to get Lesson by Divisions id");
        return lessonRepository.findByDivisionId(pageable, divisionsId)
            .map(lessonMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<LessonDTO> findByCurrentLogin(Pageable pageable) {
        log.debug("Request to get all Lesson by current user");
        return lessonRepository.findByCurrentLogin(pageable)
            .map(lessonMapper::toDto);
    }

    @Transactional(readOnly = true)
    public LessonDTO findOne(Long id) {
        log.debug("Request to get Lesson : {}", id);
        Lesson lesson = lessonRepository.findOne(id);
        return lessonMapper.toDto(lesson);
    }

    public void delete(Long id) {
        log.debug("Request to delete Lesson : {}", id);
        lessonRepository.delete(id);
    }
}
