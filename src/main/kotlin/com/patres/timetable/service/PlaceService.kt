package com.patres.timetable.service

import com.patres.timetable.domain.Place
import com.patres.timetable.repository.PlaceRepository
import com.patres.timetable.service.dto.PlaceDTO
import com.patres.timetable.service.mapper.EntityMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
open class PlaceService(entityRepository: PlaceRepository, entityMapper: EntityMapper<Place, PlaceDTO>) : DivisionOwnerService<Place, PlaceDTO, PlaceRepository>(entityRepository, entityMapper) {

    private val log = LoggerFactory.getLogger(PlaceService::class.java)

}
