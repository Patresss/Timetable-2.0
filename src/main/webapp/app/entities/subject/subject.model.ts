import {BaseEntity} from './../../shared';
import {PreferenceForDataTimeForSubjectModel} from '../../preference/preference-for-data-time-for-subject.model';

export class Subject implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public shortName?: string,
        public colorBackground?: string,
        public colorText?: string,
        public timetables?: BaseEntity[],
        public divisionId?: number,
        public preferredTeachers?: BaseEntity[],
        public preferredDivisions?: BaseEntity[],
        public preferredPlaces?: BaseEntity[],
        public preferencesDataTimeForSubject?: PreferenceForDataTimeForSubjectModel[],
    ) {
    }
}
