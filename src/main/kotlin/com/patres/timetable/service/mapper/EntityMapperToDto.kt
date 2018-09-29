package com.patres.timetable.service.mapper

import java.util.ArrayList
import java.util.HashSet

abstract class EntityMapperToDto<EntityType, EntityDtoType> {

    fun toDto(entityList: List<EntityType>?): List<EntityDtoType> {
        if (entityList == null) {
            return ArrayList()
        }

        val list = entityList.map { toDto(it) }

        return list
    }

    fun entitySetToEntityDTOSet(entitySet: Set<EntityType>?): Set<EntityDtoType> {
        if (entitySet == null) {
            return HashSet()
        }

        val entityDtoTypes = entitySet
            .map { toDto(it) }
            .toHashSet()

        return entityDtoTypes
    }

    abstract fun toDto(entity: EntityType): EntityDtoType
}
