package com.patres.timetable.generator

import com.patres.timetable.domain.*
import com.patres.timetable.preference.Preference
import com.patres.timetable.preference.hierarchy.PreferenceHierarchy


open class TimetableGeneratorContainer(
    private val curriculumListEntity: CurriculumList,
    private val places: Set<Place>,
    private val teachers: Set<Teacher>,
    private val subjects: Set<Subject>,
    private val divisions: Set<Division>,
    private val lessons: Set<Lesson>
) {

    private val curriculumList = curriculumListEntity.curriculums
    private val placesId = places.mapNotNull { it.id }.toSet()
    private val teachersId = teachers.mapNotNull { it.id }.toSet()
    private val subjectsId = subjects.mapNotNull { it.id }.toSet()
    private val divisionsId = divisions.mapNotNull { it.id }.toSet()
    private val lessonsId = lessons.mapNotNull { it.id }.toSet()
    private val timetablesFromCurriculum = ArrayList<Timetable>()

    init {
        lessons.sortedBy { it.startTime }
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
        calculateLessonAndDay()
        removeWidows()
        calculateLessonAndDay()
        removeWidows()
        calculateLessonAndDay()
        removeWidows()
        calculateLessonAndDay()
        return timetablesFromCurriculum
    }

    private fun calculateLessonAndDay() {
        sortByPreferredLessonAndDay()
        timetablesFromCurriculum
            .filter { timetable -> timetable.lesson == null || timetable.dayOfWeek == null }
            .forEach { timetableFromCurriculum ->
                val lessonDayOfWeekPreferenceElement = timetableFromCurriculum.preference.preferredLessonAndDayOfWeekSet.maxBy { preferred -> preferred.preference.pointsWithWindowHandicap }
                timetableFromCurriculum.lesson = lessons.find { it.id == lessonDayOfWeekPreferenceElement?.lessonId }
                timetableFromCurriculum.dayOfWeek = lessonDayOfWeekPreferenceElement?.dayOfWeek
                setTakenLessonAndDay(timetableFromCurriculum)
            }
    }

    private fun sortByPreferredLessonAndDay() {
        timetablesFromCurriculum.sortedBy { it.preference.preferredLessonAndDayOfWeekSet.maxBy { preferred -> preferred.preference.pointsWithWindowHandicap } }
    }

    private fun setTakenLessonAndDay(timetableFromCurriculum: Timetable) {
        timetablesFromCurriculum
            .filter { timetable -> timetableFromCurriculum != timetable }
            .forEach { timetable -> timetable.preference.getPreferenceByLessonAndDay(timetableFromCurriculum.dayOfWeek, timetableFromCurriculum.lesson?.id)?.preference?.setTakenByAll() }
    }

    private fun removeWidows() {
        timetablesFromCurriculum.sortBy { it.lesson?.startTime }
        timetablesFromCurriculum.groupBy { it.dayOfWeek }.forEach { dayOfWeek, timetable ->
            var hasLesson = false
            var lastLesson: Lesson? = null
            lessons.forEach { lesson ->
                val currentLesson = timetable.find { it.lesson == lesson }?.lesson
                if (currentLesson == null && hasLesson) {
                    removeLessonAndDayFromTimetables(timetable, lastLesson, dayOfWeek, lesson)
                }
                if (currentLesson != null) {
                    hasLesson = true
                    lastLesson = currentLesson
                }
            }

        }
    }

    private fun removeLessonAndDayFromTimetables(timetables: List<Timetable>, lastLesson: Lesson?, dayOfWeek: Int?, lesson: Lesson) {
        timetables
            .filter { it.lesson?.startTime ?: 1L > lastLesson?.startTime ?: 1L }
            .forEach { timetable ->
                timetable.lesson = null
                timetable.dayOfWeek = null
                timetable.preference.getPreferenceByLessonAndDay(dayOfWeek, lesson.id)?.preference?.setFreeByAll()
                timetable.preference.getPreferenceByLessonAndDay(dayOfWeek, lesson.id)?.preference?.let { it.windowHandicap += PreferenceHierarchy.HANDICAP }
            }
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
