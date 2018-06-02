package com.patres.timetable.generator.algorithm

import com.patres.timetable.generator.BlockWithTimetable
import com.patres.timetable.generator.TimetableGeneratorContainer
import com.patres.timetable.generator.Window
import com.patres.timetable.preference.hierarchy.PreferenceHierarchy

// TODO refactor this a lot of duplicate
class TimetableGeneratorHandicapNearToBlockAlgorithm(private var container: TimetableGeneratorContainer) : TimetableGeneratorAlgorithm(container) {

    override fun runAlgorithm() {
        val blocksWithTimetable = container.findAndSetupTheBiggestGroups()
        removeTimetableBeforeBlocks(blocksWithTimetable)
        removeTimetableAfterBlocks(blocksWithTimetable)
        addHandicap(getNearEmptySpace(blocksWithTimetable))
    }

    private fun getNearEmptySpace(blocks: Set<BlockWithTimetable>): List<Window> {
        return blocks.flatMap { it.getNearEmptySpace(container.lessons) }
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
                }
        }
    }


    private fun addHandicap(window: List<Window>) {
        window.forEach { addHandicap(it) }
    }

    private fun addHandicap(window: Window) {
        timetablesFromCurriculum
            .filter { it.lesson == null && it.dayOfWeek == null && it.division == window.division }
            .forEach { timetable ->
                val preference = timetable.preference.getPreferenceByLessonAndDay(window.dayOfWeek, window.lesson?.id)?.preference
                preference?.let { it.windowHandicap += PreferenceHierarchy.HANDICAP }
            }
    }
}
