import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TimetableSharedModule } from '../../shared';
import { TimetableAdminModule } from '../../admin/admin.module';
import {
    DivisionService,
    DivisionPopupService,
    DivisionComponent,
    DivisionDetailComponent,
    DivisionDialogComponent,
    DivisionPopupComponent,
    DivisionDeletePopupComponent,
    DivisionDeleteDialogComponent,
    divisionRoute,
    divisionPopupRoute,
    DivisionResolvePagingParams,
} from './';
import {ColorPickerModule} from 'ngx-color-picker';

const ENTITY_STATES = [
    ...divisionRoute,
    ...divisionPopupRoute,
];

@NgModule({
    imports: [
        TimetableSharedModule,
        TimetableAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        ColorPickerModule // how to put it global
    ],
    declarations: [
        DivisionComponent,
        DivisionDetailComponent,
        DivisionDialogComponent,
        DivisionDeleteDialogComponent,
        DivisionPopupComponent,
        DivisionDeletePopupComponent,
    ],
    entryComponents: [
        DivisionComponent,
        DivisionDialogComponent,
        DivisionPopupComponent,
        DivisionDeleteDialogComponent,
        DivisionDeletePopupComponent,
    ],
    providers: [
        DivisionService,
        DivisionPopupService,
        DivisionResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TimetableDivisionModule {}
