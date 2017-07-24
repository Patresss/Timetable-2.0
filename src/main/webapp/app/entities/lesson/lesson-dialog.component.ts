import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Lesson } from './lesson.model';
import { LessonPopupService } from './lesson-popup.service';
import { LessonService } from './lesson.service';
import { Division, DivisionService } from '../division';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-lesson-dialog',
    templateUrl: './lesson-dialog.component.html'
})
export class LessonDialogComponent implements OnInit {

    lesson: Lesson;
    isSaving: boolean;

    divisions: Division[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private lessonService: LessonService,
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
        if (this.lesson.id !== undefined) {
            this.subscribeToSaveResponse(
                this.lessonService.update(this.lesson));
        } else {
            this.subscribeToSaveResponse(
                this.lessonService.create(this.lesson));
        }
    }

    private subscribeToSaveResponse(result: Observable<Lesson>) {
        result.subscribe((res: Lesson) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Lesson) {
        this.eventManager.broadcast({ name: 'lessonListModification', content: 'OK'});
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
    selector: 'jhi-lesson-popup',
    template: ''
})
export class LessonPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private lessonPopupService: LessonPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.lessonPopupService
                    .open(LessonDialogComponent as Component, params['id']);
            } else {
                this.lessonPopupService
                    .open(LessonDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
