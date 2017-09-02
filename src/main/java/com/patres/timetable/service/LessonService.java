package com.patres.timetable.service;

import com.patres.timetable.domain.Lesson;
import com.patres.timetable.repository.LessonRepository;
import com.patres.timetable.service.dto.LessonDTO;
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
public class LessonService extends EntityService<Lesson, LessonDTO, LessonRepository> {

    private final Logger log = LoggerFactory.getLogger(LessonService.class);

    public LessonService(LessonRepository entityRepository, EntityMapper<Lesson, LessonDTO> entityMapper) {
        super(entityRepository, entityMapper);
    }

    @Transactional(readOnly = true)
    public Page<LessonDTO> findByDivisionOwnerId(Pageable pageable, List<Long> divisionsId) {
        log.debug("Request to get Lesson by Division owners id");
        return entityRepository.findByDivisionOwnerId(pageable, divisionsId).map(entityMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<LessonDTO> findByCurrentLogin(Pageable pageable) {
        log.debug("Request to get all Lesson by current user");
        return entityRepository.findByCurrentLogin(pageable).map(entityMapper::toDto);
    }

}
