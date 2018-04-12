import {DayOfWeek} from '../entities/timetable';
import {PreferenceForDataTimeModel} from './preference-for-data-time.model';

export class PreferenceForDataTimeForTeacherModel implements PreferenceForDataTimeModel {

    constructor(
        public id?: number,
        public teacherId?: number,
        public lessonId?: number,
        public dayOfWeek?: DayOfWeek,
        public points?: number,
        public teacherFullName = '',
        public lessonName = '') {
    }

}
