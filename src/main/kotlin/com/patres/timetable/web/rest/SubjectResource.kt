package com.patres.timetable.web.rest

import com.codahale.metrics.annotation.Timed
import com.patres.timetable.service.SubjectService
import com.patres.timetable.service.dto.SubjectDTO
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
 * REST controller for managing Subject.
 */
@RestController
@RequestMapping("/api")
open class SubjectResource(private val subjectService: SubjectService) {

    companion object {
        private val log = LoggerFactory.getLogger(SubjectResource::class.java)
        private val ENTITY_NAME = "subject"
    }

    /**
     * POST  /subjects : Create a new subject.
     *
     * @param subjectDTO the subjectDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new subjectDTO, or with status 400 (Bad Request) if the subject has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PreAuthorize("@subjectService.hasPrivilegeToAddEntity(#subjectDTO)")
    @PostMapping("/subjects")
    @Timed
    @Throws(URISyntaxException::class)
    open fun createSubject(@Valid @RequestBody subjectDTO: SubjectDTO): ResponseEntity<SubjectDTO> {
        log.debug("REST request to save Subject : {}", subjectDTO)
        if (subjectDTO.id != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new subject cannot already have an ID")).body(null)
        }
        val result = subjectService.save(subjectDTO)
        return ResponseEntity.created(URI("/api/subjects/" + result.id!!))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.id!!.toString()))
                .body(result)
    }

    /**
     * PUT  /subjects : Updates an existing subject.
     *
     * @param subjectDTO the subjectDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated subjectDTO,
     * or with status 400 (Bad Request) if the subjectDTO is not valid,
     * or with status 500 (Internal Server Error) if the subjectDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PreAuthorize("@subjectService.hasPrivilegeToModifyEntity(#subjectDTO)")
    @PutMapping("/subjects")
    @Timed
    @Throws(URISyntaxException::class)
    open fun updateSubject(@Valid @RequestBody subjectDTO: SubjectDTO): ResponseEntity<SubjectDTO> {
        log.debug("REST request to update Subject : {}", subjectDTO)
        if (subjectDTO.id == null) {
            return createSubject(subjectDTO)
        }
        val result = subjectService.save(subjectDTO)
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, subjectDTO.id!!.toString()))
                .body(result)
    }

    /**
     * GET  /subjects : get all the subjects.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of subjects in body
     */
    @GetMapping("/subjects")
    @Timed
    open fun getAllSubjects(@ApiParam pageable: Pageable): ResponseEntity<List<SubjectDTO>> {
        log.debug("REST request to get a page of Subjects")
        val page = subjectService.findAll(pageable)
        val headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/subjects")
        return ResponseEntity(page.content, headers, HttpStatus.OK)
    }

    /**
     * GET  /subjects/division-owners : get all the subjects by Division owners id.
     *
     * @param divisionsId divisions id
     * @param pageable    the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of subjects in body
     */
    @GetMapping("/subjects/division-owners")
    @Timed
    open fun getSubjectsByDivisionsId(@ApiParam pageable: Pageable, @RequestParam divisionsId: List<Long>): ResponseEntity<List<SubjectDTO>> {
        log.debug("REST request to get a page of Subjects by Division owners id")

        val page = subjectService.findByDivisionOwnerId(pageable, divisionsId)
        val headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/subjects")
        return ResponseEntity(page.content, headers, HttpStatus.OK)
    }

    /**
     * GET  /subjects/login : get the subjects by current login.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of subjects in body
     */
    @GetMapping("/subjects/login")
    @Timed
    open fun getSubjectsByCurrentLogin(@ApiParam pageable: Pageable): ResponseEntity<List<SubjectDTO>> {
        log.debug("REST request to get a page of Subjects")

        val page = subjectService.findByCurrentLogin(pageable)
        val headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/subjects")
        return ResponseEntity(page.content, headers, HttpStatus.OK)
    }

    /**
     * GET  /subjects/:id : get the "id" subject.
     *
     * @param id the id of the subjectDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the subjectDTO, or with status 404 (Not Found)
     */
    @GetMapping("/subjects/{id}")
    @Timed
    open fun getSubject(@PathVariable id: Long?): ResponseEntity<SubjectDTO> {
        log.debug("REST request to get Subject : {}", id)
        val subjectDTO = subjectService.findOne(id)
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(subjectDTO))
    }

    /**
     * DELETE  /subjects/:id : delete the "id" subject.
     *
     * @param id the id of the subjectDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @PreAuthorize("@subjectService.hasPrivilegeToDeleteEntity(#id)")
    @DeleteMapping("/subjects/{id}")
    @Timed
    open fun deleteSubject(@PathVariable id: Long?): ResponseEntity<Void> {
        log.debug("REST request to delete Subject : {}", id)
        subjectService.delete(id)
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id!!.toString())).build()
    }


}
