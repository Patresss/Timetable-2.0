package com.patres.timetable.generator.algorithm

import com.patres.timetable.domain.Division
import com.patres.timetable.domain.Timetable
import com.patres.timetable.generator.BlockWithTimetable
import com.patres.timetable.generator.TimetableGeneratorContainer
import com.patres.timetable.generator.Window
import com.patres.timetable.preference.hierarchy.PreferenceHierarchy
import com.patres.timetable.util.EntityUtil

class TimetableGeneratorSwapInWindowAlgorithm(private var container: TimetableGeneratorContainer): TimetableGeneratorAlgorithm(container) {

    override fun runAlgorithm() {
        val blocksWithTimetable = container.findAndSetupTheBiggestGroups()
        fillWindowsAndSwap(getWindows(blocksWithTimetable))
    }

    private fun getWindows(blocks: Set<BlockWithTimetable>): List<Window> {
        return blocks.flatMap { it.getWindows(container.lessons) }
    }

    private fun fillWindowsAndSwap(windows: List<Window>) {
        windows.forEach { fillWindowAndSwap(it) }
    }

    private fun fillWindowAndSwap(window: Window) {
        val timetablesToFillAndSwap = timetablesFromCurriculum
            .filter { it.lesson?.startTime ?: 1L >= window.lesson?.startTime ?: 1L && it.division == window.division && it.dayOfWeek == window.dayOfWeek }
        timetablesToFillAndSwap
            .forEach { timetable ->
                timetable.dayOfWeek = window.dayOfWeek
                timetable.lesson = window.lesson

                window.division?.let {division ->
                    val swapLessonAndDay = tryChangeLessonAndDay(division = division, timetableWithCollision = timetable)
                    if (!swapLessonAndDay) {
                        timetable.lesson = null
                        timetable.dayOfWeek = null
                    }
                }
            }
    }

    private fun tryChangeLessonAndDay(division: Division, timetableWithCollision: Timetable): Boolean {
        val divisionTimetable = timetablesFromCurriculum
            .filter { it.division == division && it.lesson != null && it.dayOfWeek != null && it != timetableWithCollision }
            .sortedBy { it.preference.getPreferencePointsByLessonAndDay(it) }

        val timetableToSwap = divisionTimetable.find { timetableToTest -> canChangeLessonAndDay(timetableWithCollision, timetableToTest) }
        return if (timetableToSwap == null) {
            TimetableGeneratorContainer.log.warn("Not found timetables to swap lesson and day")
            false
        } else {
            TimetableGeneratorContainer.log.debug("Swap timetables: $timetableWithCollision <-> $timetableToSwap")
            swapLessonAndDayWithCalculatePreference(timetableWithCollision, timetableToSwap)
            true
        }
    }

    private fun canChangeLessonAndDay(timetableWithCollision: Timetable, timetableToTest: Timetable): Boolean {
        swapLessonAndDayWithCalculatePreference(timetableWithCollision, timetableToTest)
        val canSwap = isValidPointsAfterChangeLessonAndDay(timetableWithCollision, timetableToTest)
        swapLessonAndDayWithCalculatePreference(timetableWithCollision, timetableToTest)
        return canSwap
    }

    private fun swapLessonAndDayWithCalculatePreference(timetable1: Timetable, timetable2: Timetable) {
        EntityUtil.swapLessonAndDay(timetable1, timetable2)
        preferenceManager.calculateTakenLessonAndDay(timetable1) // TODO Can we check smaller list?
        preferenceManager.calculateTakenLessonAndDay(timetable2)
    }

    private fun isValidPointsAfterChangeLessonAndDay(timetableWithCollision: Timetable, timetableToTest: Timetable) = timetableWithCollision.preference.getPreferencePointsByLessonAndDay(timetableWithCollision) ?: 0 >= PreferenceHierarchy.CAN_BE_USED && timetableToTest.preference.getPreferencePointsByLessonAndDay(timetableToTest) ?: 0 >= PreferenceHierarchy.CAN_BE_USED

}
