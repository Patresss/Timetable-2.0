import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Period } from './period.model';
import { PeriodPopupService } from './period-popup.service';
import { PeriodService } from './period.service';
import { Division, DivisionService } from '../division';
import { ResponseWrapper } from '../../shared';
import {Interval} from '../interval/interval.model';

@Component({
    selector: 'jhi-period-popup',
    template: ''
})
export class PeriodPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private periodPopupService: PeriodPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.periodPopupService
                    .open(PeriodDialogComponent as Component, params['id']);
            } else {
                this.periodPopupService
                    .open(PeriodDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

@Component({
    selector: 'jhi-period-dialog',
    templateUrl: './period-dialog.component.html'
})
export class PeriodDialogComponent implements OnInit {

    period: Period;
    isSaving: boolean;

    divisions: Division[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private periodService: PeriodService,
        private divisionService: DivisionService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.divisionService.query()
            .subscribe((res: ResponseWrapper) => { this.divisions = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        console.log('status: ');
        if (this.period.id !== undefined) {
            this.subscribeToSaveResponse(
                this.periodService.update(this.period));
        } else {
            this.subscribeToSaveResponse(
                this.periodService.create(this.period));
        }
    }

    removeIntervalTime(interval: Interval) {
        const index: number = this.period.intervalTimes.indexOf(interval);
        if (index !== -1) {
            this.period.intervalTimes.splice(index, 1);
        }
    }

    addIntervalTime(included: boolean) {
        const interval = new Interval();
        interval.included = included;
        this.period.intervalTimes.push(interval);
    }

    intervalsExists(included: boolean) {
        for (const interval of this.period.intervalTimes) {
            if (interval.included === included) {
                return true;
            }
        }
        return false;
    }

    private subscribeToSaveResponse(result: Observable<Period>) {
        result.subscribe((res: Period) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Period) {
        this.eventManager.broadcast({ name: 'periodListModification', content: 'OK'});
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
}


