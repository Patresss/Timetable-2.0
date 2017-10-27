package com.patres.timetable.security

import com.patres.timetable.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
open class DomainUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {

    companion object {
        private val log = LoggerFactory.getLogger(DomainUserDetailsService::class.java)
    }


    @Transactional
    override fun loadUserByUsername(login: String): UserDetails {
        log.debug("Authenticating {}", login)
        val lowercaseLogin = login.toLowerCase(Locale.ENGLISH)
        val userFromDatabase = Optional.ofNullable(userRepository.findOneWithAuthoritiesByLogin(lowercaseLogin))
        return userFromDatabase.map { user ->
            if (!user.activated) {
                throw UserNotActivatedException("User $lowercaseLogin was not activated")
            }
            val grantedAuthorities = user.authorities.map { authority -> SimpleGrantedAuthority(authority.name) }
            org.springframework.security.core.userdetails.User(lowercaseLogin, user.password?: "", grantedAuthorities)
        }.orElseThrow {
            UsernameNotFoundException("User $lowercaseLogin was not found in the database")
        }
    }
}
