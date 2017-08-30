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
 * A Teacher.
 */
@Entity
@Table(name = "teacher")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Teacher extends ApplicationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "jhi_degree")
    private String degree;

    @Column(name = "short_name")
    private String shortName;

    @OneToMany(mappedBy = "teacher")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Timetable> timetables = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "teacher_preferred_subject",
               joinColumns = @JoinColumn(name="teachers_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="preferred_subjects_id", referencedColumnName="id"))
    private Set<Subject> preferredSubjects = new HashSet<>();

    @ManyToOne
    private Division divisionOwner;

    @ManyToMany(mappedBy = "preferredTeachers")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Division> preferredDivisions = new HashSet<>();

    @ManyToMany(mappedBy = "preferredTeachers")
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

    public Teacher name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public Teacher surname(String surname) {
        this.surname = surname;
        return this;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDegree() {
        return degree;
    }

    public Teacher degree(String degree) {
        this.degree = degree;
        return this;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getShortName() {
        return shortName;
    }

    public Teacher shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Set<Timetable> getTimetables() {
        return timetables;
    }

    public Teacher timetables(Set<Timetable> timetables) {
        this.timetables = timetables;
        return this;
    }

    public Teacher addTimetable(Timetable timetable) {
        this.timetables.add(timetable);
        timetable.setTeacher(this);
        return this;
    }

    public Teacher removeTimetable(Timetable timetable) {
        this.timetables.remove(timetable);
        timetable.setTeacher(null);
        return this;
    }

    public void setTimetables(Set<Timetable> timetables) {
        this.timetables = timetables;
    }

    public Set<Subject> getPreferredSubjects() {
        return preferredSubjects;
    }

    public Teacher preferredSubjects(Set<Subject> subjects) {
        this.preferredSubjects = subjects;
        return this;
    }

    public Teacher addPreferredSubject(Subject subject) {
        this.preferredSubjects.add(subject);
        subject.getPreferredTeachers().add(this);
        return this;
    }

    public Teacher removePreferredSubject(Subject subject) {
        this.preferredSubjects.remove(subject);
        subject.getPreferredTeachers().remove(this);
        return this;
    }

    public void setPreferredSubjects(Set<Subject> subjects) {
        this.preferredSubjects = subjects;
    }

    public Division getDivisionOwner() {
        return divisionOwner;
    }

    public Teacher divisionOwner(Division divisionOwner) {
        this.divisionOwner = divisionOwner;
        return this;
    }

    public void setDivisionOwner(Division divisionOwner) {
        this.divisionOwner = divisionOwner;
    }

    public Set<Division> getPreferredDivisions() {
        return preferredDivisions;
    }

    public Teacher preferredDivisions(Set<Division> divisions) {
        this.preferredDivisions = divisions;
        return this;
    }

    public Teacher addPreferredDivision(Division division) {
        this.preferredDivisions.add(division);
        division.getPreferredTeachers().add(this);
        return this;
    }

    public Teacher removePreferredDivision(Division division) {
        this.preferredDivisions.remove(division);
        division.getPreferredTeachers().remove(this);
        return this;
    }

    public void setPreferredDivisions(Set<Division> divisions) {
        this.preferredDivisions = divisions;
    }

    public Set<Place> getPreferredPlaces() {
        return preferredPlaces;
    }

    public Teacher preferredPlaces(Set<Place> places) {
        this.preferredPlaces = places;
        return this;
    }

    public Teacher addPreferredPlace(Place place) {
        this.preferredPlaces.add(place);
        place.getPreferredTeachers().add(this);
        return this;
    }

    public Teacher removePreferredPlace(Place place) {
        this.preferredPlaces.remove(place);
        place.getPreferredTeachers().remove(this);
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
        Teacher teacher = (Teacher) o;
        if (teacher.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), teacher.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Teacher{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", surname='" + getSurname() + "'" +
            ", degree='" + getDegree() + "'" +
            ", shortName='" + getShortName() + "'" +
            "}";
    }
}
