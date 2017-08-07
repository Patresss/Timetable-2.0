package com.patres.timetable.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Place entity.
 */
public class PlaceDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private Long numberOfSeats;

    private String shortName;

    private String colorBackground;

    private String colorText;

    private Set<SubjectDTO> preferredSubjects = new HashSet<>();

    private Set<DivisionDTO> preferredDivisions = new HashSet<>();

    private Set<TeacherDTO> preferredTeachers = new HashSet<>();

    private Long divisionOwnerId;

    private String divisionOwnerName;

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

    public Long getDivisionOwnerId() {
        return divisionOwnerId;
    }

    public void setDivisionOwnerId(Long divisionOwnerId) {
        this.divisionOwnerId = divisionOwnerId;
    }

    public String getDivisionOwnerName() {
        return divisionOwnerName;
    }

    public void setDivisionOwnerName(String divisionOwnerName) {
        this.divisionOwnerName = divisionOwnerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PlaceDTO placeDTO = (PlaceDTO) o;
        if(placeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), placeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
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
