import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {RouterModule} from '@angular/router';

import {TimetableSharedModule} from '../shared';

import {HOME_ROUTE, HomeComponent} from './';
import {GlassComponent} from "../components/glass/glass.component";

@NgModule({
  imports: [
    TimetableSharedModule,
    RouterModule.forRoot([HOME_ROUTE], {useHash: true})
  ],
  declarations: [
    HomeComponent,
    GlassComponent,
  ],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TimetableHomeModule {
}
