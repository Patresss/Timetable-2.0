import { BaseEntity } from './../../shared';
import {PreferenceForDataTimeForTeacherModel} from '../../preference/preference-for-data-time-for-teacher.model';

export class Teacher implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public surname?: string,
        public degree?: string,
        public shortName?: string,
        public fullName?: string,
        public timetables?: BaseEntity[],
        public preferredSubjects?: BaseEntity[],
        public preferenceDataTimeForTeachers?: PreferenceForDataTimeForTeacherModel[],
        public divisionOwnerId?: number,
        public preferredDivisions?: BaseEntity[],
        public preferredPlaces?: BaseEntity[],
    ) {
    }
}
