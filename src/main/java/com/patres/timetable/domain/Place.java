package com.patres.timetable.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Place.
 */
@Entity
@Table(name = "place")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "place")
public class Place implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "number_of_seats")
    private Long numberOfSeats;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "color_background")
    private String colorBackground;

    @Column(name = "color_text")
    private String colorText;

    @OneToMany(mappedBy = "place")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Timetable> timetables = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "place_preferred_subject",
               joinColumns = @JoinColumn(name="places_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="preferred_subjects_id", referencedColumnName="id"))
    private Set<Subject> preferredSubjects = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "place_preferred_division",
               joinColumns = @JoinColumn(name="places_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="preferred_divisions_id", referencedColumnName="id"))
    private Set<Division> preferredDivisions = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "place_preferred_teacher",
               joinColumns = @JoinColumn(name="places_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="preferred_teachers_id", referencedColumnName="id"))
    private Set<Teacher> preferredTeachers = new HashSet<>();

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

    public Place name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getNumberOfSeats() {
        return numberOfSeats;
    }

    public Place numberOfSeats(Long numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
        return this;
    }

    public void setNumberOfSeats(Long numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public String getShortName() {
        return shortName;
    }

    public Place shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getColorBackground() {
        return colorBackground;
    }

    public Place colorBackground(String colorBackground) {
        this.colorBackground = colorBackground;
        return this;
    }

    public void setColorBackground(String colorBackground) {
        this.colorBackground = colorBackground;
    }

    public String getColorText() {
        return colorText;
    }

    public Place colorText(String colorText) {
        this.colorText = colorText;
        return this;
    }

    public void setColorText(String colorText) {
        this.colorText = colorText;
    }

    public Set<Timetable> getTimetables() {
        return timetables;
    }

    public Place timetables(Set<Timetable> timetables) {
        this.timetables = timetables;
        return this;
    }

    public Place addTimetable(Timetable timetable) {
        this.timetables.add(timetable);
        timetable.setPlace(this);
        return this;
    }

    public Place removeTimetable(Timetable timetable) {
        this.timetables.remove(timetable);
        timetable.setPlace(null);
        return this;
    }

    public void setTimetables(Set<Timetable> timetables) {
        this.timetables = timetables;
    }

    public Set<Subject> getPreferredSubjects() {
        return preferredSubjects;
    }

    public Place preferredSubjects(Set<Subject> subjects) {
        this.preferredSubjects = subjects;
        return this;
    }

    public Place addPreferredSubject(Subject subject) {
        this.preferredSubjects.add(subject);
        subject.getPreferredPlaces().add(this);
        return this;
    }

    public Place removePreferredSubject(Subject subject) {
        this.preferredSubjects.remove(subject);
        subject.getPreferredPlaces().remove(this);
        return this;
    }

    public void setPreferredSubjects(Set<Subject> subjects) {
        this.preferredSubjects = subjects;
    }

    public Set<Division> getPreferredDivisions() {
        return preferredDivisions;
    }

    public Place preferredDivisions(Set<Division> divisions) {
        this.preferredDivisions = divisions;
        return this;
    }

    public Place addPreferredDivision(Division division) {
        this.preferredDivisions.add(division);
        division.getPreferredPlaces().add(this);
        return this;
    }

    public Place removePreferredDivision(Division division) {
        this.preferredDivisions.remove(division);
        division.getPreferredPlaces().remove(this);
        return this;
    }

    public void setPreferredDivisions(Set<Division> divisions) {
        this.preferredDivisions = divisions;
    }

    public Set<Teacher> getPreferredTeachers() {
        return preferredTeachers;
    }

    public Place preferredTeachers(Set<Teacher> teachers) {
        this.preferredTeachers = teachers;
        return this;
    }

    public Place addPreferredTeacher(Teacher teacher) {
        this.preferredTeachers.add(teacher);
        teacher.getPreferredPlaces().add(this);
        return this;
    }

    public Place removePreferredTeacher(Teacher teacher) {
        this.preferredTeachers.remove(teacher);
        teacher.getPreferredPlaces().remove(this);
        return this;
    }

    public void setPreferredTeachers(Set<Teacher> teachers) {
        this.preferredTeachers = teachers;
    }

    public Division getDivision() {
        return division;
    }

    public Place division(Division division) {
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
        Place place = (Place) o;
        if (place.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), place.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Place{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", numberOfSeats='" + getNumberOfSeats() + "'" +
            ", shortName='" + getShortName() + "'" +
            ", colorBackground='" + getColorBackground() + "'" +
            ", colorText='" + getColorText() + "'" +
            "}";
    }
}
