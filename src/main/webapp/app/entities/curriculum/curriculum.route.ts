import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CurriculumComponent } from './curriculum.component';
import { CurriculumDetailComponent } from './curriculum-detail.component';
import { CurriculumPopupComponent } from './curriculum-dialog.component';
import { CurriculumDeletePopupComponent } from './curriculum-delete-dialog.component';

@Injectable()
export class CurriculumResolvePagingParams implements Resolve<any> {

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

export const curriculumRoute: Routes = [
    {
        path: 'curriculum',
        component: CurriculumComponent,
        resolve: {
            'pagingParams': CurriculumResolvePagingParams
        },
        data: {
            authorities: ['ROLE_SCHOOL_ADMIN'],
            pageTitle: 'timetableApp.curriculum.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'curriculum/:id',
        component: CurriculumDetailComponent,
        data: {
            authorities: ['ROLE_SCHOOL_ADMIN'],
            pageTitle: 'timetableApp.curriculum.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const curriculumPopupRoute: Routes = [
    {
        path: 'curriculum-new',
        component: CurriculumPopupComponent,
        data: {
            authorities: ['ROLE_SCHOOL_ADMIN'],
            pageTitle: 'timetableApp.curriculum.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'curriculum/:id/edit',
        component: CurriculumPopupComponent,
        data: {
            authorities: ['ROLE_SCHOOL_ADMIN'],
            pageTitle: 'timetableApp.curriculum.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'curriculum/:id/delete',
        component: CurriculumDeletePopupComponent,
        data: {
            authorities: ['ROLE_SCHOOL_ADMIN'],
            pageTitle: 'timetableApp.curriculum.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
