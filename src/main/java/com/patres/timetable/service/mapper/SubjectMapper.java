package com.patres.timetable.service.mapper;

import com.patres.timetable.domain.Division;
import com.patres.timetable.domain.Property;
import com.patres.timetable.domain.Subject;
import com.patres.timetable.service.dto.SubjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubjectMapper extends EntityMapper<Subject, SubjectDTO> {


    @Autowired
    private DivisionMapper divisionMapper;

    public Subject toEntity(SubjectDTO subjectDTO) {
        if (subjectDTO == null) {
            return null;
        }

        Subject subject = new Subject();

        subject.setDivisionOwner(divisionMapper.fromId(subjectDTO.getDivisionOwnerId()));
        subject.setId(subjectDTO.getId());
        subject.setName(subjectDTO.getName());
        subject.setShortName(subjectDTO.getShortName());
        subject.setColorBackground(subjectDTO.getColorBackground());
        subject.setColorText(subjectDTO.getColorText());

        return subject;
    }

    public SubjectDTO toDto(Subject subject) {
        if (subject == null) {
            return null;
        }

        SubjectDTO subjectDTO = new SubjectDTO();

        subjectDTO.setDivisionOwnerId(divisionMapper.getDivisionOwnerId(subject.getDivisionOwner()));
        subjectDTO.setDivisionOwnerName(divisionMapper.getDivisionOwnerName(subject.getDivisionOwner()));
        subjectDTO.setId(subject.getId());
        subjectDTO.setName(subject.getName());
        subjectDTO.setShortName(subject.getShortName());
        subjectDTO.setColorBackground(subject.getColorBackground());
        subjectDTO.setColorText(subject.getColorText());

        return subjectDTO;
    }

    public Subject fromId(Long id) {
        if (id == null) {
            return null;
        }
        Subject entity = new Subject();
        entity.setId(id);
        return entity;
    }

}
