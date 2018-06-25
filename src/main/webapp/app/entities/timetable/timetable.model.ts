import {BaseEntity} from '../../shared';
import {Time} from '../../util/time.model';

export enum EventType {
    LESSON = 'LESSON',
    SUBSTITUTION = 'SUBSTITUTION',
    SPECIAL = 'SPECIAL'
}

export enum DayOfWeek {
    MONDAY = 1,
    TUESDAY = 2,
    WEDNESDAY = 3,
    THURSDAY = 4,
    FRIDAY = 5,
    SATURDAY = 6,
    SUNDAY = 7
}

export class Timetable implements BaseEntity {
    constructor(public id?: number,
                public title?: string,
                public startTime?: Time,
                public endTime?: Time,
                public startTimeString?: string,
                public endTimeString?: string,
                public date?: any,
                public type?: EventType,
                public divisionOwnerId?: number,
                public points?: number,
                public everyWeek?: number,
                public startWithWeek?: number,
                public description?: string,
                public colorBackground?: string,
                public colorBackgroundForTeacher?: string,
                public colorBackgroundForDivision?: string,
                public colorBackgroundForSubject?: string,
                public colorBackgroundForPlace?: string,
                public colorText?: string,
                public dayOfWeek?: number,
                public placeId?: number,
                public placeName?: string,
                public placeShortName?: string,
                public subjectId?: number,
                public subjectName?: string,
                public subjectShortName?: string,
                public teacherId?: number,
                public teacherFullName?: string,
                public teacherShortName?: string,
                public divisionId?: number,
                public divisionName?: string,
                public divisionShortName?: string,
                public lessonId?: number,
                public lessonName?: number,
                public periodId?: number,
                public periodName?: string,
                public series = false,
                public usersId: number[] = []) {}
}
