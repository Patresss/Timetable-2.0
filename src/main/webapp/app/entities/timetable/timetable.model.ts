import {BaseEntity} from '../../shared';
import {Time} from '../../util/time.model';

export enum EventType {
   LESSON = 'LESSON',
   SUBSTITUTION = 'SUBSTITUTION',
   SPECIAL = 'SPECIAL'
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
                public everyWeek?: number,
                public startWithWeek?: number,
                public description?: string,
                public colorBackground?: string,
                public colorText?: string,
                public inMonday?: boolean,
                public inTuesday?: boolean,
                public inWednesday?: boolean,
                public inThursday?: boolean,
                public inFriday?: boolean,
                public inSaturday?: boolean,
                public inSunday?: boolean,
                public placeId?: number,
                public placeName?: string,
                public subjectId?: number,
                public subjectName?: string,
                public subjectShortName?: string,
                public teacherId?: number,
                public teacherFullname?: string,
                public divisionId?: number,
                public divisionName?: string,
                public lessonId?: number,
                public lessonName?: number,
                public periodId?: number,
                public usersId: number[] = []) {
        this.inMonday = false;
        this.inTuesday = false;
        this.inWednesday = false;
        this.inThursday = false;
        this.inFriday = false;
        this.inSaturday = false;
        this.inSunday = false;
    }
}
