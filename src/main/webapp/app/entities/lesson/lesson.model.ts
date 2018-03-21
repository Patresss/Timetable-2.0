import { BaseEntity } from './../../shared';
import {Time} from '../../util/time.model';

export class Lesson implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public startTime?: Time,
        public endTime?: Time,
        public startTimeString?: string,
        public endTimeString?: string,
        public timetables?: BaseEntity[],
        public divisionId?: number,
    ) {
    }
}
