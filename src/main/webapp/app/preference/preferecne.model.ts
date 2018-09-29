import {PreferenceHierarchy} from './preferecne-hierarchy.model';
import {LessonDayOfWeekPreferenceElement} from './lesson-day-of-week-element.model';
import {SelectType} from '../util/select-type.model';

export class Preference {

    static preferenceSelectTypes = [
        new SelectType(1, '', 'timetableApp.preferenceLessonAndDayOfWeekHierarchy.hierarchy.10', 10),
        new SelectType(2, '', 'timetableApp.preferenceLessonAndDayOfWeekHierarchy.hierarchy.5', 5),
        new SelectType(3, '', 'timetableApp.preferenceLessonAndDayOfWeekHierarchy.hierarchy.3', 3),
        new SelectType(4, '', 'timetableApp.preferenceLessonAndDayOfWeekHierarchy.hierarchy.2', 2),
        new SelectType(5, '', 'timetableApp.preferenceLessonAndDayOfWeekHierarchy.hierarchy.1', 1),
        new SelectType(6, '', 'timetableApp.preferenceLessonAndDayOfWeekHierarchy.hierarchy.0', 0),
        new SelectType(7, '', 'timetableApp.preferenceLessonAndDayOfWeekHierarchy.hierarchy.-1', -1),
        new SelectType(8, '', 'timetableApp.preferenceLessonAndDayOfWeekHierarchy.hierarchy.-2', -2),
        new SelectType(9, '', 'timetableApp.preferenceLessonAndDayOfWeekHierarchy.hierarchy.-3', -3),
        new SelectType(10, '', 'timetableApp.preferenceLessonAndDayOfWeekHierarchy.hierarchy.-5', -5),
        new SelectType(11, '', 'timetableApp.preferenceLessonAndDayOfWeekHierarchy.hierarchy.-10', -10),
        new SelectType(12, '', 'timetableApp.preferenceLessonAndDayOfWeekHierarchy.hierarchy.N', -10000)
    ];

    constructor(
        public preferredTeacherMap: Map<number, PreferenceHierarchy> = new Map(),
        public preferredSubjectMap: Map<number, PreferenceHierarchy> = new Map(),
        public preferredPlaceMap: Map<number, PreferenceHierarchy> = new Map(),
        public preferredDivisionMap: Map<number, PreferenceHierarchy> = new Map(),
        public preferredLessonAndDayOfWeekSet: Array<LessonDayOfWeekPreferenceElement> = []
    ) {
    }

}
