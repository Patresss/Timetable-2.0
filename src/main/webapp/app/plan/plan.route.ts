import {Route} from '@angular/router';
import {UserRouteAccessService} from '../shared';
import {PlanComponent} from './plan.component';

export const PLAN_ROUTE: Route = {
    path: 'plan',
    component: PlanComponent,
    data: {
        authorities: [],
        pageTitle: 'timetableApp.plan.home.title'
    },
    canActivate: [UserRouteAccessService],
};
