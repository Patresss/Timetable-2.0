package com.patres.timetable.service.mapper

import com.patres.timetable.domain.Place
import com.patres.timetable.domain.Subject
import com.patres.timetable.repository.DivisionRepository
import com.patres.timetable.repository.LessonRepository
import com.patres.timetable.service.dto.PlaceDTO
import com.patres.timetable.service.dto.SubjectDTO
import com.patres.timetable.service.dto.preference.PreferenceDataTimeForSubjectDTO
import com.patres.timetable.service.mapper.preference.PreferenceDataTimeForSubjectMapper
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
    private lateinit var preferenceDataTimeForSubjectMapper: PreferenceDataTimeForSubjectMapper

    override fun toEntity(entityDto: SubjectDTO): Subject {
        return Subject(
            name = entityDto.name
        ).apply {
            divisionOwner = entityDto.divisionOwnerId?.let { divisionRepository.getOne(it) }
            id = entityDto.id
            shortName = entityDto.shortName
            colorBackground = entityDto.colorBackground
            colorText = entityDto.colorText
            preferencesDataTimeForSubject = preferenceDataTimeForSubjectMapper.entityDTOSetToEntitySet(entityDto.preferencesDataTimeForSubject)
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
            preferencesDataTimeForSubject = preferenceDataTimeForSubjectMapper.entitySetToEntityDTOSet(entity.preferencesDataTimeForSubject)
            addNeutralPreferencesDataTime()
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


}
