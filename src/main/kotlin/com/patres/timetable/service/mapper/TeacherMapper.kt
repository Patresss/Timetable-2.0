package com.patres.timetable.service.mapper

import com.patres.timetable.domain.Teacher
import com.patres.timetable.domain.preference.PreferenceSubjectByTeacher
import com.patres.timetable.repository.*
import com.patres.timetable.service.dto.TeacherDTO
import com.patres.timetable.service.dto.preference.relation.PreferenceDataTimeForTeacherDTO
import com.patres.timetable.service.dto.preference.relation.PreferenceSubjectByTeacherDTO
import com.patres.timetable.service.dto.preference.relation.PreferenceTeacherByPlaceDTO
import com.patres.timetable.service.mapper.preference.relation.PreferenceDataTimeForTeacherMapper
import com.patres.timetable.service.mapper.preference.relation.PreferenceSubjectByTeacherMapper
import com.patres.timetable.service.mapper.preference.relation.PreferenceTeacherByPlaceMapper
import com.patres.timetable.util.EntityUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.DayOfWeek

@Service
open class TeacherMapper : EntityMapper<Teacher, TeacherDTO>() {

    @Autowired
    private lateinit var preferenceDataTimeForTeacherMapper: PreferenceDataTimeForTeacherMapper

    @Autowired
    private lateinit var preferenceSubjectByTeacherMapper: PreferenceSubjectByTeacherMapper

    @Autowired
    private lateinit var preferenceTeacherByPlaceMapper: PreferenceTeacherByPlaceMapper

    @Autowired
    private lateinit var divisionMapper: DivisionMapper

    @Autowired
    private lateinit var divisionRepository: DivisionRepository

    @Autowired
    private lateinit var subjectRepository: SubjectRepository

    @Autowired
    private lateinit var lessonRepository: LessonRepository

    @Autowired
    private lateinit var placeRepository: PlaceRepository

    override fun toEntity(entityDto: TeacherDTO): Teacher {
        return Teacher(
            name = entityDto.name,
            surname = entityDto.surname
        ).apply {
            divisionOwner = entityDto.divisionOwnerId?.let { divisionRepository.getOne(it) }
            id = entityDto.id
            degree = entityDto.degree
            shortName = entityDto.shortName
            preferenceTeacherByPlace = preferenceTeacherByPlaceMapper.entityDTOSetToEntitySet(entityDto.preferenceTeacherByPlace)
            preferenceSubjectByTeacher = preferenceSubjectByTeacherMapper.entityDTOSetToEntitySet(entityDto.preferenceSubjectByTeacher)
            preferenceDateTimeForTeachers = preferenceDataTimeForTeacherMapper.entityDTOSetToEntitySet(entityDto.preferenceDateTimeForTeachers)

            preferenceTeacherByPlace.forEach { it.teacher = this }
            preferenceSubjectByTeacher.forEach { it.teacher = this }
            preferenceDateTimeForTeachers.forEach { it.teacher = this }

            if (colorBackground.isNullOrBlank()) {
                colorBackground = EntityUtil.calculateRandomColor()
            }

            addNeutralPreferenceSubjectByTeacher()
        }
    }

    override fun toDto(entity: Teacher): TeacherDTO {
        return TeacherDTO(
            name = entity.name,
            surname = entity.surname
        ).apply {
            divisionOwnerId = divisionMapper.getDivisionOwnerId(entity.divisionOwner)
            divisionOwnerName = divisionMapper.getDivisionOwnerName(entity.divisionOwner)
            id = entity.id
            degree = entity.degree
            shortName = entity.shortName
            fullName = "$degree $name $surname"
            preferenceTeacherByPlace = preferenceTeacherByPlaceMapper.entitySetToEntityDTOSet(entity.preferenceTeacherByPlace)
            addNeutralPreferenceTeacherByPlace()
            preferenceSubjectByTeacher = preferenceSubjectByTeacherMapper.entitySetToEntityDTOSet(entity.preferenceSubjectByTeacher)
            addNeutralPreferenceSubjectByTeacher()
            preferenceDateTimeForTeachers = preferenceDataTimeForTeacherMapper.entitySetToEntityDTOSet(entity.preferenceDateTimeForTeachers)
            addNeutralPreferenceDataTimeForTeachers()
        }
    }

