import {AfterContentInit, Component, OnInit} from '@angular/core';
import {ActivatedRouteSnapshot, NavigationEnd, Router} from '@angular/router';

import {JhiLanguageHelper} from '../../shared';

@Component({
    selector: 'jhi-main',
    templateUrl: './main.component.html'
})
export class JhiMainComponent implements OnInit {

    fullPage = false;
    lightView = false;
    lightGlassRegexp = [
        new RegExp('^\\/register.*'),
        new RegExp('^\\/docs.*')];
    fullPageRegexp = [
        new RegExp('^\\/plan.*'),
        new RegExp('^\\/$')];

    constructor(private jhiLanguageHelper: JhiLanguageHelper,
                private router: Router) {
    }

    ngOnInit() {
        this.router.events.subscribe((event) => {
            if (event instanceof NavigationEnd) {
                this.updateFullPageInfo(event);
                this.jhiLanguageHelper.updateTitle(this.getPageTitle(this.router.routerState.snapshot.root));
            }
        });
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
        for (const regexp of this.fullPageRegexp) {
            if (regexp.test(event.url)) {
                this.fullPage = true;
                break;
            }
        }

        this.lightView = false;
        for (const regexp of this.lightGlassRegexp) {
            if (regexp.test(event.url)) {
                this.lightView = true;
                break;
            }
        }
    }
}
