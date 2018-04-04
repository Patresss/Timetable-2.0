import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Response} from '@angular/http';

import {Observable} from 'rxjs/Rx';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import {EventType, Timetable} from './timetable.model';
import {TimetablePopupService} from './timetable-popup.service';
import {TimetableService} from './timetable.service';
import {PlaceService} from '../place';
import {SubjectService} from '../subject';
import {TeacherService} from '../teacher';
import {DivisionService} from '../division';
import {LessonService} from '../lesson';
import {PeriodService} from '../period';
import {ResponseWrapper} from '../../shared';
import {SelectType} from '../../util/select-type.model';
import {SelectUtil} from '../../util/select-util.model';
import {PreferenceService} from '../../preference/preference.service';
import {PreferenceDependency} from '../../preference/preferecne-dependency.model';
import {Preference} from '../../preference/preferecne.model';
import {PreferenceHierarchy} from '../../preference/preferecne-hierarchy.model';

@Component({
    selector: 'jhi-timetable-dialog',
    templateUrl: './timetable-dialog.component.html'
})
export class TimetableDialogComponent implements OnInit {

    timetable: Timetable;
    isSaving: boolean;
    series = false;
    dateDp: any;

    eventTypeSelectOption = [
        new SelectType(1, '', 'timetableApp.EventType.LESSON', EventType.LESSON),
        new SelectType(2, '', 'timetableApp.EventType.SUBSTITUTION', EventType.SUBSTITUTION),
        new SelectType(3, '', 'timetableApp.EventType.SPECIAL', EventType.SPECIAL)];
    selectedEventType: SelectType[] = [];
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
        private preferenceService: PreferenceService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.placeService.findByCurrentLogin({size: SelectType.MAX_INT_JAVA, sort: ['name']})
            .subscribe((res: ResponseWrapper) => {
                this.initPlaces(res.json)
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.subjectService.findByCurrentLogin({size: SelectType.MAX_INT_JAVA, sort: ['name']})
            .subscribe((res: ResponseWrapper) => {
                this.initSubjects(res.json)
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.teacherService.findByCurrentLogin({size: SelectType.MAX_INT_JAVA, sort: ['surname,name']})
            .subscribe((res: ResponseWrapper) => {
                this.initTeachers(res.json)
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.divisionService.findByCurrentLogin({size: SelectType.MAX_INT_JAVA, sort: ['name']})
            .subscribe((res: ResponseWrapper) => {
                this.initDivisions(res.json)
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.lessonService.findByCurrentLogin({size: SelectType.MAX_INT_JAVA, sort: ['name']})
            .subscribe((res: ResponseWrapper) => {
                this.initLessons(res.json)
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.periodService.findByCurrentLogin({size: SelectType.MAX_INT_JAVA, sort: ['name']})
            .subscribe((res: ResponseWrapper) => {
                this.initPeriods(res.json)
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.selectedEventType = this.eventTypeSelectOption.filter((entity) => entity.type === this.timetable.type)
        this.changePreference();
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.timetable.id !== undefined) {
            this.subscribeToSaveResponse(this.timetableService.update(this.timetable));
        } else {
            this.subscribeToSaveResponse(this.timetableService.create(this.timetable));
        }
    }

    private subscribeToSaveResponse(result: Observable<Timetable>) {
        result.subscribe((res: Timetable) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Timetable) {
        this.eventManager.broadcast({name: 'timetableListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    changeTaken() {

    }

// ================================================================
// Init select
// ================================================================
    private initPlaces(entityList: any[]) {
        this.placeSelectOption = SelectUtil.entityListToSelectList(entityList);
        console.log(this.placeSelectOption)
        this.selectedPlace = this.placeSelectOption.filter((entity) => entity.id === this.timetable.placeId)
    }

    private initSubjects(entityList: any[]) {
        this.subjectSelectOption = SelectUtil.entityListToSelectList(entityList);
        this.selectedSubject = this.subjectSelectOption.filter((entity) => entity.id === this.timetable.subjectId)
    }

    private initLessons(entityList: any[]) {
        this.lessonSelectOption = SelectUtil.entityListToSelectList(entityList);
        this.selectedLesson = this.lessonSelectOption.filter((entity) => entity.id === this.timetable.lessonId)
    }

    private initTeachers(entityList: any[]) {
        this.teacherSelectOption = SelectUtil.teacherListToSelectList(entityList);
        this.selectedTeacher = this.teacherSelectOption.filter((entity) => entity.id === this.timetable.teacherId)
    }

    private initPeriods(entityList: any[]) {
        this.periodSelectOption = SelectUtil.entityListToSelectList(entityList);
        this.selectedPeriod = this.periodSelectOption.filter((entity) => entity.id === this.timetable.periodId)
    }

    private initDivisions(entityList: any[]) {
        this.divisionSelectOption = SelectUtil.entityListToSelectList(entityList);
        this.selectedDivision = this.divisionSelectOption.filter((entity) => entity.id === this.timetable.divisionId)
    }

    // ================================================================
// On select/deselect
// ================================================================
    onPlaceSelect(item: any) {
        this.timetable.placeId = item.id;
        this.changePreference()
    }

    onPlaceDeSelect() {
        this.timetable.placeId = null;
        this.changePreference()
    }

    onSubjectSelect(item: any) {
        this.timetable.subjectId = item.id;
        this.changePreference()
    }

    onSubjectDeSelect() {
        this.timetable.subjectId = null;
        this.changePreference()
    }

    onLessonSelect(item: any) {
        this.timetable.lessonId = item.id;
        this.timetable.startTime = item.item.startTime;
        this.timetable.endTime = item.item.endTime;
        this.changePreference()
    }

    onLessonDeSelect() {
        this.timetable.lessonId = null;
        this.changePreference()
    }

    onTeacherSelect(item: any) {
        this.timetable.teacherId = item.id;
        this.changePreference()
    }

    onTeacherDeSelect() {
        this.timetable.teacherId = null;
        this.changePreference()
    }

    onPeriodSelect(item: any) {
        this.timetable.periodId = item.id;
        this.changePreference()
    }

    onPeriodDeSelect() {
        this.timetable.periodId = null;
        this.changePreference()
    }

    onDivisionSelect(item: any) {
        this.timetable.divisionId = item.id;
        this.changePreference()
    }

    onDivisionDeSelect() {
        this.timetable.divisionId = null;
        this.changePreference()
    }

    onEventTypeSelect(item: any) {
        this.timetable.type = item.type;
    }

    onEventTypeDeSelect() {
        this.timetable.type = null;
    }

    changePreference() {
        const preferenceDependency = new PreferenceDependency();
        preferenceDependency.divisionId = this.timetable.divisionId;
        preferenceDependency.teacherId = this.timetable.teacherId;
        preferenceDependency.lessonId = this.timetable.lessonId;
        preferenceDependency.subjectId = this.timetable.subjectId;
        preferenceDependency.placeId = this.timetable.placeId;
        // TODO dorobić reszte
        this.preferenceService.getPreferenceByPreferenceDependency(preferenceDependency).subscribe((response) => {
            this.updateSelectListesByPreference(response.json);
        });
    }

    updateSelectListesByPreference(preference: Preference) {
        this.updateSelectListsByPreference(preference.preferredPlaceMap, this.placeSelectOption);
        this.entitySelectSort(this.placeSelectOption);
        this.updateSelectListsByPreference(preference.preferredTeacherMap, this.teacherSelectOption);
        this.teacherSelectSort(this.teacherSelectOption);
        this.updateSelectListsByPreference(preference.preferredSubjectMap, this.subjectSelectOption);
        this.entitySelectSort(this.subjectSelectOption);
        this.updateSelectListsByPreference(preference.preferredDivisionMap, this.divisionSelectOption);
        this.entitySelectSort(this.divisionSelectOption);
    }

    updateSelectListsByPreference(profferedMap: Map<number, PreferenceHierarchy>, selectOption: any) {
        profferedMap.forEach((value, key) => {
            selectOption.filter((entity) => key === entity.id).forEach((entity) => entity.preferenceHierarchy = value)
        });
    }

    entitySelectSort(selectOption: any) {
        selectOption.sort((a: any, b: any) => {
            if (b.preferenceHierarchy.points === a.preferenceHierarchy.points) {
                return a.itemName - b.itemName
            }
            return b.preferenceHierarchy.points - a.preferenceHierarchy.points
        });
    }

    teacherSelectSort(selectOption: any) {
        selectOption.sort((a: any, b: any) => {
            if (b.preferenceHierarchy.points === a.preferenceHierarchy.points) {
                if (a.item.surname === b.item.surname) {
                    return a.item.name - b.item.name
                } else {
                    return a.item.surname - b.item.surname
                }

            }
            return b.preferenceHierarchy.points - a.preferenceHierarchy.points
        });
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
    ) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['id']) {
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
