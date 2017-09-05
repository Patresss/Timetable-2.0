package com.patres.timetable.service.dto;


import java.io.Serializable;
import java.util.Objects;

public class AbstractApplicationEntityDTO implements Serializable {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AbstractApplicationEntityDTO entityDto = (AbstractApplicationEntityDTO) o;
        if(entityDto.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), entityDto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }


}
