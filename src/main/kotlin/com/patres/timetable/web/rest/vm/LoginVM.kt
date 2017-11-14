package com.patres.timetable.web.rest.vm

import com.patres.timetable.config.Constants
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

/**
 * View Model object for storing a user's credentials.
 */
class LoginVM(
    @get: Pattern(regexp = Constants.LOGIN_REGEX)
    @get: Size(min = 1, max = 50)
    var username: String = "",

    @get: Size(min = ManagedUserVM.PASSWORD_MIN_LENGTH, max = ManagedUserVM.PASSWORD_MAX_LENGTH)
    var password: String = "",

    var isRememberMe: Boolean = false

)
