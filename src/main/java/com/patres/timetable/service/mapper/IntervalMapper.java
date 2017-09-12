package com.patres.timetable.service.mapper;

import com.patres.timetable.domain.*;
import com.patres.timetable.service.dto.IntervalDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Interval and its DTO IntervalDTO.
 */
@Mapper(componentModel = "spring", uses = {PeriodMapper.class, })
public interface IntervalMapper extends EntityMapper <IntervalDTO, Interval> {

    @Mapping(source = "period.id", target = "periodId")
    @Mapping(source = "period.name", target = "periodName")
    IntervalDTO toDto(Interval interval); 

    @Mapping(source = "periodId", target = "period")
    Interval toEntity(IntervalDTO intervalDTO); 
    default Interval fromId(Long id) {
        if (id == null) {
            return null;
        }
        Interval interval = new Interval();
        interval.setId(id);
        return interval;
    }
}
