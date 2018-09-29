package com.patres.timetable.web.rest

import com.codahale.metrics.annotation.Timed
import com.patres.timetable.preference.PreferenceFactory
import com.patres.timetable.service.dto.PreferenceDependencyDTO
import com.patres.timetable.service.dto.preference.PreferenceContainerDTO
import com.patres.timetable.service.mapper.preference.PreferenceDependencyMapper
import io.github.jhipster.web.util.ResponseUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

/**
 * REST controller for managing Preference.
 */
@RestController
@RequestMapping("/api")
open class PreferenceResource(
    private val preferenceDependencyMapper: PreferenceDependencyMapper,
    private val preferenceManager: PreferenceFactory
) {

    companion object {
        private val ENTITY_NAME = "preferenceLessonAndDayOfWeekHierarchy"
        val log: Logger = LoggerFactory.getLogger(PreferenceResource::class.java)
    }

    /**
     * GET  /preferences : get the preferences
     *
     * @param preferenceDependencyDTO the preferenceLessonAndDayOfWeekHierarchy dependency
     * @return the ResponseEntity with status 200 (OK) and the list of preferences in body
     */
    @GetMapping("/preferences")
    @Timed
    @Transactional
    open fun getPreferences(@ModelAttribute preferenceDependencyDTO: PreferenceDependencyDTO): ResponseEntity<PreferenceContainerDTO> {
        log.debug("REST request to get preferenceLessonAndDayOfWeekHierarchy by preferenceDependencyDTO: $preferenceDependencyDTO")
        val preferenceDependency = preferenceDependencyMapper.toEntity(preferenceDependencyDTO)
        val preferenceContainer =  preferenceDependency.divisionOwnerId?.let {divisionOwnerId ->
            preferenceManager.createSinglePreference(divisionOwnerId, preferenceDependency)
        }
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(preferenceContainer))
    }

}
