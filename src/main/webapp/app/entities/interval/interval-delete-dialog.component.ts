import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Interval } from './interval.model';
import { IntervalPopupService } from './interval-popup.service';
import { IntervalService } from './interval.service';

@Component({
    selector: 'jhi-interval-delete-dialog',
    templateUrl: './interval-delete-dialog.component.html'
})
export class IntervalDeleteDialogComponent {

    interval: Interval;

    constructor(
        private intervalService: IntervalService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.intervalService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'intervalListModification',
                content: 'Deleted an interval'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-interval-delete-popup',
    template: ''
})
export class IntervalDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private intervalPopupService: IntervalPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.intervalPopupService
                .open(IntervalDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
