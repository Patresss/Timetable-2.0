package com.patres.timetable.generator

import com.patres.timetable.preference.SchoolDataToPreference
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class TimetableGenerator(
    val schoolData: SchoolDataToPreference,
    val timetablesWithPreference: Set<TimetableWithPreference>

) {

    companion object {
        val log: Logger = LoggerFactory.getLogger(TimetableGenerator::class.java)
    }

    fun generate() {
        timetablesWithPreference.forEach {timetableWithPreference ->
            val pairPlaceLessonAndDay = timetableWithPreference.preferenceContainer.calculateTheBestPairPlaceLessonAndDay()
             if(pairPlaceLessonAndDay != null) {
                 val placeId =  pairPlaceLessonAndDay.preferencePlaceHierarchy.placeId
                 val place = schoolData.places.find { it.id == placeId }
                 timetableWithPreference.timetable.place = place

                 val preferenceLessonAndDayHierarchy = pairPlaceLessonAndDay.preferenceLessonAndDayHierarchy

                 val lessonId = preferenceLessonAndDayHierarchy.lessonId
                 val lesson = schoolData.lessons.find { it.id == lessonId }
                 timetableWithPreference.timetable.lesson = lesson

                 val dayOfWeek = preferenceLessonAndDayHierarchy.dayOfWeek
                 timetableWithPreference.timetable.dayOfWeek = dayOfWeek.value
             }
        }
    }

}
