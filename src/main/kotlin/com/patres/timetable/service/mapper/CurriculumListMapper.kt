package com.patres.timetable.service.mapper

import com.patres.timetable.domain.CurriculumList
import com.patres.timetable.repository.DivisionRepository
import com.patres.timetable.repository.PeriodRepository
import com.patres.timetable.service.dto.CurriculumListDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class CurriculumListMapper : EntityMapper<CurriculumList, CurriculumListDTO>() {

    @Autowired
    private lateinit var periodRepository: PeriodRepository

    @Autowired
    private lateinit var curriculumMapper: CurriculumMapper

    @Autowired
    private lateinit var divisionRepository: DivisionRepository

    override fun toEntity(entityDto: CurriculumListDTO): CurriculumList {
        return CurriculumList().apply {
            name = entityDto.name?: ""
            divisionOwner = entityDto.divisionOwnerId?.let { divisionRepository.getOne(it) }
            id = entityDto.id
            curriculums = curriculumMapper.entityDTOSetToEntitySet(entityDto.curriculums)
            period = entityDto.periodId?.let { periodRepository.getOne(it) }
        }
    }

    override fun toDto(entity: CurriculumList): CurriculumListDTO {
        return CurriculumListDTO().apply {
            name = entity.name
            divisionOwnerId = entity.divisionOwner?.id
            divisionOwnerName = entity.divisionOwner?.name
            id = entity.id
            curriculums = curriculumMapper.entitySetToEntityDTOSet(entity.curriculums)
            periodName = entity.period?.name
            periodId = entity.period?.id
        }
    }

}
