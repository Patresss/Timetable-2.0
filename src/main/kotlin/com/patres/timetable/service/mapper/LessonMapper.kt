package com.patres.timetable.service.mapper

import com.patres.timetable.domain.Lesson
import com.patres.timetable.repository.DivisionRepository
import com.patres.timetable.service.dto.LessonDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class LessonMapper : EntityMapper<Lesson, LessonDTO>() {

    @Autowired
    private lateinit var divisionMapper: DivisionMapper

    @Autowired
    private lateinit var divisionRepository: DivisionRepository

    override fun toEntity(entityDto: LessonDTO): Lesson {
        return Lesson()
            .apply {
                name = entityDto.name
                entityDto.startTimeString?.let {
                    setStartTimeHHmmFormatted(it)
                }
                entityDto.endTimeString?.let {
                    setEndTimeHHmmFormatted(it)
                }
                divisionOwner = divisionRepository.findOne(entityDto.divisionOwnerId)
                id = entityDto.id
            }
    }

    override fun toDto(entity: Lesson): LessonDTO {
        return LessonDTO()
            .apply {
                name = entity.name
                startTimeString = entity.getStartTimeHHmmFormatted()
                endTimeString = entity.getEndTimeHHmmFormatted()
                divisionOwnerName = divisionMapper.getDivisionOwnerName(entity.divisionOwner)
                divisionOwnerId = divisionMapper.getDivisionOwnerId(entity.divisionOwner)
                id = entity.id
            }
    }

}
