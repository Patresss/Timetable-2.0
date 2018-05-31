import { Route } from '@angular/router';

import { JhiHealthCheckComponent } from './health.component';

export const healthRoute: Route = {
    path: 'jhi-health',
    component: JhiHealthCheckComponent,
    data: {
        authorities: ['ROLE_ADMIN'],
        pageTitle: 'health.title'
    }
};
