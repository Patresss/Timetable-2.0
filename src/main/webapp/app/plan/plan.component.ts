import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {JhiAlertService, JhiEventManager, JhiPaginationUtil, JhiParseLinks} from 'ng-jhipster';
import {Account, ITEMS_PER_PAGE, Principal, ResponseWrapper} from '../shared';
import {PaginationConfig} from '../blocks/config/uib-pagination.config';
import {Division, DivisionService} from '../entities/division';
import {Timetable, TimetableService} from '../entities/timetable';
import {PlanColumn} from './plan-column.model';
import {PlanCell} from './plan-cell.model';
import {TeacherService} from '../entities/teacher';
import {PlaceService} from '../entities/place';
import {SelectType} from '../util/select-type.model';
import {Time} from '../util/time.model';
import {RoleType} from '../util/role-type.model';

@Component({
    selector: 'jhi-board',
    templateUrl: './plan.component.html'
})
export class PlanComponent implements OnInit, OnDestroy {

    currentAccount: Account;
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
    canModifyTimetable = false;

    schoolSelectOption = [];
    selectedSchool = [];
    schoolSelectSettings = {
        singleSelection: true,
        text: 'timetableApp.plan.choose.school',
        enableSearchFilter: true
    };

    classSelectOption = [];
    selectedClass = [];
    classSelectSettings = {
        singleSelection: true,
        text: 'timetableApp.plan.choose.class',
        enableSearchFilter: true,
        disabled: false
    };

    subgroupSelectOption = [];
    selectedSubgroup = [];
    subgroupSelectSettings = {
        singleSelection: false,
        text: 'timetableApp.plan.choose.subgroups',
        enableSearchFilter: true,
        disabled: true,
        enableCheckAll: true
    };

    teacherSelectOption = [];
    selectedTeacher = [];
    teacherSelectSettings = {
        singleSelection: true,
        text: 'timetableApp.plan.choose.teacher',
        enableSearchFilter: true
    };

    placeSelectOption = [];
    selectedPlace = [];
    placeSelectSettings = {
        singleSelection: true,
        text: 'timetableApp.plan.choose.place',
        enableSearchFilter: true
    };

    typePlanSelectOption = [
        new SelectType(1, '', 'timetableApp.plan.type.STUDENT', 'STUDENT'),
        new SelectType(2, '', 'timetableApp.plan.type.TEACHER', 'TEACHER'),
        new SelectType(3, '', 'timetableApp.plan.type.PLACE', 'PLACE')];
    selectedTypePlan: SelectType[] = [this.typePlanSelectOption[0]];
    typePlanSelectSettings = {
        singleSelection: true,
        text: 'timetableApp.plan.choose.type-plan',
        enableSearchFilter: false
    };

    currentMonday: Date;

    numberOfColumns = 5;
    timeArray = [];
    startHour = new Time('6:00');
    endHour = new Time('17:00');
    firstColumnWidth = 5.0;
    columnWidth = (100.0 - this.firstColumnWidth) / this.numberOfColumns;
    planColumns: PlanColumn[] = [];

