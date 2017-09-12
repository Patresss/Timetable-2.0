package com.patres.timetable.domain;

import com.patres.timetable.domain.enumeration.EventType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "timetable")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Timetable extends AbstractApplicationEntity implements Serializable {

    private static final long serialVersionUID = -2579744673761181702L;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "start_time")
    private Long startTime;

    @Column(name = "end_time")
    private Long endTime;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "jhi_date")
    private LocalDate date;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type", nullable = false)
    private EventType type;

    @Column(name = "every_week")
    private Long everyWeek;

    @Column(name = "start_with_week")
    private Long startWithWeek;

    @Column(name = "description")
    private String description;

    @Column(name = "color_background")
    private String colorBackground;

    @Column(name = "color_text")
    private String colorText;

    @Column(name = "in_monday")
    private Boolean inMonday;

    @Column(name = "in_tuesday")
    private Boolean inTuesday;

    @Column(name = "in_wednesday")
    private Boolean inWednesday;

    @Column(name = "in_thursday")
    private Boolean inThursday;

    @Column(name = "in_friday")
    private Boolean inFriday;

    @Column(name = "in_saturday")
    private Boolean inSaturday;

    @Column(name = "in_sunday")
    private Boolean inSunday;

    @ManyToOne
    private Place place;

    @ManyToOne
    private Subject subject;

    @ManyToOne
    private Teacher teacher;

    @ManyToOne
    private Division division;

    @ManyToOne
    private Lesson lesson;

    @ManyToOne
    private Period period;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timetable title(String title) {
        this.title = title;
        return this;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Timetable startTime(Long startTime) {
        this.startTime = startTime;
        return this;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Timetable endTime(Long endTime) {
        this.endTime = endTime;
        return this;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Timetable startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Timetable endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Timetable date(LocalDate date) {
        this.date = date;
        return this;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public Timetable type(EventType type) {
        this.type = type;
        return this;
    }

    public Long getEveryWeek() {
        return everyWeek;
    }

    public void setEveryWeek(Long everyWeek) {
        this.everyWeek = everyWeek;
    }

    public Timetable everyWeek(Long everyWeek) {
        this.everyWeek = everyWeek;
        return this;
    }

    public Long getStartWithWeek() {
        return startWithWeek;
    }

    public void setStartWithWeek(Long startWithWeek) {
        this.startWithWeek = startWithWeek;
    }

    public Timetable startWithWeek(Long startWithWeek) {
        this.startWithWeek = startWithWeek;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timetable description(String description) {
        this.description = description;
        return this;
    }

    public String getColorBackground() {
        return colorBackground;
    }

    public void setColorBackground(String colorBackground) {
        this.colorBackground = colorBackground;
    }

    public Timetable colorBackground(String colorBackground) {
        this.colorBackground = colorBackground;
        return this;
    }

    public String getColorText() {
        return colorText;
    }

    public void setColorText(String colorText) {
        this.colorText = colorText;
    }

    public Timetable colorText(String colorText) {
        this.colorText = colorText;
        return this;
    }

    public Boolean isInMonday() {
        return inMonday;
    }

    public Timetable inMonday(Boolean inMonday) {
        this.inMonday = inMonday;
        return this;
    }

    public void setInMonday(Boolean inMonday) {
        this.inMonday = inMonday;
    }

    public Boolean isInTuesday() {
        return inTuesday;
    }

    public Timetable inTuesday(Boolean inTuesday) {
        this.inTuesday = inTuesday;
        return this;
    }

    public void setInTuesday(Boolean inTuesday) {
        this.inTuesday = inTuesday;
    }

    public Boolean isInWednesday() {
        return inWednesday;
    }

    public Timetable inWednesday(Boolean inWednesday) {
        this.inWednesday = inWednesday;
        return this;
    }

    public void setInWednesday(Boolean inWednesday) {
        this.inWednesday = inWednesday;
    }

    public Boolean isInThursday() {
        return inThursday;
    }

    public Timetable inThursday(Boolean inThursday) {
        this.inThursday = inThursday;
        return this;
    }

    public void setInThursday(Boolean inThursday) {
        this.inThursday = inThursday;
    }

    public Boolean isInFriday() {
        return inFriday;
    }

    public Timetable inFriday(Boolean inFriday) {
        this.inFriday = inFriday;
        return this;
    }

    public void setInFriday(Boolean inFriday) {
        this.inFriday = inFriday;
    }

    public Boolean isInSaturday() {
        return inSaturday;
    }

    public Timetable inSaturday(Boolean inSaturday) {
        this.inSaturday = inSaturday;
        return this;
    }

    public void setInSaturday(Boolean inSaturday) {
        this.inSaturday = inSaturday;
    }

    public Boolean isInSunday() {
        return inSunday;
    }

    public Timetable inSunday(Boolean inSunday) {
        this.inSunday = inSunday;
        return this;
    }

    public void setInSunday(Boolean inSunday) {
        this.inSunday = inSunday;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Timetable place(Place place) {
        this.place = place;
        return this;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Timetable subject(Subject subject) {
        this.subject = subject;
        return this;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Timetable teacher(Teacher teacher) {
        this.teacher = teacher;
        return this;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public Timetable division(Division division) {
        this.division = division;
        return this;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public Timetable lesson(Lesson lesson) {
        this.lesson = lesson;
        return this;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public Timetable period(Period period) {
        this.period = period;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Timetable timetable = (Timetable) o;
        if (timetable.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), timetable.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Timetable{" +
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
