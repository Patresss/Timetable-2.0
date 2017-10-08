package com.patres.timetable.service.mapper

import com.patres.timetable.domain.Division
import com.patres.timetable.service.dto.DivisionDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class DivisionMapper : EntityMapper<Division, DivisionDTO>() {

    @Autowired
    lateinit private var userMapper: UserMapper
    @Autowired
    lateinit private var teacherMapper: TeacherMapper
    @Autowired
    lateinit private var subjectMapper: SubjectMapper

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
            }


        val divisionSet = entityDTOSetToEntitySet(entityDto.parents)
        division.parents = divisionSet
        val userSet = userMapper.userDTOSetToUserSet(entityDto.users)
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
            }

        val divisionDtoSet = entitySetToEntityDTOSet(entity.parents)
        divisionDTO.parents = divisionDtoSet
        val userDtoSet = userMapper.userSetToUserDTOSet(entity.users)
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
