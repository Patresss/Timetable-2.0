package com.patres.timetable.service.mapper;

import com.patres.timetable.domain.*;
import com.patres.timetable.service.dto.TimetableDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimetableMapper extends EntityMapper<Timetable, TimetableDTO> {

    @Autowired
    private PlaceMapper placeMapper;
    @Autowired
    private SubjectMapper subjectMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private DivisionMapper divisionMapper;
    @Autowired
    private LessonMapper lessonMapper;
    @Autowired
    private PeriodMapper periodMapper;

    public Timetable toEntity(TimetableDTO timetableDTO) {
        if (timetableDTO == null) {
            return null;
        }

        Timetable timetable = new Timetable();

        timetable.setDivision(divisionMapper.fromId(timetableDTO.getDivisionId()));
        timetable.setPeriod(periodMapper.fromId(timetableDTO.getPeriodId()));
        timetable.setTeacher(teacherMapper.fromId(timetableDTO.getTeacherId()));
        timetable.setSubject(subjectMapper.fromId(timetableDTO.getSubjectId()));
        timetable.setLesson(lessonMapper.fromId(timetableDTO.getLessonId()));
        timetable.setPlace(placeMapper.fromId(timetableDTO.getPlaceId()));
        timetable.setId(timetableDTO.getId());
        timetable.setTitle(timetableDTO.getTitle());
        timetable.setStartTime(timetableDTO.getStartTime());
        timetable.setEndTime(timetableDTO.getEndTime());
        timetable.setStartDate(timetableDTO.getStartDate());
        timetable.setEndDate(timetableDTO.getEndDate());
        timetable.setDate(timetableDTO.getDate());
        timetable.setType(timetableDTO.getType());
        timetable.setEveryWeek(timetableDTO.getEveryWeek());
        timetable.setStartWithWeek(timetableDTO.getStartWithWeek());
        timetable.setDescription(timetableDTO.getDescription());
        timetable.setColorBackground(timetableDTO.getColorBackground());
        timetable.setColorText(timetableDTO.getColorText());
        timetable.setInMonday(timetableDTO.isInMonday());
        timetable.setInTuesday(timetableDTO.isInTuesday());
        timetable.setInWednesday(timetableDTO.isInWednesday());
        timetable.setInThursday(timetableDTO.isInThursday());
        timetable.setInFriday(timetableDTO.isInFriday());
        timetable.setInSaturday(timetableDTO.isInSaturday());
        timetable.setInSunday(timetableDTO.isInSunday());

        return timetable;
    }

    public TimetableDTO toDto(Timetable timetable) {
        if (timetable == null) {
            return null;
        }

        TimetableDTO timetableDTO = new TimetableDTO();

        timetableDTO.setPeriodId(timetablePeriodId(timetable));
        timetableDTO.setPlaceId(timetablePlaceId(timetable));
        timetableDTO.setDivisionName(timetableDivisionName(timetable));
        timetableDTO.setLessonId(timetableLessonId(timetable));
        timetableDTO.setPeriodName(timetablePeriodName(timetable));
        timetableDTO.setTeacherSurname(timetableTeacherSurname(timetable));
        timetableDTO.setSubjectId(timetableSubjectId(timetable));
        timetableDTO.setLessonName(timetableLessonName(timetable));
        timetableDTO.setTeacherId(timetableTeacherId(timetable));
        timetableDTO.setDivisionId(timetableDivisionId(timetable));
        timetableDTO.setPlaceName(timetablePlaceName(timetable));
        timetableDTO.setSubjectName(timetableSubjectName(timetable));
        timetableDTO.setId(timetable.getId());
        timetableDTO.setTitle(timetable.getTitle());
        timetableDTO.setStartTime(timetable.getStartTime());
        timetableDTO.setEndTime(timetable.getEndTime());
        timetableDTO.setStartDate(timetable.getStartDate());
        timetableDTO.setEndDate(timetable.getEndDate());
        timetableDTO.setDate(timetable.getDate());
        timetableDTO.setType(timetable.getType());
        timetableDTO.setEveryWeek(timetable.getEveryWeek());
        timetableDTO.setStartWithWeek(timetable.getStartWithWeek());
        timetableDTO.setDescription(timetable.getDescription());
        timetableDTO.setColorBackground(timetable.getColorBackground());
        timetableDTO.setColorText(timetable.getColorText());
        timetableDTO.setInMonday(timetable.isInMonday());
        timetableDTO.setInTuesday(timetable.isInTuesday());
        timetableDTO.setInWednesday(timetable.isInWednesday());
        timetableDTO.setInThursday(timetable.isInThursday());
        timetableDTO.setInFriday(timetable.isInFriday());
        timetableDTO.setInSaturday(timetable.isInSaturday());
        timetableDTO.setInSunday(timetable.isInSunday());

        return timetableDTO;
    }


    public Timetable fromId(Long id) {
        if (id == null) {
            return null;
        }
        Timetable entity = new Timetable();
        entity.setId(id);
        return entity;
    }

    private Long timetablePeriodId(Timetable timetable) {
        if (timetable == null) {
            return null;
        }
        Period period = timetable.getPeriod();
        if (period == null) {
            return null;
        }
        return period.getId();
    }

    private Long timetablePlaceId(Timetable timetable) {
        if (timetable == null) {
            return null;
        }
        Place place = timetable.getPlace();
        if (place == null) {
            return null;
        }
        return place.getId();
    }

    private String timetableDivisionName(Timetable timetable) {
        if (timetable == null) {
            return null;
        }
        Division division = timetable.getDivision();
        if (division == null) {
            return null;
        }
        return division.getName();
    }

    private Long timetableLessonId(Timetable timetable) {
        if (timetable == null) {
            return null;
        }
        Lesson lesson = timetable.getLesson();
        if (lesson == null) {
            return null;
        }
        return lesson.getId();
    }

    private String timetablePeriodName(Timetable timetable) {
        if (timetable == null) {
            return null;
        }
        Period period = timetable.getPeriod();
        if (period == null) {
            return null;
        }
        return period.getName();
    }

    private String timetableTeacherSurname(Timetable timetable) {
        if (timetable == null) {
            return null;
        }
        Teacher teacher = timetable.getTeacher();
        if (teacher == null) {
            return null;
        }
        return teacher.getSurname();
    }

    private Long timetableSubjectId(Timetable timetable) {
        if (timetable == null) {
            return null;
        }
        Subject subject = timetable.getSubject();
        if (subject == null) {
            return null;
        }
        return subject.getId();
    }

    private String timetableLessonName(Timetable timetable) {
        if (timetable == null) {
            return null;
        }
        Lesson lesson = timetable.getLesson();
        if (lesson == null) {
            return null;
        }
        return lesson.getName();
    }

    private Long timetableTeacherId(Timetable timetable) {
        if (timetable == null) {
            return null;
        }
        Teacher teacher = timetable.getTeacher();
        if (teacher == null) {
            return null;
        }
        return teacher.getId();
    }

    private Long timetableDivisionId(Timetable timetable) {
        if (timetable == null) {
            return null;
        }
        Division division = timetable.getDivision();
        if (division == null) {
            return null;
        }
        return division.getId();
    }

    private String timetablePlaceName(Timetable timetable) {
        if (timetable == null) {
            return null;
        }
        Place place = timetable.getPlace();
        if (place == null) {
            return null;
        }
        return place.getName();
    }

    private String timetableSubjectName(Timetable timetable) {
        if (timetable == null) {
            return null;
        }
        Subject subject = timetable.getSubject();
        if (subject == null) {
            return null;
        }
        return subject.getName();
    }

}
