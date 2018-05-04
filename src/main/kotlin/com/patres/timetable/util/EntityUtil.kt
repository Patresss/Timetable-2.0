package com.patres.timetable.util

import com.patres.timetable.domain.Timetable

object EntityUtil {

    fun swapLessonAndDay(timetable1: Timetable, timetable2: Timetable) {
        timetable1.lesson = timetable2.lesson.also {  timetable2.lesson = timetable1.lesson}
        timetable1.dayOfWeek = timetable2.dayOfWeek.also {  timetable2.dayOfWeek = timetable1.dayOfWeek}
    }




}
