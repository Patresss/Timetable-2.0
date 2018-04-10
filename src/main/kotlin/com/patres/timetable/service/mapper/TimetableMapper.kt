package com.patres.timetable.service.mapper

import com.patres.timetable.domain.Timetable
import com.patres.timetable.repository.*
import com.patres.timetable.service.dto.TimetableDTO
import com.patres.timetable.service.util.getOneOrNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class TimetableMapper : EntityMapper<Timetable, TimetableDTO>() {

    @Autowired
    private lateinit var divisionRepository: DivisionRepository

    @Autowired
    private lateinit var periodRepository: PeriodRepository

    @Autowired
    private lateinit var teacherRepository: TeacherRepository

    @Autowired
    private lateinit var subjectRepository: SubjectRepository

    @Autowired
    private lateinit var lessonRepository: LessonRepository

    @Autowired
    private lateinit var placeRepository: PlaceRepository

    @Autowired
    private lateinit var divisionMapper: DivisionMapper

    override fun toEntity(entityDto: TimetableDTO): Timetable {
        return Timetable(
            title = entityDto.title,
            type = entityDto.type,
            division = divisionRepository.getOneOrNull(entityDto.divisionId),
            teacher = teacherRepository.getOneOrNull(entityDto.teacherId),
            subject = subjectRepository.getOneOrNull(entityDto.subjectId),
            lesson = lessonRepository.getOneOrNull(entityDto.lessonId),
            place = placeRepository.getOneOrNull(entityDto.placeId),
            everyWeek = entityDto.everyWeek,
            startWithWeek = entityDto.startWithWeek,
            description = entityDto.description,
            colorBackground = entityDto.colorBackground,
            colorText = entityDto.colorText,
            dayOfWeek = entityDto.dayOfWeek
        ).apply {
            division?.let {
                divisionOwner = division?.divisionOwner
            }
            id = entityDto.id
            entityDto.startTimeString?.let {setStartTimeHHmmFormatted(it)}
            entityDto.endTimeString?.let {setEndTimeHHmmFormatted(it)}

            if (entityDto.periodId != null) {
                period = periodRepository.getOneOrNull(entityDto.periodId)
            } else {
                date = entityDto.date
            }

        }
    }

    override fun toDto(entity: Timetable): TimetableDTO {
        return TimetableDTO(
            title = entity.title,
            type = entity.type,

            teacherId = entity.teacher?.id,
            teacherFullName = entity.teacher?.getFullName(),
            placeId = entity.place?.id,
            placeName = entity.place?.name,
            lessonId = entity.lesson?.id,
            lessonName = entity.lesson?.name,
            divisionId = entity.division?.id,
            divisionName = entity.division?.name,
            subjectId = entity.subject?.id,
            subjectName = entity.subject?.name,
            subjectShortName = timetableSubjectShortName(entity),
            startDate = entity.startDate,
            endDate = entity.endDate,

            everyWeek = entity.everyWeek,
            startWithWeek = entity.startWithWeek,
            description = entity.description,
            colorBackground = entity.colorBackground,
            colorText = entity.colorText,
            dayOfWeek = entity.dayOfWeek
        ).apply {
            id = entity.id
            if (entity.lesson != null) {
                startTimeString = entity.lesson?.getStartTimeHHmmFormatted()
                endTimeString = entity.lesson?.getEndTimeHHmmFormatted()
            } else {
                startTimeString = entity.getStartTimeHHmmFormatted()
                endTimeString = entity.getEndTimeHHmmFormatted()
            }
            divisionOwnerName = divisionMapper.getDivisionOwnerName(entity.divisionOwner)
            divisionOwnerId = divisionMapper.getDivisionOwnerId(entity.divisionOwner)

            if (entity.period != null) {
                periodId = entity.period?.id
                periodName = entity.period?.name
            } else {
                date = entity.date
            }
        }
    }

    private fun timetableSubjectShortName(timetable: Timetable?): String? {
        if (timetable == null) {
            return null
        }
        val subject = timetable.subject ?: return null
        return if (subject.shortName != null) subject.shortName else getShortName(subject.name)
    }

}
