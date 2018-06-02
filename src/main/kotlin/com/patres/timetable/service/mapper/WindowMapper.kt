package com.patres.timetable.service.mapper

import com.patres.timetable.generator.Window
import com.patres.timetable.repository.DivisionRepository
import com.patres.timetable.repository.LessonRepository
import com.patres.timetable.service.dto.generator.WindowDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class WindowMapper : EntityMapper<Window, WindowDTO>() {

    @Autowired
    private lateinit var divisionRepository: DivisionRepository

    @Autowired
    private lateinit var lessonRepository: LessonRepository

    override fun toEntity(entityDto: WindowDTO): Window {
        return Window()
            .apply {
                dayOfWeek = entityDto.dayOfWeek
                lesson = entityDto.lessonId?.let { lessonRepository.findOne(it) }
                division = entityDto.divisionId?.let { divisionRepository.findOne(it) }
            }
    }

    override fun toDto(entity: Window): WindowDTO {
        return WindowDTO()
            .apply {
                dayOfWeek = entity.dayOfWeek
                lessonName = entity.lesson?.name?: ""
                lessonId = entity.lesson?.id
                divisionName = entity.division?.name?: ""
                divisionId = entity.division?.id
            }
    }

}
