import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import {Curriculum} from './curriculum.model';
import { CurriculumPopupService } from './curriculum-popup.service';
import { CurriculumService } from './curriculum.service';
import { Place, PlaceService } from '../place';
import { Subject, SubjectService } from '../subject';
import { Teacher, TeacherService } from '../teacher';
import { Division, DivisionService } from '../division';
import { Lesson, LessonService } from '../lesson';
import { Period, PeriodService } from '../period';
import { ResponseWrapper } from '../../shared';
import {SelectType} from '../../util/select-type.model';
import {SelectUtil} from '../../util/select-util.model';
import {EventType} from '../timetable';
import {PreferenceDependency} from '../../preference/preferecne-dependency.model';
import {PreferenceService} from '../../preference/preference.service';
import {Preference} from '../../preference/preferecne.model';
import {PreferenceHierarchy} from '../../preference/preferecne-hierarchy.model';

@Component({
    selector: 'jhi-curriculum-dialog',
    templateUrl: './curriculum-dialog.component.html'
})
export class CurriculumDialogComponent implements OnInit {

    curriculum: Curriculum;
    isSaving: boolean;
    startDateDp: any;
    endDateDp: any;
    dateDp: any;
    series = false;
    schoolId: number;
    currentLoadCounter = 0;
    numberOfLoad = 3;

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

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private curriculumService: CurriculumService,
        private placeService: PlaceService,
        private subjectService: SubjectService,
        private teacherService: TeacherService,
        private divisionService: DivisionService,
        private lessonService: LessonService,
        private periodService: PeriodService,
        private eventManager: JhiEventManager,
        private preferenceService: PreferenceService
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        // this.placeService.findByCurrentLogin({size: SelectType.MAX_INT_JAVA, sort: ['name']})
        //     .subscribe((res: ResponseWrapper) => { this.initPlaces(res.json) }, (res: ResponseWrapper) => this.onError(res.json));
        this.subjectService.findByCurrentLogin({size: SelectType.MAX_INT_JAVA, sort: ['name']})
            .subscribe((res: ResponseWrapper) => { this.initSubjects(res.json) }, (res: ResponseWrapper) => this.onError(res.json));
        this.teacherService.findByCurrentLogin({size: SelectType.MAX_INT_JAVA, sort: ['surname,name']})
            .subscribe((res: ResponseWrapper) => { this.initTeachers(res.json) }, (res: ResponseWrapper) => this.onError(res.json));
        this.divisionService.findByCurrentLogin({size: SelectType.MAX_INT_JAVA, sort: ['name']})
            .subscribe((res: ResponseWrapper) => { this.initDivisions(res.json) }, (res: ResponseWrapper) => this.onError(res.json));
        // this.selectedEventType = this.eventTypeSelectOption.filter( (entity) => entity.value === this.curriculum.type)
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.curriculum.id !== undefined) {
            this.subscribeToSaveResponse(this.curriculumService.update(this.curriculum));
        } else {
            this.subscribeToSaveResponse(this.curriculumService.create(this.curriculum));
        }
    }

    private subscribeToSaveResponse(result: Observable<Curriculum>) {
        result.subscribe((res: Curriculum) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Curriculum) {
        this.eventManager.broadcast({ name: 'curriculumListModification', content: 'OK'});
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
        this.selectedPlace = this.placeSelectOption.filter( (entity) => entity.id === this.curriculum.placeId)
    }

    private initSubjects(entityList: any[]) {
        this.subjectSelectOption = SelectUtil.entityListToSelectList(entityList);
        this.selectedSubject = this.subjectSelectOption.filter( (entity) => entity.id === this.curriculum.subjectId);
        this.currentLoadCounter++;
        this.changePreferenceAfterLoadEntities()
    }
    private initLessons(entityList: any[]) {
        this.lessonSelectOption = SelectUtil.entityListToSelectList(entityList);
        this.selectedLesson = this.lessonSelectOption.filter( (entity) => entity.id === this.curriculum.lessonId)
    }

    private initTeachers(entityList: any[]) {
        this.teacherSelectOption = SelectUtil.teacherListToSelectList(entityList);
        this.selectedTeacher = this.teacherSelectOption.filter( (entity) => entity.id === this.curriculum.teacherId);
        this.currentLoadCounter++;
        this.changePreferenceAfterLoadEntities()

    }

    private initDivisions(entityList: any[]) {
        this.divisionSelectOption = SelectUtil.entityListToSelectList(entityList);
        this.selectedDivision = this.divisionSelectOption.filter( (entity) => entity.id === this.curriculum.divisionId);
        if (this.selectedDivision != null && this.selectedDivision[0] != null && this.selectedDivision[0].item != null) {
            this.schoolId = this.selectedDivision[0].item.divisionOwnerId;
        }
        this.currentLoadCounter++;
        this.changePreferenceAfterLoadEntities()
    }

 // ================================================================
// On select/deselect
// ================================================================
    onPlaceSelect(item: any) {
        this.curriculum.placeId = item.id;
        this.changePreference();
    }

    onPlaceDeSelect() {
        this.curriculum.placeId = null;
        this.changePreference();
    }

    onSubjectSelect(item: any) {
        this.curriculum.subjectId = item.id;
        this.changePreference();

    }

    onSubjectDeSelect() {
        this.curriculum.subjectId = null;
        this.changePreference();

    }

    onLessonSelect(item: any) {
        this.curriculum.lessonId = item.id;
        this.curriculum.startTime = item.item.startTime;
        this.curriculum.endTime = item.item.endTime;
    }

    onLessonDeSelect() {
        this.curriculum.lessonId = null;
    }

    onTeacherSelect(item: any) {
        this.curriculum.teacherId = item.id;
        this.changePreference();
    }

    onTeacherDeSelect() {
        this.curriculum.teacherId = null;
        this.changePreference();
    }

    onDivisionSelect(item: any) {
        this.curriculum.divisionId = item.id;
        this.schoolId = item.item.divisionOwnerId;
        this.changePreference();
    }

    onDivisionDeSelect() {
        this.curriculum.divisionId = null;
        this.schoolId = null;
        this.changePreference();
    }

    onEventTypeSelect(item: any) {
        this.curriculum.type = item.type;
    }

    onEventTypeDeSelect() {
        this.curriculum.type = null;
    }

// ================================================================
// Preferece
// ================================================================

    changePreference() {
        const preferenceDependency = new PreferenceDependency();
        preferenceDependency.divisionId = this.curriculum.divisionId;
        preferenceDependency.teacherId = this.curriculum.teacherId;
        preferenceDependency.subjectId = this.curriculum.subjectId;
        preferenceDependency.divisionOwnerId = this.schoolId;

        this.preferenceService.getPreferenceByPreferenceDependency(preferenceDependency).subscribe((response) => {
            this.updateSelectListesByPreference(response.json);
        });
    }

    updateSelectListesByPreference(preference: Preference) {
        this.updateSelectListsByPreference(preference.preferredTeacherMap, this.teacherSelectOption);
        this.teacherSelectSort(this.teacherSelectOption);
        this.updateSelectListsByPreference(preference.preferredSubjectMap, this.subjectSelectOption);
        this.entitySelectSort(this.subjectSelectOption);
        this.updateSelectListsByPreference(preference.preferredDivisionMap, this.divisionSelectOption);
        this.entitySelectSort(this.divisionSelectOption);

    }

    // TODO duplicat from timetable
    updateSelectListsByPreference(profferedMap: Map<number, PreferenceHierarchy>, selectOption: any) {
        selectOption.forEach(
            (selectOptionEntity) => selectOption
                .filter((entity) => selectOptionEntity.id !== entity.id)
                .forEach((entity) => entity.preferenceHierarchy = new PreferenceHierarchy())
        );
        profferedMap.forEach((value, key) => {
            selectOption.filter((entity) => key === entity.id).forEach((entity) => entity.preferenceHierarchy = value)
        });
    }

    // TODO duplicat from timetable
    entitySelectSort(selectOption: any) {
        selectOption.sort((a: any, b: any) => {
            if (b.preferenceHierarchy.points === a.preferenceHierarchy.points) {
                return a.itemName.localeCompare(b.itemName);
            }
            return b.preferenceHierarchy.points - a.preferenceHierarchy.points;
        });
    }

    // TODO duplicat from timetable
    teacherSelectSort(selectOption: any) {
        selectOption.sort((a: any, b: any) => {
            if (b.preferenceHierarchy.points === a.preferenceHierarchy.points) {
                if (a.item.surname === b.item.surname) {
                    return a.item.name.localeCompare(b.item.name)
                } else {
                    return a.item.surname.localeCompare(b.item.surname)
                }

            }
            return b.preferenceHierarchy.points - a.preferenceHierarchy.points
        });
    }

    private changePreferenceAfterLoadEntities() {
        if (this.currentLoadCounter >= this.numberOfLoad) {
            this.changePreference();
        }
    }
}

@Component({
    selector: 'jhi-curriculum-popup',
    template: ''
})
export class CurriculumPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private curriculumPopupService: CurriculumPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.curriculumPopupService
                    .open(CurriculumDialogComponent as Component, params['id']);
            } else {
                this.curriculumPopupService
                    .open(CurriculumDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
