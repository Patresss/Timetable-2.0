package com.patres.timetable.service.mapper;

import com.patres.timetable.domain.Division;
import com.patres.timetable.domain.Subject;
import com.patres.timetable.domain.Teacher;
import com.patres.timetable.service.dto.SubjectDTO;
import com.patres.timetable.service.dto.TeacherDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TeacherMapper extends EntityMapper<Teacher, TeacherDTO> {

    @Autowired
    private SubjectMapper subjectMapper;
    @Autowired
    private DivisionMapper divisionMapper;

    public Teacher toEntity(TeacherDTO teacherDTO) {
        if (teacherDTO == null) {
            return null;
        }

        Teacher teacher = new Teacher();

        teacher.setDivisionOwner(divisionMapper.fromId(teacherDTO.getDivisionOwnerId(), Division::new));
        teacher.setId(teacherDTO.getId());
        teacher.setName(teacherDTO.getName());
        teacher.setSurname(teacherDTO.getSurname());
        teacher.setDegree(teacherDTO.getDegree());
        teacher.setShortName(teacherDTO.getShortName());
        Set<Subject> set = subjectMapper.entityDTOSetToEntitySet(teacherDTO.getPreferredSubjects());
        if (set != null) {
            teacher.setPreferredSubjects(set);
        }

        return teacher;
    }

    public TeacherDTO toDto(Teacher teacher) {
        if (teacher == null) {
            return null;
        }

        TeacherDTO teacherDTO = new TeacherDTO();

        teacherDTO.setDivisionOwnerId(divisionMapper.getDivisionOwnerId(teacher.getDivisionOwner()));
        teacherDTO.setDivisionOwnerName(divisionMapper.getDivisionOwnerName(teacher.getDivisionOwner()));
        teacherDTO.setId(teacher.getId());
        teacherDTO.setName(teacher.getName());
        teacherDTO.setSurname(teacher.getSurname());
        teacherDTO.setDegree(teacher.getDegree());
        teacherDTO.setShortName(teacher.getShortName());
        Set<SubjectDTO> set = subjectMapper.entitySetToEntityDTOSet(teacher.getPreferredSubjects());
        if (set != null) {
            teacherDTO.setPreferredSubjects(set);
        }

        return teacherDTO;
    }


}
