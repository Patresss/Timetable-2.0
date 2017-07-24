import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PropertiesComponent } from './properties.component';
import { PropertiesDetailComponent } from './properties-detail.component';
import { PropertiesPopupComponent } from './properties-dialog.component';
import { PropertiesDeletePopupComponent } from './properties-delete-dialog.component';

@Injectable()
export class PropertiesResolvePagingParams implements Resolve<any> {

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

export const propertiesRoute: Routes = [
    {
        path: 'properties',
        component: PropertiesComponent,
        resolve: {
            'pagingParams': PropertiesResolvePagingParams
        },
        data: {
            authorities: ['ROLE_SCHOOL_ADMIN'],
            pageTitle: 'timetableApp.properties.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'properties/:id',
        component: PropertiesDetailComponent,
        data: {
            authorities: ['ROLE_SCHOOL_ADMIN'],
            pageTitle: 'timetableApp.properties.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const propertiesPopupRoute: Routes = [
    {
        path: 'properties-new',
        component: PropertiesPopupComponent,
        data: {
            authorities: ['ROLE_SCHOOL_ADMIN'],
            pageTitle: 'timetableApp.properties.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'properties/:id/edit',
        component: PropertiesPopupComponent,
        data: {
            authorities: ['ROLE_SCHOOL_ADMIN'],
            pageTitle: 'timetableApp.properties.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'properties/:id/delete',
        component: PropertiesDeletePopupComponent,
        data: {
            authorities: ['ROLE_SCHOOL_ADMIN'],
            pageTitle: 'timetableApp.properties.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
