package com.patres.timetable.service.mapper;

import com.patres.timetable.domain.*;
import com.patres.timetable.service.dto.TeacherDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Teacher and its DTO TeacherDTO.
 */
@Mapper(componentModel = "spring", uses = {SubjectMapper.class, DivisionMapper.class, })
public interface TeacherMapper extends EntityMapper <TeacherDTO, Teacher> {

    @Mapping(source = "divisionOwner.id", target = "divisionOwnerId")
    @Mapping(source = "divisionOwner.name", target = "divisionOwnerName")
    TeacherDTO toDto(Teacher teacher);
    @Mapping(target = "timetables", ignore = true)

    @Mapping(source = "divisionOwnerId", target = "divisionOwner")
    @Mapping(target = "preferredDivisions", ignore = true)
    @Mapping(target = "preferredPlaces", ignore = true)
    Teacher toEntity(TeacherDTO teacherDTO);
    default Teacher fromId(Long id) {
        if (id == null) {
            return null;
        }
        Teacher teacher = new Teacher();
        teacher.setId(id);
        return teacher;
    }
}
