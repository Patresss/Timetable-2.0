package com.patres.timetable.service.mapper

import com.patres.timetable.domain.Place
import com.patres.timetable.repository.DivisionRepository
import com.patres.timetable.repository.LessonRepository
import com.patres.timetable.repository.SubjectRepository
import com.patres.timetable.service.dto.PlaceDTO
import com.patres.timetable.service.dto.preference.relation.PreferenceDataTimeForPlaceDTO
import com.patres.timetable.service.dto.preference.relation.PreferenceSubjectByPlaceDTO
import com.patres.timetable.service.mapper.preference.relation.PreferenceDataTimeForPlaceMapper
import com.patres.timetable.service.mapper.preference.relation.PreferenceSubjectByPlaceMapper
import com.patres.timetable.util.EntityUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.DayOfWeek

@Service
open class PlaceMapper : EntityMapper<Place, PlaceDTO>() {


    @Autowired
    private lateinit var divisionRepository: DivisionRepository

    @Autowired
    private lateinit var subjectRepository: SubjectRepository

    @Autowired
    private lateinit var lessonRepository: LessonRepository

    @Autowired
    private lateinit var preferenceDataTimeForPlaceMapper: PreferenceDataTimeForPlaceMapper

    @Autowired
    private lateinit var divisionMapper: DivisionMapper

    @Autowired
    private lateinit var preferenceSubjectByPlaceMapper: PreferenceSubjectByPlaceMapper

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
                preferenceSubjectByPlace = preferenceSubjectByPlaceMapper.entityDTOSetToEntitySet(entityDto.preferenceSubjectByPlace)
                preferencesDateTimeForPlace = preferenceDataTimeForPlaceMapper.entityDTOSetToEntitySet(entityDto.preferencesDataTimeForPlace)

                preferenceSubjectByPlace.forEach { it.place = this }
                preferencesDateTimeForPlace.forEach { it.place = this }


                if (colorBackground.isNullOrBlank()) {
                    colorBackground = EntityUtil.calculateRandomColor()
                }
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
                preferenceSubjectByPlace = preferenceSubjectByPlaceMapper.entitySetToEntityDTOSet(entity.preferenceSubjectByPlace)
                addNeutralPreferenceSubjectByPlace()
                preferencesDataTimeForPlace = preferenceDataTimeForPlaceMapper.entitySetToEntityDTOSet(entity.preferencesDateTimeForPlace)
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
