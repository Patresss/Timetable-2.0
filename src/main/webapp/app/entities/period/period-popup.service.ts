import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Period } from './period.model';
import { PeriodService } from './period.service';

@Injectable()
export class PeriodPopupService {

    private ngbModalRef: NgbModalRef;

    static modifyDate(interval) {
        if (interval.startDate) {
            interval.startDate = {
                year: interval.startDate.getFullYear(),
                month: interval.startDate.getMonth() + 1,
                day: interval.startDate.getDate()
            };
        }
        if (interval.endDate) {
            interval.endDate = {
                year: interval.endDate.getFullYear(),
                month: interval.endDate.getMonth() + 1,
                day: interval.endDate.getDate()
            };
        }
    }

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private periodService: PeriodService

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
                this.periodService.find(id).subscribe((period) => {
                    period.intervalTimes.forEach((value) => {
                        PeriodPopupService.modifyDate(value);
                    });
                    this.ngbModalRef = this.periodModalRef(component, period);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.periodModalRef(component, new Period());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    periodModalRef(component: Component, period: Period): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.period = period;
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
