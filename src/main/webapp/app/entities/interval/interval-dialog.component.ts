import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Interval } from './interval.model';
import { IntervalPopupService } from './interval-popup.service';
import { IntervalService } from './interval.service';
import { Period, PeriodService } from '../period';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-interval-dialog',
    templateUrl: './interval-dialog.component.html'
})
export class IntervalDialogComponent implements OnInit {

    interval: Interval;
    isSaving: boolean;

    periods: Period[];
    startDateDp: any;
    endDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private intervalService: IntervalService,
        private periodService: PeriodService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.periodService.query()
            .subscribe((res: ResponseWrapper) => { this.periods = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.interval.id !== undefined) {
            this.subscribeToSaveResponse(
                this.intervalService.update(this.interval));
        } else {
            this.subscribeToSaveResponse(
                this.intervalService.create(this.interval));
        }
    }

    private subscribeToSaveResponse(result: Observable<Interval>) {
        result.subscribe((res: Interval) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Interval) {
        this.eventManager.broadcast({ name: 'intervalListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackPeriodById(index: number, item: Period) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-interval-popup',
    template: ''
})
export class IntervalPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private intervalPopupService: IntervalPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.intervalPopupService
                    .open(IntervalDialogComponent as Component, params['id']);
            } else {
                this.intervalPopupService
                    .open(IntervalDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
