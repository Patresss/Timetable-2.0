import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import {CurriculumList} from './curriculum-list.model';
import {CurriculumListService} from './curriculum-list.service';

@Injectable()
export class CurriculumListPopupService {

    private ngbModalRef: NgbModalRef;

    static modifyDate(curriculum) {
        if (curriculum.startDate) {
            curriculum.startDate = {
                year: curriculum.startDate.getFullYear(),
                month: curriculum.startDate.getMonth() + 1,
                day: curriculum.startDate.getDate()
            };
        }
        if (curriculum.endDate) {
            curriculum.endDate = {
                year: curriculum.endDate.getFullYear(),
                month: curriculum.endDate.getMonth() + 1,
                day: curriculum.endDate.getDate()
            };
        }
    }

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private curriculumListService: CurriculumListService

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
                this.curriculumListService.find(id).subscribe((curriculumList) => {
                    curriculumList.curriculums.forEach((value) => {
                        CurriculumListPopupService.modifyDate(value);
                    });
                    this.ngbModalRef = this.curriculumListModalRef(component, curriculumList);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.curriculumListModalRef(component, new CurriculumList());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    curriculumListModalRef(component: Component, curriculumList: CurriculumList): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.curriculumList = curriculumList;
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
