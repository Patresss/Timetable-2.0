package com.patres.timetable.service.mapper

import com.patres.timetable.domain.CurriculumList
import com.patres.timetable.repository.DivisionRepository
import com.patres.timetable.service.dto.CurriculumListDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class CurriculumListMapper : EntityMapper<CurriculumList, CurriculumListDTO>() {

    @Autowired
    private lateinit var divisionMapper: DivisionMapper

    @Autowired
    private lateinit var curriculumMapper: CurriculumMapper

    @Autowired
    private lateinit var divisionRepository: DivisionRepository

    override fun toEntity(entityDto: CurriculumListDTO): CurriculumList {
        val curriculumList = CurriculumList().apply {
            name = entityDto.name
            divisionOwner = divisionRepository.getOne(entityDto.divisionOwnerId)
            id = entityDto.id
        }

        val curriculums = curriculumMapper.entityDTOSetToEntitySet(entityDto.curriculums)
        curriculums.forEach { it.curriculumList = curriculumList }
        curriculumList.curriculums = curriculums

        return curriculumList
    }

    override fun toDto(entity: CurriculumList): CurriculumListDTO {
        val curriculumList = CurriculumListDTO().apply {
            name = entity.name
            divisionOwnerId = divisionMapper.getDivisionOwnerId(entity.divisionOwner)
            divisionOwnerName = divisionMapper.getDivisionOwnerName(entity.divisionOwner)
            id = entity.id
        }

        val curriculums = entity.curriculums.map { curriculumMapper.toDto(it) }.toSet()
        curriculumList.curriculums = curriculums

        return curriculumList
    }

}
