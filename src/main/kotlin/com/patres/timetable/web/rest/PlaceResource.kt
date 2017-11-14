package com.patres.timetable.web.rest

import com.codahale.metrics.annotation.Timed
import com.patres.timetable.service.PlaceService
import com.patres.timetable.service.dto.PlaceDTO
import com.patres.timetable.web.rest.util.HeaderUtil
import com.patres.timetable.web.rest.util.PaginationUtil
import io.github.jhipster.web.util.ResponseUtil
import io.swagger.annotations.ApiParam
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
 * REST controller for managing Place.
 */
@RestController
@RequestMapping("/api")
open class PlaceResource(private val placeService: PlaceService) {

    companion object {
        private val log = LoggerFactory.getLogger(PlaceResource::class.java)
        private val ENTITY_NAME = "place"
    }


    /**
     * POST  /places : Create a new place.
     *
     * @param placeDTO the placeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new placeDTO, or with status 400 (Bad Request) if the place has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PreAuthorize("@placeService.hasPriviligeToAddEntity(#placeDTO)")
    @PostMapping("/places")
    @Timed
    @Throws(URISyntaxException::class)
    open fun createPlace(@Valid @RequestBody placeDTO: PlaceDTO): ResponseEntity<PlaceDTO> {
        log.debug("REST request to save Place : {}", placeDTO)
        if (placeDTO.id != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new place cannot already have an ID")).body(null)
        }
        val result = placeService.save(placeDTO)
        return ResponseEntity.created(URI("/api/places/" + result.id!!))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.id!!.toString()))
            .body(result)
    }

    /**
     * PUT  /places : Updates an existing place.
     *
     * @param placeDTO the placeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated placeDTO,
     * or with status 400 (Bad Request) if the placeDTO is not valid,
     * or with status 500 (Internal Server Error) if the placeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PreAuthorize("@placeService.hasPriviligeToModifyEntity(#placeDTO)")
    @PutMapping("/places")
    @Timed
    @Throws(URISyntaxException::class)
    open fun updatePlace(@Valid @RequestBody placeDTO: PlaceDTO): ResponseEntity<PlaceDTO> {
        log.debug("REST request to update Place : {}", placeDTO)
        if (placeDTO.id == null) {
            return createPlace(placeDTO)
        }
        val result = placeService.save(placeDTO)
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, placeDTO.id!!.toString()))
            .body(result)
    }

    /**
     * GET  /places : get all the places.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of places in body
     */
    @GetMapping("/places")
    @Timed
    open fun getAllPlaces(@ApiParam pageable: Pageable): ResponseEntity<List<PlaceDTO>> {
        log.debug("REST request to get a page of Places")
        val page = placeService.findAll(pageable)
        val headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/places")
        return ResponseEntity(page.content, headers, HttpStatus.OK)
    }

    /**
     * GET  /places/divisions : get the places by Division owners id.
     *
     * @param divisionsId divisions id
     * @param pageable    the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of places in body
     */
    @GetMapping("/places/divisions")
    @Timed
    open fun getPlacesByDivisionsId(@ApiParam pageable: Pageable, @PathVariable divisionsId: List<Long>): ResponseEntity<List<PlaceDTO>> {
        log.debug("REST request to get a page of Places by Division owners id")
        val page = placeService.findByDivisionOwnerId(pageable, divisionsId)
        val headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/places")
        return ResponseEntity(page.content, headers, HttpStatus.OK)
    }

    /**
     * GET  /places/login : get the places by current login.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of places in body
     */
    @GetMapping("/places/login")
    @Timed
    open fun getPlacesByCurrentLogin(@ApiParam pageable: Pageable): ResponseEntity<List<PlaceDTO>> {
        log.debug("REST request to get a page of Places")
        val page = placeService.findByCurrentLogin(pageable)
        val headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/places")
        return ResponseEntity(page.content, headers, HttpStatus.OK)
    }

    /**
     * GET  /places/:id : get the "id" place.
     *
     * @param id the id of the placeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the placeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/places/{id}")
    @Timed
    open fun getPlace(@PathVariable id: Long?): ResponseEntity<PlaceDTO> {
        log.debug("REST request to get Place : {}", id)
        val placeDTO = placeService.findOne(id)
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(placeDTO))
    }

    /**
     * DELETE  /places/:id : delete the "id" place.
     *
     * @param id the id of the placeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @PreAuthorize("@placeService.hasPriviligeToDeleteEntity(#id)")
    @DeleteMapping("/places/{id}")
    @Timed
    open fun deletePlace(@PathVariable id: Long?): ResponseEntity<Void> {
        log.debug("REST request to delete Place : {}", id)
        placeService.delete(id)
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id!!.toString())).build()
    }

}
