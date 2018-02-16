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
import {TimetableService} from '../entities/timetable';
import {DatePipe} from '@angular/common';
import {PlanColumn} from './plan-column.model';

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

    timetables = [];

    schoolSelectOption = [];
    selectedSchool = [];
    schoolSelectSettings = {
        singleSelection: true,
        text: 'timetableApp.plan.choose.school',
        enableSearchFilter: true,
        classes: 'plan-select'
    };

    classSelectOption = [];
    selectedClass = [];
    classSelectSettings = {
        singleSelection: true,
        text: 'timetableApp.plan.choose.class',
        enableSearchFilter: true,
        classes: 'myclass custom-class',
        disabled: true
    };

    subgroupSelectOption = [];
    selectedSubgroup = [];
    subgroupSelectSettings = {
        singleSelection: false,
        text: 'timetableApp.plan.choose.subgroups',
        enableSearchFilter: true,
        classes: 'myclass custom-class',
        disabled: true,
        enableCheckAll: true
    };

    typePlanSelectOption = [
        {'id': 1, 'itemName': this.translateService.instant('timetableApp.plan.type.STUDENT')},
        {'id': 2, 'itemName': this.translateService.instant('timetableApp.plan.type.TEACHER')},
        {'id': 3, 'itemName': this.translateService.instant('timetableApp.plan.type.PLACE')}];
    selectedTypePlan = [];
    typePlanSelectSettings = {
        singleSelection: true,
        text: 'timetableApp.plan.choose.type-plan',
        enableSearchFilter: true,
        classes: 'myclass custom-class'
    };

    weekDay = this.loadWeekDays();

    numberOfColums = 7;
    timeArray = [];
    startHour = 6;
    endHour = 17;
    firstColumnWidth = 5.0;
    columnWidth = (100.0 - this.firstColumnWidth) / 7.0;

    constructor(private parseLinks: JhiParseLinks,
                private alertService: JhiAlertService,
                private principal: Principal,
                private activatedRoute: ActivatedRoute,
                private router: Router,
                private eventManager: JhiEventManager,
                private paginationUtil: JhiPaginationUtil,
                private paginationConfig: PaginationConfig,
                private divisionService: DivisionService,
                private timetableService: TimetableService,
                private translateService: TranslateService) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe();
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
        this.loadTimeArray();
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

    private loadTimeArray() {
        this.timeArray = [];
        for (let hour = this.startHour; hour <= this.endHour; hour++) {
            const hourString = hour.toString().length <= 1 ? '0' + hour : hour.toString();
            this.timeArray.push(hourString + ':00');
        }
    }

// ================================================================
// School
// ================================================================
    onSchoolSelect(item: any) {
        this.divisionService.findClassesByParentId(item.id).subscribe(
            (res: ResponseWrapper) => this.onSuccessClass(res.json),
            (res: ResponseWrapper) => this.onError(res.json)
        );

        if (item != null) {
            this.classSelectSettings.disabled = false;
            this.classSelectSettings = Object.assign({}, this.classSelectSettings); // workaround to detect change
        }
    }

    OnSchoolDeSelect() {
        this.classSelectOption = [];
        this.subgroupSelectOption = [];
        this.selectedClass = [];
        this.selectedSubgroup = [];

        this.classSelectSettings.disabled = true;
        this.classSelectSettings = Object.assign({}, this.classSelectSettings); // workaround to detect change
        this.subgroupSelectSettings.disabled = true;
        this.subgroupSelectSettings = Object.assign({}, this.subgroupSelectSettings); // workaround to detect change
    }

// ================================================================================================================================
// Choosers
// ================================================================================================================================

// ================================================================
// Class
// ================================================================
    onClassSelect(item: any) {
        this.divisionService.findSubgroupsByParentId(item.id).subscribe(
            (res: ResponseWrapper) => this.onSuccessSubgroup(res.json),
            (res: ResponseWrapper) => this.onError(res.json)
        );

        if (item != null) {
            this.subgroupSelectSettings.disabled = false;
            this.subgroupSelectSettings = Object.assign({}, this.subgroupSelectSettings); // workaround to detect change
        }
    }

    OnClassDeSelect() {
        this.subgroupSelectOption = [];
        this.selectedSubgroup = [];

        this.subgroupSelectSettings.disabled = true;
        this.subgroupSelectSettings = Object.assign({}, this.subgroupSelectSettings); // workaround to detect change
    }

// ================================================================
// Subgroup
// ================================================================
    onSubgroupSelect(item: any) {

    }

    OnSubgroupDeSelect(item: any) {
        const array = [];
        array.push(952);
        const date = new Date();
        date.setDate(date.getDate() - (date.getDay() + 6) % 7);

        this.timetableService.findByDateAndDivisionList(date, array).subscribe(
            (res: ResponseWrapper) => this.onSuccessTimetable(res.json),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    private onSuccessTimetable(data) {
        this.timetables = data;
        this.displayTimetables()
    }

    private displayTimetables() {
    }

// ================================================================
// Type of Plan
// ================================================================
    onTypePlanSelect(item: any) {

    }

    OnTypePlanDeSelect(item: any) {

    }

// ================================================================================================================================
//
// ================================================================================================================================
    private loadWeekDays() {
        const lastMonday = new Date();
        lastMonday.setDate(lastMonday.getDate() - (lastMonday.getDay() + 6) % 7);
        const days = [];

        for (let i = 0; i < 7; i++) {
            const date = new Date(lastMonday);
            date.setDate(date.getDate() + i);
            const column = new PlanColumn(date);
            days.push(column);
        }
        return days;
    }

}
