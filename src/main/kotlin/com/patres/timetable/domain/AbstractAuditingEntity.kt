package com.patres.timetable.domain

import java.io.Serializable

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.envers.Audited
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

/**
 * Base abstract class for entities which will hold definitions for created, last modified by and created,
 * last modified by date.
 */
@MappedSuperclass
@Audited
@EntityListeners(AuditingEntityListener::class)
abstract class AbstractAuditingEntity : AbstractApplicationEntity(), Serializable {

    @CreatedBy
    @Column(name = "created_by", nullable = false, length = 50, updatable = false)
    @JsonIgnore
    var createdBy: String? = null

    @CreatedDate
    @Column(name = "created_date", nullable = false)
    @JsonIgnore
    var createdDate = Instant.now()

    @LastModifiedBy
    @Column(name = "last_modified_by", length = 50)
    @JsonIgnore
    var lastModifiedBy: String? = null

    @LastModifiedDate
    @Column(name = "last_modified_date")
    @JsonIgnore
    var lastModifiedDate = Instant.now()

    companion object {

        private const val serialVersionUID = 1L
    }
}
