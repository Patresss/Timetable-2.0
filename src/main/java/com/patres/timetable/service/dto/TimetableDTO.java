package com.patres.timetable.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.patres.timetable.domain.enumeration.EventType;

/**
 * A DTO for the Timetable entity.
 */
public class TimetableDTO implements Serializable {

    private Long id;

    @NotNull
    private String title;

    private Long startTime;

    private Long endTime;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDate date;

    @NotNull
    private EventType type;

    private Long everyWeek;

    private Long startWithWeek;

    private String description;

    private String colorBackground;

    private String colorText;

    private Boolean inMonday;

    private Boolean inTuesday;

    private Boolean inWednesday;

    private Boolean inThursday;

    private Boolean inFriday;

    private Boolean inSaturday;

    private Boolean inSunday;

    private Long placeId;

    private String placeName;

    private Long subjectId;

    private String subjectName;

    private Long teacherId;

    private String teacherSurname;

    private Long divisionId;

    private String divisionName;

    private Long lessonId;

    private String lessonName;

    private Long periodId;

    private String periodName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public Long getEveryWeek() {
        return everyWeek;
    }

    public void setEveryWeek(Long everyWeek) {
        this.everyWeek = everyWeek;
    }

    public Long getStartWithWeek() {
        return startWithWeek;
    }

    public void setStartWithWeek(Long startWithWeek) {
        this.startWithWeek = startWithWeek;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColorBackground() {
        return colorBackground;
    }

    public void setColorBackground(String colorBackground) {
        this.colorBackground = colorBackground;
    }

    public String getColorText() {
        return colorText;
    }

    public void setColorText(String colorText) {
        this.colorText = colorText;
    }

    public Boolean isInMonday() {
        return inMonday;
    }

    public void setInMonday(Boolean inMonday) {
        this.inMonday = inMonday;
    }

    public Boolean isInTuesday() {
        return inTuesday;
    }

    public void setInTuesday(Boolean inTuesday) {
        this.inTuesday = inTuesday;
    }

    public Boolean isInWednesday() {
        return inWednesday;
    }

    public void setInWednesday(Boolean inWednesday) {
        this.inWednesday = inWednesday;
    }

    public Boolean isInThursday() {
        return inThursday;
    }

    public void setInThursday(Boolean inThursday) {
        this.inThursday = inThursday;
    }

    public Boolean isInFriday() {
        return inFriday;
    }

    public void setInFriday(Boolean inFriday) {
        this.inFriday = inFriday;
    }

    public Boolean isInSaturday() {
        return inSaturday;
    }

    public void setInSaturday(Boolean inSaturday) {
        this.inSaturday = inSaturday;
    }

    public Boolean isInSunday() {
        return inSunday;
    }

    public void setInSunday(Boolean inSunday) {
        this.inSunday = inSunday;
    }

    public Long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherSurname() {
        return teacherSurname;
    }

    public void setTeacherSurname(String teacherSurname) {
        this.teacherSurname = teacherSurname;
    }

    public Long getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(Long divisionId) {
        this.divisionId = divisionId;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public Long getPeriodId() {
        return periodId;
    }

    public void setPeriodId(Long periodId) {
        this.periodId = periodId;
    }

    public String getPeriodName() {
        return periodName;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TimetableDTO timetableDTO = (TimetableDTO) o;
        if(timetableDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), timetableDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TimetableDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", date='" + getDate() + "'" +
            ", type='" + getType() + "'" +
            ", everyWeek='" + getEveryWeek() + "'" +
            ", startWithWeek='" + getStartWithWeek() + "'" +
            ", description='" + getDescription() + "'" +
            ", colorBackground='" + getColorBackground() + "'" +
            ", colorText='" + getColorText() + "'" +
            ", inMonday='" + isInMonday() + "'" +
            ", inTuesday='" + isInTuesday() + "'" +
            ", inWednesday='" + isInWednesday() + "'" +
            ", inThursday='" + isInThursday() + "'" +
            ", inFriday='" + isInFriday() + "'" +
            ", inSaturday='" + isInSaturday() + "'" +
            ", inSunday='" + isInSunday() + "'" +
            "}";
    }
}
