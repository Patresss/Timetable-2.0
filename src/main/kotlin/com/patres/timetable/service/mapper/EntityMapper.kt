package com.patres.timetable.service.mapper

import com.patres.timetable.domain.AbstractApplicationEntity
import com.patres.timetable.domain.Teacher
import com.patres.timetable.service.dto.TeacherDTO

import java.util.ArrayList
import java.util.HashSet

abstract class EntityMapper<EntityType, EntityDtoType> {

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

    abstract fun toDto(entity: EntityType): EntityDtoType

    open fun toDtoWithSampleForm(entity: EntityType): EntityDtoType {
        return toDto(entity)
    }

}
