package com.patres.timetable.service.mapper.preference

import com.patres.timetable.preference.PreferenceDependency
import com.patres.timetable.repository.*
import com.patres.timetable.repository.preference.PreferenceTeacherByPlaceRepository
import com.patres.timetable.service.dto.PreferenceDependencyDTO
import com.patres.timetable.service.mapper.EntityMapper
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

    @Autowired
    private lateinit var preferenceTeacherByPlaceRepository: PreferenceTeacherByPlaceRepository

    override fun toEntity(entityDto: PreferenceDependencyDTO): PreferenceDependency {
        return PreferenceDependency(
            division = divisionRepository.getOneOrNull(entityDto.divisionId),
            teacher = teacherRepository.getOneOrNull(entityDto.teacherId),
            subject = subjectRepository.getOneOrNull(entityDto.subjectId),
            lesson = lessonRepository.getOneOrNull(entityDto.lessonId),
            place = placeRepository.getOneOrNull(entityDto.placeId),
            notTimetableId = entityDto.notTimetableId,
            date = entityDto.date,
            everyWeek = entityDto.everyWeek,
            startWithWeek = entityDto.startWithWeek,
            divisionOwnerId = entityDto.divisionOwnerId,
            dayOfWeek = entityDto.dayOfWeek
        ).apply {
            if (lesson != null) {
                startTime = lesson?.startTime
                endTime = lesson?.endTime
            } else {
                entityDto.startTimeString?.let { setStartTimeHHmmFormatted(it) }
                entityDto.endTimeString?.let { setEndTimeHHmmFormatted(it) }
            }


            if (entityDto.periodId != null) {
                period = periodRepository.findOneWithIntervals(entityDto.periodId)
            } else if (entityDto.date != null) {
                date = entityDto.date
                dayOfWeek = date?.dayOfWeek?.value
            }

//            teacher?.id?.let { teacherId -> teacher?.preferenceTeacherByPlace = preferenceTeacherByPlaceRepository.findByTeacherId(teacherId = teacherId)}
//            place?.id?.let { placeId -> place?.preferenceTeacherByPlace = preferenceTeacherByPlaceRepository.findByPlaceId(placeId = placeId)}
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
            dayOfWeek = entity.dayOfWeek,
            divisionOwnerId = entity.divisionOwnerId
        ).apply {
            if (entity.lesson != null) {
                startTimeString = entity.lesson?.getStartTimeHHmmFormatted()
                endTimeString = entity.lesson?.getEndTimeHHmmFormatted()
            } else {
                startTimeString = entity.getStartTimeHHmmFormatted()
                endTimeString = entity.getEndTimeHHmmFormatted()
            }
        }
    }


}

