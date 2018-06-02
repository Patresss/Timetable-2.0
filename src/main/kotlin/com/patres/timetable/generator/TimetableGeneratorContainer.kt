package com.patres.timetable.generator

import com.patres.timetable.domain.*
import com.patres.timetable.domain.preference.PreferenceDataTimeForPlace
import com.patres.timetable.excpetion.ApplicationException
import com.patres.timetable.excpetion.ExceptionMessage
import com.patres.timetable.generator.algorithm.TimetableGeneratorAlgorithm
import com.patres.timetable.generator.algorithm.TimetableGeneratorHandicapInWindowsAlgorithm
import com.patres.timetable.generator.algorithm.TimetableGeneratorHandicapNearToBlockAlgorithm
import com.patres.timetable.generator.algorithm.TimetableGeneratorSwapInWindowAlgorithm
import com.patres.timetable.generator.report.GenerateReport
import com.patres.timetable.preference.Preference
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.system.measureTimeMillis


class TimetableGeneratorContainer(
    val curriculumListEntity: CurriculumList = CurriculumList(),
    val places: Set<Place> = emptySet(),
    val teachers: Set<Teacher> = emptySet(),
    val subjects: Set<Subject> = emptySet(),
    val divisions: Set<Division> = emptySet(),
    var lessons: Set<Lesson> = emptySet(),
    var preferencesDataTimeForPlace: Set<PreferenceDataTimeForPlace> = emptySet()
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
    private val handicapNearToBlockAlgorithm = TimetableGeneratorHandicapNearToBlockAlgorithm(this)
    private val swapInWindowAlgorithm = TimetableGeneratorSwapInWindowAlgorithm(this)


    init {
        lessons = lessons.sortedBy { it.startTime }.toSet()
        curriculumList.forEach { curriculum ->
            (1..curriculum.numberOfActivities).forEach {
                timetablesFromCurriculum.add(Timetable(curriculum, curriculumListEntity.period))
            }
        }
        timetablesFromCurriculum.forEach {
            it.preference = Preference(teachersId = teachersId, subjectsId = subjectsId, divisionsId = divisionsId, lessonsId = lessonsId, placesId = placesId)
        }
    }

    fun generate(): GenerateReport {
        var numberOfHandicapAlgorithmIterations = 0
        var numberOfSwapAlgorithmIterations = 0
        var numberOfHandicapNearToBlockAlgorithmIterations = 0

        var windows = findWidows()
        var globalIterations = 1
        var placeIterations = 1

        val changeWindowDetector = ChangeDetector()
        val changePlaceDetector = ChangeDetector()

        var generateTimeHandicapInWindowsAlgorithmImMs = 0L
        var generateTimeSwapInWindowAlgorithmImMs = 0L
        var generateTimeHandicapNearToBlockAlgorithmImMs = 0L
        var calculatePreferenceTimeImMs = 0L
        val generateTimeImMs = measureTimeMillis {

            calculatePreferenceTimeImMs = measureTimeMillis {
                preferenceManager.calculatePreference()
            }
            preferenceManager.calculateLessonAndDay()

            windows = findWidows()
            TimetableGeneratorContainer.log.info("Number of windows at the beginning: ${windows.size}")
            do {
                do {
                    TimetableGeneratorContainer.log.info("Iteration: $globalIterations")

                    generateTimeHandicapInWindowsAlgorithmImMs += measureTimeMillis {
                        numberOfHandicapAlgorithmIterations += handicapInWindowsAlgorithm.run()
                    }
                    windows = findWidows()
                    TimetableGeneratorContainer.log.info("Number of windows after numberOfHandicapAlgorithmIterations: ${windows.size}")

                    if (windows.isNotEmpty()) {
                        generateTimeSwapInWindowAlgorithmImMs += measureTimeMillis {
                            numberOfSwapAlgorithmIterations += swapInWindowAlgorithm.run()
                        }
                        windows = findWidows()
                        TimetableGeneratorContainer.log.info("Number of windows after swapInWindowAlgorithm: ${windows.size}")
                    }

                    if (windows.isNotEmpty()) {
                        generateTimeHandicapNearToBlockAlgorithmImMs += measureTimeMillis {
                            numberOfHandicapNearToBlockAlgorithmIterations += handicapNearToBlockAlgorithm.run()
                        }
                        windows = findWidows()
                        TimetableGeneratorContainer.log.info("Number of windows after handicapNearToBlockAlgorithm: ${windows.size}")
                    }
                    changeWindowDetector.updateValue(windows.size)
                } while ((windows.isNotEmpty() && ++globalIterations < TimetableGeneratorAlgorithm.MAX_ITERATIONS) && changeWindowDetector.hasChange())
                calculatePlace()
                val timetablesWithoutPlace = timetablesFromCurriculum.filter { it.place == null }
                timetablesWithoutPlace.forEach {
                    it.dayOfWeek = null
                    it.lesson = null
                }
                changePlaceDetector.updateValue(timetablesWithoutPlace.size)

            } while ((timetablesWithoutPlace.isNotEmpty() && ++placeIterations < TimetableGeneratorAlgorithm.MAX_ITERATIONS) && changePlaceDetector.hasChange())

        }


        TimetableGeneratorContainer.log.info("Final number of windows: ${windows.size}")
        preferenceManager.calculateLessonAndDay()
        preferenceManager.fillFinallyPoints()
        return GenerateReport(
            timetables = timetablesFromCurriculum,
            generateTimeImMs = generateTimeImMs,
            windows = windows.toList(),
            numberOfHandicapAlgorithmIterations = numberOfHandicapAlgorithmIterations,
            numberOfSwapAlgorithmIterations = numberOfSwapAlgorithmIterations,
            numberOfHandicapNearToBlockAlgorithmIterations = numberOfHandicapNearToBlockAlgorithmIterations,
            generateTimeHandicapInWindowsAlgorithmImMs = generateTimeHandicapInWindowsAlgorithmImMs,
            generateTimeSwapInWindowAlgorithmImMs = generateTimeSwapInWindowAlgorithmImMs,
            generateTimeHandicapNearToBlockAlgorithmImMs = generateTimeHandicapNearToBlockAlgorithmImMs,
            calculatePreferenceTimeImMs = calculatePreferenceTimeImMs,
            globalIterations = globalIterations
        )
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

    fun findWidows(): Set<Window> {
        val windows = HashSet<Window>()
        val sortedTimetables = timetablesFromCurriculum.sortedBy { it.lesson?.startTime }
        val timetablesByDivision = sortedTimetables.groupBy { it.division }
        timetablesByDivision.forEach { division, timetables ->

            // TODO metoda powinna szuka wszysktich timetable  tam gdzie są wszystkie podgrupy p fran | ros | niem wchodzi ale bez jednego już nie
//            val subgroupTimetables = HashSet<Timetable>()
//            division?.calculateContainersWithSetOfSubgroup()
//                ?.forEach { setOfSubgroup ->
//                    val numberOfSubgroup = setOfSubgroup.children.size
//                    sortedTimetables
//                        .filter { timetable -> setOfSubgroup.children.contains(timetable.division) }
//                        .groupBy { it.dayOfWeek }
//                        .forEach { dayOfWeek, timetables ->
//                            val timetablesInLessonAndDateSize = timetables
//                                .groupBy { it.lesson }
//                                .size
//                            if (timetablesInLessonAndDateSize == numberOfSubgroup) {
//                                subgroupTimetables.addAll(timetables)
//                            }
//                        }
//
//            }


            windows.addAll(findWindowsByDivision(timetables, division))
        }
        return windows
    }

    private fun findWindowsByDivision(timetables: List<Timetable>, division: Division?): Set<Window> {
        val windows = HashSet<Window>()
        timetables.groupBy { it.dayOfWeek }.forEach { dayOfWeek, divisionTimetables ->
            if (division != null && dayOfWeek != null) {
                for (lesson in lessons) {
                    val currentLesson = divisionTimetables.find { it.lesson == lesson }?.lesson
                    if (currentLesson == null && divisionTimetables.any { lesson.startTime ?: 0 > it.lesson?.endTime ?: 0 } && divisionTimetables.any { lesson.endTime ?: 0 < it.lesson?.startTime ?: 0 }) {
                        windows.add(Window(dayOfWeek, lesson, division))
                    }
                }
            }
        }
        return windows
    }

    fun findAndSetupTheBiggestGroups(): Set<BlockWithTimetable> {
        val blocks = HashSet<BlockWithTimetable>()
        val sortedTimetables = timetablesFromCurriculum.sortedBy { it.lesson?.startTime }
        val timetablesByDivision = sortedTimetables.groupBy { it.division }
        timetablesByDivision.forEach { _, timetables ->
            blocks.addAll(findAndSetupTheBiggestGroupsByDivision(timetables))
        }
        return blocks
    }


    fun findAndSetupTheBiggestGroupsByDivision(timetablesWithThisSameDivision: List<Timetable>): Set<BlockWithTimetable> {
        if (timetablesWithThisSameDivision.isEmpty()) {
            return emptySet()
        } else if (timetablesWithThisSameDivision.any { timetablesWithThisSameDivision.first().division != it.division }) {
            throw ApplicationException(ExceptionMessage.TIMETABLES_MUST_HAVE_THIS_SAME_DIVISION)
        }

        val groups = HashSet<BlockWithTimetable>()
        timetablesWithThisSameDivision
            .groupBy { it.dayOfWeek }
            .filterKeys { it != null }
            .forEach { _, divisionTimetables ->
                // TODO metoda powinna szuka wszysktich timetable  tam gdzie są wszystkie podgrupy p fran | ros | niem wchodzi ale bez jednego już nie
//                val subgroupTimetables = HashSet<Timetable>()
//                val sortedTimetables = timetablesFromCurriculum.sortedBy { it.lesson?.startTime }
//                timetablesWithThisSameDivision.first().division?.calculateContainersWithSetOfSubgroup()
//                    ?.forEach { setOfSubgroup ->
//                        val numberOfSubgroup = setOfSubgroup.children.size
//                        sortedTimetables
//                            .filter { timetable -> setOfSubgroup.children.contains(timetable.division) }
//                            .groupBy { it.dayOfWeek }
//                            .forEach { dayOfWeek, timetables ->
//                                val timetablesInLessonAndDateSize = timetables
//                                    .groupBy { it.lesson }
//                                    .size
//                                if (timetablesInLessonAndDateSize == numberOfSubgroup) {
//                                    subgroupTimetables.addAll(timetables)
//                                }
//                            }
//
//                    }

                setupAndReturnTheBiggestGroup(divisionTimetables)?.let { groups.add(it) }
            }
        return groups
    }

    private fun setupAndReturnTheBiggestGroup(divisionTimetables: List<Timetable>): BlockWithTimetable? {
        val groupOfBlock = findGroupsFromTimetablesWithThisSameDayAndDivision(divisionTimetables)
        val theBiggestGroup = groupOfBlock.getTheBiggestGroup()
        theBiggestGroup?.let {
            groupOfBlock.setupBlock(theBiggestGroup)
            return theBiggestGroup
        }
        return null
    }

    fun findGroupsFromTimetablesWithThisSameDayAndDivision(timetableWithThisSameDayAndDivision: List<Timetable>): GroupOfBlockWithoutWindow {
        if (timetableWithThisSameDayAndDivision.isEmpty()) {
            return GroupOfBlockWithoutWindow()
        } else if (timetableWithThisSameDayAndDivision.any { timetableWithThisSameDayAndDivision.first().dayOfWeek != it.dayOfWeek || timetableWithThisSameDayAndDivision.first().division != it.division }) {
            throw ApplicationException(ExceptionMessage.TIMETABLES_MUST_HAVE_THIS_SAME_DAY_AND_DIVISION)
        }

        val groupOfBlockWithoutWindowByDayOfWeek = GroupOfBlockWithoutWindow()
        var currentBlock = BlockWithTimetable()
        for (lesson in lessons) {
            val currentTimetable = timetableWithThisSameDayAndDivision.find { it.lesson == lesson }
            if (currentTimetable != null) {
                currentBlock.timetables.add(currentTimetable)
            } else if (!currentBlock.timetables.isEmpty()) {
                groupOfBlockWithoutWindowByDayOfWeek.add(currentBlock)
                currentBlock = BlockWithTimetable()
            }

            if (currentTimetable != null && lessons.last() == lesson) {
                groupOfBlockWithoutWindowByDayOfWeek.add(currentBlock)
            }
        }
        return groupOfBlockWithoutWindowByDayOfWeek
    }


}
