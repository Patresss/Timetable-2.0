package com.patres.timetable.web.rest

import com.codahale.metrics.annotation.Timed
import com.patres.timetable.service.DivisionService
import com.patres.timetable.service.dto.DivisionDTO
import com.patres.timetable.web.rest.util.HeaderUtil
import com.patres.timetable.web.rest.util.PaginationUtil
import io.github.jhipster.web.util.ResponseUtil
import io.swagger.annotations.ApiParam
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import javax.validation.Valid
import java.net.URI
import java.net.URISyntaxException
import java.util.Optional

/**
 * REST controller for managing Division.
 */
@RestController
@RequestMapping("/api")
open class DivisionResource(private val divisionService: DivisionService) {

    companion object {
        private val ENTITY_NAME = "division"
        private val log = LoggerFactory.getLogger(DivisionResource::class.java)
    }


    /**
     * POST  /divisions : Create a new division.
     *
     * @param divisionDTO the divisionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new divisionDTO, or with status 400 (Bad Request) if the division has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/divisions")
    @Timed
    @Throws(URISyntaxException::class)
    open fun createDivision(@Valid @RequestBody divisionDTO: DivisionDTO): ResponseEntity<DivisionDTO> {
        log.debug("REST request to save Division : {}", divisionDTO)
        if (divisionDTO.id != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new division cannot already have an ID")).body(null)
        }
        val result = divisionService.save(divisionDTO)
        return ResponseEntity.created(URI("/api/divisions/" + result.id!!))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.id!!.toString()))
                .body(result)
    }

    /**
     * PUT  /divisions : Updates an existing division.
     *
     * @param divisionDTO the divisionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated divisionDTO,
     * or with status 400 (Bad Request) if the divisionDTO is not valid,
     * or with status 500 (Internal Server Error) if the divisionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/divisions")
    @Timed
    @Throws(URISyntaxException::class)
    open fun updateDivision(@Valid @RequestBody divisionDTO: DivisionDTO): ResponseEntity<DivisionDTO> {
        log.debug("REST request to update Division : {}", divisionDTO)
        if (divisionDTO.id == null) {
            return createDivision(divisionDTO)
        }
        val result = divisionService.save(divisionDTO)
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, divisionDTO.id!!.toString()))
                .body(result)
    }

    /**
     * GET  /divisions : get all the divisions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of divisions in body
     */
    @GetMapping("/divisions")
    @Timed
    open fun getAllDivisions(@ApiParam pageable: Pageable): ResponseEntity<List<DivisionDTO>> {
        log.debug("REST request to get a page of Divisions")
        val page = divisionService.findAll(pageable)
        val headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/divisions")
        return ResponseEntity(page.content, headers, HttpStatus.OK)
    }

    /**
     * GET  /divisions/:id : get the "id" division.
     *
     * @param id the id of the divisionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the divisionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/divisions/{id}")
    @Timed
    open fun getDivision(@PathVariable id: Long?): ResponseEntity<DivisionDTO> {
        log.debug("REST request to get Division : {}", id)
        val divisionDTO = divisionService.findOne(id)
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(divisionDTO))
    }

    /**
     * DELETE  /divisions/:id : delete the "id" division.
     *
     * @param id the id of the divisionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/divisions/{id}")
    @Timed
    open fun deleteDivision(@PathVariable id: Long?): ResponseEntity<Void> {
        log.debug("REST request to delete Division : {}", id)
        divisionService.delete(id)
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id!!.toString())).build()
    }

}
