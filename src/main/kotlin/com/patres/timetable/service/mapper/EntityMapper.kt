package com.patres.timetable.service.mapper

import java.util.*

abstract class EntityMapper<EntityType, EntityDtoType> : EntityMapperToDto<EntityType, EntityDtoType>() {

    companion object {
        private const val SHORT_NAME_MAX_LENGTH = 3
        fun getShortName(fullName: String?): String {
            val nameWithoutWhitespaces = fullName?.replace("\\s".toRegex(), "")?: ""
            val maxChar = if (nameWithoutWhitespaces.length < SHORT_NAME_MAX_LENGTH) nameWithoutWhitespaces.length else SHORT_NAME_MAX_LENGTH
            return nameWithoutWhitespaces.substring(0, maxChar).toUpperCase()
        }
    }

    fun toEntity(dtoList: List<EntityDtoType>?): List<EntityType> {
        if (dtoList == null) {
            return ArrayList()
        }

        val list = dtoList.map { toEntity(it) }

        return list
    }

    fun entityDTOSetToEntitySet(entityDtoSet: Set<EntityDtoType>?): Set<EntityType> {
        if (entityDtoSet == null) {
            return HashSet()
        }

        val entitySet = entityDtoSet
            .map { toEntity(it) }
            .toHashSet()

        return entitySet
    }

    abstract fun toEntity(entityDto: EntityDtoType): EntityType

    open fun toDtoWithSampleForm(entity: EntityType): EntityDtoType {
        return toDto(entity)
    }

}
