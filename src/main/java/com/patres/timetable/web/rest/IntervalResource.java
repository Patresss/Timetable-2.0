package com.patres.timetable.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.patres.timetable.service.IntervalService;
import com.patres.timetable.service.dto.IntervalDTO;
import com.patres.timetable.web.rest.util.HeaderUtil;
import com.patres.timetable.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing Interval.
 */
@RestController
@RequestMapping("/api")
public class IntervalResource {

    private final Logger log = LoggerFactory.getLogger(IntervalResource.class);

    private static final String ENTITY_NAME = "interval";

    private final IntervalService intervalService;

    public IntervalResource(IntervalService intervalService) {
        this.intervalService = intervalService;
    }

    /**
     * POST  /intervals : Create a new interval.
     *
     * @param intervalDTO the intervalDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new intervalDTO, or with status 400 (Bad Request) if the interval has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/intervals")
    @Timed
    public ResponseEntity<IntervalDTO> createInterval(@RequestBody IntervalDTO intervalDTO) throws URISyntaxException {
        log.debug("REST request to save Interval : {}", intervalDTO);
        if (intervalDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new interval cannot already have an ID")).body(null);
        }
        IntervalDTO result = intervalService.save(intervalDTO);
        return ResponseEntity.created(new URI("/api/intervals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /intervals : Updates an existing interval.
     *
     * @param intervalDTO the intervalDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated intervalDTO,
     * or with status 400 (Bad Request) if the intervalDTO is not valid,
     * or with status 500 (Internal Server Error) if the intervalDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/intervals")
    @Timed
    public ResponseEntity<IntervalDTO> updateInterval(@RequestBody IntervalDTO intervalDTO) throws URISyntaxException {
        log.debug("REST request to update Interval : {}", intervalDTO);
        if (intervalDTO.getId() == null) {
            return createInterval(intervalDTO);
        }
        IntervalDTO result = intervalService.save(intervalDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, intervalDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /intervals : get all the intervals.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of intervals in body
     */
    @GetMapping("/intervals")
    @Timed
    public ResponseEntity<List<IntervalDTO>> getAllIntervals(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Intervals");
        Page<IntervalDTO> page = intervalService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/intervals");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /intervals/period/:id : get the intervals by period.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of intervals in body
     */
    @GetMapping("/intervals/period/{id}")
    @Timed
    public ResponseEntity<List<IntervalDTO>> getIntervalsByPeriodId(@ApiParam Pageable pageable, @PathVariable Long id) throws URISyntaxException {
        log.debug("REST request to get a Intervals by period Id");
        Page<IntervalDTO> page = intervalService.findByPeriodId(pageable, id);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/intervals");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /intervals/:id : get the "id" interval.
     *
     * @param id the id of the intervalDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the intervalDTO, or with status 404 (Not Found)
     */
    @GetMapping("/intervals/{id}")
    @Timed
    public ResponseEntity<IntervalDTO> getInterval(@PathVariable Long id) {
        log.debug("REST request to get Interval : {}", id);
        IntervalDTO intervalDTO = intervalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(intervalDTO));
    }

    /**
     * DELETE  /intervals/:id : delete the "id" interval.
     *
     * @param id the id of the intervalDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/intervals/{id}")
    @Timed
    public ResponseEntity<Void> deleteInterval(@PathVariable Long id) {
        log.debug("REST request to delete Interval : {}", id);
        intervalService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
