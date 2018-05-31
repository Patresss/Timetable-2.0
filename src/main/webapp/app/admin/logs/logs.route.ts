import { Route } from '@angular/router';

import { LogsComponent } from './logs.component';

export const logsRoute: Route = {
    path: 'logs',
    component: LogsComponent,
    data: {
        authorities: ['ROLE_ADMIN'],
        pageTitle: 'logs.title'
    }
};
