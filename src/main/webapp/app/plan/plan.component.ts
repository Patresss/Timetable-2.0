import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';
import {Principal} from '../shared/auth/principal.service';
import {PaginationConfig} from '../blocks/config/uib-pagination.config';


@Component({
    selector: 'jhi-board',
    templateUrl: './plan.component.html'
})
export class PlanComponent implements OnInit, OnDestroy {

currentAccount: any;
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;

    constructor(
        private parseLinks: JhiParseLinks,
        private alertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private eventManager: JhiEventManager,
        private paginationUtil: JhiPaginationUtil,
        private paginationConfig: PaginationConfig
    ) {
        this.routeData = this.activatedRoute.data.subscribe((data) => { data
        });
    }

    loadAll() {
    }

    transition() {
        this.router.navigate(['/plan'], {
        });
        this.loadAll();
    }


    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
    }

    ngOnDestroy() {
    }



    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        // this.page = pagingParams.page;
    }
    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
