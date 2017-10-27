package com.patres.timetable.repository

import com.patres.timetable.domain.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.time.Instant

@Repository
interface UserRepository : JpaRepository<User, Long> {

    fun findOneByActivationKey(activationKey: String): User?

    fun findAllByActivatedIsFalseAndCreatedDateBefore(dateTime: Instant): List<User>

    fun findOneByResetKey(resetKey: String): User?

    fun findOneByEmail(email: String): User?

    fun findOneByLogin(login: String): User?

    @EntityGraph(attributePaths = arrayOf("authorities"))
    fun findOneWithAuthoritiesById(id: Long?): User

    @EntityGraph(attributePaths = arrayOf("authorities"))
    fun findOneWithAuthoritiesByLogin(login: String): User

    fun findAllByLoginNot(pageable: Pageable, login: String): Page<User>
}
