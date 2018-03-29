package com.patres.timetable.service

import com.patres.timetable.domain.Curriculum
import com.patres.timetable.repository.CurriculumRepository
import com.patres.timetable.service.dto.CurriculumDTO
import com.patres.timetable.service.mapper.EntityMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
open class CurriculumService(entityRepository: CurriculumRepository, entityMapper: EntityMapper<Curriculum, CurriculumDTO>) : DivisionOwnerService<Curriculum, CurriculumDTO, CurriculumRepository>(entityRepository, entityMapper)
