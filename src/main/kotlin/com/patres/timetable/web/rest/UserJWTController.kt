package com.patres.timetable.web.rest

import com.codahale.metrics.annotation.Timed
import com.fasterxml.jackson.annotation.JsonProperty
import com.patres.timetable.security.jwt.JWTConfigurer
import com.patres.timetable.security.jwt.TokenProvider
import com.patres.timetable.web.rest.vm.LoginVM
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
open class UserJWTController(private val tokenProvider: TokenProvider, private val authenticationManager: AuthenticationManager) {

    companion object {
        private val log = LoggerFactory.getLogger(UserJWTController::class.java)
    }


    @PostMapping("/authenticate")
    @Timed
    open fun authorize(@Valid @RequestBody loginVM: LoginVM, response: HttpServletResponse): ResponseEntity<*> {

        val authenticationToken = UsernamePasswordAuthenticationToken(loginVM.username, loginVM.password)

        return try {
            val authentication = this.authenticationManager.authenticate(authenticationToken)
            SecurityContextHolder.getContext().authentication = authentication
            val rememberMe = loginVM.isRememberMe
            val jwt = tokenProvider.createToken(authentication, rememberMe)
            response.addHeader(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt)
            ResponseEntity.ok(JWTToken(jwt))
        } catch (ae: AuthenticationException) {
            log.trace("Authentication exception trace: {}", ae)
            ResponseEntity(Collections.singletonMap("AuthenticationException",
                ae.localizedMessage), HttpStatus.UNAUTHORIZED)
        }

    }

    /**
     * Object to return as body in JWT Authentication.
     */
    internal class JWTToken(
        @get:JsonProperty("id_token")
        var idToken: String?)
}
