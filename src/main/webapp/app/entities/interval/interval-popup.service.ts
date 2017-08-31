import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Interval } from './interval.model';
import { IntervalService } from './interval.service';

@Injectable()
export class IntervalPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private intervalService: IntervalService

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
                this.intervalService.find(id).subscribe((interval) => {
                    this.modifyDate(interval);
                    this.ngbModalRef = this.intervalModalRef(component, interval);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.intervalModalRef(component, new Interval());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    modifyDate(interval) {
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

    intervalModalRef(component: Component, interval: Interval): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.interval = interval;
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
