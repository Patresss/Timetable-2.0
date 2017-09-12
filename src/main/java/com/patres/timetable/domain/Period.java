package com.patres.timetable.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Period.
 */
@Entity
@Table(name = "period")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Period implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "period")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Interval> intervalTimes = new HashSet<>();

    @OneToMany(mappedBy = "period")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Timetable> timetables = new HashSet<>();

    @ManyToOne
    private Division division;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Period name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Interval> getIntervalTimes() {
        return intervalTimes;
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

    public void setIntervalTimes(Set<Interval> intervals) {
        this.intervalTimes = intervals;
    }

    public Set<Timetable> getTimetables() {
        return timetables;
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

    public void setTimetables(Set<Timetable> timetables) {
        this.timetables = timetables;
    }

    public Division getDivision() {
        return division;
    }

    public Period division(Division division) {
        this.division = division;
        return this;
    }

    public void setDivision(Division division) {
        this.division = division;
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
