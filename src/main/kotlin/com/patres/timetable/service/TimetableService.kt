package com.patres.timetable.service

import com.patres.timetable.domain.Timetable
import com.patres.timetable.repository.TimetableRepository
import com.patres.timetable.service.dto.TimetableDTO
import com.patres.timetable.service.mapper.EntityMapper
import com.patres.timetable.web.rest.TimetableResource
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional
open class TimetableService(entityRepository: TimetableRepository, entityMapper: EntityMapper<Timetable, TimetableDTO>) : EntityService<Timetable, TimetableDTO, TimetableRepository>(entityRepository, entityMapper) {

    companion object {
        private val log = LoggerFactory.getLogger(TimetableResource::class.java)
        private val ENTITY_NAME = "timetable"
    }


    @Transactional(readOnly = true)
    open fun findByDivisionListAndDateFromPeriod(date: LocalDate, divisionsId: List<Long> ): Set<TimetableDTO> {
        TimetableService.log.debug("Request to findByDivisionListAndDateFromPeriod date: $date and divisionsId: $divisionsId")
        val timetables = entityRepository.findByDivisionListAndDateFromPeriod(date, divisionsId)
        return timetables.map{ entityMapper.toDto(it)}.toSet()
    }
}
