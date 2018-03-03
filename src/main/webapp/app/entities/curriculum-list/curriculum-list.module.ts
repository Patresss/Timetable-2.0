import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TimetableSharedModule } from '../../shared';
import {CurriculumListComponent} from './curriculum-list.component';
import {CurriculumListDetailComponent} from './curriculum-list-detail.component';
import {CurriculumListDialogComponent, CurriculumListPopupComponent} from './curriculum-list-dialog.component';
import {CurriculumListDeleteDialogComponent, CurriculumListDeletePopupComponent} from './curriculum-list-delete-dialog.component';
import {CurriculumListService} from './curriculum-list.service';
import {CurriculumListPopupService} from './curriculum-list-popup.service';
import {curriculumListPopupRoute, CurriculumListResolvePagingParams, curriculumListRoute} from './curriculum-list.route';

const ENTITY_STATES = [
    ...curriculumListRoute,
    ...curriculumListPopupRoute,
];

@NgModule({
    imports: [
        TimetableSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CurriculumListComponent,
        CurriculumListDetailComponent,
        CurriculumListDialogComponent,
        CurriculumListDeleteDialogComponent,
        CurriculumListPopupComponent,
        CurriculumListDeletePopupComponent
    ],
    entryComponents: [
        CurriculumListComponent,
        CurriculumListDialogComponent,
        CurriculumListPopupComponent,
        CurriculumListDeleteDialogComponent,
        CurriculumListDeletePopupComponent,
    ],
    providers: [
        CurriculumListService,
        CurriculumListPopupService,
        CurriculumListResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TimetableCurriculumListModule {}
