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
        return CurriculumList().apply {
            name = entityDto.name
            divisionOwner = entityDto.divisionOwnerId?.let { divisionRepository.getOne(it) }
            id = entityDto.id
            curriculums = curriculumMapper.entityDTOSetToEntitySet(entityDto.curriculums)
        }
    }

    override fun toDto(entity: CurriculumList): CurriculumListDTO {
        return CurriculumListDTO().apply {
            name = entity.name
            divisionOwnerId = divisionMapper.getDivisionOwnerId(entity.divisionOwner)
            divisionOwnerName = divisionMapper.getDivisionOwnerName(entity.divisionOwner)
            id = entity.id
            curriculums = curriculumMapper.entitySetToEntityDTOSet(entity.curriculums)
        }
    }

}
