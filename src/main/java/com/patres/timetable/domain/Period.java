package com.patres.timetable.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "period")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Period extends AbstractDivisionOwner implements Serializable {

    private static final long serialVersionUID = -4976199580753440331L;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "period")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Interval> intervalTimes = new HashSet<>();

    @OneToMany(mappedBy = "period")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Timetable> timetables = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Period name(String name) {
        this.name = name;
        return this;
    }

    public Set<Interval> getIntervalTimes() {
        return intervalTimes;
    }

    public void setIntervalTimes(Set<Interval> intervals) {
        this.intervalTimes = intervals;
    }

    public Period intervalTimes(Set<Interval> intervals) {
        this.intervalTimes = intervals;
        return this;
    }

    public Period addIntervalTime(Interval interval) {
        this.intervalTimes.add(interval);
        interval.setPeriod(this);
        return this;
    }

    public Period removeIntervalTime(Interval interval) {
        this.intervalTimes.remove(interval);
        interval.setPeriod(null);
        return this;
    }

    public Set<Timetable> getTimetables() {
        return timetables;
    }

    public void setTimetables(Set<Timetable> timetables) {
        this.timetables = timetables;
    }

    public Period timetables(Set<Timetable> timetables) {
        this.timetables = timetables;
        return this;
    }

    public Period addTimetable(Timetable timetable) {
        this.timetables.add(timetable);
        timetable.setPeriod(this);
        return this;
    }

    public Period removeTimetable(Timetable timetable) {
        this.timetables.remove(timetable);
        timetable.setPeriod(null);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Period period = (Period) o;
        if (period.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), period.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Period{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
