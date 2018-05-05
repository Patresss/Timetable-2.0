package com.patres.timetable.generator.algorithm

import com.patres.timetable.generator.TimetableGeneratorContainer
import com.patres.timetable.generator.Window
import com.patres.timetable.preference.hierarchy.PreferenceHierarchy

class TimetableGeneratorHandicapInWindowsAlgorithm(private var container: TimetableGeneratorContainer): TimetableGeneratorAlgorithm(container) {

    override fun runAlgorithm() {
        val windows = findWidows()
        removeTimetablesAfterWindows(windows)
        addHandicapInWindows(windows)
    }

    private fun removeTimetablesAfterWindows(windows: Set<Window>) {
        windows.forEach { removeTimetablesAfterWindow(it) }
    }

    private fun removeTimetablesAfterWindow(window: Window) {
        val timetablesToRemove = timetablesFromCurriculum
            .filter { it.lesson?.startTime ?: 1L > window.lesson.startTime ?: 1L && it.division == window.division && it.dayOfWeek == window.dayOfWeek }
        if (timetablesToRemove.isNotEmpty()) {
            TimetableGeneratorContainer.log.debug("Remove windows for division: ${window.division.name} in: day of week: ${window.dayOfWeek} after lesson when start ${window.lesson.getStartTimeHHmmFormatted()}")
            timetablesToRemove
                .forEach { timetable ->
                    timetable.lesson = null
                    timetable.dayOfWeek = null
                }
        }
    }

    private fun addHandicapInWindows(window: Set<Window>) {
        window.forEach { addHandicapInWindow(it) }
    }

    private fun addHandicapInWindow(window: Window) {
        timetablesFromCurriculum
            .filter { it.lesson == window.lesson && it.dayOfWeek == window.dayOfWeek && it.division == window.division }
            .forEach { timetable ->
                val preference = timetable.preference.getPreferenceByLessonAndDay(window.dayOfWeek, window.lesson.id)?.preference
                preference?.let { it.windowHandicap += PreferenceHierarchy.HANDICAP }
            }
    }
}
