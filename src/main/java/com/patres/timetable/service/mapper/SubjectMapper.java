package com.patres.timetable.service.mapper;

import com.patres.timetable.domain.*;
import com.patres.timetable.service.dto.SubjectDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Subject and its DTO SubjectDTO.
 */
@Mapper(componentModel = "spring", uses = {DivisionMapper.class, })
public interface SubjectMapper extends EntityMapper <SubjectDTO, Subject> {

    @Mapping(source = "divisionOwner.id", target = "divisionOwnerId")
    @Mapping(source = "divisionOwner.name", target = "divisionOwnerName")
    SubjectDTO toDto(Subject subject);
    @Mapping(target = "timetables", ignore = true)

    @Mapping(source = "divisionOwnerId", target = "divisionOwner")
    @Mapping(target = "preferredTeachers", ignore = true)
    @Mapping(target = "preferredDivisions", ignore = true)
    @Mapping(target = "preferredPlaces", ignore = true)
    Subject toEntity(SubjectDTO subjectDTO);
    default Subject fromId(Long id) {
        if (id == null) {
            return null;
        }
        Subject subject = new Subject();
        subject.setId(id);
        return subject;
    }
}
