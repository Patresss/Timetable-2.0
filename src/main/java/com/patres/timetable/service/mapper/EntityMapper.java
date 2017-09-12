package com.patres.timetable.service.mapper;

import com.patres.timetable.domain.AbstractApplicationEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class EntityMapper<EntityType extends AbstractApplicationEntity, EntityDtoType> {

    public List<EntityType> toEntity(List<EntityDtoType> dtoList) {
        if (dtoList == null) {
            return null;
        }

        List<EntityType> list = new ArrayList<>();
        for (EntityDtoType entityDto : dtoList) {
            list.add(toEntity(entityDto));
        }

        return list;
    }

    public List<EntityDtoType> toDto(List<EntityType> entityList) {
        if (entityList == null) {
            return null;
        }

        List<EntityDtoType> list = new ArrayList<>();
        for (EntityType entity : entityList) {
            list.add(toDto(entity));
        }

        return list;
    }


    public Set<EntityDtoType> entitySetToEntityDTOSet(Set<EntityType> entitySet) {
        if (entitySet == null) {
            return null;
        }

        Set<EntityDtoType> entityDtoTypes = new HashSet<>();
        for (EntityType entity : entitySet) {
            entityDtoTypes.add(toDto(entity));
        }

        return entityDtoTypes;
    }

    public Set<EntityType> entityDTOSetToEntitySet(Set<EntityDtoType> entityDtoSet) {
        if (entityDtoSet == null) {
            return null;
        }

        Set<EntityType> entitySet = new HashSet<>();
        for (EntityDtoType entityDto : entityDtoSet) {
            entitySet.add(toEntity(entityDto));
        }

        return entitySet;
    }

    abstract public EntityType toEntity(EntityDtoType entityDto);

    abstract public EntityDtoType toDto(EntityType entity);

    abstract public EntityType fromId(Long id);

}
