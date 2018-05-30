package com.patres.timetable.web.rest

import com.codahale.metrics.annotation.Timed
import com.patres.timetable.generator.TimetableGeneratorStrategyType
import com.patres.timetable.generator.TimetableGeneratorManager
import com.patres.timetable.generator.report.GenerateReport
import com.patres.timetable.service.CurriculumListService
import com.patres.timetable.service.TimetableService
import com.patres.timetable.service.dto.TimetableDTO
import com.patres.timetable.service.dto.generator.GenerateReportDTO
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import java.net.URISyntaxException


@RestController
@RequestMapping("/api")
open class GeneratorController(
    private val curriculumListService: CurriculumListService,
    private val timetableService: TimetableService,
    private val timetableGeneratorManager: TimetableGeneratorManager
    ) {

    private val log = LoggerFactory.getLogger(GeneratorController::class.java)

    /**
     * POST  /generator : Generate
     *
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    // TODO add security
    @PostMapping("/generator")
    @Timed
    @Throws(URISyntaxException::class)
    open fun generate(@RequestParam curriculumListId: Long): ResponseEntity<GenerateReportDTO> {
        log.debug("REST request generate with CurriculumListId : {}", curriculumListId)
        val generateReport = timetableGeneratorManager.generate(curriculumListId)
        val savedTimetablesDTO = timetableService.saveEntityList(generateReport.timetables)
        val generateReportDTO = GenerateReportDTO().apply {
            numberOfWindows = generateReport.numberOfWindows
            timetables = savedTimetablesDTO
        }
        return ResponseEntity(generateReportDTO, HttpStatus.OK)
    }

}