    constructor(private parseLinks: JhiParseLinks,
                private alertService: JhiAlertService,
                private principal: Principal,
                private activatedRoute: ActivatedRoute,
                private router: Router,
                private eventManager: JhiEventManager,
                private paginationUtil: JhiPaginationUtil,
                private paginationConfig: PaginationConfig,
                private divisionService: DivisionService,
                private teacherService: TeacherService,
                private placeService: PlaceService,
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

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    transition() {
        this.router.navigate(['/plan'], {});
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        if (this.principal.isAuthenticated()) {
            this.principal.identity().then((account) => {
                this.currentAccount = account;
            });
        }
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

    private onSuccessTeacher(data) {
        const selectList = [];
        data.forEach((entity) => {
            const obj = {id: entity.id, itemName: entity.fullName};
            selectList.push(obj)
        });
        this.teacherSelectOption = selectList;
    }

    private onSuccessPlace(data) {
        this.placeSelectOption = this.entityListToSelectList(data);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    private entityListToSelectList(entityList: Division[]) {
        const selectList = [];
        entityList.forEach((entity) => {
            const obj = {id: entity.id, itemName: entity.name, item: entity};
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

// ================================================================================================================================
// Choosers
// ================================================================================================================================
// ================================================================
// School
// ================================================================
    onSchoolSelect(item: any) {
        this.calculateModifyFlag();
        this.clearClassesSelect();
        this.loadSchoolEntity(item);

        this.classSelectSettings.disabled = false;
        this.classSelectSettings = Object.assign({}, this.classSelectSettings); // workaround to detect change

        this.reloadTimetables();
    }

    private calculateModifyFlag() {
        if (this.currentAccount) {
            if (this.currentAccount.authorities.indexOf(RoleType.ROLE_ADMIN.toString()) > -1) {
                this.canModifyTimetable = true;
            } else if (this.currentAccount.authorities.indexOf(RoleType.ROLE_SCHOOL_ADMIN.toString()) > -1) {
                this.canModifyTimetable = this.selectedSchool[0].item.users.indexOf(this.currentAccount.id) > -1;
            }
        } else {
            this.canModifyTimetable = false;
        }
    }

    private loadSchoolEntity(item: any) {
        this.divisionService.findClassesByParentId(item.id).subscribe(
            (res: ResponseWrapper) => this.onSuccessClass(res.json),
            (res: ResponseWrapper) => this.onError(res.json)
        );
        const schoolsId = this.selectedSchool.map((school) => school.id);
        this.teacherService.findByDivision(schoolsId, {size: SelectType.MAX_INT_JAVA, sort: ['surname,name']}).subscribe(
            (res: ResponseWrapper) => this.onSuccessTeacher(res.json),
            (res: ResponseWrapper) => this.onError(res.json)
        );
        this.placeService.findByDivision(schoolsId, {size: SelectType.MAX_INT_JAVA, sort: ['name']}).subscribe(
            (res: ResponseWrapper) => this.onSuccessPlace(res.json),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    OnSchoolDeSelect() {
        this.clearClassesSelect();
        this.clearSubgroupsSelect();
        this.classSelectSettings.disabled = true;
        this.classSelectSettings = Object.assign({}, this.classSelectSettings); // workaround to detect change
        this.subgroupSelectSettings.disabled = true;
        this.subgroupSelectSettings = Object.assign({}, this.subgroupSelectSettings); // workaround to detect change
        this.reloadTimetables();
    }

// ================================================================
// Class
// ================================================================
    onClassSelect(item: any) {
        this.clearSubgroupsSelect();
        this.divisionService.findSubgroupsByParentId(item.id).subscribe(
            (res: ResponseWrapper) => this.onSuccessSubgroup(res.json),
            (res: ResponseWrapper) => this.onError(res.json)
        );

        this.subgroupSelectSettings.disabled = false;
        this.subgroupSelectSettings = Object.assign({}, this.subgroupSelectSettings); // workaround to detect change
        this.reloadTimetables();
    }

    OnClassDeSelect() {
        this.clearSubgroupsSelect();
        this.subgroupSelectSettings.disabled = true;
        this.subgroupSelectSettings = Object.assign({}, this.subgroupSelectSettings); // workaround to detect change
        this.reloadTimetables();
    }

    private clearClassesSelect() {
        this.selectedClass = [];
        this.classSelectOption = [];
        this.clearSubgroupsSelect();
    }

// ================================================================
// Subgroup
// ================================================================
    private clearSubgroupsSelect() {
        this.subgroupSelectOption = [];
        this.selectedSubgroup = [];
    }

    private onSuccessTimetable(weekDay: PlanColumn, data: Timetable[]) {
        for (const timetable of data) {
            weekDay.planCells.push(new PlanCell(timetable, this.startHour, this.endHour))
        }
        weekDay.calculatePosition();
    }

    reloadTimetables() {
        this.clearPlanColumns();
        for (const weekDay of this.planColumns) {
            switch (this.selectedTypePlan[0].type) {
                case 'STUDENT': {
                    const divisionListId = [];
                    if (this.selectedClass[0]) {
                        divisionListId.push(this.selectedClass[0].id);
                        for (const subgroup of this.selectedSubgroup) {
                            divisionListId.push(subgroup.id);
                        }
                    }
                    this.timetableService.findByDateAndDivisionList(weekDay.date, divisionListId).subscribe(
                        (res: ResponseWrapper) => this.onSuccessTimetable(weekDay, res.json),
                        (res: ResponseWrapper) => this.onError(res.json)
                    );
                    break;
                }
                case 'TEACHER': {
                    if (this.selectedTeacher[0] && this.selectedTeacher[0].id) {
                        this.timetableService.findByDateAndTeacherId(weekDay.date, this.selectedTeacher[0].id).subscribe(
                            (res: ResponseWrapper) => this.onSuccessTimetable(weekDay, res.json),
                            (res: ResponseWrapper) => this.onError(res.json)
                        );
                    }
                    break;
                }
                case 'PLACE': {
                    if (this.selectedPlace[0] && this.selectedPlace[0].id) {
                        this.timetableService.findByDateAndPlaceId(weekDay.date, this.selectedPlace[0].id).subscribe(
                            (res: ResponseWrapper) => this.onSuccessTimetable(weekDay, res.json),
                            (res: ResponseWrapper) => this.onError(res.json)
                        );
                    }
                    break;
                }
                default: {
                    break;
                }
            }
        }
    }

// ================================================================
// Type
// ================================================================
    onTypeSelect(item: any) {
        this.clearPlanColumns();

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

    private loadCurrentMonday() {
        this.currentMonday = new Date();
        this.currentMonday.setDate(this.currentMonday.getDate() - (this.currentMonday.getDay() + 6) % 7);
    }

// ================================================================================================================================
//
// ================================================================================================================================
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
