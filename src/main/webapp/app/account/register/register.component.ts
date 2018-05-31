import { Component, OnInit, AfterViewInit, Renderer, ElementRef } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import {JhiAlertService, JhiLanguageService} from 'ng-jhipster';

import { Register } from './register.service';
import {LoginModalService, ResponseWrapper} from '../../shared';
import {DivisionService} from '../../entities/division';
import {SelectUtil} from '../../util/select-util.model';
import {SelectType} from '../../util/select-type.model';
import {TeacherService} from '../../entities/teacher';

@Component({
    selector: 'jhi-register',
    templateUrl: './register.component.html'
})
export class RegisterComponent implements OnInit, AfterViewInit {

    confirmPassword: string;
    doNotMatch: string;
    error: string;
    errorEmailExists: string;
    errorUserExists: string;
    registerAccount: any;
    success: boolean;
    modalRef: NgbModalRef;

    registerNewSchool = false;

    schoolSelectOption = [];
    selectedSchool = [];
    schoolSelectSettings = {
        singleSelection: true,
        text: 'timetableApp.plan.choose.school',
        enableSearchFilter: true
    };

    teacherSelectOption = [];
    selectedTeacher = [];
    teacherSelectSettings = {
        singleSelection: true,
        text: 'timetableApp.plan.choose.teacher',
        enableSearchFilter: true
    };

    constructor(
        private languageService: JhiLanguageService,
        private loginModalService: LoginModalService,
        private registerService: Register,
        private elementRef: ElementRef,
        private renderer: Renderer,
        private alertService: JhiAlertService,
        private teacherService: TeacherService,
        private divisionService: DivisionService
    ) {
    }

    loadAll() {
    }

    private onSuccessSchool(data) {
        this.schoolSelectOption = SelectUtil.entityListToSelectList(data);
    }

    private onSuccessTeacher(data) {
        this.teacherSelectOption = SelectUtil.teacherListToSelectList(data);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    ngOnInit() {
        this.success = false;
        this.registerAccount = {};
        this.divisionService.findByDivisionType('SCHOOL').subscribe(
            (res: ResponseWrapper) => this.onSuccessSchool(res.json),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    onSchoolSelect(item: any) {
        this.registerAccount.schoolId = item.id;
        this.loadTeacher();
    }

    onSchoolDeSelect() {
        this.registerAccount.schoolId = null;
        this.teacherSelectOption = [];
        this.registerAccount.teacherId = null;
        this.selectedTeacher = []
    }

    onTeacherSelect(item: any) {
        this.registerAccount.teacherId = item.id;
        this.loadTeacher();
    }

    onTeacherDeSelect() {
        this.registerAccount.teacherId = null;

    }

    loadTeacher() {
        this.teacherService.findByDivisionOwner(this.registerAccount.schoolId, {size: SelectType.MAX_INT_JAVA, sort: ['surname,name']}).subscribe(
            (res: ResponseWrapper) => this.onSuccessTeacher(res.json),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    ngAfterViewInit() {
        this.renderer.invokeElementMethod(this.elementRef.nativeElement.querySelector('#login'), 'focus', []);
    }

    register() {
        if (this.registerAccount.password !== this.confirmPassword) {
            this.doNotMatch = 'ERROR';
        } else {
            this.doNotMatch = null;
            this.error = null;
            this.errorUserExists = null;
            this.errorEmailExists = null;
            this.languageService.getCurrent().then((key) => {
                this.registerAccount.langKey = key;
                this.registerService.save(this.registerAccount).subscribe(() => {
                    this.success = true;
                }, (response) => this.processError(response));
            });
        }
    }

    openLogin() {
        this.modalRef = this.loginModalService.open();
    }

    private processError(response) {
        this.success = null;
        if (response.status === 400 && response._body === 'login already in use') {
            this.errorUserExists = 'ERROR';
        } else if (response.status === 400 && response._body === 'email address already in use') {
            this.errorEmailExists = 'ERROR';
        } else {
            this.error = 'ERROR';
        }
    }

}
