import { BaseEntity } from './../../shared';
import {PreferenceForDataTimeForTeacherModel} from '../../preference/preference-for-data-time-for-teacher.model';
import {PreferenceSubjectByTeacherModel} from '../../preference/preference-subject-by-teacher.model';
import {PreferenceTeacherByPlaceModel} from '../../preference/preference-teacher-by-place.model';

export class Teacher implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public surname?: string,
        public degree?: string,
        public shortName?: string,
        public fullName?: string,
        public timetables?: BaseEntity[],
        public preferenceTeacherByPlace?: PreferenceTeacherByPlaceModel[],
        public preferenceSubjectByTeacher?: PreferenceSubjectByTeacherModel[],
        public preferenceDateTimeForTeachers?: PreferenceForDataTimeForTeacherModel[],
        public divisionOwnerId?: number,
    ) {
    }
}
