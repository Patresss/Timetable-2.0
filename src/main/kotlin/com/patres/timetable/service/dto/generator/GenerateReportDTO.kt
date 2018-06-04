package com.patres.timetable.service.dto.generator

import com.patres.timetable.service.dto.TimetableDTO
import java.text.DecimalFormat

class GenerateReportDTO {

    companion object {
        val decimalFormat = DecimalFormat("#.#")
    }

    var timetables: List<TimetableDTO> = emptyList()

    var unacceptedTimetables: List<TimetableDTO> = emptyList()

    var windows: List<WindowDTO> = emptyList()

    var numberOfWindows: Int = 0

    var numberOfHandicapAlgorithmIterations: Int = 0

    var numberOfSwapAlgorithmIterations: Int = 0

    var numberOfHandicapNearToBlockAlgorithmIterations: Int = 0

    var globalIterations: Int = 0

    var allTimeImMs: Long = 0

    var generateTimeImMs: Long = 0

    var removeWindowsTimeImMs: Long = 0

    var generateTimeHandicapInWindowsAlgorithmImMs: Long = 0L

    var generateTimeSwapInWindowAlgorithmImMs: Long = 0L

    var generateTimeHandicapNearToBlockAlgorithmImMs: Long = 0L

    var calculatePreferenceTimeImMs: Long = 0L

    var calculateLessonAndTimeInMs: Long = 0L

    var calculatePlaceTimeInMs: Long = 0L

    var numberOfRemoveWindowsByHandicapInWindow: Int = 0

    var numberOfRemoveWindowsBySwapInWindow: Int = 0

    var numberOfRemoveHandicapNearToBlockInWindow: Int = 0

    var minPoint: Int = 0

    var maxPoints: Int = 0

    var averagePoints: Double = 0.0

    var medianPoints: Double = 0.0

    var statistic1 = ""
        get() = "$numberOfWindows\t${unacceptedTimetables.size}\t$maxPoints\t$minPoint\t${decimalFormat.format(averagePoints)}\t$medianPoints"

    var statistic2 = ""
        get() = "$numberOfHandicapAlgorithmIterations\t$generateTimeHandicapInWindowsAlgorithmImMs\t$numberOfRemoveWindowsByHandicapInWindow\t$numberOfSwapAlgorithmIterations\t$generateTimeSwapInWindowAlgorithmImMs\t$numberOfRemoveWindowsBySwapInWindow\t$numberOfHandicapNearToBlockAlgorithmIterations\t$generateTimeHandicapNearToBlockAlgorithmImMs\t$numberOfRemoveHandicapNearToBlockInWindow"

    var statistic3 = ""
        get() = "$allTimeImMs\t$calculatePreferenceTimeImMs\t$generateTimeImMs\t$calculateLessonAndTimeInMs\t$calculatePlaceTimeInMs\t$removeWindowsTimeImMs"


}

