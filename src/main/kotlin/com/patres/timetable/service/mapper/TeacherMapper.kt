package com.patres.timetable.service.mapper

import com.patres.timetable.domain.Teacher
import com.patres.timetable.repository.DivisionRepository
import com.patres.timetable.service.dto.TeacherDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class TeacherMapper : EntityMapper<Teacher, TeacherDTO>() {

    @Autowired
    private lateinit var subjectMapper: SubjectMapper

    @Autowired
    private lateinit var divisionMapper: DivisionMapper

    @Autowired
    private lateinit var divisionRepository: DivisionRepository

    override fun toEntity(entityDto: TeacherDTO): Teacher {
        val set = subjectMapper.entityDTOSetToEntitySet(entityDto.preferredSubjects)
        return Teacher(
            name = entityDto.name,
            surname = entityDto.surname
        ).apply {
            divisionOwner = divisionRepository.getOne(entityDto.divisionOwnerId)
            id = entityDto.id
            degree = entityDto.degree
            shortName = entityDto.shortName
            preferredSubjects = set
        }
    }

    override fun toDto(entity: Teacher): TeacherDTO {
        val set = subjectMapper.entitySetToEntityDTOSet(entity.preferredSubjects)
        return TeacherDTO(
            name = entity.name,
            surname = entity.surname
        ).apply {
            divisionOwnerId = divisionMapper.getDivisionOwnerId(entity.divisionOwner)
            divisionOwnerName = divisionMapper.getDivisionOwnerName(entity.divisionOwner)
            id = entity.id
            degree = entity.degree
            shortName = entity.shortName
            fullName = "$degree $name $surname"
            preferredSubjects = set
        }
    }

}
