import {Component, OnInit} from '@angular/core';
import {ActivatedRouteSnapshot, NavigationEnd, Router} from '@angular/router';

import {JhiLanguageHelper} from '../../shared';

@Component({
    selector: 'jhi-main',
    templateUrl: './main.component.html'
})
export class JhiMainComponent implements OnInit {

    fullPage = false;
    fullPageRegexp = [new RegExp('^\\/plan.*')];

    constructor(private jhiLanguageHelper: JhiLanguageHelper,
                private router: Router) {
    }

    private getPageTitle(routeSnapshot: ActivatedRouteSnapshot) {
        let title: string = (routeSnapshot.data && routeSnapshot.data['pageTitle']) ? routeSnapshot.data['pageTitle'] : 'timetableApp';
        if (routeSnapshot.firstChild) {
            title = this.getPageTitle(routeSnapshot.firstChild) || title;
        }
        return title;
    }

    private updateFullPageInfo(event: NavigationEnd) {
        this.fullPage = false;
        for (let regexp of this.fullPageRegexp) {
            if (regexp.test(event.url)) {
                this.fullPage = true;
            } else {
                break;
            }
        }
    }

    ngOnInit() {
        this.router.events.subscribe((event) => {
            if (event instanceof NavigationEnd) {
                this.jhiLanguageHelper.updateTitle(this.getPageTitle(this.router.routerState.snapshot.root));
                this.updateFullPageInfo(event);
            }
        });
    }
}
