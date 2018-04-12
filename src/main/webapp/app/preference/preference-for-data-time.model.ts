import {DayOfWeek} from '../entities/timetable';
import {BaseEntity} from '../shared';

export class PreferenceForDataTimeModel implements BaseEntity {

    constructor(
        public id?: number,
        public lessonId?: number,
        public dayOfWeek?: DayOfWeek,
        public points?: number,
        public lessonName = "",
    ) {
    }

}
