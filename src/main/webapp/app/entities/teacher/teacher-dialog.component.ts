import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Response} from '@angular/http';

import {Observable} from 'rxjs/Rx';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import {Teacher} from './teacher.model';
import {TeacherPopupService} from './teacher-popup.service';
import {TeacherService} from './teacher.service';
import {Division, DivisionService} from '../division';
import {ResponseWrapper} from '../../shared';
import {DayOfWeek} from '../timetable/timetable.model';
import {Preference} from '../../preference/preferecne.model';

@Component({
    selector: 'jhi-teacher-dialog',
    templateUrl: './teacher-dialog.component.html'
})
export class TeacherDialogComponent implements OnInit {

    isSaving: boolean;
    schoolId: number;
    teacher: Teacher;

    divisions: Division[];

    preferenceSelectTypes = Preference.preferenceSelectTypes;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private teacherService: TeacherService,
        private divisionService: DivisionService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.schoolId = this.teacher.divisionOwnerId;
        this.isSaving = false;
        this.divisionService.query()
            .subscribe((res: ResponseWrapper) => {
                this.divisions = res.json;
            }, (res: ResponseWrapper) => this.onError(res.json));
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
