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
import {Timetable, TimetableService} from '../entities/timetable';
import {PlanColumn} from './plan-column.model';
import {PlanCell} from './plan-cell.model';
import {Time} from './time.model';

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
        {'id': 1, 'itemName': '', 'itemTranslate': 'timetableApp.plan.type.STUDENT'},
        {'id': 2, 'itemName': '', 'itemTranslate': 'timetableApp.plan.type.TEACHER'},
        {'id': 3, 'itemName': '', 'itemTranslate': 'timetableApp.plan.type.PLACE'}];
    selectedTypePlan = [];
    typePlanSelectSettings = {
        singleSelection: true,
        text: 'timetableApp.plan.choose.type-plan',
        enableSearchFilter: true,
        classes: 'myclass custom-class'
    };

    currentMonday: Date;

    numberOfColumns = 5;
    timeArray = [];
    startHour = new Time('6:00');
    endHour = new Time('17:00');
    firstColumnWidth = 5.0;
    columnWidth = (100.0 - this.firstColumnWidth) / this.numberOfColumns;
    planColumns: PlanColumn[]= [];

    constructor(private parseLinks: JhiParseLinks,
                private alertService: JhiAlertService,
                private principal: Principal,
                private activatedRoute: ActivatedRoute,
                private router: Router,
                private eventManager: JhiEventManager,
                private paginationUtil: JhiPaginationUtil,
                private paginationConfig: PaginationConfig,
                private divisionService: DivisionService,
                private timetableService: TimetableService) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe();
    }

    loadAll() {
        this.loadCurrentMonday();
        this.loadEmptyPlanColumns();
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
        for (let hour = this.startHour.hour; hour <= this.endHour.hour; hour++) {

            this.timeArray.push(new Time(hour + ':00'));
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
        this.reloadTimetables();
    }

    OnClassDeSelect() {
        this.subgroupSelectOption = [];
        this.selectedSubgroup = [];

        this.subgroupSelectSettings.disabled = true;
        this.subgroupSelectSettings = Object.assign({}, this.subgroupSelectSettings); // workaround to detect change
        this.reloadTimetables();
    }

// ================================================================
// Subgroup
// ================================================================
    onSubgroupSelect(item: any) {
        this.reloadTimetables();
    }

    OnSubgroupDeSelect(item: any) {
        this.reloadTimetables();
    }

    private onSuccessTimetable(weekDay: PlanColumn, data: Timetable[]) {
        for (const timetable of data) {
            weekDay.planCells.push(new PlanCell(timetable, this.startHour, this.endHour))
        }
    }

    private reloadTimetables() {
        console.log(this.currentMonday);
        this.clearPlanColumns();
        for (const weekDay of this.planColumns) {
            const array = [];
            if (this.selectedClass[0]) {
                array.push(this.selectedClass[0].id);
                for (const subgroup of this.selectedSubgroup) {
                    array.push(subgroup.id);
                }
            }
            this.timetableService.findByDateAndDivisionList(weekDay.date, array).subscribe(
                (res: ResponseWrapper) => this.onSuccessTimetable(weekDay, res.json),
                (res: ResponseWrapper) => this.onError(res.json)
            );
        }
        console.log(this.planColumns)
    }

// ================================================================
// Type of Plan
// ================================================================
    onTypePlanSelect(item: any) {

    }

    OnTypePlanDeSelect(item: any) {

    }

// ================================================================================================================================
// Date
// ================================================================================================================================
    nextWeek() {
        this.currentMonday = new Date(this.currentMonday.getFullYear(), this.currentMonday.getMonth(), this.currentMonday.getDate() + 7);

        this.loadNewDays();
        this.reloadTimetables();
    }

    previousWeek() {
        this.currentMonday = new Date(this.currentMonday.getFullYear(), this.currentMonday.getMonth(), this.currentMonday.getDate() - 7);

        this.loadNewDays();
        this.reloadTimetables();
    }

    currentWeek() {
        this.loadCurrentMonday();

        this.loadNewDays();
        this.reloadTimetables();
    }

// ================================================================================================================================
//
// ================================================================================================================================
    private loadCurrentMonday() {
        this.currentMonday = new Date();
        this.currentMonday.setDate(this.currentMonday.getDate() - (this.currentMonday.getDay() + 6) % 7);
    }

    private loadEmptyPlanColumns() {
        const days = [];
        for (let i = 0; i < this.numberOfColumns; i++) {
            const date = new Date(this.currentMonday);
            date.setDate(date.getDate() + i);
            const column = new PlanColumn(date);
            days.push(column);
        }
        this.planColumns = days;
    }

    private loadNewDays() {
        this.planColumns.forEach((item, index) => {
            const date = new Date(this.currentMonday);
            date.setDate(date.getDate() + index);
            item.date = date;
        });
    }

    private clearPlanColumns() {
        for (const planColumn of this.planColumns) {
            planColumn.planCells = []
        }
    }

}
