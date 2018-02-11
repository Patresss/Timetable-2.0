import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { navbarRoute } from '../app.route';
import { errorRoute } from './';
import {FillerService} from '../admin/filler/filler.service';

const LAYOUT_ROUTES = [
    navbarRoute,
    ...errorRoute
];

@NgModule({
    imports: [
        RouterModule.forRoot(LAYOUT_ROUTES, { useHash: true })
    ],
    exports: [
        RouterModule
    ],
    providers: [
        FillerService
    ]

})
export class LayoutRoutingModule {}
