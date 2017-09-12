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
@Table(name = "subject")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Subject extends AbstractDivisionOwner implements Serializable {

    private static final long serialVersionUID = 2588786756261194426L;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "color_background")
    private String colorBackground;

    @Column(name = "color_text")
    private String colorText;

    @OneToMany(mappedBy = "subject")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Timetable> timetables = new HashSet<>();

    @ManyToMany(mappedBy = "preferredSubjects")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Teacher> preferredTeachers = new HashSet<>();

    @ManyToMany(mappedBy = "preferredSubjects")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Division> preferredDivisions = new HashSet<>();

    @ManyToMany(mappedBy = "preferredSubjects")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Place> preferredPlaces = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Subject name(String name) {
        this.name = name;
        return this;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Subject shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public String getColorBackground() {
        return colorBackground;
    }

    public void setColorBackground(String colorBackground) {
        this.colorBackground = colorBackground;
    }

    public Subject colorBackground(String colorBackground) {
        this.colorBackground = colorBackground;
        return this;
    }

    public String getColorText() {
        return colorText;
    }

    public void setColorText(String colorText) {
        this.colorText = colorText;
    }

    public Subject colorText(String colorText) {
        this.colorText = colorText;
        return this;
    }

    public Set<Timetable> getTimetables() {
        return timetables;
    }

    public void setTimetables(Set<Timetable> timetables) {
        this.timetables = timetables;
    }

    public Subject timetables(Set<Timetable> timetables) {
        this.timetables = timetables;
        return this;
    }

    public Subject addTimetable(Timetable timetable) {
        this.timetables.add(timetable);
        timetable.setSubject(this);
        return this;
    }

    public Subject removeTimetable(Timetable timetable) {
        this.timetables.remove(timetable);
        timetable.setSubject(null);
        return this;
    }

    public Set<Teacher> getPreferredTeachers() {
        return preferredTeachers;
    }

    public void setPreferredTeachers(Set<Teacher> teachers) {
        this.preferredTeachers = teachers;
    }

    public Subject preferredTeachers(Set<Teacher> teachers) {
        this.preferredTeachers = teachers;
        return this;
    }

    public Subject addPreferredTeacher(Teacher teacher) {
        this.preferredTeachers.add(teacher);
        teacher.getPreferredSubjects().add(this);
        return this;
    }

    public Subject removePreferredTeacher(Teacher teacher) {
        this.preferredTeachers.remove(teacher);
        teacher.getPreferredSubjects().remove(this);
        return this;
    }

    public Set<Division> getPreferredDivisions() {
        return preferredDivisions;
    }

    public void setPreferredDivisions(Set<Division> divisions) {
        this.preferredDivisions = divisions;
    }

    public Subject preferredDivisions(Set<Division> divisions) {
        this.preferredDivisions = divisions;
        return this;
    }

    public Subject addPreferredDivision(Division division) {
        this.preferredDivisions.add(division);
        division.getPreferredSubjects().add(this);
        return this;
    }

    public Subject removePreferredDivision(Division division) {
        this.preferredDivisions.remove(division);
        division.getPreferredSubjects().remove(this);
        return this;
    }

    public Set<Place> getPreferredPlaces() {
        return preferredPlaces;
    }

    public void setPreferredPlaces(Set<Place> places) {
        this.preferredPlaces = places;
    }

    public Subject preferredPlaces(Set<Place> places) {
        this.preferredPlaces = places;
        return this;
    }

    public Subject addPreferredPlace(Place place) {
        this.preferredPlaces.add(place);
        place.getPreferredSubjects().add(this);
        return this;
    }

    public Subject removePreferredPlace(Place place) {
        this.preferredPlaces.remove(place);
        place.getPreferredSubjects().remove(this);
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
        Subject subject = (Subject) o;
        if (subject.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), subject.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Subject{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", shortName='" + getShortName() + "'" +
            ", colorBackground='" + getColorBackground() + "'" +
            ", colorText='" + getColorText() + "'" +
            "}";
    }
}
