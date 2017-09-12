import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { TimetableComponent } from './timetable.component';
import { TimetableDetailComponent } from './timetable-detail.component';
import { TimetablePopupComponent } from './timetable-dialog.component';
import { TimetableDeletePopupComponent } from './timetable-delete-dialog.component';

@Injectable()
export class TimetableResolvePagingParams implements Resolve<any> {

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

export const timetableRoute: Routes = [
    {
        path: 'timetable',
        component: TimetableComponent,
        resolve: {
            'pagingParams': TimetableResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'timetableApp.timetable.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'timetable/:id',
        component: TimetableDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'timetableApp.timetable.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const timetablePopupRoute: Routes = [
    {
        path: 'timetable-new',
        component: TimetablePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'timetableApp.timetable.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'timetable/:id/edit',
        component: TimetablePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'timetableApp.timetable.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'timetable/:id/delete',
        component: TimetableDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'timetableApp.timetable.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
