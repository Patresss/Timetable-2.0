package com.patres.timetable.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.patres.timetable.config.Constants
import org.apache.commons.lang3.StringUtils
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

/**
 * A user.
 */
@Entity
@Table(name = "jhi_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
class User(

    //Lowercase the login before saving it in database
    @get:NotNull
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    var login: String? = null,

    @JsonIgnore
    @get:NotNull
    @Size(min = 60, max = 60)
    @Column(name = "password_hash", length = 60)
    var password: String? = null,

    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    var firstName: String? = null,

    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    var lastName: String? = null,

    @Email
    @Size(min = 5, max = 100)
    @Column(length = 100, unique = true)
    var email: String? = null,

    @get:NotNull
    @Column(nullable = false)
    var activated:Boolean = false,

    @Size(min = 2, max = 5)
    @Column(name = "lang_key", length = 5)
    var langKey: String? = null,

    @Size(max = 256)
    @Column(name = "image_url", length = 256)
    var imageUrl: String? = null,

    @Size(max = 20)
    @Column(name = "activation_key", length = 20)
    @JsonIgnore
    var activationKey: String? = null,

    @Size(max = 20)
    @Column(name = "reset_key", length = 20)
    @JsonIgnore
    var resetKey: String? = null,

    @Column(name = "reset_date")
    var resetDate: Instant? = null,

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "jhi_user_authority", joinColumns = arrayOf(JoinColumn(name = "user_id", referencedColumnName = "id")), inverseJoinColumns = arrayOf(JoinColumn(name = "authority_name", referencedColumnName = "name")))
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = 20)
    var authorities: Set<Authority> = HashSet(),

    @ManyToMany(mappedBy = "users")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    var divisions: Set<Division> = HashSet()


) : AbstractAuditingEntity(), Serializable {


    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }

        val user = o as User?
        return !(user!!.id == null || id == null) && id == user.id
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }




}
