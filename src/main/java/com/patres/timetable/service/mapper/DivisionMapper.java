package com.patres.timetable.service.mapper;

import com.patres.timetable.domain.*;
import com.patres.timetable.service.dto.DivisionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Division and its DTO DivisionDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, TeacherMapper.class, SubjectMapper.class, })
public interface DivisionMapper extends EntityMapper <DivisionDTO, Division> {
    
    @Mapping(target = "timetables", ignore = true)
    @Mapping(target = "divisionPlaces", ignore = true)
    @Mapping(target = "divisionTeachers", ignore = true)
    @Mapping(target = "divisionSubjects", ignore = true)
    @Mapping(target = "divisionLessons", ignore = true)
    @Mapping(target = "divisionPeriods", ignore = true)
    @Mapping(target = "divisionProperties", ignore = true)
    @Mapping(target = "preferredPlaces", ignore = true)
    Division toEntity(DivisionDTO divisionDTO); 
    default Division fromId(Long id) {
        if (id == null) {
            return null;
        }
        Division division = new Division();
        division.setId(id);
        return division;
    }
}
