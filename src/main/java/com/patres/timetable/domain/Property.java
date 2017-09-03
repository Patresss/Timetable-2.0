package com.patres.timetable.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "property")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Property extends AbstractDivisionOwner implements Serializable {

    private static final long serialVersionUID = 3166015179341462218L;

    @NotNull
    @Column(name = "property_key", nullable = false)
    private String propertyKey;

    @Column(name = "property_value")
    private String propertyValue;

    public String getPropertyKey() {
        return propertyKey;
    }

    public void setPropertyKey(String propertyKey) {
        this.propertyKey = propertyKey;
    }

    public Property propertyKey(String propertyKey) {
        this.propertyKey = propertyKey;
        return this;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    public Property propertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Property property = (Property) o;
        if (property.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), property.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Property{" +
            "id=" + getId() +
            ", propertyKey='" + getPropertyKey() + "'" +
            ", propertyValue='" + getPropertyValue() + "'" +
            "}";
    }
}
