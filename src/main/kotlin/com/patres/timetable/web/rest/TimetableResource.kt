package com.patres.timetable.web.rest

import com.codahale.metrics.annotation.Timed
import com.patres.timetable.domain.Interval
import com.patres.timetable.repository.IntervalRepository
import com.patres.timetable.service.TimetableService
import com.patres.timetable.service.dto.TimetableDTO
import com.patres.timetable.web.rest.util.HeaderUtil
import com.patres.timetable.web.rest.util.PaginationUtil
import com.patres.timetable.web.rest.util.TimetableDate
import io.github.jhipster.web.util.ResponseUtil
import io.swagger.annotations.ApiParam
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.net.URISyntaxException
import java.time.LocalDate
import java.util.*
import javax.validation.Valid
import kotlin.collections.HashMap


/**
 * REST controller for managing Timetable.
 */
@RestController
@RequestMapping("/api")
open class TimetableResource(
    private val timetableService: TimetableService,
    private val intervalRepository: IntervalRepository
) {

    companion object {
        private val log = LoggerFactory.getLogger(TimetableResource::class.java)
        private val ENTITY_NAME = "timetable"
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
    @Throws(URISyntaxException::class)
    open fun createTimetable(@Valid @RequestBody timetableDTO: TimetableDTO): ResponseEntity<TimetableDTO> {
        log.debug("REST request to save Timetable : {}", timetableDTO)
        if (timetableDTO.id != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new timetable cannot already have an ID")).body(null)
        }
        val result = timetableService.save(timetableDTO)
        return ResponseEntity.created(URI("/api/timetables/" + result.id!!))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.id!!.toString()))
            .body(result)
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
    @Throws(URISyntaxException::class)
    open fun updateTimetable(@Valid @RequestBody timetableDTO: TimetableDTO): ResponseEntity<TimetableDTO> {
        log.debug("REST request to update Timetable : {}", timetableDTO)
        if (timetableDTO.id == null) {
            return createTimetable(timetableDTO)
        }
        val result = timetableService.save(timetableDTO)
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, timetableDTO.id!!.toString()))
            .body(result)
    }

    /**
     * GET  /timetables : get all the timetables.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of timetables in body
     */
    @GetMapping("/timetables")
    @Timed
    open fun getAllTimetables(@ApiParam pageable: Pageable): ResponseEntity<List<TimetableDTO>> {
        log.debug("REST request to get a page of Timetables")
        val page = timetableService.findAll(pageable)
        val headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/timetables")
        return ResponseEntity(page.content, headers, HttpStatus.OK)
    }

    /**
     * GET  /timetables/:id : get the "id" timetable.
     *
     * @param id the id of the timetableDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the timetableDTO, or with status 404 (Not Found)
     */
    @GetMapping("/timetables/{id}")
    @Timed
    open fun getTimetable(@PathVariable id: Long?): ResponseEntity<TimetableDTO> {
        log.debug("REST request to get Timetable : {}", id)
        val timetableDTO = timetableService.findOne(id)
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(timetableDTO))
    }

    /**
     * DELETE  /timetables/:id : delete the "id" timetable.
     *
     * @param id the id of the timetableDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/timetables/{id}")
    @Timed
    open fun deleteTimetable(@PathVariable id: Long?): ResponseEntity<Void> {
        log.debug("REST request to delete Timetable : {}", id)
        timetableService.delete(id)
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id!!.toString())).build()
    }

    /**
     * GET  /timetables/division-list : get the timetable by dateAndDivisionList
     *
     * @param divisionIdList
     * @param date
     * @return the ResponseEntity with status 200 (OK) and with body the List of timetableDTO, or with status 404 (Not Found)
     */
    @GetMapping("/timetables/division-list", params = ["divisionIdList", "date"])
    @Timed
    open fun getTimetableByDateAndDivisionList(@RequestParam(value = "divisionIdList") divisionIdList: List<Long>, @RequestParam(value = "date") date: String): ResponseEntity<Set<TimetableDTO>> {
        log.debug("REST request to get Timetable by date: $date Division List: $divisionIdList")
        val localDate = LocalDate.parse(date)
        val timetablesDTO = timetableService.findByDivisionListAndDateFromPeriod(localDate, divisionIdList)
        return getTimetables(localDate, timetablesDTO)
    }

    /**
     * GET  /timetables/teacher : get the timetable by dateAndDivisionList
     *
     * @param teacherId
     * @param date
     * @return the ResponseEntity with status 200 (OK) and with body the List of timetableDTO, or with status 404 (Not Found)
     */
    @GetMapping("/timetables/teacher", params = ["teacherId", "date"])
    @Timed
    open fun getTimetableByDateAndTeacherId(@RequestParam(value = "teacherId") teacherId: Long, @RequestParam(value = "date") date: String): ResponseEntity<Set<TimetableDTO>> {
        log.debug("REST request to get Timetable by date: $date Teacher id: $teacherId")
        val localDate = LocalDate.parse(date)
        val timetablesDTO = timetableService.findByDivisionListAndDateFromTeacherId(localDate, teacherId)
        return getTimetables(localDate, timetablesDTO)
    }

    /**
     * GET  /timetables/place : get the timetable by dateAndDivisionList
     *
     * @param teacherId
     * @param date
     * @return the ResponseEntity with status 200 (OK) and with body the List of timetableDTO, or with status 404 (Not Found)
     */
    @GetMapping("/timetables/place", params = ["placeId", "date"])
    @Timed
    open fun getTimetableByDateAndPlaceId(@RequestParam(value = "placeId") placeId: Long, @RequestParam(value = "date") date: String): ResponseEntity<Set<TimetableDTO>> {
        log.debug("REST request to get Timetable by date: $date Place id: $placeId")
        val localDate = LocalDate.parse(date)
        val timetablesDTO = timetableService.findByDivisionListAndDateFromPeriod(localDate, placeId)
        return getTimetables(localDate, timetablesDTO)
    }

    open fun getTimetables(localDate: LocalDate, timetables: Set<TimetableDTO>): ResponseEntity<Set<TimetableDTO>> {
        val filteredByWeekDay = timetables.filter { TimetableDate.canAddByWeekDay(localDate, it) }

        val timetablesWithDate = filteredByWeekDay.filter { it.date != null }
        var timetablesWithPeriod = filteredByWeekDay.filter { it.date == null && it.periodId != null }

        val periodIdIntervalMap = HashMap<Long, Interval>()
        val periodsId = timetablesWithPeriod.map { it.periodId }.toSet()
        periodsId.filterNotNull().forEach { periodIdIntervalMap[it] = intervalRepository.findFirstByPeriodIdAndIncludedTrueOrderByStartDate(it) }

        timetablesWithPeriod = timetablesWithPeriod.filter { TimetableDate.canAddByEveryDay(localDate, periodIdIntervalMap[it.periodId]?.startDate, it) }

        val timetableSet = (timetablesWithDate + timetablesWithPeriod).toSet()
        return ResponseEntity(timetableSet, HttpStatus.OK)
    }
}
