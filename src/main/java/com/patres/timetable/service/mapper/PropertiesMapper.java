package com.patres.timetable.service.mapper;

import com.patres.timetable.domain.*;
import com.patres.timetable.service.dto.PropertiesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Properties and its DTO PropertiesDTO.
 */
@Mapper(componentModel = "spring", uses = {DivisionMapper.class, })
public interface PropertiesMapper extends EntityMapper <PropertiesDTO, Properties> {

    @Mapping(source = "division.id", target = "divisionId")
    @Mapping(source = "division.name", target = "divisionName")
    PropertiesDTO toDto(Properties properties); 

    @Mapping(source = "divisionId", target = "division")
    Properties toEntity(PropertiesDTO propertiesDTO); 
    default Properties fromId(Long id) {
        if (id == null) {
            return null;
        }
        Properties properties = new Properties();
        properties.setId(id);
        return properties;
    }
}
