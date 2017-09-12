package com.patres.timetable.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

public class TeacherDTO extends AbstractDivisionOwnerDTO implements Serializable {

    @NotNull
    private String name;

    @NotNull
    private String surname;

    private String degree;

    private String shortName;

    private Set<SubjectDTO> preferredSubjects = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Set<SubjectDTO> getPreferredSubjects() {
        return preferredSubjects;
    }

    public void setPreferredSubjects(Set<SubjectDTO> subjects) {
        this.preferredSubjects = subjects;
    }

    @Override
    public String toString() {
        return "TeacherDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", surname='" + getSurname() + "'" +
            ", degree='" + getDegree() + "'" +
            ", shortName='" + getShortName() + "'" +
            "}";
    }
}
