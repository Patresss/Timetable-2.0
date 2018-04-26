import {DayOfWeek} from '../entities/timetable';
import {PreferenceForDataTimeModel} from './preference-for-data-time.model';

export class PreferenceForDataTimeForPlaceModel implements PreferenceForDataTimeModel {

    constructor(
        public id?: number,
        public placeId?: number,
        public lessonId?: number,
        public dayOfWeek?: DayOfWeek,
        public points?: number,
        public placeName = '',
        public lessonName = '') {
    }

}
