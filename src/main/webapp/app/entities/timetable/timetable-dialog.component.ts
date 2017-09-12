import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Timetable } from './timetable.model';
import { TimetablePopupService } from './timetable-popup.service';
import { TimetableService } from './timetable.service';
import { Place, PlaceService } from '../place';
import { Subject, SubjectService } from '../subject';
import { Teacher, TeacherService } from '../teacher';
import { Division, DivisionService } from '../division';
import { Lesson, LessonService } from '../lesson';
import { Period, PeriodService } from '../period';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-timetable-dialog',
    templateUrl: './timetable-dialog.component.html'
})
export class TimetableDialogComponent implements OnInit {

    timetable: Timetable;
    isSaving: boolean;

    places: Place[];

    subjects: Subject[];

    teachers: Teacher[];

    divisions: Division[];

    lessons: Lesson[];

    periods: Period[];
    startDateDp: any;
    endDateDp: any;
    dateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private timetableService: TimetableService,
        private placeService: PlaceService,
        private subjectService: SubjectService,
        private teacherService: TeacherService,
        private divisionService: DivisionService,
        private lessonService: LessonService,
        private periodService: PeriodService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.placeService.findByCurrentLogin()
            .subscribe((res: ResponseWrapper) => { this.places = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.subjectService.findByCurrentLogin()
            .subscribe((res: ResponseWrapper) => { this.subjects = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.teacherService.findByCurrentLogin()
            .subscribe((res: ResponseWrapper) => { this.teachers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.divisionService.query()
            .subscribe((res: ResponseWrapper) => { this.divisions = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.lessonService.findByCurrentLogin()
            .subscribe((res: ResponseWrapper) => { this.lessons = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.periodService.findByCurrentLogin()
            .subscribe((res: ResponseWrapper) => { this.periods = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.timetable.id !== undefined) {
            this.subscribeToSaveResponse(
                this.timetableService.update(this.timetable));
        } else {
            this.subscribeToSaveResponse(
                this.timetableService.create(this.timetable));
        }
    }

    private subscribeToSaveResponse(result: Observable<Timetable>) {
        result.subscribe((res: Timetable) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Timetable) {
        this.eventManager.broadcast({ name: 'timetableListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackPlaceById(index: number, item: Place) {
        return item.id;
    }

    trackSubjectById(index: number, item: Subject) {
        return item.id;
    }

    trackTeacherById(index: number, item: Teacher) {
        return item.id;
    }

    trackDivisionById(index: number, item: Division) {
        return item.id;
    }

    trackLessonById(index: number, item: Lesson) {
        return item.id;
    }

    trackPeriodById(index: number, item: Period) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-timetable-popup',
    template: ''
})
export class TimetablePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private timetablePopupService: TimetablePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.timetablePopupService
                    .open(TimetableDialogComponent as Component, params['id']);
            } else {
                this.timetablePopupService
                    .open(TimetableDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
