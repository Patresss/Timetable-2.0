import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Period } from './period.model';
import { PeriodPopupService } from './period-popup.service';
import { PeriodService } from './period.service';

@Component({
    selector: 'jhi-period-delete-dialog',
    templateUrl: './period-delete-dialog.component.html'
})
export class PeriodDeleteDialogComponent {

    period: Period;

    constructor(
        private periodService: PeriodService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.periodService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'periodListModification',
                content: 'Deleted an period'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-period-delete-popup',
    template: ''
})
export class PeriodDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private periodPopupService: PeriodPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.periodPopupService
                .open(PeriodDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
