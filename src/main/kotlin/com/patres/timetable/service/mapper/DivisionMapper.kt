package com.patres.timetable.service.mapper

import com.patres.timetable.domain.Division
import com.patres.timetable.repository.*
import com.patres.timetable.service.dto.DivisionDTO
import com.patres.timetable.service.dto.preference.relation.PreferenceDataTimeForDivisionDTO
import com.patres.timetable.service.dto.preference.relation.PreferenceDivisionByPlaceDTO
import com.patres.timetable.service.dto.preference.relation.PreferenceSubjectByDivisionDTO
import com.patres.timetable.service.dto.preference.relation.PreferenceTeacherByDivisionDTO
import com.patres.timetable.service.mapper.preference.relation.PreferenceDataTimeForDivisionMapper
import com.patres.timetable.service.mapper.preference.relation.PreferenceDivisionByPlaceMapper
import com.patres.timetable.service.mapper.preference.relation.PreferenceSubjectByDivisionMapper
import com.patres.timetable.service.mapper.preference.relation.PreferenceTeacherByDivisionMapper
import com.patres.timetable.util.EntityUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.DayOfWeek

@Service
open class DivisionMapper : EntityMapper<Division, DivisionDTO>() {


    @Autowired
    private lateinit var lessonRepository: LessonRepository

    @Autowired
    private lateinit var placeRepository: PlaceRepository

    @Autowired
    private lateinit var divisionRepository: DivisionRepository

    @Autowired
    private lateinit var preferenceDataTimeForDivisionMapper: PreferenceDataTimeForDivisionMapper

    @Autowired
    private lateinit var preferenceTeacherByDivisionMapper: PreferenceTeacherByDivisionMapper

    @Autowired
    private lateinit var preferenceSubjectByDivisionMapper: PreferenceSubjectByDivisionMapper

    @Autowired
    private lateinit var preferenceDivisionByPlaceMapper: PreferenceDivisionByPlaceMapper

    @Autowired
    private lateinit var teacherRepository: TeacherRepository

    @Autowired
    private lateinit var subjectRepository: SubjectRepository


    override fun toEntity(entityDto: DivisionDTO): Division {
        return Division(
            name = entityDto.name,
            divisionType = entityDto.divisionType)
            .apply {
                id = entityDto.id
                shortName = entityDto.shortName
                numberOfPeople = entityDto.numberOfPeople
                colorBackground = entityDto.colorBackground
                colorText = entityDto.colorText
                parents = entityDTOSetToEntitySet(entityDto.parents)
                divisionOwner = entityDto.divisionOwnerId?.let { divisionRepository.findOne(it) }
                preferencesTeacherByDivision = preferenceTeacherByDivisionMapper.entityDTOSetToEntitySet(entityDto.preferencesTeacherByDivision)
                preferencesSubjectByDivision = preferenceSubjectByDivisionMapper.entityDTOSetToEntitySet(entityDto.preferencesSubjectByDivision)
                preferencesDateTimeForDivision = preferenceDataTimeForDivisionMapper.entityDTOSetToEntitySet(entityDto.preferencesDataTimeForDivision)
                preferenceDivisionByPlace = preferenceDivisionByPlaceMapper.entityDTOSetToEntitySet(entityDto.preferenceDivisionByPlace)

                preferencesTeacherByDivision.forEach { it.division = this }
                preferencesSubjectByDivision.forEach { it.division = this }
                preferencesDateTimeForDivision.forEach { it.division = this }
                preferenceDivisionByPlace.forEach { it.division = this }


                if (colorBackground.isNullOrBlank()) {
                    colorBackground = EntityUtil.calculateRandomColor()
                }

            }
    }

    override fun toDto(entity: Division): DivisionDTO {
        val divisionDTO = DivisionDTO(
            name = entity.name,
            divisionType = entity.divisionType)
            .apply {
                id = entity.id
                shortName = entity.shortName
                numberOfPeople = entity.numberOfPeople
                colorBackground = entity.colorBackground
                colorText = entity.colorText
                divisionOwnerId = getDivisionOwnerId(entity.divisionOwner)
                divisionOwnerName = getDivisionOwnerName(entity.divisionOwner)

                preferencesTeacherByDivision = preferenceTeacherByDivisionMapper.entitySetToEntityDTOSet(entity.preferencesTeacherByDivision)
                addNeutralPreferenceTeacherByDivision()
                preferencesSubjectByDivision = preferenceSubjectByDivisionMapper.entitySetToEntityDTOSet(entity.preferencesSubjectByDivision)
                addNeutralPreferenceSubjectByDivision()
                preferenceDivisionByPlace = preferenceDivisionByPlaceMapper.entitySetToEntityDTOSet(entity.preferenceDivisionByPlace)
                addNeutralPreferenceDivisionByPlace()
                preferencesDataTimeForDivision = preferenceDataTimeForDivisionMapper.entitySetToEntityDTOSet(entity.preferencesDateTimeForDivision)
                addNeutralPreferencesDataTime()
            }

        val divisionDtoSet = entitySetToEntityDTOSet(entity.parents)
        divisionDTO.parents = divisionDtoSet
        return divisionDTO
    }

