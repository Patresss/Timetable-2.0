package com.patres.timetable.service.mapper

import com.patres.timetable.domain.Division
import com.patres.timetable.repository.DivisionRepository
import com.patres.timetable.service.dto.DivisionDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class DivisionMapper : EntityMapper<Division, DivisionDTO>() {

    @Autowired
    private lateinit var userMapper: UserMapper

    @Autowired
    private lateinit var teacherMapper: TeacherMapper

    @Autowired
    private lateinit var subjectMapper: SubjectMapper

    @Autowired
    private lateinit var divisionRepository: DivisionRepository

    override fun toEntity(entityDto: DivisionDTO): Division {
        val division = Division(
            name = entityDto.name,
            divisionType = entityDto.divisionType)
            .apply {
                id = entityDto.id
                shortName = entityDto.shortName
                numberOfPeople = entityDto.numberOfPeople
                colorBackground = entityDto.colorBackground
                colorText = entityDto.colorText
                divisionOwner = entityDto.divisionOwnerId?.let { divisionRepository.getOne(it) }
            }


        val divisionSet = entityDTOSetToEntitySet(entityDto.parents)
        division.parents = divisionSet
        val userSet = userMapper.entityDTOSetToEntitySet(entityDto.users)
        division.users = userSet
        val teacherSet = teacherMapper.entityDTOSetToEntitySet(entityDto.preferredTeachers)
        division.preferredTeachers = teacherSet
        val subjectSet = subjectMapper.entityDTOSetToEntitySet(entityDto.preferredSubjects)
        division.preferredSubjects = subjectSet

        return division
    }

    override fun toDto(entity: Division): DivisionDTO {
        val divisionDTO = DivisionDTO(
            name = entity.name,
            divisionType = entity.divisionType)
            .apply {
                id = entity.id
                shortName = entity.shortName
                numberOfPeople = entity.numberOfPeople
                colorBackground = entity.colorBackground
                colorText = entity.colorText
                divisionOwnerId = getDivisionOwnerId(entity.divisionOwner)
                divisionOwnerName = getDivisionOwnerName(entity.divisionOwner)
            }

        val divisionDtoSet = entitySetToEntityDTOSet(entity.parents)
        divisionDTO.parents = divisionDtoSet
        val userDtoSet = userMapper.entitySetToEntityDTOSet(entity.users)
        divisionDTO.users = userDtoSet
        val teacherDtoSet = teacherMapper.entitySetToEntityDTOSet(entity.preferredTeachers)
        divisionDTO.preferredTeachers = teacherDtoSet
        val subjectDtoSet = subjectMapper.entitySetToEntityDTOSet(entity.preferredSubjects)
        divisionDTO.preferredSubjects = subjectDtoSet

        return divisionDTO
    }

    fun getDivisionOwnerId(divisionOwner: Division?): Long? {
        if (divisionOwner == null) {
            return null
        }
        return divisionOwner.id
    }

    fun getDivisionOwnerName(divisionOwner: Division?): String? {
        if (divisionOwner == null) {
            return null
        }
        return divisionOwner.name
    }


}
