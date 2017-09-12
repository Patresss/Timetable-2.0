package com.patres.timetable.service.mapper;

import com.patres.timetable.domain.*;
import com.patres.timetable.service.dto.PeriodDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Period and its DTO PeriodDTO.
 */
@Mapper(componentModel = "spring", uses = {DivisionMapper.class, })
public interface PeriodMapper extends EntityMapper <PeriodDTO, Period> {

    @Mapping(source = "division.id", target = "divisionId")
    @Mapping(source = "division.name", target = "divisionName")
    PeriodDTO toDto(Period period); 
    @Mapping(target = "intervalTimes", ignore = true)
    @Mapping(target = "timetables", ignore = true)

    @Mapping(source = "divisionId", target = "division")
    Period toEntity(PeriodDTO periodDTO); 
    default Period fromId(Long id) {
        if (id == null) {
            return null;
        }
        Period period = new Period();
        period.setId(id);
        return period;
    }
}
