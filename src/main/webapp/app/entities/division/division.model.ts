import { BaseEntity, User } from './../../shared';

export const enum DivisionType {
    'SCHOOL',
    'CLASS',
    'SUBGROUP'
}

export class Division implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public shortName?: string,
        public numberOfPeople?: number,
        public divisionType?: DivisionType,
        public colorBackground?: string,
        public colorText?: string,
        public timetables?: BaseEntity[],
        public divisionPlaces?: BaseEntity[],
        public divisionTeachers?: BaseEntity[],
        public divisionSubjects?: BaseEntity[],
        public divisionLessons?: BaseEntity[],
        public divisionPeriods?: BaseEntity[],
        public divisionProperties?: BaseEntity[],
        public parents?: BaseEntity[],
        public users?: User[],
        public preferredTeachers?: BaseEntity[],
        public preferredSubjects?: BaseEntity[],
        public preferredPlaces?: BaseEntity[],
    ) {
    }
}
