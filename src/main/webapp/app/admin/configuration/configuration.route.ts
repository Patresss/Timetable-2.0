import { Route } from '@angular/router';

import { JhiConfigurationComponent } from './configuration.component';

export const configurationRoute: Route = {
    path: 'jhi-configuration',
    component: JhiConfigurationComponent,
    data: {
        authorities: ['ROLE_ADMIN'],
        pageTitle: 'configuration.title'
    }
};
