package com.patres.timetable.domain;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@MappedSuperclass
public abstract class AbstractDivisionOwner extends AbstractApplicationEntity implements Serializable {

    private static final long serialVersionUID = 8302717018638550091L;

    @ManyToOne
    @NotNull
    private Division divisionOwner;

    public Division getDivisionOwner() {
        return divisionOwner;
    }

    public void setDivisionOwner(Division divisionOwner) {
        this.divisionOwner = divisionOwner;
    }

    public AbstractDivisionOwner divisionOwner(Division divisionOwner) {
        this.divisionOwner = divisionOwner;
        return this;
    }

}
