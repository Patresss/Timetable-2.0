import {BaseEntity} from '../shared';

export class PreferenceSubjectByPlaceModel implements BaseEntity {

    constructor(
        public id?: number,
        public placeId?: number,
        public subjectId?: number,
        public points?: number,
        public placeName = '',
        public subjectName = '') {
    }

}
