package com.patres.timetable.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.patres.timetable.service.TimetableService;
import com.patres.timetable.web.rest.util.HeaderUtil;
import com.patres.timetable.web.rest.util.PaginationUtil;
import com.patres.timetable.service.dto.TimetableDTO;
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
 * REST controller for managing Timetable.
 */
@RestController
@RequestMapping("/api")
public class TimetableResource {

    private final Logger log = LoggerFactory.getLogger(TimetableResource.class);

    private static final String ENTITY_NAME = "timetable";

    private final TimetableService timetableService;

    public TimetableResource(TimetableService timetableService) {
        this.timetableService = timetableService;
    }

    /**
     * POST  /timetables : Create a new timetable.
     *
     * @param timetableDTO the timetableDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new timetableDTO, or with status 400 (Bad Request) if the timetable has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/timetables")
    @Timed
    public ResponseEntity<TimetableDTO> createTimetable(@Valid @RequestBody TimetableDTO timetableDTO) throws URISyntaxException {
        log.debug("REST request to save Timetable : {}", timetableDTO);
        if (timetableDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new timetable cannot already have an ID")).body(null);
        }
        TimetableDTO result = timetableService.save(timetableDTO);
        return ResponseEntity.created(new URI("/api/timetables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /timetables : Updates an existing timetable.
     *
     * @param timetableDTO the timetableDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated timetableDTO,
     * or with status 400 (Bad Request) if the timetableDTO is not valid,
     * or with status 500 (Internal Server Error) if the timetableDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/timetables")
    @Timed
    public ResponseEntity<TimetableDTO> updateTimetable(@Valid @RequestBody TimetableDTO timetableDTO) throws URISyntaxException {
        log.debug("REST request to update Timetable : {}", timetableDTO);
        if (timetableDTO.getId() == null) {
            return createTimetable(timetableDTO);
        }
        TimetableDTO result = timetableService.save(timetableDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, timetableDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /timetables : get all the timetables.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of timetables in body
     */
    @GetMapping("/timetables")
    @Timed
    public ResponseEntity<List<TimetableDTO>> getAllTimetables(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Timetables");
        Page<TimetableDTO> page = timetableService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/timetables");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /timetables/:id : get the "id" timetable.
     *
     * @param id the id of the timetableDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the timetableDTO, or with status 404 (Not Found)
     */
    @GetMapping("/timetables/{id}")
    @Timed
    public ResponseEntity<TimetableDTO> getTimetable(@PathVariable Long id) {
        log.debug("REST request to get Timetable : {}", id);
        TimetableDTO timetableDTO = timetableService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(timetableDTO));
    }

    /**
     * DELETE  /timetables/:id : delete the "id" timetable.
     *
     * @param id the id of the timetableDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/timetables/{id}")
    @Timed
    public ResponseEntity<Void> deleteTimetable(@PathVariable Long id) {
        log.debug("REST request to delete Timetable : {}", id);
        timetableService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
