package com.patres.timetable.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

public class PlaceDTO extends AbstractDivisionOwnerDTO implements Serializable {

    @NotNull
    private String name;

    private Long numberOfSeats;

    private String shortName;

    private String colorBackground;

    private String colorText;

    private Set<SubjectDTO> preferredSubjects = new HashSet<>();

    private Set<DivisionDTO> preferredDivisions = new HashSet<>();

    private Set<TeacherDTO> preferredTeachers = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Long numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
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

    public Set<SubjectDTO> getPreferredSubjects() {
        return preferredSubjects;
    }

    public void setPreferredSubjects(Set<SubjectDTO> subjects) {
        this.preferredSubjects = subjects;
    }

    public Set<DivisionDTO> getPreferredDivisions() {
        return preferredDivisions;
    }

    public void setPreferredDivisions(Set<DivisionDTO> divisions) {
        this.preferredDivisions = divisions;
    }

    public Set<TeacherDTO> getPreferredTeachers() {
        return preferredTeachers;
    }

    public void setPreferredTeachers(Set<TeacherDTO> teachers) {
        this.preferredTeachers = teachers;
    }

    @Override
    public String toString() {
        return "PlaceDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", numberOfSeats='" + getNumberOfSeats() + "'" +
            ", shortName='" + getShortName() + "'" +
            ", colorBackground='" + getColorBackground() + "'" +
            ", colorText='" + getColorText() + "'" +
            "}";
    }
}
