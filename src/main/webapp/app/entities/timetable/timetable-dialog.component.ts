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
import {SelectType} from '../../util/select-type.model';

@Component({
    selector: 'jhi-timetable-dialog',
    templateUrl: './timetable-dialog.component.html'
})
export class TimetableDialogComponent implements OnInit {

    timetable: Timetable;
    isSaving: boolean;
    startDateDp: any;
    endDateDp: any;
    dateDp: any;

    eventTypeSelectOption = [
        new SelectType(1, '', 'timetableApp.EventType.LESSON', 'STUDENT'),
        new SelectType(2, '', 'timetableApp.EventType.SUBSTITUTION', 'SUBSTITUTION'),
        new SelectType(3, '', 'timetableApp.EventType.SPECIAL', 'SPECIAL')];
    selectedEventType: SelectType[] = [this.eventTypeSelectOption[0]];
    eventTypeSelectSettings = {
        singleSelection: true,
        text: 'timetableApp.plan.choose.event-type',
        enableSearchFilter: false
    };

    placeSelectOption = [];
    selectedPlace = [];
    placeSelectSettings = {
        singleSelection: true,
        text: 'timetableApp.plan.choose.place',
        enableSearchFilter: true
    };

    subjectSelectOption = [];
    selectedSubject = [];
    subjectSelectSettings = {
        singleSelection: true,
        text: 'timetableApp.plan.choose.subject',
        enableSearchFilter: true
    };

    teacherSelectOption = [];
    selectedTeacher = [];
    teacherSelectSettings = {
        singleSelection: true,
        text: 'timetableApp.plan.choose.teacher',
        enableSearchFilter: true
    };

    divisionSelectOption = [];
    selectedDivision = [];
    divisionSelectSettings = {
        singleSelection: true,
        text: 'timetableApp.plan.choose.division',
        enableSearchFilter: true
    };

    lessonSelectOption = [];
    selectedLesson = [];
    lessonSelectSettings = {
        singleSelection: true,
        text: 'timetableApp.plan.choose.lesson',
        enableSearchFilter: true
    };

    periodSelectOption = [];
    selectedPeriod = [];
    periodSelectSettings = {
        singleSelection: true,
        text: 'timetableApp.plan.choose.period',
        enableSearchFilter: true
    };

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
            .subscribe((res: ResponseWrapper) => { this.placeSelectOption = this.entityListToSelectList(res.json) }, (res: ResponseWrapper) => this.onError(res.json));
        this.subjectService.findByCurrentLogin()
            .subscribe((res: ResponseWrapper) => { this.subjectSelectOption = this.entityListToSelectList(res.json) }, (res: ResponseWrapper) => this.onError(res.json));
        this.teacherService.findByCurrentLogin()
            .subscribe((res: ResponseWrapper) => { this.teacherSelectOption = this.entityListToSelectList(res.json) }, (res: ResponseWrapper) => this.onError(res.json));
        this.divisionService.query()
            .subscribe((res: ResponseWrapper) => { this.divisionSelectOption = this.entityListToSelectList(res.json) }, (res: ResponseWrapper) => this.onError(res.json));
        this.lessonService.findByCurrentLogin()
            .subscribe((res: ResponseWrapper) => { this.lessonSelectOption = this.entityListToSelectList(res.json) }, (res: ResponseWrapper) => this.onError(res.json));
        this.periodService.findByCurrentLogin()
            .subscribe((res: ResponseWrapper) => { this.periodSelectOption = this.entityListToSelectList(res.json) }, (res: ResponseWrapper) => this.onError(res.json));
    }

    // TODO to samo w plan. Przneisć do wspólnej częśći
    private entityListToSelectList(entityList: any[]) {
        const selectList = [];
        entityList.forEach((entity) => {
            const obj = {id: entity.id, itemName: entity.name, item: entity};
            selectList.push(obj)
        });
        return selectList;
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
