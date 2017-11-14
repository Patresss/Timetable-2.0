package com.patres.timetable.web.rest

import com.codahale.metrics.annotation.Timed
import com.patres.timetable.service.LessonService
import com.patres.timetable.service.dto.LessonDTO
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
 * REST controller for managing Lesson.
 */
@RestController
@RequestMapping("/api")
open class LessonResource(private val lessonService: LessonService) {

    companion object {
        private val ENTITY_NAME = "lesson"
        val log: Logger = LoggerFactory.getLogger(LessonResource::class.java)
    }

    /**
     * POST  /lessons : Create a new lesson.
     *
     * @param lessonDTO the lessonDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lessonDTO, or with status 400 (Bad Request) if the lesson has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lessons")
    @PreAuthorize("@lessonService.hasPriviligeToAddEntity(#lessonDTO)")
    @Timed
    @Throws(URISyntaxException::class)
    open fun createLesson(@Valid @RequestBody lessonDTO: LessonDTO): ResponseEntity<LessonDTO> {
        log.debug("REST request to save Lesson : {}", lessonDTO)
        if (lessonDTO.id != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new lesson cannot already have an ID")).body(null)
        }

        val result = lessonService.save(lessonDTO)
        return ResponseEntity.created(URI("/api/lessons/" + result.id!!))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.id!!.toString()))
                .body(result)
    }

    /**
     * PUT  /lessons : Updates an existing lesson.
     *
     * @param lessonDTO the lessonDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lessonDTO,
     * or with status 400 (Bad Request) if the lessonDTO is not valid,
     * or with status 500 (Internal Server Error) if the lessonDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lessons")
    @Timed
    @Throws(URISyntaxException::class)
    open fun updateLesson(@Valid @RequestBody lessonDTO: LessonDTO): ResponseEntity<LessonDTO> {
        log.debug("REST request to update Lesson : {}", lessonDTO)
        if (lessonDTO.id == null) {
            return createLesson(lessonDTO)
        }
        val result = lessonService.save(lessonDTO)
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lessonDTO.id!!.toString()))
                .body(result)
    }

    /**
     * GET  /lessons : get all the lessons.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of lessons in body
     */
    @GetMapping("/lessons")
    @Timed
    open fun getAllLessons(@ApiParam pageable: Pageable): ResponseEntity<List<LessonDTO>> {
        log.debug("REST request to get a page of Lessons")
        val page = lessonService.findAll(pageable)
        val headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/lessons")
        return ResponseEntity(page.content, headers, HttpStatus.OK)
    }


    /**
     * GET  /lessons/divisions : get the lessons by Division owners id.
     *
     * @param divisionsId divisions id
     * @param pageable    the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of lessons in body
     */
    @GetMapping("/lessons/divisions")
    @Timed
    open fun getLessonsByDivisionsId(@ApiParam pageable: Pageable, @PathVariable divisionsId: List<Long>): ResponseEntity<List<LessonDTO>> {
        log.debug("REST request to get a page of Lessons by Division owners id")

        val page = lessonService.findByDivisionOwnerId(pageable, divisionsId)
        val headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/lessons")
        return ResponseEntity(page.content, headers, HttpStatus.OK)
    }

    /**
     * GET  /lessons/login : get the lessons by current login.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of lessons in body
     */
    @GetMapping("/lessons/login")
    @Timed
    open fun getLessonsByCurrentLogin(@ApiParam pageable: Pageable): ResponseEntity<List<LessonDTO>> {
        log.debug("REST request to get a page of Lessons")

        val page = lessonService.findByCurrentLogin(pageable)
        val headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/lessons")
        return ResponseEntity(page.content, headers, HttpStatus.OK)
    }

    /**
     * GET  /lessons/:id : get the "id" lesson.
     *
     * @param id the id of the lessonDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lessonDTO, or with status 404 (Not Found)
     */
    @GetMapping("/lessons/{id}")
    @Timed
    open fun getLesson(@PathVariable id: Long?): ResponseEntity<LessonDTO> {
        log.debug("REST request to get Lesson : {}", id)
        val lessonDTO = lessonService.findOne(id)
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(lessonDTO))
    }

    /**
     * DELETE  /lessons/:id : delete the "id" lesson.
     *
     * @param id the id of the lessonDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @PreAuthorize("@lessonService.hasPriviligeToDeleteEntity(#id)")
    @DeleteMapping("/lessons/{id}")
    @Timed
    open fun deleteLesson(@PathVariable id: Long?): ResponseEntity<Void> {
        log.debug("REST request to delete Lesson : {}", id)
        lessonService.delete(id)
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id!!.toString())).build()
    }

}
