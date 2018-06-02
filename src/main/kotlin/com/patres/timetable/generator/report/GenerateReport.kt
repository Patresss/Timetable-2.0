package com.patres.timetable.generator.report

import com.patres.timetable.domain.Timetable
import com.patres.timetable.generator.Window
import com.patres.timetable.preference.hierarchy.PreferenceHierarchy
import org.nield.kotlinstatistics.median

class GenerateReport(
    var timetables: List<Timetable> = emptyList(),
    var generateTimeImMs: Long = 0,
    var allTimeImMs: Long = 0,
    var windows: List<Window> = emptyList()
    ) {

    var unacceptedTimetables: List<Timetable> = emptyList()
        get() = timetables.filter { it.points <= PreferenceHierarchy.UNACCEPTED_EVENT }

    var numberOfWindows: Int = 0
        get() = windows.size

    var minPoint: Int = 0
        get() = timetables.map { it.points }.min()?: 0

    var maxPoints: Int = 0
        get() = timetables.map { it.points }.max()?: 0

    var averagePoints: Double = 0.0
        get() = timetables.map { it.points }.average()

    var medianPoints: Double = 0.0
        get() = timetables.map { it.points }.median()

    init {
        timetables = timetables.sortedByDescending { it.points }
    }
}

