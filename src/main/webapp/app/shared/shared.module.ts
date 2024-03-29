import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { DatePipe } from '@angular/common';

import {
    TimetableSharedLibsModule,
    TimetableSharedCommonModule,
    CSRFService,
    AuthServerProvider,
    AccountService,
    UserService,
    StateStorageService,
    LoginService,
    LoginModalService,
    Principal,
    HasAnyAuthorityDirective,
    JhiLoginModalComponent
} from './';
import {GlassComponent} from '../components/glass/glass.component';
import {PreferenceDataTimeByLessonComponent} from '../preference/preference-data-time-by-lesson/preference-data-time-by-lesson.component';

@NgModule({
    imports: [
        TimetableSharedLibsModule,
        TimetableSharedCommonModule
    ],
    declarations: [
        JhiLoginModalComponent,
        HasAnyAuthorityDirective,
        GlassComponent,
        PreferenceDataTimeByLessonComponent,
],
    providers: [
        LoginService,
        LoginModalService,
        AccountService,
        StateStorageService,
        Principal,
        CSRFService,
        AuthServerProvider,
        UserService,
        DatePipe
    ],
    entryComponents: [JhiLoginModalComponent],
    exports: [
        TimetableSharedCommonModule,
        JhiLoginModalComponent,
        HasAnyAuthorityDirective,
        DatePipe,
        GlassComponent,
        PreferenceDataTimeByLessonComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]

})
export class TimetableSharedModule {}
