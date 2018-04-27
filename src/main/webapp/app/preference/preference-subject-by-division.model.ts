import {BaseEntity} from '../shared';

export class PreferenceSubjectByDivisionModel implements BaseEntity {

    constructor(
        public id?: number,
        public divisionId?: number,
        public subjectId?: number,
        public points?: number,
        public divisionName = '',
        public subjectName = '') {
    }

}
