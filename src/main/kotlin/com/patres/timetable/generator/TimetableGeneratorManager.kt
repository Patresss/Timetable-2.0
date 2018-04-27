package com.patres.timetable.generator

import com.patres.timetable.domain.Division
import com.patres.timetable.domain.Place
import com.patres.timetable.domain.Timetable
import com.patres.timetable.preference.Preference
import com.patres.timetable.preference.PreferenceManager
import com.patres.timetable.repository.*
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext


@Component
open class TimetableGeneratorManager(
    private var preferenceManager: PreferenceManager,
    private var placeRepository: PlaceRepository,
    private var teacherRepository: TeacherRepository,
    private var subjectRepository: SubjectRepository,
    private var divisionRepository: DivisionRepository,
    private var lessonRepository: LessonRepository,
    private var curriculumListRepository: CurriculumListRepository,
    private var timetableRepository: TimetableRepository) {

    @PersistenceContext
    var entityManager: EntityManager? = null

    @Transactional
    open fun generate(curriculumListId: Long, generatorStrategyType: TimetableGeneratorStrategyType): List<Timetable> {
        val curriculumListEntity = curriculumListRepository.findOneWithEagerRelationships(curriculumListId)
        val curriculumList = curriculumListEntity?.curriculums?: emptySet()
        val schoolId = curriculumListEntity?.divisionOwner?.id

        val timetablesFromCurriculum = schoolId?.let {
            val places = placeRepository.findByDivisionOwnerId(schoolId)
            val placesId = places.mapNotNull { it.id }.toSet()
            val teachers = teacherRepository.findByDivisionOwnerId(schoolId)
            val teachersId = teachers.mapNotNull { it.id }.toSet()
            val subjects = subjectRepository.findByDivisionOwnerId(schoolId)
            val subjectsId = subjects.mapNotNull { it.id }.toSet()
            val divisions = divisionRepository.findByDivisionOwnerId(schoolId)
            val divisionsId = divisions.mapNotNull { it.id }.toSet()
            val lessons = lessonRepository.findByDivisionOwnerId(schoolId)
            val lessonsId = lessons.mapNotNull { it.id }.toSet()
            val timetablesFromCurriculum = ArrayList<Timetable>()
            curriculumList.forEach {curriculum ->
                (1..curriculum.numberOfActivities).forEach {
                    timetablesFromCurriculum.add(Timetable(curriculum, curriculumListEntity.period))
                }
            }
            timetablesFromCurriculum.forEach {
                it.preference = Preference(teachersId = teachersId, subjectsId = subjectsId, divisionsId = divisionsId, lessonsId = lessonsId, placesId = placesId)
                calculateAllPreference(it, places)
            }

            timetablesFromCurriculum.forEach {
                val lessonDayOfWeekPreferenceElement = it.preference.preferredLessonAndDayOfWeekSet.maxBy { preferred -> preferred.preference.points }
                it.lesson = lessons.find { it.id == lessonDayOfWeekPreferenceElement?.lessonId  }
                it.dayOfWeek = lessonDayOfWeekPreferenceElement?.dayOfWeek
            }
            timetablesFromCurriculum
        }

        return timetablesFromCurriculum?: emptyList()
    }

    private fun calculateAllPreference(timetable: Timetable, places: Set<Place>) {
        val tooSmallPlaceId = getTooSmallPlaceId(places, timetable.division)
        preferenceManager.calculateAllForTimetable(timetable.preference, timetable, tooSmallPlaceId)
    }

    private fun getTooSmallPlaceId(places: Set<Place>, division: Division?) = places.filter { place -> place.isPlaceTooSmallEnough(division) }.mapNotNull { it.id }.toSet()

}
