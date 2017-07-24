import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { IntervalComponent } from './interval.component';
import { IntervalDetailComponent } from './interval-detail.component';
import { IntervalPopupComponent } from './interval-dialog.component';
import { IntervalDeletePopupComponent } from './interval-delete-dialog.component';

@Injectable()
export class IntervalResolvePagingParams implements Resolve<any> {

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

export const intervalRoute: Routes = [
    {
        path: 'interval',
        component: IntervalComponent,
        resolve: {
            'pagingParams': IntervalResolvePagingParams
        },
        data: {
            authorities: ['ROLE_SCHOOL_ADMIN'],
            pageTitle: 'timetableApp.interval.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'interval/:id',
        component: IntervalDetailComponent,
        data: {
            authorities: ['ROLE_SCHOOL_ADMIN'],
            pageTitle: 'timetableApp.interval.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const intervalPopupRoute: Routes = [
    {
        path: 'interval-new',
        component: IntervalPopupComponent,
        data: {
            authorities: ['ROLE_SCHOOL_ADMIN'],
            pageTitle: 'timetableApp.interval.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'interval/:id/edit',
        component: IntervalPopupComponent,
        data: {
            authorities: ['ROLE_SCHOOL_ADMIN'],
            pageTitle: 'timetableApp.interval.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'interval/:id/delete',
        component: IntervalDeletePopupComponent,
        data: {
            authorities: ['ROLE_SCHOOL_ADMIN'],
            pageTitle: 'timetableApp.interval.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
