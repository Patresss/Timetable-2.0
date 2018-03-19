import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TimetableSharedModule } from '../../shared';
import {
    TimetableService,
    TimetablePopupService,
    TimetableComponent,
    TimetableDetailComponent,
    TimetableDialogComponent,
    TimetablePopupComponent,
    TimetableDeletePopupComponent,
    TimetableDeleteDialogComponent,
    timetableRoute,
    timetablePopupRoute,
    TimetableResolvePagingParams,
} from './';
import {AngularMultiSelectModule} from '../../components/angular2-multiselect-dropdown';

const ENTITY_STATES = [
    ...timetableRoute,
    ...timetablePopupRoute,
];

@NgModule({
    imports: [
        TimetableSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        AngularMultiSelectModule
    ],
    declarations: [
        TimetableComponent,
        TimetableDetailComponent,
        TimetableDialogComponent,
        TimetableDeleteDialogComponent,
        TimetablePopupComponent,
        TimetableDeletePopupComponent,
    ],
    entryComponents: [
        TimetableComponent,
        TimetableDialogComponent,
        TimetablePopupComponent,
        TimetableDeleteDialogComponent,
        TimetableDeletePopupComponent,
    ],
    providers: [
        TimetableService,
        TimetablePopupService,
        TimetableResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TimetableTimetableModule {}
