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
public class LessonService extends DivisionOwnerService<Lesson, LessonDTO, LessonRepository> {

    public LessonService(LessonRepository entityRepository, EntityMapper<Lesson, LessonDTO> entityMapper) {
        super(entityRepository, entityMapper);
    }

}
