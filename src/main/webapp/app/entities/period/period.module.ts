import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TimetableSharedModule } from '../../shared';
import {
    PeriodService,
    PeriodPopupService,
    PeriodComponent,
    PeriodDetailComponent,
    PeriodDialogComponent,
    PeriodPopupComponent,
    PeriodDeletePopupComponent,
    PeriodDeleteDialogComponent,
    periodRoute,
    periodPopupRoute,
    PeriodResolvePagingParams,
} from './';
import {IntervalIncludedFilter} from '../../shared/interval-filter.pipe';

const ENTITY_STATES = [
    ...periodRoute,
    ...periodPopupRoute,
];

@NgModule({
    imports: [
        TimetableSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PeriodComponent,
        PeriodDetailComponent,
        PeriodDialogComponent,
        PeriodDeleteDialogComponent,
        PeriodPopupComponent,
        PeriodDeletePopupComponent,
        IntervalIncludedFilter
    ],
    entryComponents: [
        PeriodComponent,
        PeriodDialogComponent,
        PeriodPopupComponent,
        PeriodDeleteDialogComponent,
        PeriodDeletePopupComponent,
    ],
    providers: [
        PeriodService,
        PeriodPopupService,
        PeriodResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TimetablePeriodModule {}
