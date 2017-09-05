package com.patres.timetable.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class PeriodDTO extends AbstractDivisionOwnerDTO implements Serializable {

    @NotNull
    private String name;

    private Set<IntervalDTO> intervalTimes = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<IntervalDTO> getIntervalTimes() {
        return intervalTimes;
    }

    public void setIntervalTimes(Set<IntervalDTO> intervalTimes) {
        this.intervalTimes = intervalTimes;
    }

    @Override
    public String toString() {
        return "PeriodDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
