package com.patres.timetable.generator.report

import com.patres.timetable.domain.Timetable

class GenerateReport(var timetables: List<Timetable> = emptyList(), val numberOfWinndows: Int = 0) {

    init {
        timetables = timetables.sortedByDescending { it.points }
    }
}

