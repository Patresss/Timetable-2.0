import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TimetableSharedModule } from '../../shared';
import {
    CurriculumService,
} from './';

const ENTITY_STATES = [
];

@NgModule({
    imports: [
        TimetableSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
    ],
    entryComponents: [
    ],
    providers: [
        CurriculumService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TimetableCurriculumModule {}
