package com.patres.timetable.service.mapper;

import com.patres.timetable.domain.Division;
import com.patres.timetable.domain.Interval;
import com.patres.timetable.domain.Period;
import com.patres.timetable.service.dto.IntervalDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IntervalMapper extends EntityMapper<Interval, IntervalDTO> {


    @Autowired
    private PeriodMapper periodMapper;

    public Interval toEntity(IntervalDTO intervalDTO) {
        if (intervalDTO == null) {
            return null;
        }

        Interval interval = new Interval();

        interval.setPeriod(periodMapper.fromId(intervalDTO.getPeriodId()));
        interval.setId(intervalDTO.getId());
        interval.setIncluded(intervalDTO.isIncluded());
        interval.setStartDate(intervalDTO.getStartDate());
        interval.setEndDate(intervalDTO.getEndDate());

        return interval;
    }

    public IntervalDTO toDto(Interval interval) {
        if (interval == null) {
            return null;
        }

        IntervalDTO intervalDTO = new IntervalDTO();

        intervalDTO.setPeriodId(intervalPeriodId(interval));
        intervalDTO.setPeriodName(intervalPeriodName(interval));
        intervalDTO.setId(interval.getId());
        intervalDTO.setIncluded(interval.isIncluded());
        intervalDTO.setStartDate(interval.getStartDate());
        intervalDTO.setEndDate(interval.getEndDate());

        return intervalDTO;
    }

    public Interval fromId(Long id) {
        if (id == null) {
            return null;
        }
        Interval entity = new Interval();
        entity.setId(id);
        return entity;
    }

    private Long intervalPeriodId(Interval interval) {
        if (interval == null) {
            return null;
        }
        Period period = interval.getPeriod();
        if (period == null) {
            return null;
        }
        return period.getId();
    }

    private String intervalPeriodName(Interval interval) {
        if (interval == null) {
            return null;
        }
        Period period = interval.getPeriod();
        if (period == null) {
            return null;
        }
        return period.getName();
    }
}
