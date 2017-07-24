import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Timetable } from './timetable.model';
import { TimetablePopupService } from './timetable-popup.service';
import { TimetableService } from './timetable.service';

@Component({
    selector: 'jhi-timetable-delete-dialog',
    templateUrl: './timetable-delete-dialog.component.html'
})
export class TimetableDeleteDialogComponent {

    timetable: Timetable;

    constructor(
        private timetableService: TimetableService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.timetableService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'timetableListModification',
                content: 'Deleted an timetable'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-timetable-delete-popup',
    template: ''
})
export class TimetableDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private timetablePopupService: TimetablePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.timetablePopupService
                .open(TimetableDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
