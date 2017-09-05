package com.patres.timetable.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

public class PropertyDTO extends AbstractDivisionOwnerDTO implements Serializable {

    @NotNull
    private String propertyKey;

    private String propertyValue;

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

    @Override
    public String toString() {
        return "PropertyDTO{" +
            "id=" + getId() +
            ", propertyKey='" + getPropertyKey() + "'" +
            ", propertyValue='" + getPropertyValue() + "'" +
            "}";
    }
}
