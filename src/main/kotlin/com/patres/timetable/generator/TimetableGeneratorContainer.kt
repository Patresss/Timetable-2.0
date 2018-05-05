package com.patres.timetable.generator

import com.patres.timetable.domain.*
import com.patres.timetable.domain.preference.PreferenceDataTimeForPlace
import com.patres.timetable.generator.algorithm.TimetableGeneratorHandicapInWindowsAlgorithm
import com.patres.timetable.generator.algorithm.TimetableGeneratorSwapInWindowAlgorithm
import com.patres.timetable.preference.Preference
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class TimetableGeneratorContainer(
    val curriculumListEntity: CurriculumList,
    val places: Set<Place>,
    val teachers: Set<Teacher>,
    val subjects: Set<Subject>,
    val divisions: Set<Division>,
    var lessons: List<Lesson>,
    private var preferencesDataTimeForPlace: Set<PreferenceDataTimeForPlace>
) {

    companion object {
        val log: Logger = LoggerFactory.getLogger(TimetableGeneratorContainer::class.java)
    }

    private val curriculumList = curriculumListEntity.curriculums
    private val placesId = places.mapNotNull { it.id }.toSet()
    private val teachersId = teachers.mapNotNull { it.id }.toSet()
    private val subjectsId = subjects.mapNotNull { it.id }.toSet()
    private val divisionsId = divisions.mapNotNull { it.id }.toSet()
    private val lessonsId = lessons.mapNotNull { it.id }.toSet()

    var timetablesFromCurriculum = mutableListOf<Timetable>()

    val preferenceManager = TimetableGeneratorPreferenceManager(this)
    private val handicapInWindowsAlgorithm = TimetableGeneratorHandicapInWindowsAlgorithm(this)
    private val swapInWindowAlgorithm = TimetableGeneratorSwapInWindowAlgorithm(this)


    init {
        lessons = lessons.sortedBy { it.startTime }
        curriculumList.forEach { curriculum ->
            (1..curriculum.numberOfActivities).forEach {
                timetablesFromCurriculum.add(Timetable(curriculum, curriculumListEntity.period))
            }
        }
        timetablesFromCurriculum.forEach {
            it.preference = Preference(teachersId = teachersId, subjectsId = subjectsId, divisionsId = divisionsId, lessonsId = lessonsId, placesId = placesId)
        }
    }

    fun generate(): List<Timetable> {
        preferenceManager.calculatePreference()
        preferenceManager.calculateLessonAndDay()

        handicapInWindowsAlgorithm.run()
        swapInWindowAlgorithm.run()

        calculatePlace()
        return timetablesFromCurriculum
    }


    private fun calculatePlace() {
        timetablesFromCurriculum.forEach { timetable ->
            val preferences = preferencesDataTimeForPlace.filter { it.lesson?.id == timetable.lesson?.id }.toSet()
            timetable.preference.calculatePlaceByLessonAndDayOfWeek(preferences)
        }

        preferenceManager.sortByPreferredPlace()
        timetablesFromCurriculum
            .filter { timetable -> timetable.place == null }
            .forEach { timetableFromCurriculum ->
                preferenceManager.calculateTakenPlace(timetableFromCurriculum)
                val placeId = timetableFromCurriculum.preference.preferredPlaceMap.maxBy { preferred -> preferred.value?.points ?: 0 }?.key
                timetableFromCurriculum.place = places.find { it.id == placeId }
            }
    }


}
