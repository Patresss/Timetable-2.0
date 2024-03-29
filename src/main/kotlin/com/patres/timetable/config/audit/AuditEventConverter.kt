package com.patres.timetable.config.audit

import com.patres.timetable.domain.PersistentAuditEvent

import org.springframework.boot.actuate.audit.AuditEvent
import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.stereotype.Component

import java.util.*

@Component
class AuditEventConverter {

    /**
     * Convert a list of PersistentAuditEvent to a list of AuditEvent
     *
     * @param persistentAuditEvents the list to convertToServer
     * @return the converted list.
     */
    fun convertToAuditEvent(persistentAuditEvents: Iterable<PersistentAuditEvent>?): List<AuditEvent> {
        if (persistentAuditEvents == null) {
            return emptyList()
        }
        val auditEvents = persistentAuditEvents.map { convertToAuditEvent(it) }
        return auditEvents.filterNotNull()
    }

    /**
     * Convert a PersistentAuditEvent to an AuditEvent
     *
     * @param persistentAuditEvent the event to convertToServer
     * @return the converted list.
     */
    fun convertToAuditEvent(persistentAuditEvent: PersistentAuditEvent?): AuditEvent? {
        return if (persistentAuditEvent == null) {
            null
        } else AuditEvent(Date.from(persistentAuditEvent.auditEventDate), persistentAuditEvent.principal,
            persistentAuditEvent.auditEventType, convertDataToObjects(persistentAuditEvent.data))
    }

    /**
     * Internal conversion. This is needed to support the current SpringBoot actuator AuditEventRepository interface
     *
     * @param data the data to convertToServer
     * @return a map of String, Object
     */
    private fun convertDataToObjects(data: Map<String, String>?): Map<String, Any> {
        val results = HashMap<String, Any>()

        if (data != null) {
            for ((key, value) in data) {
                results.put(key, value)
            }
        }
        return results
    }

    /**
     * Internal conversion. This method will allow to save additional data.
     * By default, it will save the object as string
     *
     * @param data the data to convertToServer
     * @return a map of String, String
     */
    fun convertDataToStrings(data: Map<String, Any?>?): Map<String, String> {
        val results = HashMap<String, String>()
        if (data != null) {
            for ((key, value) in data) {
                // Extract the data that will be saved.
                when (value) {
                    is WebAuthenticationDetails -> {
                        results.put("remoteAddress", value.remoteAddress)
                        results.put("sessionId", value.sessionId)
                    }
                    value == null -> results.put(key, "null")
                    else -> results.put(key, value.toString())
                }
            }
        }

        return results
    }
}
