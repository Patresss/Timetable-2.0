import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {PLAN_ROUTE} from './plan.route';
import {PlanComponent} from './plan.component';
import {TimetableSharedModule} from '../shared/shared.module';
import {AngularMultiSelectModule} from '../components/angular2-multiselect-dropdown';

@NgModule({
    imports: [
        TimetableSharedModule,
        RouterModule.forRoot([PLAN_ROUTE], { useHash: true }),
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
