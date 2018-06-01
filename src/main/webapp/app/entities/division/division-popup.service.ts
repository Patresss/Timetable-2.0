import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Division } from './division.model';
import { DivisionService } from './division.service';
import {Principal, ResponseWrapper} from '../../shared';
import {Teacher} from '../teacher/teacher.model';

@Injectable()
export class DivisionPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private divisionService: DivisionService,
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
                this.divisionService.find(id).subscribe((division) => {
                    this.ngbModalRef = this.divisionModalRef(component, division);
                    resolve(this.ngbModalRef);
                });
            } else {
                if (this.principal.isAuthenticated()) {
                    this.principal.identity().then((account) => {
                        this.divisionService.calculateDefaultEntity(account.schoolId).subscribe((res) => {
                            this.ngbModalRef = this.divisionModalRef(component, res);
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
        this.ngbModalRef = this.divisionModalRef(component, new Division());
        resolve(this.ngbModalRef);
    }

    divisionModalRef(component: Component, division: Division): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.division = division;
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
