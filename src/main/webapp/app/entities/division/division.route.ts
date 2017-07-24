import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { DivisionComponent } from './division.component';
import { DivisionDetailComponent } from './division-detail.component';
import { DivisionPopupComponent } from './division-dialog.component';
import { DivisionDeletePopupComponent } from './division-delete-dialog.component';

@Injectable()
export class DivisionResolvePagingParams implements Resolve<any> {

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

export const divisionRoute: Routes = [
    {
        path: 'division',
        component: DivisionComponent,
        resolve: {
            'pagingParams': DivisionResolvePagingParams
        },
        data: {
            authorities: ['ROLE_SCHOOL_ADMIN'],
            pageTitle: 'timetableApp.division.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'division/:id',
        component: DivisionDetailComponent,
        data: {
            authorities: ['ROLE_SCHOOL_ADMIN'],
            pageTitle: 'timetableApp.division.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const divisionPopupRoute: Routes = [
    {
        path: 'division-new',
        component: DivisionPopupComponent,
        data: {
            authorities: ['ROLE_SCHOOL_ADMIN'],
            pageTitle: 'timetableApp.division.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'division/:id/edit',
        component: DivisionPopupComponent,
        data: {
            authorities: ['ROLE_SCHOOL_ADMIN'],
            pageTitle: 'timetableApp.division.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'division/:id/delete',
        component: DivisionDeletePopupComponent,
        data: {
            authorities: ['ROLE_SCHOOL_ADMIN'],
            pageTitle: 'timetableApp.division.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
