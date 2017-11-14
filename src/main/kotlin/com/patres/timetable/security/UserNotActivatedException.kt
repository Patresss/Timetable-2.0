package com.patres.timetable.security

import org.springframework.security.core.AuthenticationException

/**
 * This exception is thrown in case of a not activated user trying to authenticate.
 */
class UserNotActivatedException : AuthenticationException {

    companion object {
        private val serialVersionUID = 1L
    }

    constructor(message: String) : super(message)
    constructor(message: String, t: Throwable) : super(message, t)

}
