import {BaseEntity} from '../shared';

export class PreferenceTeacherByDivisionModel implements BaseEntity {

    constructor(
        public id?: number,
        public divisionId?: number,
        public teacherId?: number,
        public points?: number,
        public divisionName = '',
        public teacherFullName = '') {
    }

}
