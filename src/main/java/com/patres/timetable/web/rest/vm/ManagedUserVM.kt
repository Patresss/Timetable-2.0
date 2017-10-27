package com.patres.timetable.web.rest.vm

import com.patres.timetable.service.dto.UserDTO
import javax.validation.constraints.Size

/**
 * View Model extending the UserDTO, which is meant to be used in the user management UI.
 */
data class ManagedUserVM(
    @get:Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    var password: String? = null
) : UserDTO() {

    companion object {
        const val PASSWORD_MIN_LENGTH = 4
        const val PASSWORD_MAX_LENGTH = 100
    }

}
