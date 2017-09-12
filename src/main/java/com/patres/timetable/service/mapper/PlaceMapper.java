package com.patres.timetable.service.mapper;

import com.patres.timetable.domain.*;
import com.patres.timetable.service.dto.PlaceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Place and its DTO PlaceDTO.
 */
@Mapper(componentModel = "spring", uses = {SubjectMapper.class, DivisionMapper.class, TeacherMapper.class, })
public interface PlaceMapper extends EntityMapper <PlaceDTO, Place> {

    @Mapping(source = "division.id", target = "divisionId")
    @Mapping(source = "division.name", target = "divisionName")
    PlaceDTO toDto(Place place); 
    @Mapping(target = "timetables", ignore = true)

    @Mapping(source = "divisionId", target = "division")
    Place toEntity(PlaceDTO placeDTO); 
    default Place fromId(Long id) {
        if (id == null) {
            return null;
        }
        Place place = new Place();
        place.setId(id);
        return place;
    }
}
