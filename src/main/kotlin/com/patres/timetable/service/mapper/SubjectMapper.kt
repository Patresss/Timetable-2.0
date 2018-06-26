package com.patres.timetable.service.mapper

import com.patres.timetable.domain.Subject
import com.patres.timetable.repository.DivisionRepository
import com.patres.timetable.repository.LessonRepository
import com.patres.timetable.repository.PlaceRepository
import com.patres.timetable.service.dto.PlaceDTO
import com.patres.timetable.service.dto.SubjectDTO
import com.patres.timetable.service.dto.preference.PreferenceDataTimeForSubjectDTO
import com.patres.timetable.service.dto.preference.PreferenceSubjectByPlaceDTO
import com.patres.timetable.service.mapper.preference.PreferenceDataTimeForSubjectMapper
import com.patres.timetable.service.mapper.preference.PreferenceSubjectByPlaceMapper
import com.patres.timetable.util.EntityUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.DayOfWeek

@Service
open class SubjectMapper : EntityMapper<Subject, SubjectDTO>() {

    @Autowired
    private
    lateinit var divisionMapper: DivisionMapper

    @Autowired
    private lateinit var divisionRepository: DivisionRepository

    @Autowired
    private lateinit var lessonRepository: LessonRepository

    @Autowired
    private lateinit var placeRepository: PlaceRepository

    @Autowired
    private lateinit var preferenceDataTimeForSubjectMapper: PreferenceDataTimeForSubjectMapper

    @Autowired
    private lateinit var preferenceSubjectByPlaceMapper: PreferenceSubjectByPlaceMapper

    override fun toEntity(entityDto: SubjectDTO): Subject {
        return Subject(
            name = entityDto.name
        ).apply {
            divisionOwner = entityDto.divisionOwnerId?.let { divisionRepository.getOne(it) }
            id = entityDto.id
            shortName = entityDto.shortName
            colorBackground = entityDto.colorBackground
            colorText = entityDto.colorText
            preferencesDateTimeForSubject = preferenceDataTimeForSubjectMapper.entityDTOSetToEntitySet(entityDto.preferencesDataTimeForSubject)
            preferenceSubjectByPlace = preferenceSubjectByPlaceMapper.entityDTOSetToEntitySet(entityDto.preferenceSubjectByPlace)

            preferencesDateTimeForSubject.forEach { it.subject = this }
            preferenceSubjectByPlace.forEach { it.subject = this }

            if (colorBackground.isNullOrBlank()) {
                colorBackground = EntityUtil.calculateRandomColor()
            }
        }
    }

    override fun toDto(entity: Subject): SubjectDTO {
        return SubjectDTO(
            name = entity.name
        ).apply {
            divisionOwnerId = divisionMapper.getDivisionOwnerId(entity.divisionOwner)
            divisionOwnerName = divisionMapper.getDivisionOwnerName(entity.divisionOwner)
            id = entity.id
            shortName = if (entity.shortName != null) entity.shortName else getShortName(name)
            colorBackground = entity.colorBackground
            colorText = entity.colorText
            preferencesDataTimeForSubject = preferenceDataTimeForSubjectMapper.entitySetToEntityDTOSet(entity.preferencesDateTimeForSubject)
            addNeutralPreferencesDataTime()
            preferenceSubjectByPlace = preferenceSubjectByPlaceMapper.entitySetToEntityDTOSet(entity.preferenceSubjectByPlace)
            addNeutralPreferenceSubjectByPlace()
        }
    }

    override fun toDtoWithSampleForm(entity: Subject): SubjectDTO {
        return SubjectDTO(
            name = entity.name
        ).apply {
            divisionOwnerId = divisionMapper.getDivisionOwnerId(entity.divisionOwner)
            divisionOwnerName = divisionMapper.getDivisionOwnerName(entity.divisionOwner)
            id = entity.id
            shortName = if (entity.shortName != null) entity.shortName else getShortName(name)
            colorBackground = entity.colorBackground
        }
    }


    private fun SubjectDTO.addNeutralPreferencesDataTime() {
        val neutralPreferenceDataTimeForToAdd = HashSet<PreferenceDataTimeForSubjectDTO>()
        divisionOwnerId?.let {
            val lessons = lessonRepository.findByDivisionOwnerId(it)
            val daysOfWeeks = DayOfWeek.values().map { it.value }
            for (lesson in lessons) {
                for (dayOfWeek in daysOfWeeks) {
                    if (!preferencesDataTimeForSubject.any { it.dayOfWeek == dayOfWeek && it.lessonId == lesson.id }) {
                        neutralPreferenceDataTimeForToAdd.add(PreferenceDataTimeForSubjectDTO(subjectId = id, subjectName = name ?: "", lessonId = lesson.id, lessonName = lesson.name ?: "", dayOfWeek = dayOfWeek, points = 0))
                    }
                }
            }
            preferencesDataTimeForSubject += neutralPreferenceDataTimeForToAdd
            preferencesDataTimeForSubject = preferencesDataTimeForSubject.sortedBy { it.dayOfWeek }.toSet()
        }
    }

    private fun SubjectDTO.addNeutralPreferenceSubjectByPlace() {
        divisionOwnerId?.let {
            val places = placeRepository.findByDivisionOwnerId(it)
            val neutralPreferenceToAdd =
                places
                    .filter { place -> !preferenceSubjectByPlace.any { preference -> id == preference.subjectId && place.id == preference.placeId } }
                    .map { place -> PreferenceSubjectByPlaceDTO(subjectId = id, subjectName = name ?: "", placeId = place.id, placeName = place.name ?: "") }
            preferenceSubjectByPlace += neutralPreferenceToAdd
            preferenceSubjectByPlace = preferenceSubjectByPlace.sortedBy { it.placeName }.toSet()
        }
    }


}
