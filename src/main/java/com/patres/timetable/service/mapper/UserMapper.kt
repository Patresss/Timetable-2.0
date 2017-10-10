package com.patres.timetable.service.mapper

import com.patres.timetable.domain.Authority
import com.patres.timetable.domain.User
import com.patres.timetable.service.dto.UserDTO
import org.springframework.stereotype.Service


@Service
open class UserMapper : EntityMapper<User, UserDTO>() {

    override fun toEntity(entityDto: UserDTO): User {

        return User().apply {
            id = entityDto.id
            login = entityDto.login
            firstName = entityDto.firstName
            lastName = entityDto.lastName
            email = entityDto.email
            imageUrl = entityDto.imageUrl
            activated = entityDto.isActivated
            langKey = entityDto.langKey
            authorities = authoritiesFromStrings(entityDto.authorities)
        }
    }

    override fun toDto(entity: User): UserDTO {
        return UserDTO(
            id = entity.id,
            login = entity.login,
            firstName = entity.firstName,
            lastName = entity.lastName,
            email = entity.email,
            imageUrl = entity.imageUrl,
            isActivated = entity.activated,
            langKey = entity.langKey,
            createdBy = entity.createdBy,
            createdDate = entity.createdDate,
            lastModifiedBy = entity.lastModifiedBy,
            lastModifiedDate = entity.lastModifiedDate,
            authorities = entity.authorities.map { it.name }.toHashSet()
        )
    }

    fun authoritiesFromStrings(strings: Set<String>?): Set<Authority> {
        return strings?.map { string ->
            val auth = Authority()
            auth.name = string
            auth
        }?.toSet() ?: HashSet()
    }
}