    override fun toDtoWithSampleForm(entity: Teacher): TeacherDTO {
        return TeacherDTO(
            name = entity.name,
            surname = entity.surname
        ).apply {
            divisionOwnerId = divisionMapper.getDivisionOwnerId(entity.divisionOwner)
            divisionOwnerName = divisionMapper.getDivisionOwnerName(entity.divisionOwner)
            id = entity.id
            degree = entity.degree
            shortName = entity.shortName
            fullName = "$degree $name $surname"
        }
    }

    private fun TeacherDTO.addNeutralPreferenceSubjectByTeacher() {
        divisionOwnerId?.let {
            val subjects = subjectRepository.findByDivisionOwnerId(it)
            val neutralPreferenceSubjectByTeacherToAdd =
                subjects
                    .filter { subject -> !preferenceSubjectByTeacher.any { preference -> id == preference.teacherId && subject.id == preference.subjectId } }
                    .map { subject -> PreferenceSubjectByTeacherDTO(teacherId = id, teacherFullName = fullName ?: "", subjectId = subject.id, subjectName = subject.name ?: "", points = -10_000) }
            preferenceSubjectByTeacher += neutralPreferenceSubjectByTeacherToAdd
            preferenceSubjectByTeacher = preferenceSubjectByTeacher.sortedBy { it.subjectName }.toSet()
        }
    }

    private fun Teacher.addNeutralPreferenceSubjectByTeacher() {
        divisionOwner?.id?.let {
            val subjects = subjectRepository.findByDivisionOwnerId(it)
            val neutralPreferenceSubjectByTeacherToAdd =
                subjects
                    .filter { subject -> !preferenceSubjectByTeacher.any { preference -> id == preference.teacher?.id && subject.id == preference.subject?.id } }
                    .map { subject -> PreferenceSubjectByTeacher(teacher = this, subject = subject, points = -10_000) }
            preferenceSubjectByTeacher += neutralPreferenceSubjectByTeacherToAdd
        }
    }


    private fun TeacherDTO.addNeutralPreferenceTeacherByPlace() {
        divisionOwnerId?.let {
            val places = placeRepository.findByDivisionOwnerId(it)
            val neutralPreferenceToAdd =
                places
                    .filter { place -> !preferenceTeacherByPlace.any { preference -> id == preference.teacherId && place.id == preference.placeId } }
                    .map { place -> PreferenceTeacherByPlaceDTO(placeId = place.id, placeName = place.name ?: "", teacherId = id, teacherFullName = fullName ?: "", teacherDegree = degree ?: "", teacherName = name ?: "", teacherSurname = surname ?: "") }
            preferenceTeacherByPlace += neutralPreferenceToAdd
            preferenceTeacherByPlace = preferenceTeacherByPlace.sortedBy { it.placeName }.toSet()
        }
    }

    private fun TeacherDTO.addNeutralPreferenceDataTimeForTeachers() {
        val neutralPreferenceDataTimeForTeachersToAdd = HashSet<PreferenceDataTimeForTeacherDTO>()
        divisionOwnerId?.let {
            val lessons = lessonRepository.findByDivisionOwnerId(it)
            val daysOfWeeks = DayOfWeek.values().map { it.value }
            for (lesson in lessons) {
                for (dayOfWeek in daysOfWeeks) {
                    if (!preferenceDateTimeForTeachers.any { it.dayOfWeek == dayOfWeek && it.lessonId == lesson.id }) {
                        neutralPreferenceDataTimeForTeachersToAdd.add(PreferenceDataTimeForTeacherDTO(teacherId = id, teacherFullName = fullName ?: "", lessonId = lesson.id, lessonName = lesson.name ?: "", dayOfWeek = dayOfWeek, points = 0))
                    }
                }
            }
            preferenceDateTimeForTeachers += neutralPreferenceDataTimeForTeachersToAdd
            preferenceDateTimeForTeachers = preferenceDateTimeForTeachers.sortedBy { it.dayOfWeek }.toSet()
        }
    }
}

