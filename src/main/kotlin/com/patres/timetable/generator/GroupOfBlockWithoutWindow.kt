package com.patres.timetable.generator

import com.patres.timetable.domain.Timetable

class GroupOfBlockWithoutWindow(val blocksWithoutWindow: ArrayList<BlockWithoutWindow> = ArrayList()) {

    var size = 0
        get() = blocksWithoutWindow.size

    var timetables = emptyList<Timetable>()
        get() = blocksWithoutWindow.flatMap { it.timetables }


    fun add(blockWithoutWindow: BlockWithoutWindow) {
        blocksWithoutWindow.add(blockWithoutWindow)
    }

    fun getTheBiggestGroup(): BlockWithoutWindow? {
        return blocksWithoutWindow.maxWith(Comparator { g1, g2 ->
            when {
                g1.timetables.size > g2.timetables.size -> 1
                g1.timetables.size == g2.timetables.size && g1.timetables.first().lesson?.startTime ?: 0 <= g2.timetables.first().lesson?.startTime ?: 0 -> 1
                g1.timetables.size == g2.timetables.size && g1.timetables.first().lesson?.startTime ?: 0 > g2.timetables.first().lesson?.startTime ?: 0 -> -1
                g1.timetables.size == g2.timetables.size && g1.timetables.first().lesson?.startTime ?: 0 == g2.timetables.first().lesson?.startTime ?: 0 -> 0
                else -> -1
            }
        })
    }


    fun setupBlock(blockWithoutWindow: BlockWithoutWindow) {
       // blockWithoutWindow.hasBlockBefore = blocksWithoutWindow.indexOf(blockWithoutWindow) != 0
       // blockWithoutWindow.hasBlockAfter = blocksWithoutWindow.indexOf(blockWithoutWindow) != size - 1
    }


}
