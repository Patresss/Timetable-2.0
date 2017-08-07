package com.patres.timetable.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Period entity.
 */
public class PeriodDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

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

        PeriodDTO periodDTO = (PeriodDTO) o;
        if(periodDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), periodDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PeriodDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
