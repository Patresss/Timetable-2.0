import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import {TimetableSharedModule} from '../shared';
import {PreferenceService} from './preference.service';

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
        PreferenceService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TimetablePreferenceModule {}
