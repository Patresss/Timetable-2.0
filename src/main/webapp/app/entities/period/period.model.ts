import { BaseEntity } from './../../shared';

export class Period implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public intervalTimes?: BaseEntity[],
        public timetables?: BaseEntity[],
        public divisionId?: number,
    ) {
    }
}
