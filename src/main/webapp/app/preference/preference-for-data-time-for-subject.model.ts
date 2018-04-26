import {DayOfWeek} from '../entities/timetable';
import {PreferenceForDataTimeModel} from './preference-for-data-time.model';

export class PreferenceForDataTimeForSubjectModel implements PreferenceForDataTimeModel {

    constructor(
        public id?: number,
        public subjectId?: number,
        public lessonId?: number,
        public dayOfWeek?: DayOfWeek,
        public points?: number,
        public subjectName = '',
        public lessonName = '') {
    }

}
