package com.patres.timetable.generator

import com.patres.timetable.domain.*
import com.patres.timetable.domain.preference.PreferenceDataTimeForPlace
import com.patres.timetable.preference.Preference
import com.patres.timetable.preference.hierarchy.PreferenceHierarchy
import org.slf4j.Logger
import org.slf4j.LoggerFactory


open class TimetableGeneratorContainer(
    private val curriculumListEntity: CurriculumList,
    private val places: Set<Place>,
    private val teachers: Set<Teacher>,
    private val subjects: Set<Subject>,
    private val divisions: Set<Division>,
    private var lessons: List<Lesson>,
    private var preferencesDataTimeForPlace: Set<PreferenceDataTimeForPlace>
) {

    companion object {
        const val MAX_WINDOWS_REMOVE_ITERATOR = 10
        val log: Logger = LoggerFactory.getLogger(TimetableGeneratorContainer::class.java)
    }

    private val curriculumList = curriculumListEntity.curriculums
    private val placesId = places.mapNotNull { it.id }.toSet()
    private val teachersId = teachers.mapNotNull { it.id }.toSet()
    private val subjectsId = subjects.mapNotNull { it.id }.toSet()
    private val divisionsId = divisions.mapNotNull { it.id }.toSet()
    private val lessonsId = lessons.mapNotNull { it.id }.toSet()
    private var timetablesFromCurriculum = mutableListOf<Timetable>()

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
        var windowsRemoveCounter = 0
        calculatePreference()
        while (timetablesFromCurriculum.count { it.dayOfWeek == null && it.lesson == null } != 0 && ++windowsRemoveCounter < MAX_WINDOWS_REMOVE_ITERATOR) {
            calculateLessonAndDay()
            removeWidows()
        }
        log.info("Remove iterate: $windowsRemoveCounter")


        calculatePlace()
        return timetablesFromCurriculum
    }

    private fun calculateLessonAndDay() {
        sortByPreferredLessonAndDay()
        timetablesFromCurriculum
            .filter { timetable -> timetable.lesson == null || timetable.dayOfWeek == null }
            .forEach { timetableFromCurriculum ->
                calculateTakenLessonAndDay(timetableFromCurriculum)
                val lessonDayOfWeekPreferenceElement = timetableFromCurriculum.preference.preferredLessonAndDayOfWeekSet.maxBy { preferred -> preferred.preference.pointsWithWindowHandicap }
                timetableFromCurriculum.lesson = lessons.find { it.id == lessonDayOfWeekPreferenceElement?.lessonId }
                timetableFromCurriculum.dayOfWeek = lessonDayOfWeekPreferenceElement?.dayOfWeek
                timetableFromCurriculum.points = timetableFromCurriculum.preference.getPreferenceByLessonAndDay(timetableFromCurriculum.dayOfWeek, timetableFromCurriculum.lesson?.id)?.preference?.points ?: 0
            }
    }

    private fun calculatePlace() {
        timetablesFromCurriculum.forEach { timetable ->
            val preferences = preferencesDataTimeForPlace.filter { it.lesson?.id == timetable.lesson?.id }.toSet()
            timetable.preference.calculatePlaceByLessonAndDayOfWeek(preferences)
        }

        sortByPreferredPlace()
        timetablesFromCurriculum
            .filter { timetable -> timetable.place == null}
            .forEach { timetableFromCurriculum ->
                calculateTakenPlace(timetableFromCurriculum)
                val placeId = timetableFromCurriculum.preference.preferredPlaceMap.maxBy { preferred -> preferred.value?.points?:0}?.key
                timetableFromCurriculum.place = places.find { it.id == placeId }
            }
    }


    private fun sortByPreferredLessonAndDay() {
        timetablesFromCurriculum = timetablesFromCurriculum.sortedByDescending { it.preference.preferredLessonAndDayOfWeekSet.maxBy { preferred -> preferred.preference.pointsWithWindowHandicap } }.toMutableList()
    }

    private fun sortByPreferredPlace() {
        timetablesFromCurriculum = timetablesFromCurriculum.sortedByDescending { it.preference.preferredPlaceMap.maxBy { entry -> entry.value.points }?.value?.points }.toMutableList()
    }

    private fun calculateTakenLessonAndDay(timetableFromCurriculum: Timetable) {
        timetableFromCurriculum.division?.let { timetableFromCurriculum.preference.calculateTakenLessonAndDayOfWeekByDivision(it, timetablesFromCurriculum.toSet()) }
        timetableFromCurriculum.teacher?.let { timetableFromCurriculum.preference.calculateTakenLessonAndDayOfWeekByTeacher(it, timetablesFromCurriculum.toSet()) }
        timetableFromCurriculum.place?.let { timetableFromCurriculum.preference.calculateTakenLessonAndDayOfWeekByPlace(it, timetablesFromCurriculum.toSet()) }
    }

    private fun calculateTakenPlace(timetableFromCurriculum: Timetable) {
        timetableFromCurriculum.preference.calculateTakenPlace( timetablesFromCurriculum.filter { it.lesson?.id == timetableFromCurriculum.lesson?.id && it.dayOfWeek == timetableFromCurriculum.dayOfWeek}.toSet())
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
