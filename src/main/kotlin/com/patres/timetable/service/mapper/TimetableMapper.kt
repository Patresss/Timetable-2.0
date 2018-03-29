package com.patres.timetable.service.mapper

import com.patres.timetable.domain.Timetable
import com.patres.timetable.repository.*
import com.patres.timetable.service.dto.TimetableDTO
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

    override fun toEntity(entityDto: TimetableDTO): Timetable {
        return Timetable(
            title = entityDto.title,
            type = entityDto.type
        ).apply {
            entityDto.divisionId?.let {
                division = divisionRepository.getOne(entityDto.divisionId)
            }
            entityDto.periodId?.let {
                period = periodRepository.getOne(entityDto.periodId)
            }
            entityDto.teacherId?.let {
                teacher = teacherRepository.getOne(entityDto.teacherId)
            }
            entityDto.subjectId?.let {
                subject = subjectRepository.getOne(entityDto.subjectId)
            }
            entityDto.lessonId?.let {
                lesson = lessonRepository.getOne(entityDto.lessonId)
            }
            entityDto.placeId?.let {
                place = placeRepository.getOne(entityDto.placeId)
            }
            id = entityDto.id

            entityDto.startTimeString?.let {
                setStartTimeHHmmFormatted(it)
            }
            entityDto.endTimeString?.let {
                setEndTimeHHmmFormatted(it)
            }

            startDate = entityDto.startDate
            endDate = entityDto.endDate
            date = entityDto.date
            everyWeek = entityDto.everyWeek
            startWithWeek = entityDto.startWithWeek
            description = entityDto.description
            colorBackground = entityDto.colorBackground
            colorText = entityDto.colorText
            inMonday = entityDto.inMonday
            inTuesday = entityDto.inTuesday
            inWednesday = entityDto.inWednesday
            inThursday = entityDto.inThursday
            inFriday = entityDto.inFriday
            inSaturday = entityDto.inSaturday
            inSunday = entityDto.inSunday
        }
    }

    override fun toDto(entity: Timetable): TimetableDTO {
        return TimetableDTO(
            title = entity.title,
            type = entity.type
        ).apply {
            title = entity.title
            periodId = timetablePeriodId(entity)
            placeId = timetablePlaceId(entity)
            divisionName = timetableDivisionName(entity)
            lessonId = timetableLessonId(entity)
            periodName = timetablePeriodName(entity)
            subjectId = timetableSubjectId(entity)
            lessonName = timetableLessonName(entity)
            teacherId = timetableTeacherId(entity)
            divisionId = timetableDivisionId(entity)
            placeName = timetablePlaceName(entity)
            subjectName = timetableSubjectName(entity)
            subjectShortName = timetableSubjectShortName(entity)
            id = entity.id
            if (entity.lesson != null) {
                startTimeString = entity.lesson?.getStartTimeHHmmFormatted()
                endTimeString = entity.lesson?.getEndTimeHHmmFormatted()
            } else {
                startTimeString = entity.getStartTimeHHmmFormatted()
                endTimeString = entity.getEndTimeHHmmFormatted()
            }
            startDate = entity.startDate
            endDate = entity.endDate
            date = entity.date
            everyWeek = entity.everyWeek
            startWithWeek = entity.startWithWeek
            description = entity.description
            colorBackground = entity.colorBackground
            colorText = entity.colorText
            inMonday = entity.inMonday
            inTuesday = entity.inTuesday
            inWednesday = entity.inWednesday
            inThursday = entity.inThursday
            inFriday = entity.inFriday
            inSaturday = entity.inSaturday
            inSunday = entity.inSunday
            teacherFullName = entity.teacher?.getFullName()
        }
    }

    private fun timetablePeriodId(timetable: Timetable?): Long? {
        if (timetable == null) {
            return null
        }
        val period = timetable.period ?: return null
        return period.id
    }

    private fun timetablePlaceId(timetable: Timetable?): Long? {
        if (timetable == null) {
            return null
        }
        val place = timetable.place ?: return null
        return place.id
    }

    private fun timetableDivisionName(timetable: Timetable?): String? {
        if (timetable == null) {
            return null
        }
        val division = timetable.division ?: return null
        return division.name
    }

    private fun timetableLessonId(timetable: Timetable?): Long? {
        if (timetable == null) {
            return null
        }
        val lesson = timetable.lesson ?: return null
        return lesson.id
    }

    private fun timetablePeriodName(timetable: Timetable?): String? {
        if (timetable == null) {
            return null
        }
        val period = timetable.period ?: return null
        return period.name
    }

    private fun timetableTeacherSurname(timetable: Timetable?): String? {
        if (timetable == null) {
            return null
        }
        val teacher = timetable.teacher ?: return null
        return teacher.surname
    }

    private fun timetableSubjectId(timetable: Timetable?): Long? {
        if (timetable == null) {
            return null
        }
        val subject = timetable.subject ?: return null
        return subject.id
    }

    private fun timetableLessonName(timetable: Timetable?): String? {
        if (timetable == null) {
            return null
        }
        val lesson = timetable.lesson ?: return null
        return lesson.name
    }

    private fun timetableTeacherId(timetable: Timetable?): Long? {
        if (timetable == null) {
            return null
        }
        val teacher = timetable.teacher ?: return null
        return teacher.id
    }

    private fun timetableDivisionId(timetable: Timetable?): Long? {
        if (timetable == null) {
            return null
        }
        val division = timetable.division ?: return null
        return division.id
    }

    private fun timetablePlaceName(timetable: Timetable?): String? {
        if (timetable == null) {
            return null
        }
        val place = timetable.place ?: return null
        return place.name
    }

    private fun timetableSubjectName(timetable: Timetable?): String? {
        if (timetable == null) {
            return null
        }
        val subject = timetable.subject ?: return null
        return subject.name
    }

    private fun timetableSubjectShortName(timetable: Timetable?): String? {
        if (timetable == null) {
            return null
        }
        val subject = timetable.subject ?: return null
        return if (subject.shortName != null) subject.shortName else getShortName(subject.name)
    }

}
