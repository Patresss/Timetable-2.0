package com.patres.timetable.service.dto.generator

import com.patres.timetable.service.dto.TimetableDTO

class GenerateReportDTO {

    var timetables: List<TimetableDTO> = emptyList()

    var unacceptedTimetables: List<TimetableDTO> = emptyList()

    var windows: List<WindowDTO> = emptyList()

    var numberOfWindows: Int = 0

    var numberOfHandicapAlgorithmIterations: Int = 0

    var numberOfSwapAlgorithmIterations: Int = 0

    var numberOfHandicapNearToBlockAlgorithmIterations: Int = 0

    var globalIterations: Int = 0

    var generateTimeImMs: Long = 0

    var allTimeImMs: Long = 0

    var minPoint: Int = 0

    var maxPoints: Int = 0

    var averagePoints: Double = 0.0

    var medianPoints: Double = 0.0


}

