package com.patres.timetable.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.patres.timetable.service.PropertiesService;
import com.patres.timetable.web.rest.util.HeaderUtil;
import com.patres.timetable.web.rest.util.PaginationUtil;
import com.patres.timetable.service.dto.PropertiesDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Properties.
 */
@RestController
@RequestMapping("/api")
public class PropertiesResource {

    private final Logger log = LoggerFactory.getLogger(PropertiesResource.class);

    private static final String ENTITY_NAME = "properties";

    private final PropertiesService propertiesService;

    public PropertiesResource(PropertiesService propertiesService) {
        this.propertiesService = propertiesService;
    }

    /**
     * POST  /properties : Create a new properties.
     *
     * @param propertiesDTO the propertiesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new propertiesDTO, or with status 400 (Bad Request) if the properties has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/properties")
    @Timed
    public ResponseEntity<PropertiesDTO> createProperties(@Valid @RequestBody PropertiesDTO propertiesDTO) throws URISyntaxException {
        log.debug("REST request to save Properties : {}", propertiesDTO);
        if (propertiesDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new properties cannot already have an ID")).body(null);
        }
        PropertiesDTO result = propertiesService.save(propertiesDTO);
        return ResponseEntity.created(new URI("/api/properties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /properties : Updates an existing properties.
     *
     * @param propertiesDTO the propertiesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated propertiesDTO,
     * or with status 400 (Bad Request) if the propertiesDTO is not valid,
     * or with status 500 (Internal Server Error) if the propertiesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/properties")
    @Timed
    public ResponseEntity<PropertiesDTO> updateProperties(@Valid @RequestBody PropertiesDTO propertiesDTO) throws URISyntaxException {
        log.debug("REST request to update Properties : {}", propertiesDTO);
        if (propertiesDTO.getId() == null) {
            return createProperties(propertiesDTO);
        }
        PropertiesDTO result = propertiesService.save(propertiesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, propertiesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /properties : get all the properties.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of properties in body
     */
    @GetMapping("/properties")
    @Timed
    public ResponseEntity<List<PropertiesDTO>> getAllProperties(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Properties");
        Page<PropertiesDTO> page = propertiesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/properties");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /properties/:id : get the "id" properties.
     *
     * @param id the id of the propertiesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the propertiesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/properties/{id}")
    @Timed
    public ResponseEntity<PropertiesDTO> getProperties(@PathVariable Long id) {
        log.debug("REST request to get Properties : {}", id);
        PropertiesDTO propertiesDTO = propertiesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(propertiesDTO));
    }

    /**
     * DELETE  /properties/:id : delete the "id" properties.
     *
     * @param id the id of the propertiesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/properties/{id}")
    @Timed
    public ResponseEntity<Void> deleteProperties(@PathVariable Long id) {
        log.debug("REST request to delete Properties : {}", id);
        propertiesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
