package com.patres.timetable.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.patres.timetable.config.Constants
import org.hibernate.annotations.BatchSize
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import org.hibernate.validator.constraints.Email
import java.io.Serializable
import java.time.Instant
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@Entity
@Table(name = "jhi_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
class User : AbstractAuditingEntity(), Serializable {

    @get:NotNull
    @get:Pattern(regexp = Constants.LOGIN_REGEX)
    @get:Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    var login: String? = null
        set(value) {
            field = value?.toLowerCase()
        }

    @JsonIgnore
    @get:NotNull
    @get:Size(min = 60, max = 60)
    @Column(name = "password_hash", length = 60)
    var password: String? = null

    @get:Size(max = 50)
    @Column(name = "first_name", length = 50)
    var firstName: String? = null

    @get:Size(max = 50)
    @Column(name = "last_name", length = 50)
    var lastName: String? = null

    @get:Email
    @get:Size(min = 5, max = 100)
    @Column(length = 100, unique = true)
    var email: String? = null

    @get:NotNull
    @Column(nullable = false)
    var activated: Boolean = false

    @get:Size(min = 2, max = 5)
    @Column(name = "lang_key", length = 5)
    var langKey: String? = null

    @get:Size(max = 256)
    @Column(name = "image_url", length = 256)
    var imageUrl: String? = null

    @get:Size(max = 20)
    @Column(name = "activation_key", length = 20)
    @JsonIgnore
    var activationKey: String? = null

    @get:Size(max = 20)
    @Column(name = "reset_key", length = 20)
    @JsonIgnore
    var resetKey: String? = null

    @Column(name = "reset_date")
    var resetDate: Instant? = null

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "jhi_user_authority", joinColumns = arrayOf(JoinColumn(name = "user_id", referencedColumnName = "id")), inverseJoinColumns = arrayOf(JoinColumn(name = "authority_name", referencedColumnName = "name")))
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = 20)
    var authorities: Set<Authority> = HashSet()

    @ManyToMany(mappedBy = "users")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var divisions: Set<Division> = HashSet()


    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }

        val user = other as User?
        return !(user!!.id == null || id == null) && id == user.id
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }


}
