package com.patres.timetable.service.mapper

import com.patres.timetable.domain.Property
import com.patres.timetable.repository.DivisionRepository
import com.patres.timetable.service.dto.PropertyDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class PropertyMapper : EntityMapper<Property, PropertyDTO>() {

    @Autowired
    lateinit private var divisionMapper: DivisionMapper

    @Autowired
    lateinit private var divisionRepository: DivisionRepository

    override fun toEntity(entityDto: PropertyDTO): Property {
        return Property(
            propertyKey = entityDto.propertyKey,
            propertyValue = entityDto.propertyValue)
            .apply {
                divisionOwner = entityDto.divisionOwnerId?.let { divisionRepository.getOne(it) }
                id = entityDto.id
            }
    }

    override fun toDto(entity: Property): PropertyDTO {
        return PropertyDTO(
            propertyKey = entity.propertyKey,
            propertyValue = entity.propertyValue)
            .apply {
                divisionOwnerId = divisionMapper.getDivisionOwnerId(entity.divisionOwner)
                divisionOwnerName = divisionMapper.getDivisionOwnerName(entity.divisionOwner)
                id = entity.id
            }
    }

}
