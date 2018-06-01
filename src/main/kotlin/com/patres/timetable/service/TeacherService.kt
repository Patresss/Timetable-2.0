package com.patres.timetable.service

import com.patres.timetable.domain.Teacher
import com.patres.timetable.domain.preference.PreferenceDataTimeForTeacher
import com.patres.timetable.repository.LessonRepository
import com.patres.timetable.repository.TeacherRepository
import com.patres.timetable.repository.UserRepository
import com.patres.timetable.security.AuthoritiesConstants
import com.patres.timetable.security.SecurityUtils
import com.patres.timetable.service.dto.AbstractDivisionOwnerDTO
import com.patres.timetable.service.dto.TeacherDTO
import com.patres.timetable.service.dto.preference.PreferenceDataTimeForTeacherDTO
import com.patres.timetable.service.mapper.EntityMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.DayOfWeek

@Service
@Transactional
open class TeacherService(entityRepository: TeacherRepository, entityMapper: EntityMapper<Teacher, TeacherDTO>) : DivisionOwnerService<Teacher, TeacherDTO, TeacherRepository>(entityRepository, entityMapper) {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(TeacherService::class.java)
    }

    override fun hasPrivilegeToModifyEntity(entityDto: AbstractDivisionOwnerDTO): Boolean {
        return when {
            SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) -> true
            SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCHOOL_ADMIN) -> {
                val login = SecurityUtils.getCurrentUserLogin()
                return login?.let {
                    val userFromRepository = userRepository.findOneByLogin(login)
                    entityDto.divisionOwnerId == userFromRepository?.school?.id
                }?: false
            }
            SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.TEACHER) -> {
                val login = SecurityUtils.getCurrentUserLogin()
                val canModify = login?.let {
                    val userFromRepository = userRepository.findOneByLogin(login)
                    userFromRepository?.teacher?.id == entityDto.id
                }
                canModify?: false
            }
            else -> false
        }
    }

    @Transactional(readOnly = true)
    override fun findOne(id: Long?): TeacherDTO? {
        log.debug("Request to get Teacher : {}", id)
        val teacher = entityRepository.findOneWithEagerRelationships(id)
        return if(teacher != null) entityMapper.toDto(teacher) else null
    }


}
