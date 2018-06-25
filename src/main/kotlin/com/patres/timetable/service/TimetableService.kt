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
open class TimetableService(entityRepository: TimetableRepository, entityMapper: EntityMapper<Timetable, TimetableDTO>) : DivisionOwnerService<Timetable, TimetableDTO, TimetableRepository>(entityRepository, entityMapper) {

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

    @Transactional(readOnly = true)
    open fun findByDivisionListAndDateFromTeacherId(date: LocalDate, teacherId: Long ): Set<TimetableDTO> {
        TimetableService.log.debug("Request to findByDivisionListAndDateFromTeacherId date: $date and teacherId: $teacherId")
        val timetables = entityRepository.findByTeacherIdAndDateFromPeriod(date, teacherId)
        return timetables.map{ entityMapper.toDto(it)}.toSet()
    }

    @Transactional(readOnly = true)
    open fun findByDivisionListAndDateFromPeriod(date: LocalDate, placeId: Long ): Set<TimetableDTO> {
        TimetableService.log.debug("Request to findByDivisionListAndDateFromPeriod date: $date and placeId: $placeId")
        val timetables = entityRepository.findByPlaceIdAndDateFromPeriod(date, placeId)
        return timetables.map{ entityMapper.toDto(it)}.toSet()
    }

    @Transactional(readOnly = true)
    open fun findByDivisionListAndDateFromSubjectId(date: LocalDate, subjectId: Long ): Set<TimetableDTO> {
        TimetableService.log.debug("Request to findByDivisionListAndDateFromSubjectId date: $date and subjectId: $subjectId")
        val timetables = entityRepository.findBySubjectIdAndDateFromPeriod(date, subjectId)
        return timetables.map{ entityMapper.toDto(it)}.toSet()
    }

    open fun deleteAll() {
        TimetableService.log.debug("Request to delete all Timetables")
        entityRepository.deleteAll()
    }
}
