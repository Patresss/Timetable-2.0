import {Routes} from '@angular/router';
import {UserRouteAccessService} from '../shared/auth/user-route-access-service';
import {PlanComponent} from './plan.component';


export const planRoute: Routes = [
    {
        path: 'plan',
        component: PlanComponent,
        data: {
            authorities: [],
            pageTitle: 'timetableApp.board.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
