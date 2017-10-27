package com.patres.timetable.security

import com.patres.timetable.config.Constants

import org.springframework.data.domain.AuditorAware
import org.springframework.stereotype.Component

/**
 * Implementation of AuditorAware based on Spring Security.
 */
@Component
class SpringSecurityAuditorAware : AuditorAware<String> {

    override fun getCurrentAuditor(): String {
        val userName = SecurityUtils.getCurrentUserLogin()
        return userName ?: Constants.SYSTEM_ACCOUNT
    }
}
