package com.patres.timetable.service.mapper;

import com.patres.timetable.domain.Division;
import com.patres.timetable.domain.Property;
import com.patres.timetable.service.dto.PropertyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PropertyMapper extends EntityMapper<Property, PropertyDTO> {

    @Autowired
    private DivisionMapper divisionMapper;


    public Property toEntity(PropertyDTO propertyDTO) {
        if (propertyDTO == null) {
            return null;
        }

        Property property = new Property();

        property.setDivisionOwner(divisionMapper.fromId(propertyDTO.getDivisionOwnerId(), Division::new));
        property.setId(propertyDTO.getId());
        property.setPropertyKey(propertyDTO.getPropertyKey());
        property.setPropertyValue(propertyDTO.getPropertyValue());

        return property;
    }

    public PropertyDTO toDto(Property property) {
        if (property == null) {
            return null;
        }

        PropertyDTO propertyDTO = new PropertyDTO();

        propertyDTO.setDivisionOwnerId(divisionMapper.getDivisionOwnerId(property.getDivisionOwner()));
        propertyDTO.setDivisionOwnerName(divisionMapper.getDivisionOwnerName(property.getDivisionOwner()));
        propertyDTO.setId(property.getId());
        propertyDTO.setPropertyKey(property.getPropertyKey());
        propertyDTO.setPropertyValue(property.getPropertyValue());

        return propertyDTO;
    }


}
