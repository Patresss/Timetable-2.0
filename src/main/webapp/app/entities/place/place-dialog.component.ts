import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Place } from './place.model';
import { PlacePopupService } from './place-popup.service';
import { PlaceService } from './place.service';
import { Subject, SubjectService } from '../subject';
import { Division, DivisionService } from '../division';
import { Teacher, TeacherService } from '../teacher';
import { ResponseWrapper } from '../../shared';
import {DayOfWeek} from '../timetable/timetable.model';
import {Preference} from '../../preference/preferecne.model';

@Component({
    selector: 'jhi-place-dialog',
    templateUrl: './place-dialog.component.html'
})
export class PlaceDialogComponent implements OnInit {

    place: Place;
    isSaving: boolean;

    preferenceSelectTypes = Preference.preferenceSelectTypes;

    divisions: Division[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private placeService: PlaceService,
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
        if (this.place.id !== undefined) {
            this.subscribeToSaveResponse(
                this.placeService.update(this.place));
        } else {
            this.subscribeToSaveResponse(
                this.placeService.create(this.place));
        }
    }

    private subscribeToSaveResponse(result: Observable<Place>) {
        result.subscribe((res: Place) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Place) {
        this.eventManager.broadcast({ name: 'placeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackSubjectById(index: number, item: Subject) {
        return item.id;
    }

    trackDivisionById(index: number, item: Division) {
        return item.id;
    }

    trackTeacherById(index: number, item: Teacher) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-place-popup',
    template: ''
})
export class PlacePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private placePopupService: PlacePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.placePopupService
                    .open(PlaceDialogComponent as Component, params['id']);
            } else {
                this.placePopupService
                    .open(PlaceDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
