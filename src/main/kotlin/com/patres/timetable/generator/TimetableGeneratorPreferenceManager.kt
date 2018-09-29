package com.patres.timetable.generator

import com.patres.timetable.domain.Division
import com.patres.timetable.domain.Place
import com.patres.timetable.domain.Timetable
import com.patres.timetable.preference.hierarchy.PreferenceHierarchy
import kotlin.math.absoluteValue

class TimetableGeneratorPreferenceManager(private var container: TimetableGeneratorContainer) {


    fun calculateLessonAndDay() {
//        sortByPreferredLessonAndDay()
//        container.timetablesFromCurriculum
//            .filter { timetable -> timetable.lesson == null || timetable.dayOfWeek == null }
//            .forEach { timetableFromCurriculum ->
//               // calculateTakenLessonAndDay(timetableFromCurriculum)
//                val lessonDayOfWeekPreferenceElement = timetableFromCurriculum.preference.preferredLessonAndDayOfWeekSet.maxBy { preferred -> preferred.preferenceLessonAndDayOfWeekHierarchy.pointsWithHandicap }
//                timetableFromCurriculum.lesson = container.lessons.find { it.id == lessonDayOfWeekPreferenceElement?.lessonId }
//                timetableFromCurriculum.dayOfWeek = lessonDayOfWeekPreferenceElement?.dayOfWeek?.value

                // TODO add handicap to subgropu, code belowe dont work with other class
//                if (DivisionType.SUBGROUP == timetableFromCurriculum.division?.divisionType) {
//                    val parents = timetableFromCurriculum.division?.parents ?: emptySet()
//                    val subgroups = parents
//                        .filter { parent -> parent.divisionType == DivisionType.SET_OF_SUBGROUPS }
//                        .flatMap { parent -> parent.subgroups }
//                    container.timetablesFromCurriculum
//                        .filter { timetable -> subgroups.contains(timetable.division) }
//                        .forEach { timetable -> timetable.preferenceLessonAndDayOfWeekHierarchy.calculateSubgroupHandicap(lessonDayOfWeekPreferenceElement?.dayOfWeek, lessonDayOfWeekPreferenceElement?.lessonId) }
//                }

           // }
    }

    fun calculateTimeAndPlace() {
        val timetablesToCalculate = container.timetablesFromCurriculum
            .filter { timetable -> timetable.lesson == null || timetable.dayOfWeek == null || timetable.place == null}

        timetablesToCalculate.forEach { timetableFromCurriculum ->
            calculateTakenLessonAndDay(timetableFromCurriculum)
            calculateTakenPlace(timetableFromCurriculum)


            val availablePlaces = timetableFromCurriculum.preference.preferredPlaceMap
                .filterValues { it.points > PreferenceHierarchy.CAN_BE_USED }


        }


// TOODO jestes pijanty, ale chyba cos zjebales, bo jak posortowac cos niezaleznie? Trzeba labo jakos pomyslec albo przy kazdym dopasowaniu przeliczac jeszcze raz. Elo, z fartem!
//
//        timetablesToCalculate.sortedBy { it.preferenceLessonAndDayOfWeekHierarchy.preferredPlaceMap }
//                val lessonDayOfWeekPreferenceElement = timetableFromCurriculum.preferenceLessonAndDayOfWeekHierarchy.preferredLessonAndDayOfWeekSet.maxBy { preferred -> preferred.preferenceLessonAndDayOfWeekHierarchy.pointsWithHandicap }
//                timetableFromCurriculum.lesson = container.lessons.find { it.id == lessonDayOfWeekPreferenceElement?.lessonId }
//                timetableFromCurriculum.dayOfWeek = lessonDayOfWeekPreferenceElement?.dayOfWeek

    }

    fun calculateTakenLessonAndDay() {
        container.timetablesFromCurriculum
            .forEach { timetableFromCurriculum ->
                calculateTakenLessonAndDay(timetableFromCurriculum)
            }
    }

    fun fillFinallyPoints() {
        container.timetablesFromCurriculum
            .forEach { timetableFromCurriculum ->
              //  timetableFromCurriculum.points = timetableFromCurriculum.preference.calculateFullPreferencePoints(timetableFromCurriculum)

            }
    }

    private fun sortByPreferredLessonAndDay() {
        container.timetablesFromCurriculum = container.timetablesFromCurriculum
            .sortedByDescending {
                it.preference.preferredLessonAndDayOfWeekSet
                 //   .map { it.preferenceLessonAndDayOfWeekHierarchy.pointsWithHandicap.absoluteValue }
                    .map { 1 }
                    .sum()
            }
            .toMutableList()
    }

    fun sortByPreferredPlace() {
        container.timetablesFromCurriculum = container.timetablesFromCurriculum
            .sortedByDescending {
                it.preference.preferredPlaceMap
                    .map { entry -> entry.value.points.absoluteValue }
                    .sum()
            }
            .toMutableList()
    }

    fun calculateTakenLessonAndDay(timetableFromCurriculum: Timetable) {
//        timetableFromCurriculum.preference.
//        val takenTimetables = container.timetablesFromCurriculum.filter { it != timetableFromCurriculum }.toSet()
//        timetableFromCurriculum.division?.let { timetableFromCurriculum.preference.calculateTakenLessonAndDayOfWeekByDivision(it, takenTimetables) }
//        timetableFromCurriculum.teacher?.let { timetableFromCurriculum.preference.calculateTakenLessonAndDayOfWeekByTeacher(it, takenTimetables) }
//        timetableFromCurriculum.place?.let { timetableFromCurriculum.preference.calculateTakenLessonAndDayOfWeekByPlace(it, takenTimetables) }
    }

    fun calculateTakenPlace(timetableFromCurriculum: Timetable) {
//        timetableFromCurriculum.preference.calculateTakenPlace(container.timetablesFromCurriculum
//            .filter { it.lesson?.id == timetableFromCurriculum.lesson?.id && it.dayOfWeek == timetableFromCurriculum.dayOfWeek }
//            .toSet())
    }

    fun calculateTakenPlaces() {
//        container.timetablesFromCurriculum.forEach { timetableFromCurriculum ->
//            timetableFromCurriculum.preference.calculateTakenPlace(container.timetablesFromCurriculum.filter { it.lesson?.id == timetableFromCurriculum.lesson?.id && it.dayOfWeek == timetableFromCurriculum.dayOfWeek }.toSet())
//        }
    }

    fun calculatePreference() {
        container.timetablesFromCurriculum.forEach {
            calculateAllPreference(it, container.places)
        }
    }

    private fun calculateAllPreference(timetable: Timetable, places: Set<Place>) {
        val tooSmallPlaceId = getTooSmallPlaceId(places, timetable.division)
        //timetable.preference.calculateAllForTimetable(timetable, tooSmallPlaceId)
    }

    private fun getTooSmallPlaceId(places: Set<Place>, division: Division?) = places.filter { place -> place.isPlaceTooSmallEnough(division) }.mapNotNull { it.id }.toSet()


}
