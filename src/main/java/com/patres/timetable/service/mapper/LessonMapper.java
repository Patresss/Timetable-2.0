package com.patres.timetable.service.mapper;

import com.patres.timetable.domain.Division;
import com.patres.timetable.domain.Lesson;
import com.patres.timetable.service.dto.LessonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LessonMapper extends EntityMapper<Lesson, LessonDTO> {

    @Autowired
    private DivisionMapper divisionMapper;

    public Lesson toEntity(LessonDTO lessonDTO) {
        if (lessonDTO == null) {
            return null;
        }

        Lesson lesson = new Lesson();

        lesson.setDivisionOwner(divisionMapper.fromId(lessonDTO.getDivisionOwnerId(), Division::new));
        lesson.setId(lessonDTO.getId());
        lesson.setName(lessonDTO.getName());
        lesson.setStartTime(lessonDTO.getStartTime());
        lesson.setEndTime(lessonDTO.getEndTime());

        return lesson;
    }

    public LessonDTO toDto(Lesson lesson) {
        if (lesson == null) {
            return null;
        }

        LessonDTO lessonDTO = new LessonDTO();

        lessonDTO.setDivisionOwnerId(divisionMapper.getDivisionOwnerId(lesson.getDivisionOwner()));
        lessonDTO.setDivisionOwnerName(divisionMapper.getDivisionOwnerName(lesson.getDivisionOwner()));
        lessonDTO.setId(lesson.getId());
        lessonDTO.setName(lesson.getName());
        lessonDTO.setStartTime(lesson.getStartTime());
        lessonDTO.setEndTime(lesson.getEndTime());

        return lessonDTO;
    }


}
