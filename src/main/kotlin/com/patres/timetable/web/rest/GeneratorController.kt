package com.patres.timetable.web.rest

import com.codahale.metrics.annotation.Timed
import com.patres.timetable.generator.TimetableGeneratorManager
import com.patres.timetable.preference.hierarchy.PreferenceHierarchy
import com.patres.timetable.service.CurriculumListService
import com.patres.timetable.service.TimetableService
import com.patres.timetable.service.dto.generator.GenerateReportDTO
import com.patres.timetable.service.mapper.WindowMapper
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.URISyntaxException
import kotlin.system.measureTimeMillis


@RestController
@RequestMapping("/api")
open class GeneratorController(
    private val curriculumListService: CurriculumListService,
    private val timetableService: TimetableService,
    private val timetableGeneratorManager: TimetableGeneratorManager,
    private val windowMapper: WindowMapper // TODO refatoring
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
        val generateReportDTO = GenerateReportDTO()
        val time = measureTimeMillis {
            val generateReport = timetableGeneratorManager.generate(curriculumListId)
            val savedTimetablesDTO = timetableService.saveEntityList(generateReport.timetables)
            // TODO add mapper
            generateReportDTO.apply {
                numberOfWindows = generateReport.numberOfWindows
                timetables = savedTimetablesDTO
                generateTimeImMs = generateReport.generateTimeImMs
                windows = windowMapper.toDto(generateReport.windows)
                minPoint = generateReport.minPoint
                maxPoints = generateReport.maxPoints
                medianPoints = generateReport.medianPoints
                averagePoints = generateReport.averagePoints
                numberOfHandicapAlgorithmIterations = generateReport.numberOfHandicapAlgorithmIterations
                numberOfHandicapNearToBlockAlgorithmIterations = generateReport.numberOfHandicapNearToBlockAlgorithmIterations
                numberOfSwapAlgorithmIterations = generateReport.numberOfSwapAlgorithmIterations
                globalIterations = generateReport.globalIterations
                generateTimeHandicapInWindowsAlgorithmImMs = generateReport.generateTimeHandicapInWindowsAlgorithmImMs
                generateTimeSwapInWindowAlgorithmImMs = generateReport.generateTimeSwapInWindowAlgorithmImMs
                generateTimeHandicapNearToBlockAlgorithmImMs = generateReport.generateTimeHandicapNearToBlockAlgorithmImMs
                calculatePreferenceTimeImMs = generateReport.calculatePreferenceTimeImMs
                unacceptedTimetables = savedTimetablesDTO.filter { it.points <= PreferenceHierarchy.UNACCEPTED_EVENT } // TODO refactor - this same is in class GenerateReport
            }
        }
        generateReportDTO.apply { allTimeImMs = time }
        return ResponseEntity(generateReportDTO, HttpStatus.OK)
    }

}
