import { BaseEntity } from './../../shared';

export class Teacher implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public surname?: string,
        public degree?: string,
        public shortName?: string,
        public timetables?: BaseEntity[],
        public preferredSubjects?: BaseEntity[],
        public divisionId?: number,
        public preferredDivisions?: BaseEntity[],
        public preferredPlaces?: BaseEntity[],
    ) {
    }
}
