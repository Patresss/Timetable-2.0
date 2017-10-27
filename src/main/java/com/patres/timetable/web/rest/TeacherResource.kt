package com.patres.timetable.web.rest

import com.codahale.metrics.annotation.Timed
import com.patres.timetable.service.TeacherService
import com.patres.timetable.service.dto.TeacherDTO
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
 * REST controller for managing Teacher.
 */
@RestController
@RequestMapping("/api")
open class TeacherResource(private val teacherService: TeacherService) {

    companion object {
        private val log = LoggerFactory.getLogger(TeacherResource::class.java)
        private val ENTITY_NAME = "teacher"
    }

    /**
     * POST  /teachers : Create a new teacher.
     *
     * @param teacherDTO the teacherDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new teacherDTO, or with status 400 (Bad Request) if the teacher has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PreAuthorize("@teacherService.hasPriviligeToAddEntity(#teacherDTO)")
    @PostMapping("/teachers")
    @Timed
    @Throws(URISyntaxException::class)
    open fun createTeacher(@Valid @RequestBody teacherDTO: TeacherDTO): ResponseEntity<TeacherDTO> {
        log.debug("REST request to save Teacher : {}", teacherDTO)
        if (teacherDTO.id != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new teacher cannot already have an ID")).body(null)
        }
        val result = teacherService.save(teacherDTO)
        return ResponseEntity.created(URI("/api/teachers/" + result.id!!))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.id!!.toString()))
                .body(result)
    }

    /**
     * PUT  /teachers : Updates an existing teacher.
     *
     * @param teacherDTO the teacherDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated teacherDTO,
     * or with status 400 (Bad Request) if the teacherDTO is not valid,
     * or with status 500 (Internal Server Error) if the teacherDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PreAuthorize("@teacherService.hasPriviligeToModifyEntity(#teacherDTO)")
    @PutMapping("/teachers")
    @Timed
    @Throws(URISyntaxException::class)
    open fun updateTeacher(@Valid @RequestBody teacherDTO: TeacherDTO): ResponseEntity<TeacherDTO> {
        log.debug("REST request to update Teacher : {}", teacherDTO)
        if (teacherDTO.id == null) {
            return createTeacher(teacherDTO)
        }
        val result = teacherService.save(teacherDTO)
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, teacherDTO.id!!.toString()))
                .body(result)
    }

    /**
     * GET  /teachers : get all the teachers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of teachers in body
     */
    @GetMapping("/teachers")
    @Timed
    open fun getAllTeachers(@ApiParam pageable: Pageable): ResponseEntity<List<TeacherDTO>> {
        log.debug("REST request to get a page of Teachers")

        val page = teacherService.findAll(pageable)
        val headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/teachers")
        return ResponseEntity(page.content, headers, HttpStatus.OK)
    }

    /**
     * GET  /teachers/divisions : get the teachers by Division owners id.
     *
     * @param divisionsId divisions id
     * @param pageable    the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of teachers in body
     */
    @GetMapping("/teachers/divisions")
    @Timed
    open fun getTeachersByDivisionsId(@ApiParam pageable: Pageable, @PathVariable divisionsId: List<Long>): ResponseEntity<List<TeacherDTO>> {
        log.debug("REST request to get a page of Teachers by Division owners id")

        val page = teacherService.findByDivisionOwnerId(pageable, divisionsId)
        val headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/teachers")
        return ResponseEntity(page.content, headers, HttpStatus.OK)
    }

    /**
     * GET  /teachers/login : get the teachers by current login.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of teachers in body
     */
    @GetMapping("/teachers/login")
    @Timed
    open fun getTeachersByCurrentLogin(@ApiParam pageable: Pageable): ResponseEntity<List<TeacherDTO>> {
        log.debug("REST request to get a page of Teachers")

        val page = teacherService.findByCurrentLogin(pageable)
        val headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/teachers")
        return ResponseEntity(page.content, headers, HttpStatus.OK)
    }

    /**
     * GET  /teachers/:id : get the "id" teacher.
     *
     * @param id the id of the teacherDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the teacherDTO, or with status 404 (Not Found)
     */
    @GetMapping("/teachers/{id}")
    @Timed
    open fun getTeacher(@PathVariable id: Long?): ResponseEntity<TeacherDTO> {
        log.debug("REST request to get Teacher : {}", id)
        val teacherDTO = teacherService.findOne(id)
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(teacherDTO))
    }

    /**
     * DELETE  /teachers/:id : delete the "id" teacher.
     *
     * @param id the id of the teacherDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @PreAuthorize("@teacherService.hasPriviligeToDeleteEntity(#id)")
    @DeleteMapping("/teachers/{id}")
    @Timed
    open fun deleteTeacher(@PathVariable id: Long?): ResponseEntity<Void> {
        log.debug("REST request to delete Teacher : {}", id)
        teacherService.delete(id)
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id!!.toString())).build()
    }


}
