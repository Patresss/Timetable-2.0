package com.patres.timetable.service.dto

import com.patres.timetable.config.Constants
import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.NotBlank
import java.time.Instant
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

open class UserDTO() : AbstractApplicationEntityDTO() {

    @NotBlank
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    public var login: String? = null

    @Size(max = 50)
    var firstName: String? = null

    @Size(max = 50)
    var lastName: String? = null

    @Email
    @Size(min = 5, max = 100)
    var email: String? = null

    @Size(max = 256)
    var imageUrl: String? = null

    var isActivated: Boolean = false

    @Size(min = 2, max = 5)
    var langKey: String? = null

    var createdBy: String? = null

    var createdDate: Instant? = null

    var lastModifiedBy: String? = null

    var lastModifiedDate: Instant? = null

    var authorities: Set<String> = HashSet()

    constructor(id: Long?) : this() {
        this.id = id
    }

    constructor(
        id: Long?,
        login: String?,
        firstName: String?,
        lastName: String?,
        email: String?,
        imageUrl: String?,
        isActivated: Boolean,
        langKey: String?,
        createdBy: String?,
        createdDate: Instant?,
        lastModifiedBy: String?,
        lastModifiedDate: Instant?,
        authorities: Set<String>) : this() {

        this.id = id
        this.login = login
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
        this.imageUrl = imageUrl
        this.isActivated = isActivated
        this.langKey = langKey
        this.createdBy = createdBy
        this.createdDate = createdDate
        this.lastModifiedBy = lastModifiedBy
        this.lastModifiedDate = lastModifiedDate
        this.authorities = authorities
    }

}
