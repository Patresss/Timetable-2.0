package com.patres.timetable.service.mapper;

import com.patres.timetable.domain.*;
import com.patres.timetable.service.dto.TimetableDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Timetable and its DTO TimetableDTO.
 */
@Mapper(componentModel = "spring", uses = {PlaceMapper.class, SubjectMapper.class, TeacherMapper.class, DivisionMapper.class, LessonMapper.class, PeriodMapper.class, })
public interface TimetableMapper extends EntityMapper <TimetableDTO, Timetable> {

    @Mapping(source = "place.id", target = "placeId")
    @Mapping(source = "place.name", target = "placeName")

    @Mapping(source = "subject.id", target = "subjectId")
    @Mapping(source = "subject.name", target = "subjectName")

    @Mapping(source = "teacher.id", target = "teacherId")
    @Mapping(source = "teacher.surname", target = "teacherSurname")

    @Mapping(source = "division.id", target = "divisionId")
    @Mapping(source = "division.name", target = "divisionName")

    @Mapping(source = "lesson.id", target = "lessonId")
    @Mapping(source = "lesson.name", target = "lessonName")

    @Mapping(source = "period.id", target = "periodId")
    @Mapping(source = "period.name", target = "periodName")
    TimetableDTO toDto(Timetable timetable); 

    @Mapping(source = "placeId", target = "place")

    @Mapping(source = "subjectId", target = "subject")

    @Mapping(source = "teacherId", target = "teacher")

    @Mapping(source = "divisionId", target = "division")

    @Mapping(source = "lessonId", target = "lesson")

    @Mapping(source = "periodId", target = "period")
    Timetable toEntity(TimetableDTO timetableDTO); 
    default Timetable fromId(Long id) {
        if (id == null) {
            return null;
        }
        Timetable timetable = new Timetable();
        timetable.setId(id);
        return timetable;
    }
}
