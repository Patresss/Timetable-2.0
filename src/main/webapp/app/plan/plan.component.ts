import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {JhiAlertService, JhiEventManager, JhiPaginationUtil, JhiParseLinks} from 'ng-jhipster';
import {Principal} from '../shared/auth/principal.service';
import {PaginationConfig} from '../blocks/config/uib-pagination.config';
import {IMultiSelectOption, IMultiSelectSettings, IMultiSelectTexts} from 'angular-2-dropdown-multiselect';
import {DivisionService} from '../entities/division/division.service';
import {ResponseWrapper} from '../shared/model/response-wrapper.model';
import {Division} from '../entities/division/division.model';
import {ITEMS_PER_PAGE} from '../shared/constants/pagination.constants';


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

    schools: any;

    selectedSchool: number[] = [];

    schoolSelectSettings: IMultiSelectSettings = {
        enableSearch: true,
        checkedStyle: 'fontawesome',
        buttonClasses: 'btn btn-default btn-block',
        selectionLimit: 1,
        autoUnselect: true,
    };

    schoolSelectTexts: IMultiSelectTexts = {};

    schoolSelectOption: IMultiSelectOption[] = [];

    private entityListToSelectList(entityList: Division[]): IMultiSelectOption[] {
        let selectList: IMultiSelectOption[] = [];
        entityList.forEach((entity) => {
            let obj: IMultiSelectOption = {id: entity.id, name: entity.name};
            selectList.push(obj)
        });
        return selectList;
    }


    constructor(private parseLinks: JhiParseLinks,
                private alertService: JhiAlertService,
                private principal: Principal,
                private activatedRoute: ActivatedRoute,
                private router: Router,
                private eventManager: JhiEventManager,
                private paginationUtil: JhiPaginationUtil,
                private paginationConfig: PaginationConfig,
                private divisionService: DivisionService) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe((data) => {
            data
        });
    }

    loadAll() {
        this.divisionService.findByDivisionType("SCHOOL").subscribe(
            (res: ResponseWrapper) => this.onSuccessSchool(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }


    transition() {
        this.router.navigate(['/plan'], {});
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

    private onSuccessSchool(data, headers) {
        this.schools = data;
        this.schoolSelectOption = this.entityListToSelectList(this.schools);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

}
