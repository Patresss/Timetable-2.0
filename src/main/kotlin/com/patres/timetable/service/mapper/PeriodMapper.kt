package com.patres.timetable.service.mapper

import com.patres.timetable.domain.Period
import com.patres.timetable.repository.DivisionRepository
import com.patres.timetable.service.dto.IntervalDTO
import com.patres.timetable.service.dto.PeriodDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.util.HashSet

@Service
open class PeriodMapper : EntityMapper<Period, PeriodDTO>() {


    @Autowired
    lateinit private var divisionMapper: DivisionMapper
    @Autowired
    lateinit private var intervalMapper: IntervalMapper

    @Autowired
    lateinit private var divisionRepository: DivisionRepository

    override fun toEntity(entityDto: PeriodDTO): Period {
        val period = Period(
            name = entityDto.name
        ).apply {
            divisionOwner = entityDto.divisionOwnerId?.let { divisionRepository.getOne(it) }
            id = entityDto.id
        }

        val intervals = intervalMapper.entityDTOSetToEntitySet(entityDto.intervalTimes)
        intervals.forEach { interval -> interval.period = period }
        period.intervalTimes = intervals

        return period
    }

    override fun toDto(entity: Period): PeriodDTO {
        val periodDTO = PeriodDTO(
            name = entity.name)
            .apply {
                divisionOwnerId = divisionMapper.getDivisionOwnerId(entity.divisionOwner)
                divisionOwnerName = divisionMapper.getDivisionOwnerName(entity.divisionOwner)
                id = entity.id
            }

        val intervals = entity.intervalTimes
            .map { intervalMapper.toDto(it) }
            .toSet()
        periodDTO.intervalTimes = intervals

        return periodDTO
    }

}
