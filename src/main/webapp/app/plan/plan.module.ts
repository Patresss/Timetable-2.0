import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {PLAN_ROUTE} from './plan.route';
import {PlanComponent} from './plan.component';
import {TimetableSharedModule} from '../shared';
import {AngularMultiSelectModule} from '../components/angular2-multiselect-dropdown';
import {TimetableElementComponent} from './timetable-element/timetable-popover.component';

@NgModule({
    imports: [
        TimetableSharedModule,
        RouterModule.forRoot([PLAN_ROUTE], { useHash: true }),
        AngularMultiSelectModule,

    ],
    declarations: [
        PlanComponent,
        TimetableElementComponent
    ],
    entryComponents: [
        PlanComponent,
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TimetablePlanModule {}
