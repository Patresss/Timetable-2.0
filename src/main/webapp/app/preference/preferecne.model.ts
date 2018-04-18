import {PreferenceHierarchy} from './preferecne-hierarchy.model';
import {LessonDayOfWeekPreferenceElement} from './lesson-day-of-week-element.model';
import {SelectType} from '../util/select-type.model';

export class Preference {

    static preferenceSelectTypes = [
        new SelectType(1, '', 'timetableApp.preference.hierarchy.10', 10),
        new SelectType(2, '', 'timetableApp.preference.hierarchy.5', 5),
        new SelectType(3, '', 'timetableApp.preference.hierarchy.3', 3),
        new SelectType(4, '', 'timetableApp.preference.hierarchy.2', 2),
        new SelectType(5, '', 'timetableApp.preference.hierarchy.1', 1),
        new SelectType(6, '', 'timetableApp.preference.hierarchy.0', 0),
        new SelectType(7, '', 'timetableApp.preference.hierarchy.-1', -1),
        new SelectType(8, '', 'timetableApp.preference.hierarchy.-2', -2),
        new SelectType(9, '', 'timetableApp.preference.hierarchy.-3', -3),
        new SelectType(10, '', 'timetableApp.preference.hierarchy.-5', -5),
        new SelectType(11, '', 'timetableApp.preference.hierarchy.-10', -10),
        new SelectType(12, '', 'timetableApp.preference.hierarchy.N', -1000)
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
