package com.patres.timetable.service

import com.patres.timetable.domain.Teacher
import com.patres.timetable.repository.TeacherRepository
import com.patres.timetable.service.dto.TeacherDTO
import com.patres.timetable.service.mapper.EntityMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
open class TeacherService(entityRepository: TeacherRepository, entityMapper: EntityMapper<Teacher, TeacherDTO>) : DivisionOwnerService<Teacher, TeacherDTO, TeacherRepository>(entityRepository, entityMapper) {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(TeacherService::class.java)
    }

    @Transactional(readOnly = true)
    override fun findOne(id: Long?): TeacherDTO? {
        log.debug("Request to get Teacher : {}", id)
        val teacher = entityRepository.findOneWithEagerRelationships(id)
        return if(teacher != null) entityMapper.toDto(teacher) else null
    }


}
