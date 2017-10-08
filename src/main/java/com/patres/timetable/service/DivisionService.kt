package com.patres.timetable.service

import com.patres.timetable.domain.Division
import com.patres.timetable.repository.DivisionRepository
import com.patres.timetable.service.dto.DivisionDTO
import com.patres.timetable.service.mapper.EntityMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
open class DivisionService(entityRepository: DivisionRepository, entityMapper: EntityMapper<Division, DivisionDTO>) : EntityService<Division, DivisionDTO, DivisionRepository>(entityRepository, entityMapper) {

    private val log = LoggerFactory.getLogger(DivisionService::class.java)

    @Transactional(readOnly = true)
    override fun findOne(id: Long?): DivisionDTO? {
        log.debug("Request to get Division : {}", id)
        val division = entityRepository.findOneWithEagerRelationships(id)
        return if (division != null) entityMapper.toDto(division) else null
    }
}
