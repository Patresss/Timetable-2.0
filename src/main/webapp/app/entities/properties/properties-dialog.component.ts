import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Properties } from './properties.model';
import { PropertiesPopupService } from './properties-popup.service';
import { PropertiesService } from './properties.service';
import { Division, DivisionService } from '../division';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-properties-dialog',
    templateUrl: './properties-dialog.component.html'
})
export class PropertiesDialogComponent implements OnInit {

    properties: Properties;
    isSaving: boolean;

    divisions: Division[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private propertiesService: PropertiesService,
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
        if (this.properties.id !== undefined) {
            this.subscribeToSaveResponse(
                this.propertiesService.update(this.properties));
        } else {
            this.subscribeToSaveResponse(
                this.propertiesService.create(this.properties));
        }
    }

    private subscribeToSaveResponse(result: Observable<Properties>) {
        result.subscribe((res: Properties) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Properties) {
        this.eventManager.broadcast({ name: 'propertiesListModification', content: 'OK'});
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

    trackDivisionById(index: number, item: Division) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-properties-popup',
    template: ''
})
export class PropertiesPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private propertiesPopupService: PropertiesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.propertiesPopupService
                    .open(PropertiesDialogComponent as Component, params['id']);
            } else {
                this.propertiesPopupService
                    .open(PropertiesDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
