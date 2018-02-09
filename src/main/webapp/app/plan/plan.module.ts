import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {planRoute} from './plan.route';
import {PlanComponent} from './plan.component';
import {TimetableSharedModule} from '../shared/shared.module';
import {AngularMultiSelectModule} from 'angular2-multiselect-dropdown';

const ENTITY_STATES = [
    ...planRoute,
];

@NgModule({
    imports: [
        TimetableSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        AngularMultiSelectModule
    ],
    declarations: [
        PlanComponent,
    ],
    entryComponents: [
        PlanComponent,
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TimetablePlanModule {}
