package com.patres.timetable.generator.algorithm

import com.patres.timetable.generator.ChangeDetector
import com.patres.timetable.generator.TimetableGeneratorContainer

abstract class TimetableGeneratorAlgorithm(private var container: TimetableGeneratorContainer) {


    companion object {
        const val MAX_ITERATIONS = 20
    }

    var timetablesFromCurriculum = container.timetablesFromCurriculum
    var preferenceManager = container.preferenceManager


    fun run() {
            runAlgorithm()

    }

    abstract fun runAlgorithm()

}
