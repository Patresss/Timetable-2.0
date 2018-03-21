package com.patres.timetable.service.mapper

import com.patres.timetable.domain.Subject
import com.patres.timetable.repository.DivisionRepository
import com.patres.timetable.service.dto.SubjectDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class SubjectMapper : EntityMapper<Subject, SubjectDTO>() {


    @Autowired private
    lateinit var divisionMapper: DivisionMapper

    @Autowired
    private lateinit var divisionRepository: DivisionRepository

    override fun toEntity(entityDto: SubjectDTO): Subject {
        return Subject(
            name = entityDto.name
        ).apply {
                divisionOwner = entityDto.divisionOwnerId?.let { divisionRepository.getOne(it) }
                id = entityDto.id
                shortName = entityDto.shortName
                colorBackground = entityDto.colorBackground
                colorText = entityDto.colorText
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
            }
    }



}