    override fun toDtoWithSampleForm(entity: Division): DivisionDTO {
        return DivisionDTO(
            name = entity.name,
            divisionType = entity.divisionType
        ).apply {
            id = entity.id
            shortName = entity.shortName
            numberOfPeople = entity.numberOfPeople
            divisionOwnerId = getDivisionOwnerId(entity.divisionOwner)
            divisionOwnerName = getDivisionOwnerName(entity.divisionOwner)
        }
    }

    fun getDivisionOwnerId(divisionOwner: Division?): Long? {
        if (divisionOwner == null) {
            return null
        }
        return divisionOwner.id
    }

    fun getDivisionOwnerName(divisionOwner: Division?): String? {
        if (divisionOwner == null) {
            return null
        }
        return divisionOwner.name
    }

    private fun DivisionDTO.addNeutralPreferenceSubjectByDivision() {
        divisionOwnerId?.let {
            val subjects = subjectRepository.findByDivisionOwnerId(it)
            val neutralPreferenceToAdd =
                subjects
                    .filter { subject -> !preferencesSubjectByDivision.any { preference -> id == preference.divisionId && subject.id == preference.subjectId } }
                    .map { subject -> PreferenceSubjectByDivisionDTO(divisionId = id, divisionName = name ?: "", subjectId = subject.id, subjectName = subject.name ?: "") }
            preferencesSubjectByDivision += neutralPreferenceToAdd
            preferencesSubjectByDivision = preferencesSubjectByDivision.sortedBy { it.subjectName }.toSet()
        }
    }

    private fun DivisionDTO.addNeutralPreferenceTeacherByDivision() {
        divisionOwnerId?.let {
            val teachers = teacherRepository.findByDivisionOwnerId(it)
            val neutralPreferenceToAdd =
                teachers
                    .filter { teacher -> !preferencesTeacherByDivision.any { preference -> id == preference.divisionId && teacher.id == preference.teacherId } }
                    .map { teacher -> PreferenceTeacherByDivisionDTO(divisionId = id, divisionName = name ?: "", teacherId = teacher.id, teacherFullName = teacher.getFullName(), teacherDegree = teacher.degree ?: "", teacherName = teacher.name ?: "", teacherSurname = teacher.surname ?: "") }
            preferencesTeacherByDivision += neutralPreferenceToAdd
            preferencesTeacherByDivision = preferencesTeacherByDivision.sortedWith(compareBy({ it.teacherSurname }, { it.teacherName }, { it.teacherDegree })).toSet()
        }
    }

    private fun DivisionDTO.addNeutralPreferencesDataTime() {
        val neutralPreferenceDataTimeForToAdd = HashSet<PreferenceDataTimeForDivisionDTO>()
        divisionOwnerId?.let {
            val lessons = lessonRepository.findByDivisionOwnerId(it)
            val daysOfWeeks = DayOfWeek.values().map { it.value }
            for (lesson in lessons) {
                for (dayOfWeek in daysOfWeeks) {
                    if (!preferencesDataTimeForDivision.any { it.dayOfWeek == dayOfWeek && it.lessonId == lesson.id }) {
                        neutralPreferenceDataTimeForToAdd.add(PreferenceDataTimeForDivisionDTO(divisionId = id, divisionName = name ?: "", lessonId = lesson.id, lessonName = lesson.name ?: "", dayOfWeek = dayOfWeek, points = 0))
                    }
                }
            }
            preferencesDataTimeForDivision += neutralPreferenceDataTimeForToAdd
            preferencesDataTimeForDivision = preferencesDataTimeForDivision.sortedBy { it.dayOfWeek }.toSet()
        }
    }


    private fun DivisionDTO.addNeutralPreferenceDivisionByPlace() {
        divisionOwnerId?.let {
            val places = placeRepository.findByDivisionOwnerId(it)
            val neutralPreferenceToAdd =
                places
                    .filter { place -> !preferenceDivisionByPlace.any { preference -> id == preference.divisionId && place.id == preference.placeId } }
                    .map { place -> PreferenceDivisionByPlaceDTO(placeId = place.id, placeName = place.name ?: "", divisionId = id, divisionName = name ?: "") }
            preferenceDivisionByPlace += neutralPreferenceToAdd
            preferenceDivisionByPlace = preferenceDivisionByPlace.sortedBy { it.placeName }.toSet()
        }
    }


}
