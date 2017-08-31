package com.patres.timetable.service.mapper;

import com.patres.timetable.domain.Division;
import com.patres.timetable.domain.Interval;
import com.patres.timetable.domain.Period;
import com.patres.timetable.service.dto.IntervalDTO;
import com.patres.timetable.service.dto.PeriodDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PeriodMapper extends EntityMapper<Period, PeriodDTO> {


    @Autowired
    private DivisionMapper divisionMapper;
    @Autowired
    private IntervalMapper intervalMapper;

    public Period toEntity(PeriodDTO periodDTO) {
        if (periodDTO == null) {
            return null;
        }

        Period period = new Period();

        period.setDivisionOwner(divisionMapper.fromId(periodDTO.getDivisionOwnerId(), Division::new));
        period.setId(periodDTO.getId());
        period.setName(periodDTO.getName());

        Set<Interval> intervals = intervalMapper.entityDTOSetToEntitySet(periodDTO.getIntervalTimes());
        intervals.forEach(interval -> interval.setPeriod(period));
        period.setIntervalTimes(intervals);

        return period;
    }

    public PeriodDTO toDto(Period period) {
        if (period == null) {
            return null;
        }

        PeriodDTO periodDTO = new PeriodDTO();

        periodDTO.setDivisionOwnerId(divisionMapper.getDivisionOwnerId(period.getDivisionOwner()));
        periodDTO.setDivisionOwnerName(divisionMapper.getDivisionOwnerName(period.getDivisionOwner()));
        periodDTO.setId(period.getId());
        periodDTO.setName(period.getName());
        Set<IntervalDTO> intervals = new HashSet<>();
        for (Interval interval : period.getIntervalTimes()) {
            intervals.add(intervalMapper.toDto(interval));
        }
        periodDTO.setIntervalTimes(intervals);

        return periodDTO;
    }


}
