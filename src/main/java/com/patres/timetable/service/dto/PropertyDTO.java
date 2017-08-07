package com.patres.timetable.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Property entity.
 */
public class PropertyDTO implements Serializable {

    private Long id;

    @NotNull
    private String propertyKey;

    private String propertyValue;

    private Long divisionOwnerId;

    private String divisionOwnerName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPropertyKey() {
        return propertyKey;
    }

    public void setPropertyKey(String propertyKey) {
        this.propertyKey = propertyKey;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
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

        PropertyDTO propertyDTO = (PropertyDTO) o;
        if(propertyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), propertyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PropertyDTO{" +
            "id=" + getId() +
            ", propertyKey='" + getPropertyKey() + "'" +
            ", propertyValue='" + getPropertyValue() + "'" +
            "}";
    }
}
