import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PlaceComponent } from './place.component';
import { PlaceDetailComponent } from './place-detail.component';
import { PlacePopupComponent } from './place-dialog.component';
import { PlaceDeletePopupComponent } from './place-delete-dialog.component';

@Injectable()
export class PlaceResolvePagingParams implements Resolve<any> {

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

export const placeRoute: Routes = [
    {
        path: 'place',
        component: PlaceComponent,
        resolve: {
            'pagingParams': PlaceResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'timetableApp.place.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'place/:id',
        component: PlaceDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'timetableApp.place.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const placePopupRoute: Routes = [
    {
        path: 'place-new',
        component: PlacePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'timetableApp.place.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'place/:id/edit',
        component: PlacePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'timetableApp.place.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'place/:id/delete',
        component: PlaceDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'timetableApp.place.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
