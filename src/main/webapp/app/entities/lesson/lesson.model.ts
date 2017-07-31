import { BaseEntity } from './../../shared';

export class Lesson implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public startTime?: number,
        public endTime?: number,
        public timetables?: BaseEntity[],
        public divisionId?: number,
    ) {
    }
}
