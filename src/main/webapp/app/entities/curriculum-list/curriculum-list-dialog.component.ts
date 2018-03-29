import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Response} from '@angular/http';

import {Observable, Subscription} from 'rxjs/Rx';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import {Division, DivisionService} from '../division';
import {ResponseWrapper} from '../../shared';
import {Curriculum} from '../curriculum';
import {CurriculumList} from './curriculum-list.model';
import {CurriculumListService} from './curriculum-list.service';
import {PeriodDialogComponent} from '../period/period-dialog.component';
import {PeriodPopupService} from '../period/period-popup.service';
import {CurriculumListPopupService} from './curriculum-list-popup.service';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {PeriodService} from '../period/period.service';

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

    divisionSelectOption = [];
    selectedDivision = [];
    divisionSelectSettings = {
        singleSelection: true,
        text: 'timetableApp.plan.choose.school',
        enableSearchFilter: true,
        classes: 'plan-select'
    };

    constructor(private alertService: JhiAlertService,
                private divisionService: DivisionService,
                private eventManager: JhiEventManager,
                private curriculumListService: CurriculumListService,
                private activeModal: NgbActiveModal) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.divisionService.query()
            .subscribe((res: ResponseWrapper) => {
                this.divisions = res.json;
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    load(id) {
        this.curriculumListService.find(id).subscribe((curriculumList) => {
            this.curriculumList = curriculumList;
        });
    }

    save() {
        this.isSaving = true;
        if (this.curriculumList.id !== undefined) {
            this.subscribeToSaveResponse(
                this.curriculumListService.update(this.curriculumList));
        } else {
            this.subscribeToSaveResponse(
                this.curriculumListService.create(this.curriculumList));
        }
    }

    removeCurriculumTime(curriculum: Curriculum) {
        const index: number = this.curriculumList.curriculums.indexOf(curriculum);
        if (index !== -1) {
            this.curriculumList.curriculums.splice(index, 1);
        }
    }

    addCurriculumTime() {
        this.curriculumList.curriculums.push(new Curriculum());
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

    trackDivisionById(index: number, item: Division) {
        return item.id;
    }

    onDivisionSelect(item: any) {
        console.log(item);
    }
    OnDivisionDeSelect(item: any) {
        console.log(item);
    }

    // TODO refctoring in plan is this same
    private entityListToSelectList(entityList: Division[]) {
        const selectList = [];
        entityList.forEach((entity) => {
            const obj = {id: entity.id, itemName: entity.name};
            selectList.push(obj)
        });
        return selectList;
    }

}
