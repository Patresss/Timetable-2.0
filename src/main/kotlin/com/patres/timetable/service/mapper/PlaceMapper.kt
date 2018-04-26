package com.patres.timetable.service.mapper

import com.patres.timetable.domain.Place
import com.patres.timetable.repository.DivisionRepository
import com.patres.timetable.repository.LessonRepository
import com.patres.timetable.repository.SubjectRepository
import com.patres.timetable.repository.TeacherRepository
import com.patres.timetable.service.dto.PlaceDTO
import com.patres.timetable.service.dto.preference.PreferenceDataTimeForPlaceDTO
import com.patres.timetable.service.dto.preference.PreferenceDivisionByPlaceDTO
import com.patres.timetable.service.dto.preference.PreferenceSubjectByPlaceDTO
import com.patres.timetable.service.dto.preference.PreferenceTeacherByPlaceDTO
import com.patres.timetable.service.mapper.preference.PreferenceDataTimeForPlaceMapper
import com.patres.timetable.service.mapper.preference.PreferenceDivisionByPlaceMapper
import com.patres.timetable.service.mapper.preference.PreferenceSubjectByPlaceMapper
import com.patres.timetable.service.mapper.preference.PreferenceTeacherByPlaceMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.DayOfWeek

@Service
open class PlaceMapper : EntityMapper<Place, PlaceDTO>() {


    @Autowired
    private lateinit var divisionRepository: DivisionRepository

    @Autowired
    private lateinit var teacherRepository: TeacherRepository

    @Autowired
    private lateinit var subjectRepository: SubjectRepository

    @Autowired
    private lateinit var lessonRepository: LessonRepository

    @Autowired
    private lateinit var preferenceDataTimeForPlaceMapper: PreferenceDataTimeForPlaceMapper

    @Autowired
    private lateinit var divisionMapper: DivisionMapper

    @Autowired
    private lateinit var preferenceDivisionByPlaceMapper: PreferenceDivisionByPlaceMapper

    @Autowired
    private lateinit var preferenceSubjectByPlaceMapper: PreferenceSubjectByPlaceMapper

    @Autowired
    private lateinit var preferenceTeacherByPlaceMapper: PreferenceTeacherByPlaceMapper

    override fun toEntity(entityDto: PlaceDTO): Place {
        return Place(
            name = entityDto.name)
            .apply {
                divisionOwner = entityDto.divisionOwnerId?.let { divisionRepository.getOne(it) }
                id = entityDto.id
                numberOfSeats = entityDto.numberOfSeats
                shortName = entityDto.shortName
                colorBackground = entityDto.colorBackground
                colorText = entityDto.colorText
                preferenceTeacherByPlace = preferenceTeacherByPlaceMapper.entityDTOSetToEntitySet(entityDto.preferenceTeacherByPlace)
                preferenceSubjectByPlace = preferenceSubjectByPlaceMapper.entityDTOSetToEntitySet(entityDto.preferenceSubjectByPlace)
                preferenceDivisionByPlace = preferenceDivisionByPlaceMapper.entityDTOSetToEntitySet(entityDto.preferenceDivisionByPlace)
                preferencesDataTimeForPlace = preferenceDataTimeForPlaceMapper.entityDTOSetToEntitySet(entityDto.preferencesDataTimeForPlace)
            }
    }

    override fun toDto(entity: Place): PlaceDTO {
        return PlaceDTO(
            name = entity.name)
            .apply {
                divisionOwnerId = divisionMapper.getDivisionOwnerId(entity.divisionOwner)
                divisionOwnerName = divisionMapper.getDivisionOwnerName(entity.divisionOwner)
                id = entity.id
                numberOfSeats = entity.numberOfSeats
                shortName = entity.shortName
                colorBackground = entity.colorBackground
                colorText = entity.colorText
                preferenceTeacherByPlace = preferenceTeacherByPlaceMapper.entitySetToEntityDTOSet(entity.preferenceTeacherByPlace)
                addNeutralPreferenceTeacherByPlace()
                preferenceSubjectByPlace = preferenceSubjectByPlaceMapper.entitySetToEntityDTOSet(entity.preferenceSubjectByPlace)
                addNeutralPreferenceSubjectByPlace()
                preferenceDivisionByPlace = preferenceDivisionByPlaceMapper.entitySetToEntityDTOSet(entity.preferenceDivisionByPlace)
                addNeutralPreferenceDivisionByPlace()
                preferencesDataTimeForPlace = preferenceDataTimeForPlaceMapper.entitySetToEntityDTOSet(entity.preferencesDataTimeForPlace)
                addNeutralPreferencesDataTime()
            }
    }

