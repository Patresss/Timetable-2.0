import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {JhiAlertService, JhiEventManager, JhiPaginationUtil, JhiParseLinks} from 'ng-jhipster';
import {Principal} from '../shared/auth/principal.service';
import {PaginationConfig} from '../blocks/config/uib-pagination.config';
import {DivisionService} from '../entities/division/division.service';
import {ResponseWrapper} from '../shared/model/response-wrapper.model';
import {Division} from '../entities/division/division.model';
import {ITEMS_PER_PAGE} from '../shared/constants/pagination.constants';
import {TranslateService} from '@ngx-translate/core';

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

    schoolSelectOption = [];
    selectedSchool = [];
    schoolSelectSettings = {
        singleSelection: true,
        text: this.translateService.instant('timetableApp.plan.choose.school'),
        enableSearchFilter: true,
        classes: 'myclass custom-class'
    };

    classSelectOption = [];
    selectedClass = [];
    classSelectSettings = {
        singleSelection: true,
        text: this.translateService.instant('timetableApp.plan.choose.class'),
        enableSearchFilter: true,
        classes: 'myclass custom-class'
    };

    subgroupSelectOption = [];
    selectedSubgroup = [];
    subgroupSelectSettings = {
        singleSelection: true,
        text: this.translateService.instant('timetableApp.plan.choose.subgroups'),
        enableSearchFilter: true,
        classes: 'myclass custom-class'
    };

    typePlanSelectOption = [
        {'id': 1, 'itemName': this.translateService.instant('timetableApp.plan.type.STUDENT')},
        {'id': 2, 'itemName': this.translateService.instant('timetableApp.plan.type.TEACHER')},
        {'id': 3, 'itemName': this.translateService.instant('timetableApp.plan.type.PLACE')}];
    selectedTypePlan = [];
    typePlanSelectSettings = {
        singleSelection: true,
        text: this.translateService.instant('timetableApp.plan.choose.type-plan'),
        enableSearchFilter: true,
        classes: 'myclass custom-class'
    };

    constructor(private parseLinks: JhiParseLinks,
                private alertService: JhiAlertService,
                private principal: Principal,
                private activatedRoute: ActivatedRoute,
                private router: Router,
                private eventManager: JhiEventManager,
                private paginationUtil: JhiPaginationUtil,
                private paginationConfig: PaginationConfig,
                private divisionService: DivisionService,
                private translateService: TranslateService) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe((data) => {
            data
        });
    }

    loadAll() {
        this.divisionService.findByDivisionType('SCHOOL').subscribe(
            (res: ResponseWrapper) => this.onSuccessSchool(res.json),
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

    private onSuccessSchool(data) {
        this.schoolSelectOption = this.entityListToSelectList(data);
    }

    private onSuccessClass(data) {
        this.classSelectOption = this.entityListToSelectList(data);
    }

    private onSuccessSubgroup(data) {
        this.subgroupSelectOption = this.entityListToSelectList(data);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    private entityListToSelectList(entityList: Division[]) {
        const selectList = [];
        entityList.forEach((entity) => {
            const obj = {id: entity.id, itemName: entity.name};
            selectList.push(obj)
        });
        return selectList;
    }

// ================================================================
// School
// ================================================================
    onSchoolSelect(item: any) {
        this.divisionService.findClassesByParentId(item.id).subscribe(
            (res: ResponseWrapper) => this.onSuccessClass(res.json),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    OnSchoolDeSelect(item: any) {
        this.classSelectOption = [];
        this.subgroupSelectOption = [];
        this.selectedClass = [];
        this.selectedSubgroup = [];
    }

// ================================================================
// Class
// ================================================================
    onClassSelect(item: any) {
        this.divisionService.findSubgroupsByParentId(item.id).subscribe(
            (res: ResponseWrapper) => this.onSuccessSubgroup(res.json),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    OnClassDeSelect(item: any) {
        this.subgroupSelectOption = [];
        this.selectedSubgroup = [];
    }

// ================================================================
// Subgroup
// ================================================================
    onSubgroupSelect(item: any) {

    }

    OnSubgroupDeSelect(item: any) {
    }

// ================================================================
// Type of Plan
// ================================================================
    onTypePlanSelect(item: any) {

    }

    OnSTypePlanDeSelect(item: any) {
    }

}
