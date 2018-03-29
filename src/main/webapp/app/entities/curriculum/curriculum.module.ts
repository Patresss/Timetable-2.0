import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TimetableSharedModule } from '../../shared';
import {
    CurriculumService,
} from './';
import {AngularMultiSelectModule} from '../../components/angular2-multiselect-dropdown';
import {CurriculumComponent} from './curriculum.component';
import {CurriculumDialogComponent, CurriculumPopupComponent} from './curriculum-dialog.component';
import {CurriculumDetailComponent} from './curriculum-detail.component';
import {CurriculumDeleteDialogComponent, CurriculumDeletePopupComponent} from './curriculum-delete-dialog.component';
import {CurriculumPopupService} from './curriculum-popup.service';
import {curriculumPopupRoute, CurriculumResolvePagingParams, curriculumRoute} from './curriculum.route';

const ENTITY_STATES = [
    ...curriculumRoute,
    ...curriculumPopupRoute,
];

@NgModule({
    imports: [
        TimetableSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        AngularMultiSelectModule
    ],
    declarations: [
        CurriculumComponent,
        CurriculumDetailComponent,
        CurriculumDialogComponent,
        CurriculumDeleteDialogComponent,
        CurriculumPopupComponent,
        CurriculumDeletePopupComponent,
    ],
    entryComponents: [
        CurriculumComponent,
        CurriculumDialogComponent,
        CurriculumPopupComponent,
        CurriculumDeleteDialogComponent,
        CurriculumDeletePopupComponent,
    ],
    providers: [
        CurriculumService,
        CurriculumPopupService,
        CurriculumResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TimetableCurriculumModule {}
