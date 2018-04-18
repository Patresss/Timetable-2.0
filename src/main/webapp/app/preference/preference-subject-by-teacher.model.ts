import {BaseEntity} from '../shared';

export class PreferenceSubjectByTeacherModel implements BaseEntity {

    constructor(
        public id?: number,
        public teacherId?: number,
        public subjectId?: number,
        public points?: number,
        public teacherFullName = '',
        public subjectName = '') {
    }

}
