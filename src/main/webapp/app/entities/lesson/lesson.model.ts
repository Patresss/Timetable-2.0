import { BaseEntity } from './../../shared';

export class Lesson implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public startTime?: string,
        public endTime?: string,
        public timetables?: BaseEntity[],
        public divisionId?: number,
    ) {
    }
}
