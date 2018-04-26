import {DayOfWeek} from '../entities/timetable';
import {PreferenceForDataTimeModel} from './preference-for-data-time.model';

export class PreferenceForDataTimeForDivisionModel implements PreferenceForDataTimeModel {

    constructor(
        public id?: number,
        public divisionId?: number,
        public lessonId?: number,
        public dayOfWeek?: DayOfWeek,
        public points?: number,
        public divisionName = '',
        public lessonName = '') {
    }

}
