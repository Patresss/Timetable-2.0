package com.patres.timetable.web.rest.vm

import com.patres.timetable.service.dto.UserDTO
import java.time.Instant
import javax.validation.constraints.Size

/**
 * View Model extending the UserDTO, which is meant to be used in the user management UI.
 */
class ManagedUserVM(
    id: Long?,
    login: String,
    firstName: String,
    lastName: String,
    email: String,
    imageUrl: String,
    activated: Boolean,
    langKey: String,
    createdBy: String,
    createdDate: Instant,
    lastModifiedBy: String,
    lastModifiedDate: Instant,
    authorities: Set<String>,

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    var password: String

) : UserDTO(
    id = id,
    login = login,
    firstName = firstName,
    lastName = lastName,
    email = email,
    imageUrl = imageUrl,
    isActivated = activated,
    langKey = langKey,
    createdBy = createdBy,
    createdDate = createdDate,
    lastModifiedBy = lastModifiedBy,
    lastModifiedDate = lastModifiedDate,
    authorities = authorities
) {

    companion object {

        const val PASSWORD_MIN_LENGTH = 4

        const val PASSWORD_MAX_LENGTH = 100
    }

    override fun toString(): String {
        return "ManagedUserVM{" +
            "} " + super.toString()
    }

}
