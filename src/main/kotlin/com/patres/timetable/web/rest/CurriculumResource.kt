package com.patres.timetable.web.rest

import com.codahale.metrics.annotation.Timed
import com.patres.timetable.service.CurriculumService
import com.patres.timetable.service.dto.CurriculumDTO
import com.patres.timetable.web.rest.util.HeaderUtil
import com.patres.timetable.web.rest.util.PaginationUtil
import io.github.jhipster.web.util.ResponseUtil
import io.swagger.annotations.ApiParam
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

import javax.validation.Valid
import java.net.URI
import java.net.URISyntaxException
import java.util.Optional

/**
 * REST controller for managing Curriculum.
 */
@RestController
@RequestMapping("/api")
open class CurriculumResource(private val curriculumService: CurriculumService) {

    private val log = LoggerFactory.getLogger(CurriculumResource::class.java)

    /**
     * POST  /curriculums : Create a new curriculum.
     *
     * @param curriculumDTO the curriculumDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new curriculumDTO, or with status 400 (Bad Request) if the curriculum has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PreAuthorize("@curriculumService.hasPrivilegeToAddEntity(#curriculumDTO)")
    @PostMapping("/curriculums")
    @Timed
    @Throws(URISyntaxException::class)
    open fun createCurriculum(@Valid @RequestBody curriculumDTO: CurriculumDTO): ResponseEntity<CurriculumDTO> {
        log.debug("REST request to save Curriculum : {}", curriculumDTO)
        if (curriculumDTO.id != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new curriculum cannot already have an ID")).body(null)
        }
        val result = curriculumService.save(curriculumDTO)
        return ResponseEntity.created(URI("/api/curriculums-lists/" + result.id!!))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.id!!.toString()))
                .body(result)
    }

    /**
     * PUT  /curriculums : Updates an existing curriculum.
     *
     * @param curriculumDTO the curriculumDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated curriculumDTO,
     * or with status 400 (Bad Request) if the curriculumDTO is not valid,
     * or with status 500 (Internal Server Error) if the curriculumDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PreAuthorize("@curriculumService.hasPrivilegeToModifyEntity(#curriculumDTO)")
    @PutMapping("/curriculums")
    @Timed
    @Throws(URISyntaxException::class)
    open fun updateCurriculum(@Valid @RequestBody curriculumDTO: CurriculumDTO): ResponseEntity<CurriculumDTO> {
        log.debug("REST request to update Curriculum : {}", curriculumDTO)
        if (curriculumDTO.id == null) {
            return createCurriculum(curriculumDTO)
        }
        val result = curriculumService.save(curriculumDTO)
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, curriculumDTO.id!!.toString()))
                .body(result)
    }

    /**
     * GET  /curriculums : get all the curriculums.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of curriculums in body
     */
    @GetMapping("/curriculums")
    @Timed
    open fun getAllCurriculums(@ApiParam pageable: Pageable): ResponseEntity<List<CurriculumDTO>> {
        log.debug("REST request to get a page of Curriculums")
        val page = curriculumService.findAll(pageable)
        val headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/curriculums-lists")
        return ResponseEntity(page.content, headers, HttpStatus.OK)
    }


    /**
     * GET  /curriculums/division-owners : get the curriculums by Division owners id.
     *
     * @param divisionsId divisions id
     * @param pageable    the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of curriculums in body
     */
    @GetMapping("/curriculums/division-owners")
    @Timed
    open fun getCurriculumsByDivisionsId(@ApiParam pageable: Pageable, @RequestParam divisionsId: List<Long>): ResponseEntity<List<CurriculumDTO>> {
        log.debug("REST request to get a page of Curriculums by Division owners id")

        val page = curriculumService.findByDivisionOwnerId(pageable, divisionsId)
        val headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/curriculums-lists")
        return ResponseEntity(page.content, headers, HttpStatus.OK)
    }

    /**
     * GET  /curriculums/login : get the curriculums by current login.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of curriculums in body
     */
    @GetMapping("/curriculums/login")
    @Timed
    open fun getCurriculumsByCurrentLogin(@ApiParam pageable: Pageable): ResponseEntity<List<CurriculumDTO>> {
        log.debug("REST request to get a page of Curriculums by current login")

        val page = curriculumService.findByCurrentLogin(pageable)
        val headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/curriculums-lists")
        return ResponseEntity(page.content, headers, HttpStatus.OK)
    }

    /**
     * GET  /curriculums/:id : get the "id" curriculum.
     *
     * @param id the id of the curriculumDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the curriculumDTO, or with status 404 (Not Found)
     */
    @GetMapping("/curriculums/{id}")
    @Timed
    open fun getCurriculum(@PathVariable id: Long?): ResponseEntity<CurriculumDTO> {
        log.debug("REST request to get Curriculum : {}", id)
        val curriculumDTO = curriculumService.findOne(id)
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(curriculumDTO))
    }

    /**
     * DELETE  /curriculums/:id : delete the "id" curriculum.
     *
     * @param id the id of the curriculumDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @PreAuthorize("@curriculumService.hasPrivilegeToDeleteEntity(#id)")
    @DeleteMapping("/curriculums/{id}")
    @Timed
    open fun deleteCurriculum(@PathVariable id: Long?): ResponseEntity<Void> {
        log.debug("REST request to delete Curriculum : {}", id)
        curriculumService.delete(id)
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id!!.toString())).build()
    }

    companion object {

        private val ENTITY_NAME = "curriculum"
    }
}
