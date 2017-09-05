package com.patres.timetable.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.patres.timetable.service.PeriodService;
import com.patres.timetable.service.dto.PeriodDTO;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Period.
 */
@RestController
@RequestMapping("/api")
public class PeriodResource {

    private final Logger log = LoggerFactory.getLogger(PeriodResource.class);

    private static final String ENTITY_NAME = "period";

    private final PeriodService periodService;

    public PeriodResource(PeriodService periodService) {
        this.periodService = periodService;
    }

    /**
     * POST  /periods : Create a new period.
     *
     * @param periodDTO the periodDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new periodDTO, or with status 400 (Bad Request) if the period has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PreAuthorize("@periodService.hasPriviligeToAddEntity(#periodDTO)")
    @PostMapping("/periods")
    @Timed
    public ResponseEntity<PeriodDTO> createPeriod(@Valid @RequestBody PeriodDTO periodDTO) throws URISyntaxException {
        log.debug("REST request to save Period : {}", periodDTO);
        if (periodDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new period cannot already have an ID")).body(null);
        }
        PeriodDTO result = periodService.save(periodDTO);
        return ResponseEntity.created(new URI("/api/periods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /periods : Updates an existing period.
     *
     * @param periodDTO the periodDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated periodDTO,
     * or with status 400 (Bad Request) if the periodDTO is not valid,
     * or with status 500 (Internal Server Error) if the periodDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PreAuthorize("@periodService.hasPriviligeToModifyEntity(#periodDTO)")
    @PutMapping("/periods")
    @Timed
    public ResponseEntity<PeriodDTO> updatePeriod(@Valid @RequestBody PeriodDTO periodDTO) throws URISyntaxException {
        log.debug("REST request to update Period : {}", periodDTO);
        if (periodDTO.getId() == null) {
            return createPeriod(periodDTO);
        }
        PeriodDTO result = periodService.save(periodDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, periodDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /periods : get all the periods.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of periods in body
     */
    @GetMapping("/periods")
    @Timed
    public ResponseEntity<List<PeriodDTO>> getAllPeriods(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Periods");
        Page<PeriodDTO> page = periodService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/periods");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    /**
     * GET  /periods/divisions : get the periods by Division owners id.
     *
     * @param divisionsId divisions id
     * @param pageable    the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of periods in body
     */
    @GetMapping("/periods/divisions")
    @Timed
    public ResponseEntity<List<PeriodDTO>> getPeriodsByDivisionsId(@ApiParam Pageable pageable, @PathVariable List<Long> divisionsId) {
        log.debug("REST request to get a page of Periods by Division owners id");

        Page<PeriodDTO> page = periodService.findByDivisionOwnerId(pageable, divisionsId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/periods");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /periods/login : get the periods by current login.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of periods in body
     */
    @GetMapping("/periods/login")
    @Timed
    public ResponseEntity<List<PeriodDTO>> getPeriodsByCurrentLogin(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Periods by current login");

        Page<PeriodDTO> page = periodService.findByCurrentLogin(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/periods");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /periods/:id : get the "id" period.
     *
     * @param id the id of the periodDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the periodDTO, or with status 404 (Not Found)
     */
    @GetMapping("/periods/{id}")
    @Timed
    public ResponseEntity<PeriodDTO> getPeriod(@PathVariable Long id) {
        log.debug("REST request to get Period : {}", id);
        PeriodDTO periodDTO = periodService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(periodDTO));
    }

    /**
     * DELETE  /periods/:id : delete the "id" period.
     *
     * @param id the id of the periodDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @PreAuthorize("@periodService.hasPriviligeToDeleteEntity(#id)")
    @DeleteMapping("/periods/{id}")
    @Timed
    public ResponseEntity<Void> deletePeriod(@PathVariable Long id) {
        log.debug("REST request to delete Period : {}", id);
        periodService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
