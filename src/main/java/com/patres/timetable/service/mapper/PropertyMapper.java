package com.patres.timetable.service.mapper;

import com.patres.timetable.domain.*;
import com.patres.timetable.service.dto.PropertyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Property and its DTO PropertyDTO.
 */
@Mapper(componentModel = "spring", uses = {DivisionMapper.class, })
public interface PropertyMapper extends EntityMapper <PropertyDTO, Property> {

    @Mapping(source = "divisionOwner.id", target = "divisionOwnerId")
    @Mapping(source = "divisionOwner.name", target = "divisionOwnerName")
    PropertyDTO toDto(Property property);

    @Mapping(source = "divisionOwnerId", target = "divisionOwner")
    Property toEntity(PropertyDTO propertyDTO);
    default Property fromId(Long id) {
        if (id == null) {
            return null;
        }
        Property property = new Property();
        property.setId(id);
        return property;
    }
}
