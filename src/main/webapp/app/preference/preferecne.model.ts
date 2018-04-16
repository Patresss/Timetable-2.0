import {PreferenceHierarchy} from './preferecne-hierarchy.model';
import {LessonDayOfWeekPreferenceElement} from './lesson-day-of-week-element.model';

export class Preference {

    constructor(
        public preferredTeacherMap: Map<number, PreferenceHierarchy> = new Map(),
        public preferredSubjectMap: Map<number, PreferenceHierarchy> = new Map(),
        public preferredPlaceMap: Map<number, PreferenceHierarchy> = new Map(),
        public preferredDivisionMap: Map<number, PreferenceHierarchy> = new Map(),
        public preferredLessonAndDayOfWeekSet: Array<LessonDayOfWeekPreferenceElement> = []
        ) {
    }

}
