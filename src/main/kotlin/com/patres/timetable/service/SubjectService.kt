package com.patres.timetable.service

import com.patres.timetable.domain.Subject
import com.patres.timetable.repository.SubjectRepository
import com.patres.timetable.service.dto.SubjectDTO
import com.patres.timetable.service.mapper.EntityMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
open class SubjectService(entityRepository: SubjectRepository, entityMapper: EntityMapper<Subject, SubjectDTO>) : DivisionOwnerService<Subject, SubjectDTO, SubjectRepository>(entityRepository, entityMapper)
