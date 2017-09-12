package com.patres.timetable.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.patres.timetable.domain.enumeration.DivisionType;

/**
 * A DTO for the Division entity.
 */
public class DivisionDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String shortName;

    private Long numberOfPeople;

    @NotNull
    private DivisionType divisionType;

    private String colorBackground;

    private String colorText;

    private Set<DivisionDTO> parents = new HashSet<>();

    private Set<UserDTO> users = new HashSet<>();

    private Set<TeacherDTO> preferredTeachers = new HashSet<>();

    private Set<SubjectDTO> preferredSubjects = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Long getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(Long numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public DivisionType getDivisionType() {
        return divisionType;
    }

    public void setDivisionType(DivisionType divisionType) {
        this.divisionType = divisionType;
    }

    public String getColorBackground() {
        return colorBackground;
    }

    public void setColorBackground(String colorBackground) {
        this.colorBackground = colorBackground;
    }

    public String getColorText() {
        return colorText;
    }

    public void setColorText(String colorText) {
        this.colorText = colorText;
    }

    public Set<DivisionDTO> getParents() {
        return parents;
    }

    public void setParents(Set<DivisionDTO> divisions) {
        this.parents = divisions;
    }

    public Set<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDTO> users) {
        this.users = users;
    }

    public Set<TeacherDTO> getPreferredTeachers() {
        return preferredTeachers;
    }

    public void setPreferredTeachers(Set<TeacherDTO> teachers) {
        this.preferredTeachers = teachers;
    }

    public Set<SubjectDTO> getPreferredSubjects() {
        return preferredSubjects;
    }

    public void setPreferredSubjects(Set<SubjectDTO> subjects) {
        this.preferredSubjects = subjects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DivisionDTO divisionDTO = (DivisionDTO) o;
        if(divisionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), divisionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DivisionDTO{" +
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
