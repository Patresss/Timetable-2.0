import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Subject } from './subject.model';
import { SubjectPopupService } from './subject-popup.service';
import { SubjectService } from './subject.service';
import { Division, DivisionService } from '../division';
import { Teacher, TeacherService } from '../teacher';
import { Place, PlaceService } from '../place';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-subject-dialog',
    templateUrl: './subject-dialog.component.html'
})
export class SubjectDialogComponent implements OnInit {

    subject: Subject;
    isSaving: boolean;

    divisions: Division[];

    teachers: Teacher[];

    places: Place[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private subjectService: SubjectService,
        private divisionService: DivisionService,
        private teacherService: TeacherService,
        private placeService: PlaceService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.divisionService.query()
            .subscribe((res: ResponseWrapper) => { this.divisions = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.teacherService.query()
            .subscribe((res: ResponseWrapper) => { this.teachers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.placeService.query()
            .subscribe((res: ResponseWrapper) => { this.places = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.subject.id !== undefined) {
            this.subscribeToSaveResponse(
                this.subjectService.update(this.subject));
        } else {
            this.subscribeToSaveResponse(
                this.subjectService.create(this.subject));
        }
    }

    private subscribeToSaveResponse(result: Observable<Subject>) {
        result.subscribe((res: Subject) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Subject) {
        this.eventManager.broadcast({ name: 'subjectListModification', content: 'OK'});
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

    trackTeacherById(index: number, item: Teacher) {
        return item.id;
    }

    trackPlaceById(index: number, item: Place) {
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
    selector: 'jhi-subject-popup',
    template: ''
})
export class SubjectPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private subjectPopupService: SubjectPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.subjectPopupService
                    .open(SubjectDialogComponent as Component, params['id']);
            } else {
                this.subjectPopupService
                    .open(SubjectDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
