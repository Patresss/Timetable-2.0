package com.patres.timetable.repository

import com.patres.timetable.config.Constants
import com.patres.timetable.config.audit.AuditEventConverter
import com.patres.timetable.domain.PersistentAuditEvent
import org.springframework.boot.actuate.audit.AuditEvent
import org.springframework.boot.actuate.audit.AuditEventRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

/**
 * An implementation of Spring Boot's AuditEventRepository.
 */
@Repository
open class CustomAuditEventRepository(
    private val persistenceAuditEventRepository: PersistenceAuditEventRepository,
    private val auditEventConverter: AuditEventConverter
) : AuditEventRepository {

    companion object {
        private val AUTHORIZATION_FAILURE = "AUTHORIZATION_FAILURE"
    }

    override fun find(after: Date): List<AuditEvent> {
        val persistentAuditEvents = persistenceAuditEventRepository.findByAuditEventDateAfter(after.toInstant())
        return auditEventConverter.convertToAuditEvent(persistentAuditEvents)
    }

    override fun find(principal: String?, after: Date?): List<AuditEvent> {
        val persistentAuditEvents: Iterable<PersistentAuditEvent>
        if (principal == null && after == null) {
            persistentAuditEvents = persistenceAuditEventRepository.findAll()
        } else if (after == null) {
            persistentAuditEvents = persistenceAuditEventRepository.findByPrincipal(principal!!)
        } else {
            persistentAuditEvents = persistenceAuditEventRepository.findByPrincipalAndAuditEventDateAfter(principal!!, after.toInstant())
        }
        return auditEventConverter.convertToAuditEvent(persistentAuditEvents)
    }

    override fun find(principal: String, after: Date, type: String): List<AuditEvent> {
        val persistentAuditEvents = persistenceAuditEventRepository.findByPrincipalAndAuditEventDateAfterAndAuditEventType(principal, after.toInstant(), type)
        return auditEventConverter.convertToAuditEvent(persistentAuditEvents)
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun add(event: AuditEvent) {
        if (AUTHORIZATION_FAILURE != event.type && Constants.ANONYMOUS_USER != event.principal) {

            val persistentAuditEvent = PersistentAuditEvent()
            persistentAuditEvent.principal = event.principal
            persistentAuditEvent.auditEventType = event.type
            persistentAuditEvent.auditEventDate = event.timestamp.toInstant()
            persistentAuditEvent.data = auditEventConverter.convertDataToStrings(event.data)
            persistenceAuditEventRepository.save(persistentAuditEvent)
        }
    }


}
