package com.patres.timetable.generator.report

import com.patres.timetable.domain.Timetable
import com.patres.timetable.generator.Window

class GenerateReport(
    var timetables: List<Timetable> = emptyList(),
    var generateTimeImMs: Long = 0,
    var allTimeImMs: Long = 0,
    var windows: List<Window> = emptyList()
    ) {

    var numberOfWindows: Int = 0
        get() = windows.size

    init {
        timetables = timetables.sortedByDescending { it.points }
    }
}