    override fun toDtoWithSampleForm(entity: Place): PlaceDTO {
        return PlaceDTO(
            name = entity.name
        ).apply {
            divisionOwnerId = divisionMapper.getDivisionOwnerId(entity.divisionOwner)
            divisionOwnerName = divisionMapper.getDivisionOwnerName(entity.divisionOwner)
            id = entity.id
            numberOfSeats = entity.numberOfSeats
            shortName = entity.shortName
        }
    }

    private fun PlaceDTO.addNeutralPreferenceSubjectByPlace() {
        divisionOwnerId?.let {
            val subjects = subjectRepository.findByDivisionOwnerId(it)
            val neutralPreferenceToAdd =
                subjects
                    .filter { subject -> !preferenceSubjectByPlace.any { preference -> id == preference.placeId && subject.id == preference.subjectId } }
                    .map { subject -> PreferenceSubjectByPlaceDTO(placeId = id, placeName = name ?: "", subjectId = subject.id, subjectName = subject.name ?: "") }
            preferenceSubjectByPlace += neutralPreferenceToAdd
            preferenceSubjectByPlace = preferenceSubjectByPlace.sortedBy { it.subjectName }.toSet()
        }
    }

    private fun PlaceDTO.addNeutralPreferenceDivisionByPlace() {
        divisionOwnerId?.let {
            val divisions = divisionRepository.findByDivisionOwnerId(it)
            val neutralPreferenceToAdd =
                divisions
                    .filter { division -> !preferenceDivisionByPlace.any { preference -> id == preference.placeId && division.id == preference.divisionId } }
                    .map { division -> PreferenceDivisionByPlaceDTO(placeId = id, placeName = name ?: "", divisionId = division.id, divisionName = division.name ?: "") }
            preferenceDivisionByPlace += neutralPreferenceToAdd
            preferenceDivisionByPlace = preferenceDivisionByPlace.sortedBy { it.divisionName }.toSet()
        }
    }

    private fun PlaceDTO.addNeutralPreferenceTeacherByPlace() {
        divisionOwnerId?.let {
            val teachers = teacherRepository.findByDivisionOwnerId(it)
            val neutralPreferenceToAdd =
                teachers
                    .filter { teacher -> !preferenceTeacherByPlace.any { preference -> id == preference.placeId && teacher.id == preference.teacherId } }
                    .map { teacher -> PreferenceTeacherByPlaceDTO(placeId = id, placeName = name ?: "", teacherId = teacher.id, teacherFullName = teacher.getFullName(), teacherDegree = teacher.degree ?: "", teacherName = teacher.name ?: "", teacherSurname = teacher.surname ?: "") }
            preferenceTeacherByPlace += neutralPreferenceToAdd
            preferenceTeacherByPlace = preferenceTeacherByPlace.sortedWith(compareBy({ it.teacherSurname }, { it.teacherName }, { it.teacherDegree })).toSet()
        }
    }

    private fun PlaceDTO.addNeutralPreferencesDataTime() {
        val neutralPreferenceDataTimeForToAdd = HashSet<PreferenceDataTimeForPlaceDTO>()
        divisionOwnerId?.let {
            val lessons = lessonRepository.findByDivisionOwnerId(it)
            val daysOfWeeks = DayOfWeek.values().map { it.value }
            for (lesson in lessons) {
                for (dayOfWeek in daysOfWeeks) {
                    if (!preferencesDataTimeForPlace.any { it.dayOfWeek == dayOfWeek && it.lessonId == lesson.id }) {
                        neutralPreferenceDataTimeForToAdd.add(PreferenceDataTimeForPlaceDTO(placeId = id, placeName = name ?: "", lessonId = lesson.id, lessonName = lesson.name ?: "", dayOfWeek = dayOfWeek, points = 0))
                    }
                }
            }
            preferencesDataTimeForPlace += neutralPreferenceDataTimeForToAdd
            preferencesDataTimeForPlace = preferencesDataTimeForPlace.sortedBy { it.dayOfWeek }.toSet()
        }
    }

}
