package com.patres.timetable.util

import com.patres.timetable.domain.Timetable

object EntityUtil {

    fun swapLessonAndDay(timetable1: Timetable, timetable2: Timetable) {
        timetable1.lesson = timetable2.lesson.also {  timetable2.lesson = timetable1.lesson}
        timetable1.dayOfWeek = timetable2.dayOfWeek.also {  timetable2.dayOfWeek = timetable1.dayOfWeek}
    }

    fun calculateRandomColor(): String {
        val r = Math.round(Math.random() * 255).toInt()
        val g = Math.round(Math.random() * 255).toInt()
        val b = Math.round(Math.random() * 255).toInt()
        return "rgb($r, $g, $b)"
    }

}
