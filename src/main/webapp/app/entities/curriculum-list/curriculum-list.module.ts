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
import {AngularMultiSelectModule} from '../../components/angular2-multiselect-dropdown';
import {CurriculumListGenerateDialogComponent, CurriculumListGeneratePopupComponent} from './curriculum-list-generate-dialog.component';
import {ChartsModule} from 'ng2-charts';

const ENTITY_STATES = [
    ...curriculumListRoute,
    ...curriculumListPopupRoute,
];

@NgModule({
    imports: [
        TimetableSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        AngularMultiSelectModule,
        ChartsModule
    ],
    declarations: [
        CurriculumListComponent,
        CurriculumListDetailComponent,
        CurriculumListDialogComponent,
        CurriculumListDeleteDialogComponent,
        CurriculumListPopupComponent,
        CurriculumListDeletePopupComponent,
        CurriculumListGeneratePopupComponent,
        CurriculumListGenerateDialogComponent,
    ],
    entryComponents: [
        CurriculumListComponent,
        CurriculumListDialogComponent,
        CurriculumListPopupComponent,
        CurriculumListDeleteDialogComponent,
        CurriculumListDeletePopupComponent,
        CurriculumListGeneratePopupComponent,
        CurriculumListGenerateDialogComponent,
    ],
    providers: [
        CurriculumListService,
        CurriculumListPopupService,
        CurriculumListResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TimetableCurriculumListModule {}
