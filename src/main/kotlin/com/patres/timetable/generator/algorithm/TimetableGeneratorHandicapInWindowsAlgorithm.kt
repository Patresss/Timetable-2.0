package com.patres.timetable.generator.algorithm

import com.patres.timetable.generator.BlockWithTimetable
import com.patres.timetable.generator.TimetableGeneratorContainer
import com.patres.timetable.generator.Window
import com.patres.timetable.preference.hierarchy.PreferenceHierarchy

class TimetableGeneratorHandicapInWindowsAlgorithm(private var container: TimetableGeneratorContainer) : TimetableGeneratorAlgorithm(container) {

    override fun runAlgorithm() {
        val blocksWithTimetable = container.findAndSetupTheBiggestGroups()
        removeTimetableBeforeBlocks(blocksWithTimetable)
        removeTimetableAfterBlocks(blocksWithTimetable)
        addHandicapInWindows(getWindows(blocksWithTimetable))
    }

    private fun getWindows(blocks: Set<BlockWithTimetable>): List<Window> {
        return blocks.flatMap { it.getWindows(container.lessons) }
    }

    private fun removeTimetableBeforeBlocks(blocks: Set<BlockWithTimetable>) {
        blocks.filter { it.hasBlockBefore }.forEach { removeTimetableBeforeBlock(it) }
    }

    private fun removeTimetableAfterBlocks(blocks: Set<BlockWithTimetable>) {
        blocks.filter { it.hasBlockAfter }.forEach { removeTimetableAfterBlock(it) }
    }

    private fun removeTimetableBeforeBlock(block: BlockWithTimetable) {
        val timetablesToRemove = timetablesFromCurriculum
            .filter { it.lesson?.endTime ?: 0L < block.startTime && it.division == block.division && it.dayOfWeek == block.dayOfWeek }
        if (timetablesToRemove.isNotEmpty()) {
            TimetableGeneratorContainer.log.debug("Remove windows for division: ${block.division?.name} in: day of week: ${block.dayOfWeek} after lesson when start ${block.startTime}")
            timetablesToRemove
                .forEach { timetable ->
                    timetable.lesson = null
                    timetable.dayOfWeek = null
                    timetable.place = null
                }
        }
    }

    private fun removeTimetableAfterBlock(block: BlockWithTimetable) {
        val timetablesToRemove = timetablesFromCurriculum
            .filter { it.lesson?.startTime ?: 0L > block.endTime && it.division == block.division && it.dayOfWeek == block.dayOfWeek }
        if (timetablesToRemove.isNotEmpty()) {
            TimetableGeneratorContainer.log.debug("Remove windows for division: ${block.division?.name} in: day of week: ${block.dayOfWeek} after lesson when start ${block.startTime}")
            timetablesToRemove
                .forEach { timetable ->
                    timetable.lesson = null
                    timetable.dayOfWeek = null
                    timetable.place = null

                }
        }
    }


    private fun addHandicapInWindows(window: List<Window>) {
        window.forEach { addHandicapInWindow(it) }
    }

    private fun addHandicapInWindow(window: Window) {
//        timetablesFromCurriculum
//            .filter { it.lesson == null && it.dayOfWeek == null && it.division == window.division }
//            .forEach { timetable ->
//                val preference = timetable.preference.getPreferenceByLessonAndDay(window.dayOfWeek, window.lesson?.id)?.preferenceLessonAndDayOfWeekHierarchy
//                preference?.let { it.windowHandicap += PreferenceHierarchy.HANDICAP }
//            }
    }
}
