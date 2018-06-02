package com.patres.timetable.service.dto.generator

import com.patres.timetable.generator.Window
import com.patres.timetable.service.dto.TimetableDTO

class GenerateReportDTO {

    var timetables: List<TimetableDTO> = emptyList()

    var numberOfWindows: Int = 0

    var windows: List<WindowDTO> = emptyList()

    var generateTimeImMs: Long = 0

    var allTimeImMs: Long = 0

}

