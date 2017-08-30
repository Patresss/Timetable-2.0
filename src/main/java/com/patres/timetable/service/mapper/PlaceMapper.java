package com.patres.timetable.service.mapper;

import com.patres.timetable.domain.Division;
import com.patres.timetable.domain.Place;
import com.patres.timetable.domain.Subject;
import com.patres.timetable.domain.Teacher;
import com.patres.timetable.service.dto.DivisionDTO;
import com.patres.timetable.service.dto.PlaceDTO;
import com.patres.timetable.service.dto.SubjectDTO;
import com.patres.timetable.service.dto.TeacherDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PlaceMapper extends EntityMapper<Place, PlaceDTO> {

    @Autowired
    private SubjectMapper subjectMapper;
    @Autowired
    private DivisionMapper divisionMapper;
    @Autowired
    private TeacherMapper teacherMapper;

    public Place toEntity(PlaceDTO placeDTO) {
        if (placeDTO == null) {
            return null;
        }

        Place place = new Place();

        place.setDivisionOwner(divisionMapper.fromId(placeDTO.getDivisionOwnerId(), Division::new));
        place.setId(placeDTO.getId());
        place.setName(placeDTO.getName());
        place.setNumberOfSeats(placeDTO.getNumberOfSeats());
        place.setShortName(placeDTO.getShortName());
        place.setColorBackground(placeDTO.getColorBackground());
        place.setColorText(placeDTO.getColorText());

        Set<Subject> subjectSet = subjectMapper.entityDTOSetToEntitySet(placeDTO.getPreferredSubjects());
        place.setPreferredSubjects(subjectSet);
        Set<Division> divisionSet = divisionMapper.entityDTOSetToEntitySet(placeDTO.getPreferredDivisions());
        place.setPreferredDivisions(divisionSet);
        Set<Teacher> teacherSet = teacherMapper.entityDTOSetToEntitySet(placeDTO.getPreferredTeachers());
        place.setPreferredTeachers(teacherSet);

        return place;
    }

    public PlaceDTO toDto(Place place) {
        if (place == null) {
            return null;
        }

        PlaceDTO placeDTO = new PlaceDTO();

        placeDTO.setDivisionOwnerId(divisionMapper.getDivisionOwnerId(place.getDivisionOwner()));
        placeDTO.setDivisionOwnerName(divisionMapper.getDivisionOwnerName(place.getDivisionOwner()));
        placeDTO.setId(place.getId());
        placeDTO.setName(place.getName());
        placeDTO.setNumberOfSeats(place.getNumberOfSeats());
        placeDTO.setShortName(place.getShortName());
        placeDTO.setColorBackground(place.getColorBackground());
        placeDTO.setColorText(place.getColorText());

        Set<SubjectDTO> subjectDtoSet = subjectMapper.entitySetToEntityDTOSet(place.getPreferredSubjects());
        placeDTO.setPreferredSubjects(subjectDtoSet);
        Set<DivisionDTO> divisionDtoSet = divisionMapper.entitySetToEntityDTOSet(place.getPreferredDivisions());
        placeDTO.setPreferredDivisions(divisionDtoSet);
        Set<TeacherDTO> teacherDtoSet = teacherMapper.entitySetToEntityDTOSet(place.getPreferredTeachers());
        placeDTO.setPreferredTeachers(teacherDtoSet);

        return placeDTO;
    }

}
