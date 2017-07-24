package com.patres.timetable.service.mapper;

import com.patres.timetable.domain.*;
import com.patres.timetable.service.dto.LessonDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Lesson and its DTO LessonDTO.
 */
@Mapper(componentModel = "spring", uses = {DivisionMapper.class, })
public interface LessonMapper extends EntityMapper <LessonDTO, Lesson> {

    @Mapping(source = "division.id", target = "divisionId")
    @Mapping(source = "division.name", target = "divisionName")
    LessonDTO toDto(Lesson lesson); 
    @Mapping(target = "timetables", ignore = true)

    @Mapping(source = "divisionId", target = "division")
    Lesson toEntity(LessonDTO lessonDTO); 
    default Lesson fromId(Long id) {
        if (id == null) {
            return null;
        }
        Lesson lesson = new Lesson();
        lesson.setId(id);
        return lesson;
    }
}
