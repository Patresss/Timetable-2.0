import './vendor.ts';

import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {Ng2Webstorage} from 'ng2-webstorage';

import {TimetableSharedModule, UserRouteAccessService} from './shared';
import {TimetableHomeModule} from './home/home.module';
import {TimetableAdminModule} from './admin/admin.module';
import {TimetableAccountModule} from './account/account.module';
import {TimetableEntityModule} from './entities/entity.module';

import {customHttpProvider} from './blocks/interceptor/http.provider';
import {PaginationConfig} from './blocks/config/uib-pagination.config';
import {ActiveMenuDirective, ErrorComponent, FooterComponent, JhiMainComponent, LayoutRoutingModule, NavbarComponent, PageRibbonComponent, ProfileService} from './layouts';
import {TimetablePlanModule} from './plan/plan.module';

// jhipster-needle-angular-add-module-import JHipster will add new module here

@NgModule({
    imports: [
        BrowserModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({prefix: 'jhi', separator: '-'}),
        TimetableSharedModule,
        TimetableHomeModule,
        TimetableAdminModule,
        TimetableAccountModule,
        TimetableEntityModule,
        TimetablePlanModule,
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [JhiMainComponent]
})
export class TimetableAppModule {
}
