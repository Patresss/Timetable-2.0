import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {TimetableSharedModule} from '../shared';

import {HOME_ROUTE, HomeComponent} from './';

@NgModule({
    imports: [
        TimetableSharedModule,
        RouterModule.forRoot([HOME_ROUTE], {useHash: true})
    ],
    declarations: [
        HomeComponent,

    ],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TimetableHomeModule {
}
