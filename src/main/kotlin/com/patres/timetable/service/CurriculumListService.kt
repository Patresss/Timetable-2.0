package com.patres.timetable.service

import com.patres.timetable.domain.CurriculumList
import com.patres.timetable.domain.Period
import com.patres.timetable.repository.CurriculumListRepository
import com.patres.timetable.repository.PeriodRepository
import com.patres.timetable.service.dto.CurriculumListDTO
import com.patres.timetable.service.dto.PeriodDTO
import com.patres.timetable.service.mapper.EntityMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
open class CurriculumListService(entityRepository: CurriculumListRepository, entityMapper: EntityMapper<CurriculumList, CurriculumListDTO>) : DivisionOwnerService<CurriculumList, CurriculumListDTO, CurriculumListRepository>(entityRepository, entityMapper)
