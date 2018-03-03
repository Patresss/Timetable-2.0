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

@Component({
    selector: 'jhi-curriculum-list-popup',
    template: ''
})
export class CurriculumListPopupComponent implements OnInit, OnDestroy {

    curriculumList: CurriculumList;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(private eventManager: JhiEventManager,
                private curriculumListService: CurriculumListService,
                private route: ActivatedRoute) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCurriculumLists();
    }

    load(id) {
        this.curriculumListService.find(id).subscribe((curriculumList) => {
            this.curriculumList = curriculumList;
        });
    }

    registerChangeInCurriculumLists() {
        this.eventSubscriber = this.eventManager.subscribe(
            'curriculumListListModification',
            (response) => this.load(this.curriculumList.id)
        );
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }
}

@Component({
    selector: 'jhi-curriculum-list-dialog',
    templateUrl: './curriculum-list-dialog.component.html'
})
export class CurriculumListDialogComponent implements OnInit, OnDestroy {

    curriculumList: CurriculumList;
    private subscription: Subscription;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    divisions: Division[];

    constructor(private alertService: JhiAlertService,
                private divisionService: DivisionService,
                private eventManager: JhiEventManager,
                private curriculumListService: CurriculumListService,
                private route: ActivatedRoute) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.isSaving = false;
        this.divisionService.query()
            .subscribe((res: ResponseWrapper) => {
                this.divisions = res.json;
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.registerChangeInCurriculumLists();
    }

    registerChangeInCurriculumLists() {
        this.eventSubscriber = this.eventManager.subscribe(
            'curriculumListListModification',
            (response) => this.load(this.curriculumList.id)
        );
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
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

    startDateDp() {
    }

    endDateDp() {
    }

    clear() {
    }

    private subscribeToSaveResponse(result: Observable<CurriculumList>) {
        result.subscribe((res: CurriculumList) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: CurriculumList) {
        this.eventManager.broadcast({name: 'curriculumListListModification', content: 'OK'});
        this.isSaving = false;
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

}
