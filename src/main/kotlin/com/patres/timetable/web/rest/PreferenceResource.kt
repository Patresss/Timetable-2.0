package com.patres.timetable.web.rest

import com.codahale.metrics.annotation.Timed
import com.patres.timetable.preference.Preference
import com.patres.timetable.preference.PreferenceManager
import com.patres.timetable.service.LessonService
import com.patres.timetable.service.dto.LessonDTO
import com.patres.timetable.service.dto.PreferenceDependencyDTO
import com.patres.timetable.service.mapper.PreferenceDependencyMapper
import com.patres.timetable.web.rest.util.HeaderUtil
import com.patres.timetable.web.rest.util.PaginationUtil
import io.github.jhipster.web.util.ResponseUtil
import io.swagger.annotations.ApiParam
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.net.URISyntaxException
import java.util.*
import javax.validation.Valid

/**
 * REST controller for managing Preference.
 */
@RestController
@RequestMapping("/api")
open class PreferenceResource(val preferenceDependencyMapper: PreferenceDependencyMapper) {

    companion object {
        private val ENTITY_NAME = "preference"
        val log: Logger = LoggerFactory.getLogger(PreferenceResource::class.java)
    }

    /**
     * GET  /preferences : get the preferences
     *
     * @param preferenceDependency the preference dependency
     * @return the ResponseEntity with status 200 (OK) and the list of preferences in body
     */
    @GetMapping("/preferences")
    @Timed
    open fun getPreferences(@ModelAttribute preferenceDependencyDTO: PreferenceDependencyDTO): ResponseEntity<Preference> {
        log.debug("REST request to get preference by preferenceDependencyDTO: $preferenceDependencyDTO")
        val preferenceDependency = preferenceDependencyMapper.toEntity(preferenceDependencyDTO)
        val preference = PreferenceManager(preferenceDependency).calculatePreference()
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(preference))
    }

}
