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
 * A Subject.
 */
@Entity
@Table(name = "subject")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Subject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

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

    @ManyToOne
    private Division division;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Subject name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public Subject shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getColorBackground() {
        return colorBackground;
    }

    public Subject colorBackground(String colorBackground) {
        this.colorBackground = colorBackground;
        return this;
    }

    public void setColorBackground(String colorBackground) {
        this.colorBackground = colorBackground;
    }

    public String getColorText() {
        return colorText;
    }

    public Subject colorText(String colorText) {
        this.colorText = colorText;
        return this;
    }

    public void setColorText(String colorText) {
        this.colorText = colorText;
    }

    public Set<Timetable> getTimetables() {
        return timetables;
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

    public void setTimetables(Set<Timetable> timetables) {
        this.timetables = timetables;
    }

    public Division getDivision() {
        return division;
    }

    public Subject division(Division division) {
        this.division = division;
        return this;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public Set<Teacher> getPreferredTeachers() {
        return preferredTeachers;
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

    public void setPreferredTeachers(Set<Teacher> teachers) {
        this.preferredTeachers = teachers;
    }

    public Set<Division> getPreferredDivisions() {
        return preferredDivisions;
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

    public void setPreferredDivisions(Set<Division> divisions) {
        this.preferredDivisions = divisions;
    }

    public Set<Place> getPreferredPlaces() {
        return preferredPlaces;
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

    public void setPreferredPlaces(Set<Place> places) {
        this.preferredPlaces = places;
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
