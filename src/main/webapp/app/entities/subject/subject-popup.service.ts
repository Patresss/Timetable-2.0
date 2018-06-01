import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Subject } from './subject.model';
import { SubjectService } from './subject.service';
import {Principal, ResponseWrapper} from '../../shared';
import {Teacher} from '../teacher/teacher.model';

@Injectable()
export class SubjectPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private subjectService: SubjectService,
        private principal: Principal

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.subjectService.find(id).subscribe((subject) => {
                    this.ngbModalRef = this.subjectModalRef(component, subject);
                    resolve(this.ngbModalRef);
                });
            } else {
                if (this.principal.isAuthenticated()) {
                    this.principal.identity().then((account) => {
                        this.subjectService.calculateDefaultEntity(account.schoolId).subscribe((res) => {
                            this.ngbModalRef = this.subjectModalRef(component, res);
                            resolve(this.ngbModalRef);
                        }, (res: ResponseWrapper) => this.loadEmptyEntity(component, resolve));

                    });
                } else {
                    this.loadEmptyEntity(component, resolve);
                }
            }
        });
    }

    private loadEmptyEntity(component: Component, resolve: any) {
        this.ngbModalRef = this.subjectModalRef(component, new Subject());
        resolve(this.ngbModalRef);
    }

    subjectModalRef(component: Component, subject: Subject): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.subject = subject;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
