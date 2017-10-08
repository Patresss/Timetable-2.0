package com.patres.timetable.service.mapper

import com.patres.timetable.domain.Lesson
import com.patres.timetable.repository.DivisionRepository
import com.patres.timetable.service.dto.LessonDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class LessonMapper : EntityMapper<Lesson, LessonDTO>() {

    @Autowired
    lateinit private var divisionMapper: DivisionMapper

    @Autowired
    lateinit private var divisionRepository: DivisionRepository

    override fun toEntity(entityDto: LessonDTO): Lesson {
        return Lesson()
            .apply {
                name = entityDto.name
                startTime = entityDto.startTime
                endTime = entityDto.endTime
                divisionOwner = divisionRepository.findOne(entityDto.divisionOwnerId)
                id = entityDto.id
            }
    }

    override fun toDto(entity: Lesson): LessonDTO {
        return LessonDTO()
            .apply {
                name = entity.name
                startTime = entity.startTime
                endTime = entity.endTime
                divisionOwnerName = divisionMapper.getDivisionOwnerName(entity.divisionOwner)
                divisionOwnerId = divisionMapper.getDivisionOwnerId(entity.divisionOwner)
                id = entity.id
            }
    }

}
