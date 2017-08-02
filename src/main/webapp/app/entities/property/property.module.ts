import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TimetableSharedModule } from '../../shared';
import {
    PropertyService,
    PropertyPopupService,
    PropertyComponent,
    PropertyDetailComponent,
    PropertyDialogComponent,
    PropertyPopupComponent,
    PropertyDeletePopupComponent,
    PropertyDeleteDialogComponent,
    propertyRoute,
    propertyPopupRoute,
    PropertyResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...propertyRoute,
    ...propertyPopupRoute,
];

@NgModule({
    imports: [
        TimetableSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PropertyComponent,
        PropertyDetailComponent,
        PropertyDialogComponent,
        PropertyDeleteDialogComponent,
        PropertyPopupComponent,
        PropertyDeletePopupComponent,
    ],
    entryComponents: [
        PropertyComponent,
        PropertyDialogComponent,
        PropertyPopupComponent,
        PropertyDeleteDialogComponent,
        PropertyDeletePopupComponent,
    ],
    providers: [
        PropertyService,
        PropertyPopupService,
        PropertyResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TimetablePropertiesModule {}
