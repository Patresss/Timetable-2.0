import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Place } from './place.model';
import { PlaceService } from './place.service';
import {Principal, ResponseWrapper} from '../../shared';
import {Teacher} from '../teacher/teacher.model';

@Injectable()
export class PlacePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private placeService: PlaceService,
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
                this.placeService.find(id).subscribe((place) => {
                    this.ngbModalRef = this.placeModalRef(component, place);
                    resolve(this.ngbModalRef);
                });
            } else {
                if (this.principal.isAuthenticated()) {
                    this.principal.identity().then((account) => {
                        this.placeService.calculateDefaultEntity(account.schoolId).subscribe((res) => {
                            this.ngbModalRef = this.placeModalRef(component, res);
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
        this.ngbModalRef = this.placeModalRef(component, new Place());
        resolve(this.ngbModalRef);
    }

    placeModalRef(component: Component, place: Place): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.place = place;
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
