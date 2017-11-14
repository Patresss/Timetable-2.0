package com.patres.timetable.service.mapper

import com.patres.timetable.domain.AbstractApplicationEntity

import java.util.ArrayList
import java.util.HashSet

abstract class EntityMapper<EntityType : AbstractApplicationEntity, EntityDtoType> {

    fun toEntity(dtoList: List<EntityDtoType>?): List<EntityType> {
        if (dtoList == null) {
            return ArrayList()
        }

        val list = dtoList.map { toEntity(it) }

        return list
    }

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
            .toSet()

        return entityDtoTypes
    }

    fun entityDTOSetToEntitySet(entityDtoSet: Set<EntityDtoType>?): Set<EntityType> {
        if (entityDtoSet == null) {
            return HashSet()
        }

        val entitySet = entityDtoSet
            .map { toEntity(it) }
            .toSet()

        return entitySet
    }

    abstract fun toEntity(entityDto: EntityDtoType): EntityType

    abstract fun toDto(entity: EntityType): EntityDtoType

}
