package com.patres.timetable.generator

import com.patres.timetable.domain.Division
import com.patres.timetable.domain.Lesson
import com.patres.timetable.domain.Timetable
import com.patres.timetable.excpetion.ApplicationException
import com.patres.timetable.excpetion.ExceptionMessage

class BlockWithoutWindow(val timetables: HashSet<Timetable> = HashSet()){

    var hasBlockBefore = false
    var hasBlockAfter = false

    fun getWindows(lessons: Set<Lesson>): List<Window> {
        return setOf(
            getWindowBeforeBlock(lessons),
            getWindowAfterBlock(lessons)
        ).filterNotNull()
    }

    fun getWindowBeforeBlock(lessons: Set<Lesson>):Window? {
        val dayOfWeek = dayOfWeek
        val division = division
        if (dayOfWeek != null && division != null) {
            val sortedLesson = lessons.sortedBy { it.startTime }
            val index = sortedLesson.indexOf(startLesson)
            val indexBefore = index - 1
            if (indexBefore >= 0 && indexBefore < lessons.size) {
                val lessonBefore = sortedLesson[indexBefore]
                return Window(dayOfWeek = dayOfWeek, division = division, lesson = lessonBefore)
            }
        }
        return null
    }

    fun getWindowAfterBlock(lessons: Set<Lesson>):Window? {
        val dayOfWeek = dayOfWeek
        val division = division
        if (dayOfWeek != null && division != null) {
            val sortedLesson = lessons.sortedBy { it.startTime }
            val index = sortedLesson.indexOf(endLesson)
            val indexAfter = index + 1
            if (indexAfter >= 0 && indexAfter < sortedLesson.size) {
                val lessonAfter = sortedLesson[indexAfter]
                return Window(dayOfWeek = dayOfWeek, division = division, lesson = lessonAfter)
            }
        }
        return null
    }

    var division: Division? = null
        get() {
            if (timetables.isEmpty()) {
                return null
            } else if (timetables.any { timetables.first().division != it.division }) {
                throw ApplicationException(ExceptionMessage.TIMETABLES_MUST_HAVE_THIS_SAME_DIVISION)
            }
            return timetables.first().division
        }

    var dayOfWeek: Int? = null
        get() {
            if (timetables.isEmpty()) {
                return null
            } else if (timetables.any { timetables.first().dayOfWeek != it.dayOfWeek }) {
                throw ApplicationException(ExceptionMessage.TIMETABLES_MUST_HAVE_THIS_SAME_DAY)
            }
            return timetables.first().dayOfWeek
        }


    var startLesson: Lesson? = null
        get() = timetables.mapNotNull { it.lesson }.minBy { lesson ->  lesson.startTime?: 0L }

    var startTime = 0L
        get() = startLesson?.startTime?: 0L

    var endLesson: Lesson? = null
        get() = timetables.mapNotNull { it.lesson }.maxBy { lesson ->  lesson.endTime?: 0L }

    var endTime = 0L
        get() = endLesson?.endTime?: 0L


}
