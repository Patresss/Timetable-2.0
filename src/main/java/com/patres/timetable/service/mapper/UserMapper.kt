package com.patres.timetable.service.mapper

import com.patres.timetable.domain.Authority
import com.patres.timetable.domain.User
import com.patres.timetable.service.dto.UserDTO
import org.springframework.stereotype.Service

/**
 * Mapper for the entity User and its DTO called UserDTO.

 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
open class UserMapper {

    fun userToUserDTO(user: User): UserDTO {
        val userDto = UserDTO(
            id = user.id,
            login = user.login,
            firstName = user.firstName,
            lastName = user.lastName,
            email = user.email,
            imageUrl = user.imageUrl,
            isActivated = user.activated,
            langKey = user.langKey,
            createdBy = user.createdBy,
            createdDate = user.createdDate,
            lastModifiedBy = user.lastModifiedBy,
            lastModifiedDate = user.lastModifiedDate,
            authorities = user.authorities.map { it.name }.toHashSet()
        )
        return userDto
    }

    fun usersToUserDTOs(users: List<User>): List<UserDTO> {
        return users.filterNotNull().map { userToUserDTO(it) }
    }

    fun userDTOToUser(userDTO: UserDTO): User {
        val user = User(
            login = userDTO.login,
            firstName = userDTO.firstName,
            lastName = userDTO.lastName,
            email = userDTO.email,
            imageUrl = userDTO.imageUrl,
            activated = userDTO.isActivated,
            langKey = userDTO.langKey
        )
        user.id = userDTO.id

        val authorities = this.authoritiesFromStrings(userDTO.authorities)
        if (authorities != null) {
            user.authorities = authorities
        }
        return user
    }

    fun userDTOsToUsers(userDTOs: List<UserDTO>): List<User> {
        return userDTOs.filterNotNull().map { userDTOToUser(it) }
    }

    fun userFromId(id: Long?): User? {
        if (id == null) {
            return null
        }
        val user = User()
        user.id = id
        return user
    }

    fun userSetToUserDTOSet(userSet: Set<User>?): Set<UserDTO> {
        if (userSet == null) {
            return HashSet()
        }

        val userDtoSet = HashSet<UserDTO>()
        for (user in userSet) {
            userDtoSet.add(userToUserDTO(user))
        }

        return userDtoSet
    }

    fun userDTOSetToUserSet(userDtoSet: Set<UserDTO>?): Set<User> {
        if (userDtoSet == null) {
            return HashSet()
        }

        val userSet = HashSet<User>()
        userDtoSet.mapTo(userSet) { userDTOToUser(it) }

        return userSet
    }

    fun authoritiesFromStrings(strings: Set<String>?): Set<Authority>? {
        return strings?.map { string ->
            val auth = Authority()
            auth.name = string
            auth
        }?.toSet() ?: HashSet()
    }
}
