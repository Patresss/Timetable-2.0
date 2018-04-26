import {BaseEntity} from '../shared';

export class PreferenceTeacherByPlaceModel implements BaseEntity {

    constructor(
        public id?: number,
        public teacherId?: number,
        public placeId?: number,
        public points?: number,
        public teacherFullName = '',
        public placeName = '') {
    }

}
