package com.patres.timetable.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String SCHOOL_ADMIN = "ROLE_SCHOOL_ADMIN";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String TEACHER = "ROLE_TEACHER";

    public static final String TIMETABLE_WATCHER = "ROLE_TIMETABLE_WATCHER";


    private AuthoritiesConstants() {
    }
}
