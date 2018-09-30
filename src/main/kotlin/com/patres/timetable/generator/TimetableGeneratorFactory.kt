package com.patres.timetable.generator

import com.patres.timetable.domain.Timetable
import com.patres.timetable.excpetion.ApplicationException
import com.patres.timetable.excpetion.ExceptionMessage
import com.patres.timetable.preference.PreferenceFactory
import com.patres.timetable.repository.CurriculumListRepository
import org.springframework.stereotype.Component

@Component
class TimetableGeneratorFactory(
    private var curriculumListRepository: CurriculumListRepository,
    private var preferenceFactory: PreferenceFactory
) {

    fun createGenerator(curriculumListId: Long): TimetableGenerator {
        val curriculumListEntity = curriculumListRepository.findOneWithEagerRelationships(curriculumListId) ?: throw ApplicationException(ExceptionMessage.CANNOT_FIND_CURRICULUM_LIST_BY_ID)
        val schoolId = curriculumListEntity.divisionOwner?.id ?: throw ApplicationException(ExceptionMessage.CANNOT_FIND_SCHOOL_ID)

        val schoolData = preferenceFactory.createSchoolDataToPreference(schoolId)

        val timetablesFromCurriculum = mutableListOf<Timetable>()
        val curriculumList = curriculumListEntity.curriculums
        curriculumList.forEach { curriculum ->
            (1..curriculum.numberOfActivities).forEach {
                timetablesFromCurriculum.add(Timetable(curriculum, curriculumListEntity.period))
            }
        }
        schoolData.takenTimetables = timetablesFromCurriculum
        val timetablesWithPreference = timetablesFromCurriculum.map { TimetableWithPreference(it, preferenceFactory.createGlobalPreference(schoolData, it)) }.toSet()
        return TimetableGenerator(schoolData, timetablesWithPreference)
    }

}
