import { BaseEntity } from './../../shared';

export const enum EventType {
    'LESSON',
    'SUBSTITUTION',
    'SPECIAL'
}

export class Timetable implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public startTime?: number,
        public endTime?: number,
        public startDate?: any,
        public endDate?: any,
        public date?: any,
        public type?: EventType,
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
        public subjectId?: number,
        public teacherId?: number,
        public divisionId?: number,
        public lessonId?: number,
        public periodId?: number,
    ) {
        this.inMonday = false;
        this.inTuesday = false;
        this.inWednesday = false;
        this.inThursday = false;
        this.inFriday = false;
        this.inSaturday = false;
        this.inSunday = false;
    }
}
