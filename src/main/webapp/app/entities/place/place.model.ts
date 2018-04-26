import {BaseEntity} from './../../shared';
import {PreferenceSubjectByPlaceModel} from '../../preference/preference-subject-by-place.model';
import {PreferenceTeacherByPlaceModel} from '../../preference/preference-teacher-by-place.model';
import {PreferenceDivisionByPlaceModel} from '../../preference/preference-division-by-place.model';
import {PreferenceForDataTimeForPlaceModel} from '../../preference/preference-for-data-time-for-place.model';

export class Place implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public numberOfSeats?: number,
        public shortName?: string,
        public colorBackground?: string,
        public colorText?: string,
        public timetables?: BaseEntity[],
        public preferredSubjects?: BaseEntity[],
        public preferredDivisions?: BaseEntity[],
        public preferredTeachers?: BaseEntity[],
        public preferenceSubjectByPlace?: PreferenceSubjectByPlaceModel[],
        public preferenceTeacherByPlace?: PreferenceTeacherByPlaceModel[],
        public preferenceDivisionByPlace?: PreferenceDivisionByPlaceModel[],
        public preferencesDataTimeForPlace?: PreferenceForDataTimeForPlaceModel[],
        public divisionId?: number,
    ) {
    }
}
