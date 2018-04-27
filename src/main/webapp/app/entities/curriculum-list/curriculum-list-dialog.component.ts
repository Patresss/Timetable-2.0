import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Response} from '@angular/http';

import {Observable, Subscription} from 'rxjs/Rx';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import {Division, DivisionService} from '../division';
import {ResponseWrapper} from '../../shared';
import {CurriculumService} from '../curriculum';
import {CurriculumList} from './curriculum-list.model';
import {CurriculumListService} from './curriculum-list.service';
import {CurriculumListPopupService} from './curriculum-list-popup.service';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {PeriodService} from '../period/period.service';
import {SelectType} from '../../util/select-type.model';
import {SelectUtil} from '../../util/select-util.model';

@Component({
    selector: 'jhi-curriculum-list-popup',
    template: ''
})
export class CurriculumListPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(private route: ActivatedRoute,
                private curriculumListPopupService: CurriculumListPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['id']) {
                this.curriculumListPopupService
                    .open(CurriculumListDialogComponent as Component, params['id']);
            } else {
                this.curriculumListPopupService
                    .open(CurriculumListDialogComponent as Component);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

@Component({
    selector: 'jhi-curriculum-list-dialog',
    templateUrl: './curriculum-list-dialog.component.html'
})
export class CurriculumListDialogComponent implements OnInit {

    curriculumList: CurriculumList;
    private subscription: Subscription;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    divisions: Division[] = [];

    periodSelectOption = [];
    selectedPeriod = [];
    periodSelectSettings = {
        singleSelection: true,
        text: 'timetableApp.plan.choose.period',
        enableSearchFilter: true
    };

    curriculumSelectOption = [];
    selectedCurriculum = [];
    curriculumSelectSettings = {
        singleSelection: false,
        text: 'timetableApp.plan.choose.curriculums',
        enableSearchFilter: true
    };

    constructor(private alertService: JhiAlertService,
                private periodService: PeriodService,
                private curriculumService: CurriculumService,
                private eventManager: JhiEventManager,
                private curriculumListService: CurriculumListService,
                private divisionService: DivisionService,
                private activeModal: NgbActiveModal) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.periodService.findByCurrentLogin({size: SelectType.MAX_INT_JAVA, sort: ['name']})
            .subscribe((res: ResponseWrapper) => {
                this.initPeriods(res.json)
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.curriculumService.findByCurrentLogin({size: SelectType.MAX_INT_JAVA, sort: ['name']})
            .subscribe((res: ResponseWrapper) => {
                this.initCurriculums(res.json)
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.divisionService.query()
            .subscribe((res: ResponseWrapper) => {
                this.divisions = res.json;
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    private initPeriods(entityList: any[]) {
        this.periodSelectOption = SelectUtil.entityListToSelectList(entityList);
        this.selectedPeriod = this.periodSelectOption.filter((entity) => entity.id === this.curriculumList.periodId)
    }

    private initCurriculums(entityList: any[]) {
        this.curriculumSelectOption = SelectUtil.entityListToSelectList(entityList);
        this.selectedCurriculum = this.curriculumSelectOption.filter((entity) => this.curriculumList.curriculums.some((curriculum) => entity.id === curriculum.id))
    }

    load(id) {
        this.curriculumListService.find(id).subscribe((curriculumList) => {
            this.curriculumList = curriculumList;
        });
    }

    save() {
        this.isSaving = true;
        this.curriculumList.curriculums = this.selectedCurriculum;
        if (this.curriculumList.id !== undefined) {
            this.subscribeToSaveResponse(
                this.curriculumListService.update(this.curriculumList));
        } else {
            this.subscribeToSaveResponse(
                this.curriculumListService.create(this.curriculumList));
        }
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    private subscribeToSaveResponse(result: Observable<CurriculumList>) {
        result.subscribe((res: CurriculumList) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: CurriculumList) {
        this.eventManager.broadcast({name: 'curriculumListListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    onPeriodSelect(item: any) {
        this.curriculumList.periodId = item.id;
    }

    onPeriodDeSelect() {
        this.curriculumList.periodId = null;
    }

}
