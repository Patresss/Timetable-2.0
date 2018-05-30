package com.patres.timetable.service.mapper

import com.patres.timetable.generator.report.GenerateReport
import com.patres.timetable.service.dto.generator.GenerateReportDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class GenerateReportMapper : EntityMapper<GenerateReport, GenerateReportDTO>() {

    @Autowired
    private lateinit var timetableMapper: TimetableMapper

    override fun toEntity(entityDto: GenerateReportDTO): GenerateReport {
        return GenerateReport()
            .apply {
                numberOfWindows = entityDto.numberOfWindows
                timetables = timetableMapper.toEntity(entityDto.timetables)
            }
    }

    override fun toDto(entity: GenerateReport): GenerateReportDTO {
        return GenerateReportDTO()
            .apply {
                numberOfWindows = entity.numberOfWindows
                timetables = timetableMapper.toDto(entity.timetables)
            }
    }

}
