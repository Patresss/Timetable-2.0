package com.patres.timetable.generator

import com.patres.timetable.domain.Division
import com.patres.timetable.domain.Place
import com.patres.timetable.domain.Timetable
import com.patres.timetable.preference.hierarchy.PreferenceHierarchy

class TimetableGeneratorPreferenceManager(private var container: TimetableGeneratorContainer) {


    fun calculateLessonAndDay() {
        sortByPreferredLessonAndDay()
        container.timetablesFromCurriculum
            .filter { timetable -> timetable.lesson == null || timetable.dayOfWeek == null }
            .forEach { timetableFromCurriculum ->
                calculateTakenLessonAndDay(timetableFromCurriculum)
                val lessonDayOfWeekPreferenceElement = timetableFromCurriculum.preference.preferredLessonAndDayOfWeekSet.maxBy { preferred -> preferred.preference.pointsWithWindowHandicap }
                timetableFromCurriculum.lesson = container.lessons.find { it.id == lessonDayOfWeekPreferenceElement?.lessonId }
                timetableFromCurriculum.dayOfWeek = lessonDayOfWeekPreferenceElement?.dayOfWeek
                timetableFromCurriculum.points = timetableFromCurriculum.preference.getPreferenceByLessonAndDay(timetableFromCurriculum.dayOfWeek, timetableFromCurriculum.lesson?.id)?.preference?.points ?: 0
            }
    }

    private fun sortByPreferredLessonAndDay() {
        container.timetablesFromCurriculum = container.timetablesFromCurriculum.sortedByDescending { it.preference.preferredLessonAndDayOfWeekSet.maxBy { preferred -> preferred.preference.pointsWithWindowHandicap } }.toMutableList()
    }

    fun sortByPreferredPlace() {
        container.timetablesFromCurriculum = container.timetablesFromCurriculum.sortedByDescending { it.preference.preferredPlaceMap.maxBy { entry -> entry.value.points }?.value?.points }.toMutableList()
    }

   fun calculateTakenLessonAndDay(timetableFromCurriculum: Timetable) {
        val takenTimetables = container.timetablesFromCurriculum.filter { it != timetableFromCurriculum }.toSet()
        timetableFromCurriculum.division?.let { timetableFromCurriculum.preference.calculateTakenLessonAndDayOfWeekByDivision(it, takenTimetables) }
        timetableFromCurriculum.teacher?.let { timetableFromCurriculum.preference.calculateTakenLessonAndDayOfWeekByTeacher(it, takenTimetables) }
        timetableFromCurriculum.place?.let { timetableFromCurriculum.preference.calculateTakenLessonAndDayOfWeekByPlace(it, takenTimetables) }
    }

    fun calculateTakenPlace(timetableFromCurriculum: Timetable) {
        timetableFromCurriculum.preference.calculateTakenPlace(container.timetablesFromCurriculum.filter { it.lesson?.id == timetableFromCurriculum.lesson?.id && it.dayOfWeek == timetableFromCurriculum.dayOfWeek }.toSet())
    }


    private fun isValidPointsAfterChangeLessonAndDay(timetableWithCollision: Timetable, timetableToTest: Timetable) = timetableWithCollision.preference.getPreferencePointsByLessonAndDay(timetableWithCollision) ?: 0 >= PreferenceHierarchy.CAN_BE_USED && timetableToTest.preference.getPreferencePointsByLessonAndDay(timetableToTest) ?: 0 >= PreferenceHierarchy.CAN_BE_USED

    fun calculatePreference() {
        container.timetablesFromCurriculum.forEach {
            calculateAllPreference(it, container.places)
        }
    }

    private fun calculateAllPreference(timetable: Timetable, places: Set<Place>) {
        val tooSmallPlaceId = getTooSmallPlaceId(places, timetable.division)
        timetable.preference.calculateAllForTimetable(timetable, tooSmallPlaceId)
    }

    private fun getTooSmallPlaceId(places: Set<Place>, division: Division?) = places.filter { place -> place.isPlaceTooSmallEnough(division) }.mapNotNull { it.id }.toSet()


}
