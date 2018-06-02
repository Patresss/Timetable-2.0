package com.patres.timetable.generator.algorithm

import com.patres.timetable.generator.ChangeDetector
import com.patres.timetable.generator.TimetableGeneratorContainer

abstract class TimetableGeneratorAlgorithm(private var container: TimetableGeneratorContainer) {


    companion object {
        const val MAX_ITERATIONS = 50
    }

    var timetablesFromCurriculum = container.timetablesFromCurriculum
    var preferenceManager = container.preferenceManager


    fun run(): Int {
        preferenceManager.calculateLessonAndDay()
        var numberOfIterations = 1
        val changeDetector = ChangeDetector()
        val timetablesWithoutLessonAndDay = -1
        do {
            TimetableGeneratorContainer.log.info("Iteration: $numberOfIterations")

            val numberOfWindows = container.findWidows().size
            TimetableGeneratorContainer.log.info("Number of windows: $numberOfWindows")

            runAlgorithm()

            val timetablesWithoutDayAndLesson = timetablesFromCurriculum.count { it.dayOfWeek == null && it.lesson == null }
            TimetableGeneratorContainer.log.info("Timetables without day and lesson: $timetablesWithoutDayAndLesson")

            changeDetector.updateValue(timetablesWithoutDayAndLesson)
            preferenceManager.calculateLessonAndDay()
        } while ((timetablesWithoutLessonAndDay != 0 && ++numberOfIterations < MAX_ITERATIONS) && changeDetector.hasChange())
        return numberOfIterations - 1
    }

    abstract fun runAlgorithm()

}
