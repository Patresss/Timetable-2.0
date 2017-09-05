package com.patres.timetable.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public abstract class AbstractDivisionOwnerDTO extends AbstractApplicationEntityDTO implements Serializable {

    @NotNull
    private Long divisionOwnerId;

    private String divisionOwnerName;

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


}
