package com.patres.timetable.generator

import com.patres.timetable.domain.Division
import com.patres.timetable.domain.Place
import com.patres.timetable.domain.Timetable

class TimetableGeneratorPreferenceManager(private var container: TimetableGeneratorContainer) {


    fun calculateLessonAndDay() {
        sortByPreferredLessonAndDay()
        container.timetablesFromCurriculum
            .filter { timetable -> timetable.lesson == null || timetable.dayOfWeek == null }
            .forEach { timetableFromCurriculum ->
                calculateTakenLessonAndDay(timetableFromCurriculum)
                val lessonDayOfWeekPreferenceElement = timetableFromCurriculum.preference.preferredLessonAndDayOfWeekSet.maxBy { preferred -> preferred.preference.pointsWithHandicap }
                timetableFromCurriculum.lesson = container.lessons.find { it.id == lessonDayOfWeekPreferenceElement?.lessonId }
                timetableFromCurriculum.dayOfWeek = lessonDayOfWeekPreferenceElement?.dayOfWeek

                // TODO add handicap to subgropu, code belowe dont work with other class
//                if (DivisionType.SUBGROUP == timetableFromCurriculum.division?.divisionType) {
//                    val parents = timetableFromCurriculum.division?.parents?: emptySet()
//                    val subgroups = parents
//                        .filter { parent -> parent.divisionType == DivisionType.SET_OF_SUBGROUPS }
//                        .flatMap { parent -> parent.subgroups }
//                    container.timetablesFromCurriculum
//                        .filter { timetable -> subgroups.contains(timetable.division) }
//                        .forEach { timetable -> timetable.preference.calculateSubgroupHandicap(lessonDayOfWeekPreferenceElement?.dayOfWeek, lessonDayOfWeekPreferenceElement?.lessonId ) }
//                }

            }
    }

    fun fillFinallyPoints() {
        container.timetablesFromCurriculum
            .forEach { timetableFromCurriculum ->
                timetableFromCurriculum.points = timetableFromCurriculum.preference.calculateFullPreferencePoints(timetableFromCurriculum)

            }
    }

    private fun sortByPreferredLessonAndDay() {
        container.timetablesFromCurriculum = container.timetablesFromCurriculum.sortedByDescending { it.preference.preferredLessonAndDayOfWeekSet.maxBy { preferred -> preferred.preference.pointsWithHandicap } }.toMutableList()
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

    fun calculateTakenPlaces() {
        container.timetablesFromCurriculum.forEach { timetableFromCurriculum ->
            timetableFromCurriculum.preference.calculateTakenPlace(container.timetablesFromCurriculum.filter { it.lesson?.id == timetableFromCurriculum.lesson?.id && it.dayOfWeek == timetableFromCurriculum.dayOfWeek }.toSet())
        }
    }

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
