import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { TimetableDivisionModule } from './division/division.module';
import { TimetablePropertiesModule } from './properties/properties.module';
import { TimetableTeacherModule } from './teacher/teacher.module';
import { TimetableSubjectModule } from './subject/subject.module';
import { TimetablePlaceModule } from './place/place.module';
import { TimetableTimetableModule } from './timetable/timetable.module';
import { TimetableLessonModule } from './lesson/lesson.module';
import { TimetablePeriodModule } from './period/period.module';
import { TimetableIntervalModule } from './interval/interval.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

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
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TimetableEntityModule {}
