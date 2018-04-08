package com.patres.timetable.service.mapper

import com.patres.timetable.preference.PreferenceDependency
import com.patres.timetable.repository.*
import com.patres.timetable.service.dto.PreferenceDependencyDTO
import com.patres.timetable.service.util.getOneOrNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.DayOfWeek

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
            period = periodRepository.findOneWithIntervals(entityDto.periodId)?.let {it},
            teacher = teacherRepository.findOneWithPreference(entityDto.teacherId),
            subject = subjectRepository.findOneWithPreference(entityDto.subjectId),
            lesson = lessonRepository.getOneOrNull(entityDto.lessonId),
            place = placeRepository.findOneWithPreference(entityDto.placeId),
            notTimetableId = entityDto.notTimetableId,
            date = entityDto.date,
            everyWeek = entityDto.everyWeek,
            startWithWeek = entityDto.startWithWeek,
            divisionOwnerId = entityDto.divisionOwnerId
        ).apply {
            if (lesson != null) {
                startTime = lesson.startTime
                endTime = lesson.endTime
            } else {
                entityDto.startTimeString?.let { setStartTimeHHmmFormatted(it) }
                entityDto.endTimeString?.let { setEndTimeHHmmFormatted(it) }
            }

            if (period == null && date != null) {
                inMonday = date.dayOfWeek == DayOfWeek.MONDAY
                inTuesday = date.dayOfWeek == DayOfWeek.TUESDAY
                inWednesday = date.dayOfWeek == DayOfWeek.WEDNESDAY
                inThursday = date.dayOfWeek == DayOfWeek.THURSDAY
                inFriday = date.dayOfWeek == DayOfWeek.FRIDAY
                inSaturday = date.dayOfWeek == DayOfWeek.SATURDAY
                inSunday = date.dayOfWeek == DayOfWeek.SUNDAY
            } else {
                inMonday = entityDto.inMonday
                inTuesday = entityDto.inTuesday
                inWednesday = entityDto.inWednesday
                inThursday = entityDto.inThursday
                inFriday = entityDto.inFriday
                inSaturday = entityDto.inSaturday
                inSunday = entityDto.inSunday
            }

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
            notTimetableId = entity.notTimetableId,
            date = entity.date,
            everyWeek = entity.everyWeek,
            startWithWeek = entity.startWithWeek,
            inMonday = entity.inMonday,
            inTuesday = entity.inTuesday,
            inWednesday = entity.inWednesday,
            inThursday = entity.inThursday,
            inFriday = entity.inFriday,
            inSaturday = entity.inSaturday,
            inSunday = entity.inSunday,
            divisionOwnerId = entity.divisionOwnerId
        ).apply {
            if (entity.lesson != null) {
                startTimeString = entity.lesson.getStartTimeHHmmFormatted()
                endTimeString = entity.lesson.getEndTimeHHmmFormatted()
            } else {
                startTimeString = entity.getStartTimeHHmmFormatted()
                endTimeString = entity.getEndTimeHHmmFormatted()
            }
        }
    }


}

