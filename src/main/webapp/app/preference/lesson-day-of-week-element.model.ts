import {PreferenceHierarchy} from './preferecne-hierarchy.model';

export class LessonDayOfWeekPreferenceElement {

    constructor(
        public dayOfWeek: number,
        public lessonId: number,
        public preferenceLessonAndDayOfWeekHierarchy = new PreferenceHierarchy()) {
    }
}
