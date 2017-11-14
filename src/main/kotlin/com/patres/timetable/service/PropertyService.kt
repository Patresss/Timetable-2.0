package com.patres.timetable.service

import com.patres.timetable.domain.Property
import com.patres.timetable.repository.PropertyRepository
import com.patres.timetable.service.dto.PropertyDTO
import com.patres.timetable.service.mapper.EntityMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
open class PropertyService(entityRepository: PropertyRepository, entityMapper: EntityMapper<Property, PropertyDTO>) : DivisionOwnerService<Property, PropertyDTO, PropertyRepository>(entityRepository, entityMapper)
