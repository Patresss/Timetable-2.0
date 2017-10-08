package com.patres.timetable.service

import com.patres.timetable.domain.Timetable
import com.patres.timetable.repository.TimetableRepository
import com.patres.timetable.service.dto.TimetableDTO
import com.patres.timetable.service.mapper.EntityMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
open class TimetableService(entityRepository: TimetableRepository, entityMapper: EntityMapper<Timetable, TimetableDTO>) : EntityService<Timetable, TimetableDTO, TimetableRepository>(entityRepository, entityMapper)
