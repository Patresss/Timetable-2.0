package com.patres.timetable.service.mapper

import com.patres.timetable.domain.Division
import com.patres.timetable.domain.Subject
import com.patres.timetable.repository.LessonRepository
import com.patres.timetable.service.dto.DivisionDTO
import com.patres.timetable.service.dto.SubjectDTO
import com.patres.timetable.service.dto.preference.PreferenceDataTimeForDivisionDTO
import com.patres.timetable.service.mapper.preference.PreferenceDataTimeForDivisionMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.DayOfWeek

@Service
open class DivisionMapper : EntityMapper<Division, DivisionDTO>() {

    @Autowired
    private lateinit var userMapper: UserMapper

    @Autowired
    private lateinit var teacherMapper: TeacherMapper

    @Autowired
    private lateinit var subjectMapper: SubjectMapper

    @Autowired
    private lateinit var lessonRepository: LessonRepository

    @Autowired
    private lateinit var preferenceDataTimeForDivisionMapper: PreferenceDataTimeForDivisionMapper

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
                users = userMapper.entityDTOSetToEntitySet(entityDto.users)
                preferredTeachers = teacherMapper.entityDTOSetToEntitySet(entityDto.preferredTeachers)
                preferredSubjects = subjectMapper.entityDTOSetToEntitySet(entityDto.preferredSubjects)
                val parent = parents.elementAtOrNull(0) //TODO validate if all parents has the same owner
                divisionOwner = parent?.divisionOwner ?: parent
                preferencesDataTimeForDivision = preferenceDataTimeForDivisionMapper.entityDTOSetToEntitySet(entityDto.preferencesDataTimeForDivision)

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
                preferencesDataTimeForDivision = preferenceDataTimeForDivisionMapper.entitySetToEntityDTOSet(entity.preferencesDataTimeForDivision)
                addNeutralPreferencesDataTime()
            }

        val divisionDtoSet = entitySetToEntityDTOSet(entity.parents)
        divisionDTO.parents = divisionDtoSet
        val userDtoSet = userMapper.entitySetToEntityDTOSet(entity.users)
        divisionDTO.users = userDtoSet
        val teacherDtoSet = teacherMapper.entitySetToEntityDTOSet(entity.preferredTeachers)
        divisionDTO.preferredTeachers = teacherDtoSet
        val subjectDtoSet = subjectMapper.entitySetToEntityDTOSet(entity.preferredSubjects)
        divisionDTO.preferredSubjects = subjectDtoSet

        return divisionDTO
    }

    override fun toDtoWithSampleForm(entity: Division): DivisionDTO {
        return DivisionDTO(
            name = entity.name
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


}
