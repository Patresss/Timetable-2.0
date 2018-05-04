package com.patres.timetable.generator

import com.patres.timetable.domain.*
import com.patres.timetable.domain.preference.PreferenceDataTimeForPlace
import com.patres.timetable.preference.Preference
import com.patres.timetable.preference.hierarchy.PreferenceHierarchy
import com.patres.timetable.util.EntityUtil
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
        const val MAX_WINDOWS_REMOVE_ITERATOR = 50
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
            log.info("Iteration: $windowsRemoveCounter")
            calculateLessonAndDay()
            removeWidows()
        }

        calculateLessonAndDay()
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
            .filter { timetable -> timetable.place == null }
            .forEach { timetableFromCurriculum ->
                calculateTakenPlace(timetableFromCurriculum)
                val placeId = timetableFromCurriculum.preference.preferredPlaceMap.maxBy { preferred -> preferred.value?.points ?: 0 }?.key
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
        val takenTimetables = timetablesFromCurriculum.filter { it != timetableFromCurriculum }.toSet()
        timetableFromCurriculum.division?.let { timetableFromCurriculum.preference.calculateTakenLessonAndDayOfWeekByDivision(it, takenTimetables) }
        timetableFromCurriculum.teacher?.let { timetableFromCurriculum.preference.calculateTakenLessonAndDayOfWeekByTeacher(it, takenTimetables) }
        timetableFromCurriculum.place?.let { timetableFromCurriculum.preference.calculateTakenLessonAndDayOfWeekByPlace(it, takenTimetables) }
    }

    private fun calculateTakenPlace(timetableFromCurriculum: Timetable) {
        timetableFromCurriculum.preference.calculateTakenPlace(timetablesFromCurriculum.filter { it.lesson?.id == timetableFromCurriculum.lesson?.id && it.dayOfWeek == timetableFromCurriculum.dayOfWeek }.toSet())
    }

    private fun removeWidows() {
        timetablesFromCurriculum.sortBy { it.lesson?.startTime }
        val timetablesByDivision = timetablesFromCurriculum.groupBy { it.division }
        timetablesByDivision.forEach { division, timetables ->
            timetables.groupBy { it.dayOfWeek }.forEach { dayOfWeek, divisionTimetables ->
                if (division != null && dayOfWeek != null) {
                    var hasLesson = false
                    var lastLesson: Lesson? = null
                    lessons.forEach { lesson ->
                        val currentLesson = divisionTimetables.find { it.lesson == lesson }?.lesson
                        if (currentLesson == null && hasLesson) {
                            val lessonWithWindow = lesson

                            removeLessonAndDayFromTimetables(lastLesson, lessonWithWindow, division, dayOfWeek)
                        }
                        if (currentLesson != null) {
                            hasLesson = true
                            lastLesson = currentLesson
                        }
                    }
                }
            }
        }
    }


    private fun removeLessonAndDayFromTimetables(lastLesson: Lesson?, lessonWithWindow: Lesson, division: Division, dayOfWeek: Int) {
        val timetablesToRemoveAndSetHandicap = timetablesFromCurriculum
            .filter { it.lesson?.startTime ?: 1L > lastLesson?.startTime ?: 1L && it.division == division && it.dayOfWeek == dayOfWeek }
        if (timetablesToRemoveAndSetHandicap.isNotEmpty()) {
            log.debug("Remove windows for division: ${division.name} in: day of week: $dayOfWeek after lesson when start ${lastLesson?.getStartTimeHHmmFormatted()}")
            timetablesToRemoveAndSetHandicap
                .forEach { timetable ->
                    val preference = timetable.preference.getPreferenceByLessonAndDay(timetable.dayOfWeek, lessonWithWindow.id)?.preference
                    if (preference?.points ?: 0 > PreferenceHierarchy.CAN_BE_USED) {
                        preference?.let { it.windowHandicap += PreferenceHierarchy.HANDICAP }
                        timetable.lesson = null
                        timetable.dayOfWeek = null
                    } else {
                        timetable.dayOfWeek = dayOfWeek
                        timetable.lesson = lessonWithWindow
                        val swapLessonAndDay  = tryChangeLessonAndDay(division = division, timetableWithCollision = timetable)
                        if (!swapLessonAndDay) {
                            trySomethingsElse()
                        }
                    }

                }
        }
    }

    // TODO
    private fun trySomethingsElse() {

    }

    private fun tryChangeLessonAndDay(division: Division, timetableWithCollision: Timetable): Boolean {
        val divisionTimetable = timetablesFromCurriculum
            .filter { it.division == division && it.lesson != null && it.dayOfWeek != null && it != timetableWithCollision }
            .sortedBy {  it.preference.getPreferencePointsByLessonAndDay(it) }

        val timetableToSwap = divisionTimetable.find { timetableToTest -> canChangeLessonAndDay(timetableWithCollision, timetableToTest) }
        return if (timetableToSwap == null) {
            log.warn("Not found timetable to swap lesson and day")
            false
        } else {
            log.warn("Swap timetable: $timetableWithCollision <-> $timetableToSwap")
            swapLessonAndDayWithCalculatePreference(timetableWithCollision, timetableToSwap)
            true
        }
    }

    private fun canChangeLessonAndDay(timetableWithCollision: Timetable, timetableToTest: Timetable): Boolean {
        swapLessonAndDayWithCalculatePreference(timetableWithCollision, timetableToTest)
        val canSwap = isValidPointsAfterChangeLessonAndDay(timetableWithCollision, timetableToTest)
        swapLessonAndDayWithCalculatePreference(timetableWithCollision, timetableToTest)
        return canSwap
    }


    private fun swapLessonAndDayWithCalculatePreference(timetable1: Timetable, timetable2: Timetable) {
        EntityUtil.swapLessonAndDay(timetable1, timetable2)
        calculateTakenLessonAndDay(timetable1) // TODO Can we check smaller list?
        calculateTakenLessonAndDay(timetable2)
    }

    private fun isValidPointsAfterChangeLessonAndDay(timetableWithCollision: Timetable, timetableToTest: Timetable) = timetableWithCollision.preference.getPreferencePointsByLessonAndDay(timetableWithCollision) ?: 0 >= PreferenceHierarchy.CAN_BE_USED && timetableToTest.preference.getPreferencePointsByLessonAndDay(timetableToTest) ?: 0 >= PreferenceHierarchy.CAN_BE_USED

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
