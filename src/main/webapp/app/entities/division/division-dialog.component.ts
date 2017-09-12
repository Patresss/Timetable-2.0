import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Division } from './division.model';
import { DivisionPopupService } from './division-popup.service';
import { DivisionService } from './division.service';
import { Place, PlaceService } from '../place';
import { Teacher, TeacherService } from '../teacher';
import { Subject, SubjectService } from '../subject';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-division-dialog',
    templateUrl: './division-dialog.component.html'
})
export class DivisionDialogComponent implements OnInit {

    division: Division;
    isSaving: boolean;

    divisions: Division[];

    users: User[];

    teachers: Teacher[];

    subjects: Subject[];

    places: Place[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private divisionService: DivisionService,
        private placeService: PlaceService,
        private teacherService: TeacherService,
        private subjectService: SubjectService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.divisionService.query()
            .subscribe((res: ResponseWrapper) => { this.divisions = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.teacherService.findByCurrentLogin()
            .subscribe((res: ResponseWrapper) => { this.teachers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.subjectService.findByCurrentLogin()
            .subscribe((res: ResponseWrapper) => { this.subjects = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.placeService.findByCurrentLogin()
            .subscribe((res: ResponseWrapper) => { this.places = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.division.id !== undefined) {
            this.subscribeToSaveResponse(
                this.divisionService.update(this.division));
        } else {
            this.subscribeToSaveResponse(
                this.divisionService.create(this.division));
        }
    }

    private subscribeToSaveResponse(result: Observable<Division>) {
        result.subscribe((res: Division) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Division) {
        this.eventManager.broadcast({ name: 'divisionListModification', content: 'OK'});
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

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackTeacherById(index: number, item: Teacher) {
        return item.id;
    }

    trackSubjectById(index: number, item: Subject) {
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
    selector: 'jhi-division-popup',
    template: ''
})
export class DivisionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private divisionPopupService: DivisionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.divisionPopupService
                    .open(DivisionDialogComponent as Component, params['id']);
            } else {
                this.divisionPopupService
                    .open(DivisionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
