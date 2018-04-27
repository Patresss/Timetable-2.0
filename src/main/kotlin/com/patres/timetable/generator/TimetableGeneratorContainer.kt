package com.patres.timetable.generator

import com.patres.timetable.domain.*
import com.patres.timetable.preference.Preference
import org.springframework.transaction.annotation.Transactional


open class TimetableGeneratorContainer(
    private var curriculumListEntity: CurriculumList,
    private var places: Set<Place>,
    private var teachers: Set<Teacher>,
    private var subjects: Set<Subject>,
    private var divisions: Set<Division>,
    private var lessons: Set<Lesson>
) {

    private val curriculumList = curriculumListEntity.curriculums
    private val placesId = places.mapNotNull { it.id }.toSet()
    private val teachersId = teachers.mapNotNull { it.id }.toSet()
    private val subjectsId = subjects.mapNotNull { it.id }.toSet()
    private val divisionsId = divisions.mapNotNull { it.id }.toSet()
    private val lessonsId = lessons.mapNotNull { it.id }.toSet()
    private val timetablesFromCurriculum = ArrayList<Timetable>()

    init {
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
        calculatePreference()
        timetablesFromCurriculum.forEach {timetableFromCurriculum ->
            val lessonDayOfWeekPreferenceElement = timetableFromCurriculum.preference.preferredLessonAndDayOfWeekSet.maxBy { preferred -> preferred.preference.points }
            timetableFromCurriculum.lesson = lessons.find { it.id == lessonDayOfWeekPreferenceElement?.lessonId }
            timetableFromCurriculum.dayOfWeek = lessonDayOfWeekPreferenceElement?.dayOfWeek
            setTakenLessonAndDay(timetableFromCurriculum)
        }
        return timetablesFromCurriculum
    }

    private fun setTakenLessonAndDay(timetableFromCurriculum: Timetable) {
        timetablesFromCurriculum
            .filter { timetable -> timetableFromCurriculum != timetable }
            .forEach { timetable -> timetable.preference.getPreferenceByLessonAndDay(timetableFromCurriculum.dayOfWeek, timetableFromCurriculum.lesson?.id)?.preference?.setTakenByAll() }
    }


    private fun calculatePreference() {
        timetablesFromCurriculum.forEach {
            calculateAllPreference(it, places)
        }
    }

    private fun calculateAllPreference(timetable: Timetable, places: Set<Place>) {
        val tooSmallPlaceId = getTooSmallPlaceId(places, timetable.division)
        timetable.preference.calculateAllForTimetable(timetable, tooSmallPlaceId)
    }

    private fun getTooSmallPlaceId(places: Set<Place>, division: Division?) = places.filter { place -> place.isPlaceTooSmallEnough(division) }.mapNotNull { it.id }.toSet()

}
