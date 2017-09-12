import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TimetableSharedModule } from '../../shared';
import {
    IntervalService,
    IntervalPopupService,
    IntervalComponent,
    IntervalDetailComponent,
    IntervalDialogComponent,
    IntervalPopupComponent,
    IntervalDeletePopupComponent,
    IntervalDeleteDialogComponent,
    intervalRoute,
    intervalPopupRoute,
    IntervalResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...intervalRoute,
    ...intervalPopupRoute,
];

@NgModule({
    imports: [
        TimetableSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        IntervalComponent,
        IntervalDetailComponent,
        IntervalDialogComponent,
        IntervalDeleteDialogComponent,
        IntervalPopupComponent,
        IntervalDeletePopupComponent,
    ],
    entryComponents: [
        IntervalComponent,
        IntervalDialogComponent,
        IntervalPopupComponent,
        IntervalDeleteDialogComponent,
        IntervalDeletePopupComponent,
    ],
    providers: [
        IntervalService,
        IntervalPopupService,
        IntervalResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TimetableIntervalModule {}
