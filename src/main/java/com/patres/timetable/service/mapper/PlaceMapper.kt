package com.patres.timetable.service.mapper

import com.patres.timetable.domain.Place
import com.patres.timetable.repository.DivisionRepository
import com.patres.timetable.service.dto.PlaceDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class PlaceMapper : EntityMapper<Place, PlaceDTO>() {

    @Autowired
    lateinit private var subjectMapper: SubjectMapper

    @Autowired
    lateinit private var divisionMapper: DivisionMapper

    @Autowired
    lateinit private var teacherMapper: TeacherMapper

    @Autowired
    lateinit private var divisionRepository: DivisionRepository

    override fun toEntity(entityDto: PlaceDTO): Place {
        val place = Place(
            name = entityDto.name)
            .apply {
                divisionOwner = divisionRepository.getOne(entityDto.divisionOwnerId)
                id = entityDto.id
                numberOfSeats = entityDto.numberOfSeats
                shortName = entityDto.shortName
                colorBackground = entityDto.colorBackground
                colorText = entityDto.colorText
            }

        val subjectSet = subjectMapper.entityDTOSetToEntitySet(entityDto.preferredSubjects)
        place.preferredSubjects = subjectSet
        val divisionSet = divisionMapper.entityDTOSetToEntitySet(entityDto.preferredDivisions)
        place.preferredDivisions = divisionSet
        val teacherSet = teacherMapper.entityDTOSetToEntitySet(entityDto.preferredTeachers)
        place.preferredTeachers = teacherSet

        return place
    }

    override fun toDto(entity: Place): PlaceDTO {
        val subjectDtoSet = subjectMapper.entitySetToEntityDTOSet(entity.preferredSubjects)
        val divisionDtoSet = divisionMapper.entitySetToEntityDTOSet(entity.preferredDivisions)
        val teacherDtoSet = teacherMapper.entitySetToEntityDTOSet(entity.preferredTeachers)

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
                preferredSubjects = subjectDtoSet
                preferredDivisions = divisionDtoSet
                preferredTeachers = teacherDtoSet
            }
    }

}
