package com.patres.timetable.generator.report

import com.patres.timetable.domain.Timetable

class GenerateReport(var timetables: List<Timetable> = emptyList(), var numberOfWindows: Int = 0) {

    init {
        timetables = timetables.sortedByDescending { it.points }
    }
}

