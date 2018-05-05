package com.patres.timetable.generator.algorithm

import com.patres.timetable.generator.ChangeDetector
import com.patres.timetable.generator.TimetableGeneratorContainer
import com.patres.timetable.generator.Window

abstract class TimetableGeneratorAlgorithm(private var container: TimetableGeneratorContainer) {


    companion object {
        const val MAX_ITERATIONS = 50
    }

    var timetablesFromCurriculum = container.timetablesFromCurriculum
    var preferenceManager = container.preferenceManager


    fun run() {
        preferenceManager.calculateLessonAndDay()
        var counter = 1
        val changeDetector = ChangeDetector()
        val timetablesWithoutLessonAndDay = -1
        do {
            TimetableGeneratorContainer.log.info("Iteration: $counter")
            runAlgorithm()

            changeDetector.updateValue(timetablesFromCurriculum.count { it.dayOfWeek == null && it.lesson == null })
            preferenceManager.calculateLessonAndDay()
        } while ((timetablesWithoutLessonAndDay != 0 && ++counter < MAX_ITERATIONS) && changeDetector.hasChange())

    }

    abstract fun runAlgorithm()

    protected fun findWidows(): Set<Window> {
        val windows = HashSet<Window>()
        timetablesFromCurriculum.sortBy { it.lesson?.startTime }
        val timetablesByDivision = timetablesFromCurriculum.groupBy { it.division }
        timetablesByDivision.forEach { division, timetables ->
            timetables.groupBy { it.dayOfWeek }.forEach { dayOfWeek, divisionTimetables ->
                if (division != null && dayOfWeek != null) {
                    for (lesson in container.lessons) {
                        val currentLesson = divisionTimetables.find { it.lesson == lesson }?.lesson
                        if (currentLesson == null && divisionTimetables.any { lesson.startTime ?: 0 > it.lesson?.endTime ?: 0 } && divisionTimetables.any { lesson.endTime ?: 0 < it.lesson?.startTime ?: 0 }) {
                            windows.add(Window(dayOfWeek, lesson, division))
                            break
                        }
                    }
                }
            }
        }
        return windows
    }



}
