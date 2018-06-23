package com.patres.timetable.security

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails

/**
 * Utility class for Spring Security.
 */
object SecurityUtils {

    /**
     * Get the login of the current user.
     *
     * @return the login of the current user
     */
    fun getCurrentUserLogin(): String? {
        val securityContext = SecurityContextHolder.getContext()
        val authentication = securityContext.authentication
        var userName: String? = null
        if (authentication != null) {
            if (authentication.principal is UserDetails) {
                val springSecurityUser = authentication.principal as UserDetails
                userName = springSecurityUser.username
            } else if (authentication.principal is String) {
                userName = authentication.principal as String
            }
        }
        return userName
    }

    /**
     * Get the JWT of the current user.
     *
     * @return the JWT of the current user
     */
    fun getCurrentUserJWT(): String? {
        val securityContext = SecurityContextHolder.getContext()
        val authentication = securityContext.authentication
        return if (authentication != null && authentication.credentials is String) {
            authentication.credentials as String
        } else null
    }

    /**
     * Check if a user is authenticated.
     *
     * @return true if the user is authenticated, false otherwise
     */
    fun isAuthenticated(): Boolean {
        val securityContext = SecurityContextHolder.getContext()
        val authentication = securityContext.authentication
        return authentication?.authorities?.stream()?.noneMatch { grantedAuthority -> grantedAuthority.authority == AuthoritiesConstants.ANONYMOUS } ?: false
    }

    /**
     * If the current user has a specific authority (security role).
     *
     *
     * The name of this method comes from the isUserInRole() method in the Servlet API
     *
     * @param authority the authority to check
     * @return true if the current user has the authority, false otherwise
     */
    fun isCurrentUserInRole(authority: String): Boolean {
        val securityContext = SecurityContextHolder.getContext()
        val authentication = securityContext.authentication
        return authentication?.authorities?.any{ it.authority == authority}?: false
    }

}
