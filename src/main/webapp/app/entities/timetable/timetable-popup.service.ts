import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Timetable } from './timetable.model';
import { TimetableService } from './timetable.service';
import {Principal, ResponseWrapper} from '../../shared';
import {Place} from '../place/place.model';

@Injectable()
export class TimetablePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private timetableService: TimetableService,
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
                this.timetableService.find(id).subscribe((timetable) => {
                    if (timetable.date) {
                        timetable.date = {
                            year: timetable.date.getFullYear(),
                            month: timetable.date.getMonth() + 1,
                            day: timetable.date.getDate()
                        };
                    }
                    this.ngbModalRef = this.timetableModalRef(component, timetable);
                    resolve(this.ngbModalRef);
                });
            } else {
                const timetable = new Timetable();
                if (this.principal.isAuthenticated()) {
                    this.principal.identity().then((account) => {
                        timetable.divisionOwnerId = account.schoolId;
                        this.loadEmptyEntity(component, resolve, timetable)
                    });
                } else {
                    setTimeout(() => {
                        this.loadEmptyEntity(component, resolve, timetable)
                    }, 0);
                }
            }
        });
    }

    private loadEmptyEntity(component: Component, resolve: any, timetable: Timetable) {
        this.ngbModalRef = this.timetableModalRef(component, timetable);
        resolve(this.ngbModalRef);
    }

    timetableModalRef(component: Component, timetable: Timetable): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.timetable = timetable;
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
