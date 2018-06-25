import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes} from '@angular/router';

import {UserRouteAccessService} from '../../shared';
import {JhiPaginationUtil} from 'ng-jhipster';
import {CurriculumListComponent} from './curriculum-list.component';
import {CurriculumListDetailComponent} from './curriculum-list-detail.component';
import {CurriculumListPopupComponent} from './curriculum-list-dialog.component';
import {CurriculumListDeletePopupComponent} from './curriculum-list-delete-dialog.component';
import {CurriculumListGeneratePopupComponent} from './curriculum-list-generate-dialog.component';

@Injectable()
export class CurriculumListResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {
    }

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

export const curriculumListRoute: Routes = [
    {
        path: 'curriculum-list',
        component: CurriculumListComponent,
        resolve: {
            'pagingParams': CurriculumListResolvePagingParams
        },
        data: {
            authorities: ['ROLE_SCHOOL_ADMIN'],
            pageTitle: 'timetableApp.curriculum-list'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'curriculum-list/:id',
        component: CurriculumListDetailComponent,
        data: {
            authorities: ['ROLE_SCHOOL_ADMIN'],
            pageTitle: 'timetableApp.curriculum-list'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const curriculumListPopupRoute: Routes = [
    {
        path: 'curriculum-list-new',
        component: CurriculumListPopupComponent,
        data: {
            authorities: ['ROLE_SCHOOL_ADMIN'],
            pageTitle: 'timetableApp.curriculum-list'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'curriculum-list/:id/edit',
        component: CurriculumListPopupComponent,
        data: {
            authorities: ['ROLE_SCHOOL_ADMIN'],
            pageTitle: 'timetableApp.curriculum-list'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'curriculum-list/:id/delete',
        component: CurriculumListDeletePopupComponent,
        data: {
            authorities: ['ROLE_SCHOOL_ADMIN'],
            pageTitle: 'timetableApp.curriculum-list'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'curriculum-list/:id/generate',
        component: CurriculumListGeneratePopupComponent,
        data: {
            authorities: ['ROLE_SCHOOL_ADMIN'],
            pageTitle: 'timetableApp.curriculum-list'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
