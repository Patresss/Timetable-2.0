package com.patres.timetable.service.dto

import com.patres.timetable.config.Constants
import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.NotBlank
import java.time.Instant
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

open class UserDTO(

    @NotBlank
    @get:Pattern(regexp = Constants.LOGIN_REGEX)
    @get:Size(min = 1, max = 50)
    var login: String? = null,

    @get:Size(max = 50)
    var firstName: String? = null,

    @get:Size(max = 50)
    var lastName: String? = null,

    @get:Email
    @get:Size(min = 5, max = 100)
    var email: String? = null,

    @get:Size(max = 256)
    var imageUrl: String? = null,

    var activated: Boolean = false,

    @get:Size(min = 2, max = 5)
    var langKey: String? = null,

    var createdBy: String? = null,

    var createdDate: Instant? = null,

    var lastModifiedBy: String? = null,

    var lastModifiedDate: Instant? = null,

    var authorities: Set<String> = HashSet(),

    var schoolId: Long? = null,

    var schoolName: String = "",

    var teacherId: Long? = null,

    var teacherFullName: String = ""

    ) : AbstractApplicationEntityDTO() {


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
        activated: Boolean,
        langKey: String?,
        createdBy: String?,
        createdDate: Instant?,
        lastModifiedBy: String?,
        lastModifiedDate: Instant?,
        authorities: Set<String>?,
        schoolId: Long? = null,
        schoolName: String = "",
        teacherId: Long? = null,
        teacherFullName: String = "")
    : this() {
        this.id = id
        this.login = login
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
        this.imageUrl = imageUrl
        this.activated = activated
        this.langKey = langKey
        this.createdBy = createdBy
        this.createdDate = createdDate
        this.lastModifiedBy = lastModifiedBy
        this.lastModifiedDate = lastModifiedDate
        this.authorities = authorities?: HashSet()
        this.schoolId = schoolId
        this.schoolName = schoolName
        this.teacherId = teacherId
        this.teacherFullName = teacherFullName
    }

}
