package com.patres.timetable.web.rest

import com.codahale.metrics.annotation.Timed
import com.patres.timetable.service.CurriculumListService
import com.patres.timetable.service.dto.CurriculumListDTO
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
 * REST controller for managing CurriculumList.
 */
@RestController
@RequestMapping("/api")
open class CurriculumListResource(private val curriculumListService: CurriculumListService) {

    private val log = LoggerFactory.getLogger(CurriculumListResource::class.java)

    /**
     * POST  /curriculum-listes : Create a new curriculumList.
     *
     * @param curriculumListDTO the curriculumListDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new curriculumListDTO, or with status 400 (Bad Request) if the curriculumList has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PreAuthorize("@curriculumListService.hasPrivilegeToAddEntity(#curriculumListDTO)")
    @PostMapping("/curriculum-listes")
    @Timed
    @Throws(URISyntaxException::class)
    open fun createCurriculumList(@Valid @RequestBody curriculumListDTO: CurriculumListDTO): ResponseEntity<CurriculumListDTO> {
        log.debug("REST request to save CurriculumList : {}", curriculumListDTO)
        if (curriculumListDTO.id != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new curriculumList cannot already have an ID")).body(null)
        }
        val result = curriculumListService.save(curriculumListDTO)
        return ResponseEntity.created(URI("/api/curriculum-lists/" + result.id!!))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.id!!.toString()))
                .body(result)
    }

    /**
     * PUT  /curriculum-listes : Updates an existing curriculumList.
     *
     * @param curriculumListDTO the curriculumListDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated curriculumListDTO,
     * or with status 400 (Bad Request) if the curriculumListDTO is not valid,
     * or with status 500 (Internal Server Error) if the curriculumListDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PreAuthorize("@curriculumListService.hasPrivilegeToModifyEntity(#curriculumListDTO)")
    @PutMapping("/curriculum-listes")
    @Timed
    @Throws(URISyntaxException::class)
    open fun updateCurriculumList(@Valid @RequestBody curriculumListDTO: CurriculumListDTO): ResponseEntity<CurriculumListDTO> {
        log.debug("REST request to update CurriculumList : {}", curriculumListDTO)
        if (curriculumListDTO.id == null) {
            return createCurriculumList(curriculumListDTO)
        }
        val result = curriculumListService.save(curriculumListDTO)
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, curriculumListDTO.id!!.toString()))
                .body(result)
    }

    /**
     * GET  /curriculum-listes : get all the curriculumLists.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of curriculumLists in body
     */
    @GetMapping("/curriculum-listes")
    @Timed
    open fun getAllCurriculumLists(@ApiParam pageable: Pageable): ResponseEntity<List<CurriculumListDTO>> {
        log.debug("REST request to get a page of CurriculumLists")
        val page = curriculumListService.findAll(pageable)
        val headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/curriculum-lists")
        return ResponseEntity(page.content, headers, HttpStatus.OK)
    }


    /**
     * GET  /curriculum-listes/divisions : get the curriculumLists by Division owners id.
     *
     * @param divisionsId divisions id
     * @param pageable    the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of curriculumLists in body
     */
    @GetMapping("/curriculum-listes/divisions")
    @Timed
    open fun getCurriculumListsByDivisionsId(@ApiParam pageable: Pageable, @PathVariable divisionsId: List<Long>): ResponseEntity<List<CurriculumListDTO>> {
        log.debug("REST request to get a page of CurriculumLists by Division owners id")

        val page = curriculumListService.findByDivisionOwnerId(pageable, divisionsId)
        val headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/curriculum-lists")
        return ResponseEntity(page.content, headers, HttpStatus.OK)
    }

    /**
     * GET  /curriculum-listes/login : get the curriculumLists by current login.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of curriculumLists in body
     */
    @GetMapping("/curriculum-listes/login")
    @Timed
    open fun getCurriculumListsByCurrentLogin(@ApiParam pageable: Pageable): ResponseEntity<List<CurriculumListDTO>> {
        log.debug("REST request to get a page of CurriculumLists by current login")

        val page = curriculumListService.findByCurrentLogin(pageable)
        val headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/curriculum-lists")
        return ResponseEntity(page.content, headers, HttpStatus.OK)
    }

    /**
     * GET  /curriculum-listes/:id : get the "id" curriculumList.
     *
     * @param id the id of the curriculumListDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the curriculumListDTO, or with status 404 (Not Found)
     */
    @GetMapping("/curriculum-listes/{id}")
    @Timed
    open fun getCurriculumList(@PathVariable id: Long?): ResponseEntity<CurriculumListDTO> {
        log.debug("REST request to get CurriculumList : {}", id)
        val curriculumListDTO = curriculumListService.findOne(id)
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(curriculumListDTO))
    }

    /**
     * DELETE  /curriculum-listes/:id : delete the "id" curriculumList.
     *
     * @param id the id of the curriculumListDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @PreAuthorize("@curriculumListService.hasPrivilegeToDeleteEntity(#id)")
    @DeleteMapping("/curriculum-listes/{id}")
    @Timed
    open fun deleteCurriculumList(@PathVariable id: Long?): ResponseEntity<Void> {
        log.debug("REST request to delete CurriculumList : {}", id)
        curriculumListService.delete(id)
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id!!.toString())).build()
    }

    companion object {

        private val ENTITY_NAME = "curriculumList"
    }
}
