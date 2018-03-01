import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {TimetableDivisionModule} from './division/division.module';
import {TimetablePropertiesModule} from './property/property.module';
import {TimetableTeacherModule} from './teacher/teacher.module';
import {TimetableSubjectModule} from './subject/subject.module';
import {TimetablePlaceModule} from './place/place.module';
import {TimetableTimetableModule} from './timetable/timetable.module';
import {TimetableLessonModule} from './lesson/lesson.module';
import {TimetablePeriodModule} from './period/period.module';
import {TimetableIntervalModule} from './interval/interval.module';
import {TimetableCurriculumListModule} from './curriculum-list/curriculum-list.module';
import {TimetableCurriculumModule} from './curriculum/curriculum.module';

@NgModule({
    imports: [
        TimetableDivisionModule,
        TimetablePropertiesModule,
        TimetableTeacherModule,
        TimetableSubjectModule,
        TimetablePlaceModule,
        TimetableTimetableModule,
        TimetableLessonModule,
        TimetablePeriodModule,
        TimetableIntervalModule,
        TimetableCurriculumListModule,
        TimetableCurriculumModule,
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TimetableEntityModule {
}
