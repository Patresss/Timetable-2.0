package com.patres.timetable.domain

import org.assertj.core.api.Assertions
import org.junit.Test

open class LessonTest {

    @Test
    open fun `test changing seconds to HHmm format`() {
        val lesson = Lesson(startTime = 45240, endTime = 82440)
        Assertions.assertThat(lesson.getStartTimeHHmmFormatted()).isEqualTo("12:34")
        Assertions.assertThat(lesson.getEndTimeHHmmFormatted()).isEqualTo("22:54")
    }

    @Test
    open fun `test changing HHmm format to seconds`() {
        val lesson = Lesson()
        lesson.setStartTimeHHmmFormatted("03:45")
        lesson.setEndTimeHHmmFormatted("13:35")
        Assertions.assertThat(lesson.startTime).isEqualTo(13500)
        Assertions.assertThat(lesson.endTime).isEqualTo(48900)
    }

}
