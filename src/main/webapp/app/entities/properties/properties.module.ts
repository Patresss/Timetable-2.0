import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TimetableSharedModule } from '../../shared';
import {
    PropertiesService,
    PropertiesPopupService,
    PropertiesComponent,
    PropertiesDetailComponent,
    PropertiesDialogComponent,
    PropertiesPopupComponent,
    PropertiesDeletePopupComponent,
    PropertiesDeleteDialogComponent,
    propertiesRoute,
    propertiesPopupRoute,
    PropertiesResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...propertiesRoute,
    ...propertiesPopupRoute,
];

@NgModule({
    imports: [
        TimetableSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PropertiesComponent,
        PropertiesDetailComponent,
        PropertiesDialogComponent,
        PropertiesDeleteDialogComponent,
        PropertiesPopupComponent,
        PropertiesDeletePopupComponent,
    ],
    entryComponents: [
        PropertiesComponent,
        PropertiesDialogComponent,
        PropertiesPopupComponent,
        PropertiesDeleteDialogComponent,
        PropertiesDeletePopupComponent,
    ],
    providers: [
        PropertiesService,
        PropertiesPopupService,
        PropertiesResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TimetablePropertiesModule {}
