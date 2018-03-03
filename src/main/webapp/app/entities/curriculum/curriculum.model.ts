import {BaseEntity} from '../../shared';
import {EventType} from '../timetable';
import {Time} from '../../plan/time.model';

export class Curriculum implements BaseEntity {
    // TODO - czy potrzeba name?
    constructor(public id?: number,
                public startTime?: Time,
                public endTime?: Time,
                public type?: EventType,
                public everyWeek?: number,
                public startWithWeek?: number,
                public placeId?: number,
                public subjectId?: number,
                public teacherId?: number,
                public divisionId?: number,
                public lessonId?: number,
                public curriculumListId?: number) {
    }
}
