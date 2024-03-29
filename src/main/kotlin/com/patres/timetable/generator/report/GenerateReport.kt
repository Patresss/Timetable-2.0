package com.patres.timetable.generator.report

import com.patres.timetable.domain.Timetable
import com.patres.timetable.generator.Window
import com.patres.timetable.preference.hierarchy.PreferenceHierarchy
import org.nield.kotlinstatistics.median

class GenerateReport(
    var timetables: List<Timetable> = emptyList(),
    var windows: Set<Window> = emptySet(),
    var numberOfHandicapAlgorithmIterations: Int = 0,
    var numberOfHandicapNearToBlockAlgorithmIterations: Int = 0,
    var numberOfSwapAlgorithmIterations: Int = 0,
    var globalIterations: Int = 0,
    var generateTimeImMs: Long = 0,
    var allTimeImMs: Long = 0,
    var generateTimeHandicapInWindowsAlgorithmImMs: Long = 0L,
    var generateTimeSwapInWindowAlgorithmImMs: Long = 0L,
    var generateTimeHandicapNearToBlockAlgorithmImMs: Long = 0L,
    var calculateLessonAndTimeInMs: Long = 0L,
    var calculatePlaceTimeInMs: Long = 0L,
    var calculatePreferenceTimeImMs: Long = 0L,
    var numberOfRemoveWindowsByHandicapInWindow:Int = 0,
    var numberOfRemoveWindowsBySwapInWindow:Int = 0,
    var numberOfRemoveHandicapNearToBlockInWindow:Int = 0
) {

    var unacceptedTimetables: List<Timetable> = emptyList()
        get() = timetables.filter { it.points <= PreferenceHierarchy.UNACCEPTED_EVENT }

    var removeWindowsTimeImMs: Long = 0
        get() = generateTimeHandicapInWindowsAlgorithmImMs + generateTimeSwapInWindowAlgorithmImMs + generateTimeHandicapNearToBlockAlgorithmImMs


    var numberOfWindows: Int = 0
        get() = windows.size

    var minPoint: Int = 0
        get() = timetables.map { it.points }.min() ?: 0

    var maxPoints: Int = 0
        get() = timetables.map { it.points }.max() ?: 0

    var averagePoints: Double = 0.0
        get() = timetables.map { it.points }.average()

    var medianPoints: Double = 0.0
        get() = timetables.map { it.points }.median()

    init {
        timetables = timetables.sortedByDescending { it.points }
    }
}

