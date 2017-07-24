import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PeriodComponent } from './period.component';
import { PeriodDetailComponent } from './period-detail.component';
import { PeriodPopupComponent } from './period-dialog.component';
import { PeriodDeletePopupComponent } from './period-delete-dialog.component';

@Injectable()
export class PeriodResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const periodRoute: Routes = [
    {
        path: 'period',
        component: PeriodComponent,
        resolve: {
            'pagingParams': PeriodResolvePagingParams
        },
        data: {
            authorities: ['ROLE_SCHOOL_ADMIN'],
            pageTitle: 'timetableApp.period.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'period/:id',
        component: PeriodDetailComponent,
        data: {
            authorities: ['ROLE_SCHOOL_ADMIN'],
            pageTitle: 'timetableApp.period.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const periodPopupRoute: Routes = [
    {
        path: 'period-new',
        component: PeriodPopupComponent,
        data: {
            authorities: ['ROLE_SCHOOL_ADMIN'],
            pageTitle: 'timetableApp.period.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'period/:id/edit',
        component: PeriodPopupComponent,
        data: {
            authorities: ['ROLE_SCHOOL_ADMIN'],
            pageTitle: 'timetableApp.period.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'period/:id/delete',
        component: PeriodDeletePopupComponent,
        data: {
            authorities: ['ROLE_SCHOOL_ADMIN'],
            pageTitle: 'timetableApp.period.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
