import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Properties } from './properties.model';
import { PropertiesPopupService } from './properties-popup.service';
import { PropertiesService } from './properties.service';

@Component({
    selector: 'jhi-properties-delete-dialog',
    templateUrl: './properties-delete-dialog.component.html'
})
export class PropertiesDeleteDialogComponent {

    properties: Properties;

    constructor(
        private propertiesService: PropertiesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.propertiesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'propertiesListModification',
                content: 'Deleted an properties'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-properties-delete-popup',
    template: ''
})
export class PropertiesDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private propertiesPopupService: PropertiesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.propertiesPopupService
                .open(PropertiesDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
