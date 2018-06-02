import { BaseEntity, User } from './../../shared';
import {PreferenceForDataTimeForSubjectModel} from '../../preference/preference-for-data-time-for-subject.model';
import {PreferenceForDataTimeForDivisionModel} from '../../preference/preference-for-data-time-for-division.model';
import {PreferenceSubjectByDivisionModel} from '../../preference/preference-subject-by-division.model';
import {PreferenceTeacherByDivisionModel} from '../../preference/preference-teacher-by-division.model';
import {PreferenceDivisionByPlaceModel} from '../../preference/preference-division-by-place.model';

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
        public preferencesSubjectByDivision?: PreferenceSubjectByDivisionModel[],
        public preferencesTeacherByDivision?: PreferenceTeacherByDivisionModel[],
        public preferenceDivisionByPlace?: PreferenceDivisionByPlaceModel[],
        public preferencesDataTimeForDivision: PreferenceForDataTimeForDivisionModel[] = [],
        public divisionOwnerId?: number
    ) {
    }
}
