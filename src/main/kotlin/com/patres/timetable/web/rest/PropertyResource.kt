package com.patres.timetable.web.rest

import com.codahale.metrics.annotation.Timed
import com.patres.timetable.service.PropertyService
import com.patres.timetable.service.dto.PropertyDTO
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
 * REST controller for managing Property.
 */
@RestController
@RequestMapping("/api")
open class PropertyResource(private val propertyService: PropertyService) {


    companion object {
        private val log = LoggerFactory.getLogger(PropertyResource::class.java)
        private val ENTITY_NAME = "property"
    }


    /**
     * POST  /property : Create a new property.
     *
     * @param propertyDTO the propertyDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new propertyDTO, or with status 400 (Bad Request) if the property has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PreAuthorize("@propertyService.hasPrivilegeToAddEntity(#propertyDTO)")
    @PostMapping("/properties")
    @Timed
    @Throws(URISyntaxException::class)
    open fun createProperty(@Valid @RequestBody propertyDTO: PropertyDTO): ResponseEntity<PropertyDTO> {
        log.debug("REST request to save Property : {}", propertyDTO)
        if (propertyDTO.id != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new property cannot already have an ID")).body(null)
        }
        val result = propertyService.save(propertyDTO)
        return ResponseEntity.created(URI("/api/property/" + result.id!!))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.id!!.toString()))
                .body(result)
    }

    /**
     * PUT  /property : Updates an existing property.
     *
     * @param propertyDTO the propertyDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated propertyDTO,
     * or with status 400 (Bad Request) if the propertyDTO is not valid,
     * or with status 500 (Internal Server Error) if the propertyDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PreAuthorize("@propertyService.hasPrivilegeToModifyEntity(#propertyDTO)")
    @PutMapping("/properties")
    @Timed
    @Throws(URISyntaxException::class)
    open fun updateProperty(@Valid @RequestBody propertyDTO: PropertyDTO): ResponseEntity<PropertyDTO> {
        log.debug("REST request to update Property : {}", propertyDTO)
        if (propertyDTO.id == null) {
            return createProperty(propertyDTO)
        }
        val result = propertyService.save(propertyDTO)
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, propertyDTO.id!!.toString()))
                .body(result)
    }

    /**
     * GET  /property : get all the property.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of property in body
     */
    @GetMapping("/properties")
    @Timed
    open fun getAllProperties(@ApiParam pageable: Pageable): ResponseEntity<List<PropertyDTO>> {
        log.debug("REST request to get a page of Property")
        val page = propertyService.findAll(pageable)
        val headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/property")
        return ResponseEntity(page.content, headers, HttpStatus.OK)
    }

    /**
     * GET  /property/login : get the property by current login.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of property in body
     */
    @GetMapping("/properties/login")
    @Timed
    open fun getPropertiesByCurrentLogin(@ApiParam pageable: Pageable): ResponseEntity<List<PropertyDTO>> {
        log.debug("REST request to get a page of Property")

        val page = propertyService.findByCurrentLogin(pageable)
        val headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/property")
        return ResponseEntity(page.content, headers, HttpStatus.OK)
    }

    /**
     * GET  /property/:id : get the "id" property.
     *
     * @param id the id of the propertiesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the propertiesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/properties/{id}")
    @Timed
    open fun getProperties(@PathVariable id: Long?): ResponseEntity<PropertyDTO> {
        log.debug("REST request to get Property : {}", id)
        val propertyDTO = propertyService.findOne(id)
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(propertyDTO))
    }

    /**
     * DELETE  /property/:id : delete the "id" property.
     *
     * @param id the id of the propertiesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @PreAuthorize("@propertyService.hasPrivilegeToDeleteEntity(#id)")
    @DeleteMapping("/properties/{id}")
    @Timed
    open fun deleteProperty(@PathVariable id: Long?): ResponseEntity<Void> {
        log.debug("REST request to delete Property : {}", id)
        propertyService.delete(id)
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id!!.toString())).build()
    }


}
