package com.patres.timetable.service.mapper

import com.patres.timetable.domain.Interval
import com.patres.timetable.repository.PeriodRepository
import com.patres.timetable.service.dto.IntervalDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class IntervalMapper : EntityMapper<Interval, IntervalDTO>() {

    @Autowired
    lateinit private var periodRepository: PeriodRepository

    override fun toEntity(entityDto: IntervalDTO): Interval {
        return Interval()
            .apply {
                included = entityDto.included
                startDate = entityDto.startDate
                endDate = entityDto.endDate
                entityDto.periodId?.let { period = periodRepository.getOne(entityDto.periodId) }
                id = entityDto.id
            }
    }

    override fun toDto(entity: Interval): IntervalDTO {
        return IntervalDTO()
            .apply {
                included = entity.included
                startDate = entity.startDate
                endDate = entity.endDate
                periodId = intervalPeriodId(entity)
                periodName = intervalPeriodName(entity)
                id = entity.id
            }
    }

    private fun intervalPeriodId(interval: Interval?): Long? {
        if (interval == null) {
            return null
        }
        val period = interval.period ?: return null
        return period.id
    }

    private fun intervalPeriodName(interval: Interval?): String? {
        if (interval == null) {
            return null
        }
        val period = interval.period ?: return null
        return period.name
    }
}
