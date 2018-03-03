import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import {CurriculumListPopupService} from './curriculum-list-popup.service';
import {CurriculumList} from './curriculum-list.model';
import {CurriculumListService} from './curriculum-list.service';

@Component({
    selector: 'jhi-curriculum-list-delete-dialog',
    templateUrl: './curriculum-list-delete-dialog.component.html'
})
export class CurriculumListDeleteDialogComponent {

    curriculumList: CurriculumList;

    constructor(
        private curriculumListService: CurriculumListService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.curriculumListService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'curriculumListListModification',
                content: 'Deleted an curriculumList'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-curriculum-list-delete-popup',
    template: ''
})
export class CurriculumListDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private curriculumListPopupService: CurriculumListPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.curriculumListPopupService
                .open(CurriculumListDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
