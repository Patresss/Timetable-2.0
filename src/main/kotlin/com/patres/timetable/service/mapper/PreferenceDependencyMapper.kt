package com.patres.timetable.service.mapper

import com.patres.timetable.preference.PreferenceDependency
import com.patres.timetable.repository.*
import com.patres.timetable.service.dto.PreferenceDependencyDTO
import com.patres.timetable.service.util.getOneOrNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class PreferenceDependencyMapper : EntityMapper<PreferenceDependency, PreferenceDependencyDTO>() {

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

    override fun toEntity(entityDto: PreferenceDependencyDTO): PreferenceDependency {
        return PreferenceDependency(
            division = divisionRepository.findOneWithPreference(entityDto.divisionId),
            period = periodRepository.getOneOrNull(entityDto.periodId),
            teacher = teacherRepository.findOneWithPreference(entityDto.teacherId),
            subject = subjectRepository.findOneWithPreference(entityDto.subjectId),
            lesson = lessonRepository.getOneOrNull(entityDto.lessonId),
            place = placeRepository.findOneWithPreference(entityDto.placeId),
            date = entityDto.date,
            everyWeek = entityDto.everyWeek,
            startWithWeek = entityDto.startWithWeek,
            inMonday = entityDto.inMonday,
            inTuesday = entityDto.inTuesday,
            inWednesday = entityDto.inWednesday,
            inThursday = entityDto.inThursday,
            inFriday = entityDto.inFriday,
            inSaturday = entityDto.inSaturday,
            inSunday = entityDto.inSunday
        ).apply {
            entityDto.startTimeString?.let { setStartTimeHHmmFormatted(it) }
            entityDto.endTimeString?.let { setEndTimeHHmmFormatted(it) }
        }
    }

    override fun toDto(entity: PreferenceDependency): PreferenceDependencyDTO {
        return PreferenceDependencyDTO(
            periodId = entity.period?.id,
            placeId = entity.place?.id,
            lessonId = entity.lesson?.id,
            divisionId = entity.division?.id,
            subjectId = entity.subject?.id,
            teacherId = entity.teacher?.id,
            date = entity.date,
            everyWeek = entity.everyWeek,
            startWithWeek = entity.startWithWeek,
            inMonday = entity.inMonday,
            inTuesday = entity.inTuesday,
            inWednesday = entity.inWednesday,
            inThursday = entity.inThursday,
            inFriday = entity.inFriday,
            inSaturday = entity.inSaturday,
            inSunday = entity.inSunday
        ).apply {
            if (entity.lesson?.let { } != null) {
                startTimeString = entity.lesson.getStartTimeHHmmFormatted()
                endTimeString = entity.lesson.getEndTimeHHmmFormatted()
            } else {
                startTimeString = entity.getStartTimeHHmmFormatted()
                endTimeString = entity.getEndTimeHHmmFormatted()
            }
        }
    }


}

