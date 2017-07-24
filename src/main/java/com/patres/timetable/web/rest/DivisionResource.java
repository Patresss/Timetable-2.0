package com.patres.timetable.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.patres.timetable.service.DivisionService;
import com.patres.timetable.web.rest.util.HeaderUtil;
import com.patres.timetable.web.rest.util.PaginationUtil;
import com.patres.timetable.service.dto.DivisionDTO;
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
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Division.
 */
@RestController
@RequestMapping("/api")
public class DivisionResource {

    private final Logger log = LoggerFactory.getLogger(DivisionResource.class);

    private static final String ENTITY_NAME = "division";

    private final DivisionService divisionService;

    public DivisionResource(DivisionService divisionService) {
        this.divisionService = divisionService;
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
    public ResponseEntity<DivisionDTO> createDivision(@Valid @RequestBody DivisionDTO divisionDTO) throws URISyntaxException {
        log.debug("REST request to save Division : {}", divisionDTO);
        if (divisionDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new division cannot already have an ID")).body(null);
        }
        DivisionDTO result = divisionService.save(divisionDTO);
        return ResponseEntity.created(new URI("/api/divisions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
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
    public ResponseEntity<DivisionDTO> updateDivision(@Valid @RequestBody DivisionDTO divisionDTO) throws URISyntaxException {
        log.debug("REST request to update Division : {}", divisionDTO);
        if (divisionDTO.getId() == null) {
            return createDivision(divisionDTO);
        }
        DivisionDTO result = divisionService.save(divisionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, divisionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /divisions : get all the divisions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of divisions in body
     */
    @GetMapping("/divisions")
    @Timed
    public ResponseEntity<List<DivisionDTO>> getAllDivisions(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Divisions");
        Page<DivisionDTO> page = divisionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/divisions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /divisions/:id : get the "id" division.
     *
     * @param id the id of the divisionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the divisionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/divisions/{id}")
    @Timed
    public ResponseEntity<DivisionDTO> getDivision(@PathVariable Long id) {
        log.debug("REST request to get Division : {}", id);
        DivisionDTO divisionDTO = divisionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(divisionDTO));
    }

    /**
     * DELETE  /divisions/:id : delete the "id" division.
     *
     * @param id the id of the divisionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/divisions/{id}")
    @Timed
    public ResponseEntity<Void> deleteDivision(@PathVariable Long id) {
        log.debug("REST request to delete Division : {}", id);
        divisionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/divisions?query=:query : search for the division corresponding
     * to the query.
     *
     * @param query the query of the division search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/divisions")
    @Timed
    public ResponseEntity<List<DivisionDTO>> searchDivisions(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Divisions for query {}", query);
        Page<DivisionDTO> page = divisionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/divisions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
