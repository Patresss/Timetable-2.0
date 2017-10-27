package com.patres.timetable.security

/**
 * Constants for Spring Security authorities.
 */
object AuthoritiesConstants {

    const val ADMIN = "ROLE_ADMIN"
    const val SCHOOL_ADMIN = "ROLE_SCHOOL_ADMIN"
    const val ANONYMOUS = "ROLE_ANONYMOUS"
    const val TEACHER = "ROLE_TEACHER"
    const val TIMETABLE_WATCHER = "ROLE_TIMETABLE_WATCHER"
}
