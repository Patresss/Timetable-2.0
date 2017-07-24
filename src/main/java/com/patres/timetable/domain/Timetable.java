package com.patres.timetable.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.patres.timetable.domain.enumeration.EventType;

/**
 * A Timetable.
 */
@Entity
@Table(name = "timetable")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "timetable")
public class Timetable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Timetable title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getStartTime() {
        return startTime;
    }

    public Timetable startTime(Long startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public Timetable endTime(Long endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Timetable startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Timetable endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getDate() {
        return date;
    }

    public Timetable date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public EventType getType() {
        return type;
    }

    public Timetable type(EventType type) {
        this.type = type;
        return this;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public Long getEveryWeek() {
        return everyWeek;
    }

    public Timetable everyWeek(Long everyWeek) {
        this.everyWeek = everyWeek;
        return this;
    }

    public void setEveryWeek(Long everyWeek) {
        this.everyWeek = everyWeek;
    }

    public Long getStartWithWeek() {
        return startWithWeek;
    }

    public Timetable startWithWeek(Long startWithWeek) {
        this.startWithWeek = startWithWeek;
        return this;
    }

    public void setStartWithWeek(Long startWithWeek) {
        this.startWithWeek = startWithWeek;
    }

    public String getDescription() {
        return description;
    }

    public Timetable description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColorBackground() {
        return colorBackground;
    }

    public Timetable colorBackground(String colorBackground) {
        this.colorBackground = colorBackground;
        return this;
    }

    public void setColorBackground(String colorBackground) {
        this.colorBackground = colorBackground;
    }

    public String getColorText() {
        return colorText;
    }

    public Timetable colorText(String colorText) {
        this.colorText = colorText;
        return this;
    }

    public void setColorText(String colorText) {
        this.colorText = colorText;
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

    public Timetable place(Place place) {
        this.place = place;
        return this;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Subject getSubject() {
        return subject;
    }

    public Timetable subject(Subject subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Timetable teacher(Teacher teacher) {
        this.teacher = teacher;
        return this;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Division getDivision() {
        return division;
    }

    public Timetable division(Division division) {
        this.division = division;
        return this;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public Timetable lesson(Lesson lesson) {
        this.lesson = lesson;
        return this;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public Period getPeriod() {
        return period;
    }

    public Timetable period(Period period) {
        this.period = period;
        return this;
    }

    public void setPeriod(Period period) {
        this.period = period;
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
