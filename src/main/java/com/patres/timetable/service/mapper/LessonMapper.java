package com.patres.timetable.service.mapper;

import com.patres.timetable.domain.*;
import com.patres.timetable.service.dto.LessonDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Lesson and its DTO LessonDTO.
 */
@Mapper(componentModel = "spring", uses = {DivisionMapper.class, })
public interface LessonMapper extends EntityMapper <LessonDTO, Lesson> {

    @Mapping(source = "divisionOwner.id", target = "divisionOwnerId")
    @Mapping(source = "divisionOwner.name", target = "divisionOwnerName")
    LessonDTO toDto(Lesson lesson);
    @Mapping(target = "timetables", ignore = true)

    @Mapping(source = "divisionOwnerId", target = "divisionOwner")
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
