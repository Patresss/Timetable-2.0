package com.patres.timetable.web.rest.util

import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders

/**
 * Utility class for HTTP headers creation.
 */
object HeaderUtil {

    private val log = LoggerFactory.getLogger(HeaderUtil::class.java)
    private val APPLICATION_NAME = "timetableApp"

    fun createAlert(message: String, param: String?): HttpHeaders {
        val headers = HttpHeaders()
        headers.add("X-timetableApp-alert", message)
        headers.add("X-timetableApp-params", param)
        return headers
    }

    fun createEntityCreationAlert(entityName: String, param: String): HttpHeaders {
        return createAlert("$APPLICATION_NAME.$entityName.created", param)
    }

    fun createEntityUpdateAlert(entityName: String, param: String): HttpHeaders {
        return createAlert("$APPLICATION_NAME.$entityName.updated", param)
    }

    fun createEntityDeletionAlert(entityName: String, param: String): HttpHeaders {
        return createAlert("$APPLICATION_NAME.$entityName.deleted", param)
    }

    fun createFailureAlert(entityName: String, errorKey: String, defaultMessage: String): HttpHeaders {
        log.error("Entity processing failed, {}", defaultMessage)
        val headers = HttpHeaders()
        headers.add("X-timetableApp-error", "error." + errorKey)
        headers.add("X-timetableApp-params", entityName)
        return headers
    }
}
