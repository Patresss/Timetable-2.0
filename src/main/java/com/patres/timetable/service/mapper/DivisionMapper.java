package com.patres.timetable.service.mapper;

import com.patres.timetable.domain.Division;
import com.patres.timetable.domain.Subject;
import com.patres.timetable.domain.Teacher;
import com.patres.timetable.domain.User;
import com.patres.timetable.service.dto.DivisionDTO;
import com.patres.timetable.service.dto.SubjectDTO;
import com.patres.timetable.service.dto.TeacherDTO;
import com.patres.timetable.service.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DivisionMapper extends EntityMapper<Division, DivisionDTO> {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private SubjectMapper subjectMapper;

    public Division toEntity(DivisionDTO divisionDTO) {
        if (divisionDTO == null) {
            return null;
        }

        Division division = new Division();
        division.setId(divisionDTO.getId());
        division.setName(divisionDTO.getName());
        division.setShortName(divisionDTO.getShortName());
        division.setNumberOfPeople(divisionDTO.getNumberOfPeople());
        division.setDivisionType(divisionDTO.getDivisionType());
        division.setColorBackground(divisionDTO.getColorBackground());
        division.setColorText(divisionDTO.getColorText());

        Set<Division> divisionSet = entityDTOSetToEntitySet(divisionDTO.getParents());
        division.setParents(divisionSet);
        Set<User> userSet = userMapper.userDTOSetToUserSet(divisionDTO.getUsers());
        division.setUsers(userSet);
        Set<Teacher> teacherSet = teacherMapper.entityDTOSetToEntitySet(divisionDTO.getPreferredTeachers());
        division.setPreferredTeachers(teacherSet);
        Set<Subject> subjectSet = subjectMapper.entityDTOSetToEntitySet(divisionDTO.getPreferredSubjects());
        division.setPreferredSubjects(subjectSet);

        return division;
    }

    public DivisionDTO toDto(Division entity) {
        if (entity == null) {
            return null;
        }

        DivisionDTO divisionDTO = new DivisionDTO();
        divisionDTO.setId(entity.getId());
        divisionDTO.setName(entity.getName());
        divisionDTO.setShortName(entity.getShortName());
        divisionDTO.setNumberOfPeople(entity.getNumberOfPeople());
        divisionDTO.setDivisionType(entity.getDivisionType());
        divisionDTO.setColorBackground(entity.getColorBackground());
        divisionDTO.setColorText(entity.getColorText());

        Set<DivisionDTO> divisionDtoSet = entitySetToEntityDTOSet(entity.getParents());
        divisionDTO.setParents(divisionDtoSet);
        Set<UserDTO> userDtoSet = userMapper.userSetToUserDTOSet(entity.getUsers());
        divisionDTO.setUsers(userDtoSet);
        Set<TeacherDTO> teacherDtoSet = teacherMapper.entitySetToEntityDTOSet(entity.getPreferredTeachers());
        divisionDTO.setPreferredTeachers(teacherDtoSet);
        Set<SubjectDTO> subjectDtoSet = subjectMapper.entitySetToEntityDTOSet(entity.getPreferredSubjects());
        divisionDTO.setPreferredSubjects(subjectDtoSet);

        return divisionDTO;
    }

    public Long getDivisionOwnerId(Division divisionOwner) {
        if (divisionOwner == null) {
            return null;
        }
        return divisionOwner.getId();
    }

    public String getDivisionOwnerName(Division divisionOwner) {
        if (divisionOwner == null) {
            return null;
        }
        return divisionOwner.getName();
    }


}
