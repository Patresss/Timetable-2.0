import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Timetable } from './timetable.model';
import { TimetableService } from './timetable.service';

@Injectable()
export class TimetablePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private timetableService: TimetableService

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
                    if (timetable.startDate) {
                        timetable.startDate = {
                            year: timetable.startDate.getFullYear(),
                            month: timetable.startDate.getMonth() + 1,
                            day: timetable.startDate.getDate()
                        };
                    }
                    if (timetable.endDate) {
                        timetable.endDate = {
                            year: timetable.endDate.getFullYear(),
                            month: timetable.endDate.getMonth() + 1,
                            day: timetable.endDate.getDate()
                        };
                    }
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
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.timetableModalRef(component, new Timetable());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
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
