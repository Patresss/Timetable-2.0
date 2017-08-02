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

import com.patres.timetable.domain.enumeration.DivisionType;

/**
 * A Division.
 */
@Entity
@Table(name = "division")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Division implements Serializable {

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

    @Column(name = "number_of_people")
    private Long numberOfPeople;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "division_type", nullable = false)
    private DivisionType divisionType;

    @Column(name = "color_background")
    private String colorBackground;

    @Column(name = "color_text")
    private String colorText;

    @OneToMany(mappedBy = "division")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Timetable> timetables = new HashSet<>();

    @OneToMany(mappedBy = "division")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Place> divisionPlaces = new HashSet<>();

    @OneToMany(mappedBy = "division")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Teacher> divisionTeachers = new HashSet<>();

    @OneToMany(mappedBy = "division")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Subject> divisionSubjects = new HashSet<>();

    @OneToMany(mappedBy = "division")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Lesson> divisionLessons = new HashSet<>();

    @OneToMany(mappedBy = "division")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Period> divisionPeriods = new HashSet<>();

    @OneToMany(mappedBy = "division")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Property> divisionProperties = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "division_parent",
               joinColumns = @JoinColumn(name="divisions_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="parents_id", referencedColumnName="id"))
    private Set<Division> parents = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "division_user",
               joinColumns = @JoinColumn(name="divisions_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="users_id", referencedColumnName="id"))
    private Set<User> users = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "division_preferred_teacher",
               joinColumns = @JoinColumn(name="divisions_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="preferred_teachers_id", referencedColumnName="id"))
    private Set<Teacher> preferredTeachers = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "division_preferred_subject",
               joinColumns = @JoinColumn(name="divisions_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="preferred_subjects_id", referencedColumnName="id"))
    private Set<Subject> preferredSubjects = new HashSet<>();

    @ManyToMany(mappedBy = "preferredDivisions")
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

    public Division name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public Division shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Long getNumberOfPeople() {
        return numberOfPeople;
    }

    public Division numberOfPeople(Long numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
        return this;
    }

    public void setNumberOfPeople(Long numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public DivisionType getDivisionType() {
        return divisionType;
    }

    public Division divisionType(DivisionType divisionType) {
        this.divisionType = divisionType;
        return this;
    }

    public void setDivisionType(DivisionType divisionType) {
        this.divisionType = divisionType;
    }

    public String getColorBackground() {
        return colorBackground;
    }

    public Division colorBackground(String colorBackground) {
        this.colorBackground = colorBackground;
        return this;
    }

    public void setColorBackground(String colorBackground) {
        this.colorBackground = colorBackground;
    }

    public String getColorText() {
        return colorText;
    }

    public Division colorText(String colorText) {
        this.colorText = colorText;
        return this;
    }

    public void setColorText(String colorText) {
        this.colorText = colorText;
    }

    public Set<Timetable> getTimetables() {
        return timetables;
    }

    public Division timetables(Set<Timetable> timetables) {
        this.timetables = timetables;
        return this;
    }

    public Division addTimetable(Timetable timetable) {
        this.timetables.add(timetable);
        timetable.setDivision(this);
        return this;
    }

    public Division removeTimetable(Timetable timetable) {
        this.timetables.remove(timetable);
        timetable.setDivision(null);
        return this;
    }

    public void setTimetables(Set<Timetable> timetables) {
        this.timetables = timetables;
    }

    public Set<Place> getDivisionPlaces() {
        return divisionPlaces;
    }

    public Division divisionPlaces(Set<Place> places) {
        this.divisionPlaces = places;
        return this;
    }

    public Division addDivisionPlace(Place place) {
        this.divisionPlaces.add(place);
        place.setDivision(this);
        return this;
    }

    public Division removeDivisionPlace(Place place) {
        this.divisionPlaces.remove(place);
        place.setDivision(null);
        return this;
    }

    public void setDivisionPlaces(Set<Place> places) {
        this.divisionPlaces = places;
    }

    public Set<Teacher> getDivisionTeachers() {
        return divisionTeachers;
    }

    public Division divisionTeachers(Set<Teacher> teachers) {
        this.divisionTeachers = teachers;
        return this;
    }

    public Division addDivisionTeacher(Teacher teacher) {
        this.divisionTeachers.add(teacher);
        teacher.setDivision(this);
        return this;
    }

    public Division removeDivisionTeacher(Teacher teacher) {
        this.divisionTeachers.remove(teacher);
        teacher.setDivision(null);
        return this;
    }

    public void setDivisionTeachers(Set<Teacher> teachers) {
        this.divisionTeachers = teachers;
    }

    public Set<Subject> getDivisionSubjects() {
        return divisionSubjects;
    }

    public Division divisionSubjects(Set<Subject> subjects) {
        this.divisionSubjects = subjects;
        return this;
    }

    public Division addDivisionSubject(Subject subject) {
        this.divisionSubjects.add(subject);
        subject.setDivision(this);
        return this;
    }

    public Division removeDivisionSubject(Subject subject) {
        this.divisionSubjects.remove(subject);
        subject.setDivision(null);
        return this;
    }

    public void setDivisionSubjects(Set<Subject> subjects) {
        this.divisionSubjects = subjects;
    }

    public Set<Lesson> getDivisionLessons() {
        return divisionLessons;
    }

    public Division divisionLessons(Set<Lesson> lessons) {
        this.divisionLessons = lessons;
        return this;
    }

    public Division addDivisionLesson(Lesson lesson) {
        this.divisionLessons.add(lesson);
        lesson.setDivision(this);
        return this;
    }

    public Division removeDivisionLesson(Lesson lesson) {
        this.divisionLessons.remove(lesson);
        lesson.setDivision(null);
        return this;
    }

    public void setDivisionLessons(Set<Lesson> lessons) {
        this.divisionLessons = lessons;
    }

    public Set<Period> getDivisionPeriods() {
        return divisionPeriods;
    }

    public Division divisionPeriods(Set<Period> periods) {
        this.divisionPeriods = periods;
        return this;
    }

    public Division addDivisionPeriod(Period period) {
        this.divisionPeriods.add(period);
        period.setDivision(this);
        return this;
    }

    public Division removeDivisionPeriod(Period period) {
        this.divisionPeriods.remove(period);
        period.setDivision(null);
        return this;
    }

    public void setDivisionPeriods(Set<Period> periods) {
        this.divisionPeriods = periods;
    }

    public Set<Property> getDivisionProperties() {
        return divisionProperties;
    }

    public Division divisionProperties(Set<Property> properties) {
        this.divisionProperties = properties;
        return this;
    }

    public Division addDivisionProperties(Property property) {
        this.divisionProperties.add(property);
        property.setDivision(this);
        return this;
    }

    public Division removeDivisionProperties(Property property) {
        this.divisionProperties.remove(property);
        property.setDivision(null);
        return this;
    }

    public void setDivisionProperties(Set<Property> properties) {
        this.divisionProperties = properties;
    }

    public Set<Division> getParents() {
        return parents;
    }

    public Division parents(Set<Division> divisions) {
        this.parents = divisions;
        return this;
    }

    public Division addParent(Division division) {
        this.parents.add(division);
        return this;
    }

    public Division removeParent(Division division) {
        this.parents.remove(division);
        return this;
    }

    public void setParents(Set<Division> divisions) {
        this.parents = divisions;
    }

    public Set<User> getUsers() {
        return users;
    }

    public Division users(Set<User> users) {
        this.users = users;
        return this;
    }

    public Division addUser(User user) {
        this.users.add(user);
        user.getDivisions().add(this);
        return this;
    }

    public Division removeUser(User user) {
        this.users.remove(user);
        user.getDivisions().remove(this);
        return this;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Teacher> getPreferredTeachers() {
        return preferredTeachers;
    }

    public Division preferredTeachers(Set<Teacher> teachers) {
        this.preferredTeachers = teachers;
        return this;
    }

    public Division addPreferredTeacher(Teacher teacher) {
        this.preferredTeachers.add(teacher);
        teacher.getPreferredDivisions().add(this);
        return this;
    }

    public Division removePreferredTeacher(Teacher teacher) {
        this.preferredTeachers.remove(teacher);
        teacher.getPreferredDivisions().remove(this);
        return this;
    }

    public void setPreferredTeachers(Set<Teacher> teachers) {
        this.preferredTeachers = teachers;
    }

    public Set<Subject> getPreferredSubjects() {
        return preferredSubjects;
    }

    public Division preferredSubjects(Set<Subject> subjects) {
        this.preferredSubjects = subjects;
        return this;
    }

    public Division addPreferredSubject(Subject subject) {
        this.preferredSubjects.add(subject);
        subject.getPreferredDivisions().add(this);
        return this;
    }

    public Division removePreferredSubject(Subject subject) {
        this.preferredSubjects.remove(subject);
        subject.getPreferredDivisions().remove(this);
        return this;
    }

    public void setPreferredSubjects(Set<Subject> subjects) {
        this.preferredSubjects = subjects;
    }

    public Set<Place> getPreferredPlaces() {
        return preferredPlaces;
    }

    public Division preferredPlaces(Set<Place> places) {
        this.preferredPlaces = places;
        return this;
    }

    public Division addPreferredPlace(Place place) {
        this.preferredPlaces.add(place);
        place.getPreferredDivisions().add(this);
        return this;
    }

    public Division removePreferredPlace(Place place) {
        this.preferredPlaces.remove(place);
        place.getPreferredDivisions().remove(this);
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
        Division division = (Division) o;
        if (division.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), division.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Division{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", shortName='" + getShortName() + "'" +
            ", numberOfPeople='" + getNumberOfPeople() + "'" +
            ", divisionType='" + getDivisionType() + "'" +
            ", colorBackground='" + getColorBackground() + "'" +
            ", colorText='" + getColorText() + "'" +
            "}";
    }
}
