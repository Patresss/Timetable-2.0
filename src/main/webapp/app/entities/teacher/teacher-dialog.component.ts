import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Response} from '@angular/http';

import {Observable} from 'rxjs/Rx';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import {Teacher} from './teacher.model';
import {TeacherPopupService} from './teacher-popup.service';
import {TeacherService} from './teacher.service';
import {Subject, SubjectService} from '../subject';
import {Division, DivisionService} from '../division';
import {Place, PlaceService} from '../place';
import {ResponseWrapper} from '../../shared';
import {Lesson, LessonService} from '../lesson';
import {SelectType} from '../../util/select-type.model';
import {DayOfWeek} from '../timetable/timetable.model';
import {PreferenceForDataTimeForTeacherModel} from '../../preference/preference-for-data-time-for-teacher.model';

@Component({
    selector: 'jhi-teacher-dialog',
    templateUrl: './teacher-dialog.component.html'
})
export class TeacherDialogComponent implements OnInit {

    isSaving: boolean;
    schoolId: number;
    teacher: Teacher;

    subjects: Subject[];
    lessons: Lesson[];
    divisions: Division[];
    places: Place[];

    preferenceDataTimeByLessonList = [];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private teacherService: TeacherService,
        private subjectService: SubjectService,
        private lessonService: LessonService,
        private divisionService: DivisionService,
        private placeService: PlaceService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.schoolId = this.teacher.divisionOwnerId;
        this.isSaving = false;
        this.subjectService.findByCurrentLogin()
            .subscribe((res: ResponseWrapper) => {
                this.subjects = res.json;
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.divisionService.query()
            .subscribe((res: ResponseWrapper) => {
                this.divisions = res.json;
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.placeService.findByCurrentLogin()
            .subscribe((res: ResponseWrapper) => {
                this.places = res.json;
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.lessonService.findByDivisionOwner([this.schoolId], {size: SelectType.MAX_INT_JAVA})
            .subscribe((res: ResponseWrapper) => {
                this.initLessons(res.json)
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    private initLessons(entityList: any[]) {
        this.lessons = entityList;
        this.lessons.forEach((preferredLesson) => this.preferenceDataTimeByLessonList.push({lesson: preferredLesson, preference: []}));

        this.initPreferenceDataTimeForTeachers();
    }

    private initPreferenceDataTimeForTeachers() {
        this.preferenceDataTimeByLessonList.forEach((preferenceDataTimeByLesson) => {
                for (const dayOfWeek of Object.keys(DayOfWeek)) {
                    const numberDayOfWeek = Number(dayOfWeek);
                    if (!isNaN(numberDayOfWeek)) {
                        const preferenceForDataTimeModals = this.teacher.preferenceDataTimeForTeachers
                                    .filter((preference) => preference.lessonId === preferenceDataTimeByLesson.lesson.id && preference.dayOfWeek === numberDayOfWeek);
                        let preferenceForDataTime;
                        if (preferenceForDataTimeModals.length > 0) {
                            preferenceForDataTime = preferenceForDataTimeModals[0];
                        } else {
                            preferenceForDataTime = new PreferenceForDataTimeForTeacherModel(null, this.teacher.id, preferenceDataTimeByLesson.lesson.id, numberDayOfWeek, 0);
                            this.teacher.preferenceDataTimeForTeachers.push(preferenceForDataTime);
                        }
                        preferenceDataTimeByLesson.preference[numberDayOfWeek] = preferenceForDataTime;
                    }
                }
            }
        );
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.teacher.id !== undefined) {
            this.subscribeToSaveResponse(
                this.teacherService.update(this.teacher));
        } else {
            this.subscribeToSaveResponse(
                this.teacherService.create(this.teacher));
        }
    }

    private subscribeToSaveResponse(result: Observable<Teacher>) {
        result.subscribe((res: Teacher) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Teacher) {
        this.eventManager.broadcast({name: 'teacherListModification', content: 'OK'});
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
    selector: 'jhi-teacher-popup',
    template: ''
})
export class TeacherPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private teacherPopupService: TeacherPopupService
    ) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['id']) {
                this.teacherPopupService
                    .open(TeacherDialogComponent as Component, params['id']);
            } else {
                this.teacherPopupService
                    .open(TeacherDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
