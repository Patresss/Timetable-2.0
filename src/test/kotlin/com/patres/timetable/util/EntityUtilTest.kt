package com.patres.timetable.util

import com.patres.timetable.domain.Lesson
import com.patres.timetable.domain.Timetable
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test

open class EntityUtilTest {

    var lesson1 = Lesson()
    var lesson2 = Lesson()
    var timetable1 = Timetable()
    var timetable2 = Timetable()


    @Before
    fun initValues() {
        lesson1 = Lesson().apply {
            id = 1
            endTime = 2
        }
        lesson2 = Lesson().apply {
            id = 3
            endTime = 4
        }
        timetable1 = Timetable().apply {
            id = 12
            dayOfWeek = 3
            lesson = lesson1
        }
        timetable2 = Timetable().apply {
            id = 13
            dayOfWeek = 4
            lesson = lesson2
        }
    }

    @Test
    open fun `should change day of week`() {
        EntityUtil.swapLessonAndDay(timetable1, timetable2)
        Assertions.assertThat(timetable1.dayOfWeek).isEqualTo(4)
        Assertions.assertThat(timetable2.dayOfWeek).isEqualTo(3)
    }

    @Test
    open fun `should change lesson`() {
        EntityUtil.swapLessonAndDay(timetable1, timetable2)
        Assertions.assertThat(timetable1.lesson!!.id).isEqualTo(3)
        Assertions.assertThat(timetable2.lesson!!.id).isEqualTo(1)
    }


    @Test
    open fun `should not change id`() {
        EntityUtil.swapLessonAndDay(timetable1, timetable2)
        Assertions.assertThat(timetable1.id).isEqualTo(12)
        Assertions.assertThat(timetable2.id).isEqualTo(13)
    }

}
