package com.patres.timetable.service.mapper

import com.patres.timetable.domain.Teacher
import com.patres.timetable.repository.DivisionRepository
import com.patres.timetable.repository.LessonRepository
import com.patres.timetable.repository.SubjectRepository
import com.patres.timetable.service.dto.TeacherDTO
import com.patres.timetable.service.dto.preference.PreferenceDataTimeForTeacherDTO
import com.patres.timetable.service.dto.preference.PreferenceSubjectByTeacherDTO
import com.patres.timetable.service.mapper.preference.PreferenceDataTimeForTeacherMapper
import com.patres.timetable.service.mapper.preference.PreferenceSubjectByTeacherMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.DayOfWeek

@Service
open class TeacherMapper : EntityMapper<Teacher, TeacherDTO>() {

    @Autowired
    private lateinit var subjectMapper: SubjectMapper

    @Autowired
    private lateinit var preferenceDataTimeForTeacherMapper: PreferenceDataTimeForTeacherMapper

    @Autowired
    private lateinit var preferenceSubjectByTeacherMapper: PreferenceSubjectByTeacherMapper

    @Autowired
    private lateinit var divisionMapper: DivisionMapper

    @Autowired
    private lateinit var divisionRepository: DivisionRepository

    @Autowired
    private lateinit var subjectRepository: SubjectRepository

    @Autowired
    private lateinit var lessonRepository: LessonRepository

    override fun toEntity(entityDto: TeacherDTO): Teacher {
        return Teacher(
            name = entityDto.name,
            surname = entityDto.surname
        ).apply {
            divisionOwner = entityDto.divisionOwnerId?.let { divisionRepository.getOne(it) }
            id = entityDto.id
            degree = entityDto.degree
            shortName = entityDto.shortName
            preferenceSubjectByTeacher = preferenceSubjectByTeacherMapper.entityDTOSetToEntitySet(entityDto.preferenceSubjectByTeacher)
            preferenceDataTimeForTeachers = preferenceDataTimeForTeacherMapper.entityDTOSetToEntitySet(entityDto.preferenceDataTimeForTeachers)
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
            preferenceSubjectByTeacher = preferenceSubjectByTeacherMapper.entitySetToEntityDTOSet(entity.preferenceSubjectByTeacher)
            addNeutralPreferenceSubjectByTeacher()
            preferenceDataTimeForTeachers = preferenceDataTimeForTeacherMapper.entitySetToEntityDTOSet(entity.preferenceDataTimeForTeachers)
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
                    .map { subject -> PreferenceSubjectByTeacherDTO(teacherId = id, teacherFullName = fullName ?: "", subjectId = subject.id, subjectName = subject.name ?: "") }
            preferenceSubjectByTeacher += neutralPreferenceSubjectByTeacherToAdd
            preferenceSubjectByTeacher = preferenceSubjectByTeacher.sortedBy { it.subjectName }.toSet()
        }
    }

    private fun TeacherDTO.addNeutralPreferenceDataTimeForTeachers() {
        val neutralPreferenceDataTimeForTeachersToAdd = HashSet<PreferenceDataTimeForTeacherDTO>()
        divisionOwnerId?.let {
            val lessons = lessonRepository.findByDivisionOwnerId(it)
            val daysOfWeeks = DayOfWeek.values().map { it.value }
            for (lesson in lessons) {
                for (dayOfWeek in daysOfWeeks) {
                    if (!preferenceDataTimeForTeachers.any { it.dayOfWeek == dayOfWeek && it.lessonId == lesson.id }) {
                        neutralPreferenceDataTimeForTeachersToAdd.add(PreferenceDataTimeForTeacherDTO(teacherId = id, teacherFullName = fullName?: "", lessonId = lesson.id, lessonName = lesson.name?: "", dayOfWeek = dayOfWeek, points = 0))
                    }
                }
            }
            preferenceDataTimeForTeachers += neutralPreferenceDataTimeForTeachersToAdd
            preferenceDataTimeForTeachers = preferenceDataTimeForTeachers.sortedBy { it.dayOfWeek }.toSet()
        }
    }
}

