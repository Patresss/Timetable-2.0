package com.patres.timetable.generator

import com.patres.timetable.domain.*
import com.patres.timetable.domain.enumeration.DivisionType
import com.patres.timetable.domain.preference.PreferenceDataTimeForPlace
import com.patres.timetable.excpetion.ApplicationException
import com.patres.timetable.excpetion.ExceptionMessage
import com.patres.timetable.generator.algorithm.TimetableGeneratorHandicapInWindowsAlgorithm
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
    private var preferencesDataTimeForPlace: Set<PreferenceDataTimeForPlace> = emptySet()
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
        val generateTimeImMs = measureTimeMillis {
            preferenceManager.calculatePreference()
            preferenceManager.calculateLessonAndDay()

            handicapInWindowsAlgorithm.run()
            swapInWindowAlgorithm.run()

            calculatePlace()
        }
        val windows = findWidows()
        TimetableGeneratorContainer.log.info("Final number of windows: ${windows.size}")
        preferenceManager.fillFinallyPoints()
        return GenerateReport(timetables = timetablesFromCurriculum, generateTimeImMs = generateTimeImMs, windows = windows.toList())
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

    fun findAndSetupTheBiggestGroups(): Set<BlockWithoutWindow> {
        val blocks = HashSet<BlockWithoutWindow>()
        val sortedTimetables = timetablesFromCurriculum.sortedBy { it.lesson?.startTime }
        val timetablesByDivision = sortedTimetables.groupBy { it.division }
        timetablesByDivision.forEach { _, timetables ->
            blocks.addAll(findAndSetupTheBiggestGroupsByDivision(timetables))
        }
        return blocks
    }


    fun findAndSetupTheBiggestGroupsByDivision(timetablesWithThisSameDivision: List<Timetable>): Set<BlockWithoutWindow> {
        if (timetablesWithThisSameDivision.isEmpty()) {
            return emptySet()
        } else if (timetablesWithThisSameDivision.any { timetablesWithThisSameDivision.first().division != it.division }) {
            throw ApplicationException(ExceptionMessage.TIMETABLES_MUST_HAVE_THIS_SAME_DIVISION)
        }

        val groups = HashSet<BlockWithoutWindow>()
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

    private fun setupAndReturnTheBiggestGroup(divisionTimetables: List<Timetable>): BlockWithoutWindow? {
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
        var currentBlock = BlockWithoutWindow()
        for (lesson in lessons) {
            val currentTimetable = timetableWithThisSameDayAndDivision.find { it.lesson == lesson }
            if (currentTimetable != null) {
                currentBlock.timetables.add(currentTimetable)
            } else if (!currentBlock.timetables.isEmpty()) {
                groupOfBlockWithoutWindowByDayOfWeek.add(currentBlock)
                currentBlock = BlockWithoutWindow()
            }

            if (currentTimetable != null && lessons.last() == lesson) {
                groupOfBlockWithoutWindowByDayOfWeek.add(currentBlock)
            }
        }
        return groupOfBlockWithoutWindowByDayOfWeek
    }


}
