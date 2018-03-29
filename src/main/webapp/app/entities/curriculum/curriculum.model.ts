import {BaseEntity} from '../../shared';
import {EventType} from '../timetable';
import {Time} from '../../util/time.model';

export class Curriculum implements BaseEntity {
    // TODO - czy potrzeba name?
    constructor(public id?: number,
                public name?: string,
                public startTime?: Time,
                public endTime?: Time,
                public type?: EventType,
                public numberOfActivities?: number,
                public everyWeek?: number,
                public startWithWeek?: number,
                public placeId?: number,
                public placeName?: string,
                public subjectId?: number,
                public subjectName?: string,
                public teacherId?: number,
                public teacherFullName?: string,
                public divisionId?: number,
                public divisionName?: string,
                public lessonId?: number) {
    }
}
